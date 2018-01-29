package com.iandtop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iandtop.model.form.ChargeForSModel;
import com.iandtop.model.meal.MealRecordModel;
import com.iandtop.utils.IandtopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iandtop.dao.AllowanceCardDao;
import com.iandtop.dao.CardChargeDAO;
import com.iandtop.dao.CardDAO;
import com.iandtop.model.card.CardChargeRecordModel;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.meal.MealAllowanceModel;
import com.iandtop.model.meal.MealAllowanceNumModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.AllowanceCardService;
import com.iandtop.utils.DateUtils;


@Service
@Transactional
public class AllowanceCardServiceimpl implements AllowanceCardService{

	@Autowired
    private AllowanceCardDao allowanceCardDao;

/*	@Autowired
	private CardDAO cardDAO;
	*/
	@Autowired
    private CardChargeDAO cardChargeDAO;
	@Autowired
	private CardDAO cardDAO;
	
	@Override
	public List<CardModel> retrieveAllWithPage(CardModel vo) {
		List<CardModel> result = null;

        if(vo.getDepartment_code() == null){
            return new ArrayList<CardModel>();
        }
        //查询状态等于10的记录 
        result = allowanceCardDao.retrieveAllNormalWithPage(vo);
        return result;
	}
//补贴方法service实现
	@Override
	public int allowanceSend(String ids,String operator) {
		int result;//返回标志
		MealAllowanceModel vo = new MealAllowanceModel();//明细实例类
		vo.setIds(ids);//设置前来的参数
		//根据前台选中的补贴批次IDS，用in查询出所有的明细表
		List<MealAllowanceModel> list = allowanceCardDao.queryMealAllowanceModelData(vo);

		//******************************************
		//add by LZ   修改补贴下发时充值表中卡次的问题    2017-7-6 10:54:21
		CardModel cardModel;
		//******************************************


		//循环明细表，批量更改卡表补贴金额
		for(MealAllowanceModel mod : list){
			//更新卡表补贴金额
			result = allowanceCardDao.updateByVO(mod);
			if(result < 1){//如果发生错误退出
	            return StatusCodeConstants.Fail;
	        }

			//******************************************
			//add by LZ  修改补贴下发时充值表中卡次的问题    2017-7-6 10:54:21
			cardModel = cardDAO.retrieveByPk(mod.getPk_card());
			if(cardModel == null){
				return StatusCodeConstants.Fail;
			}
			//******************************************

			//取当前时间
			String currentTime = DateUtils.currentDatetime();
			//插入补贴记录表记录
			CardChargeRecordModel rec = new CardChargeRecordModel();//补贴记录表实例类
			rec.setPk_card(mod.getPk_card());//卡ID
			rec.setCard_code(mod.getCard_code());//卡号
			rec.setCharge_money(mod.getMoney_allowance());//金额

			if(MealAllowanceModel.MealAllowacne_Type_Clear.equals(mod.getAllowance_type())){
				rec.setMoney_retain(mod.getWallet_allowance()+mod.getMoney_cash());//充值后总余额
			}else{
				rec.setMoney_retain(mod.getMoney_allowance()*100+mod.getWallet_allowance()+mod.getMoney_cash());//充值后总余额
			}

			rec.setOperator(operator);//操作人
			rec.setPk_staff(mod.getPk_staff()+"");//员工id
			rec.setStaff_code(mod.getStaff_code());//员工编号
			rec.setCharge_ts(currentTime);//当前时间

			//******************************************
			//add by LZ  修改补贴下发时充值表中卡次的问题    2017-7-6 10:56:37
			rec.setCard_batchnum(cardModel.getSerial());
			//******************************************

			//charge_type3清零补贴 4累加补贴
			//明细表0是累加，1是补贴
			//0?"4":"3"
			rec.setCharge_type(mod.getAllowance_type()==MealAllowanceModel.MealAllowacne_Type_Add ? MealRecordModel.Charge_Type_Add : MealRecordModel.Charge_Type_Clear);
			result = allowanceCardDao.saveByVO(rec);//存入数据库
			if(result < 1){//如果发生错误退出
	            return StatusCodeConstants.Fail;
	        }
			//更新补贴批次头表，状态为已发放
			MealAllowanceNumModel bean = new MealAllowanceNumModel();
			bean.setState(MealAllowanceNumModel.Allowance_State_Already_Grant);//已发放状态为2
			bean.setTs(currentTime);//当前时间
			bean.setPk_allowance_num(mod.getPk_allowance_num());//补贴批次ID
			//数据库修改
			result = allowanceCardDao.updateMealAllowanceNum(bean);
			if(result < 1){//如果发生错误退出
	            return StatusCodeConstants.Fail;
	        }
		}
        return StatusCodeConstants.Success;
	}
//插入补贴明细
	@Override
	public int insertMealAllowanceNum(List<CardChargeRecordModel> vos) {
		//Sring 
		//生成批次号格式:日期+流水号 如:2017060001
		int result;
		
		String currentTime = DateUtils.currentDatetime();
	
		//取当期操作人:
		String operator = vos.get(0).getOperator();
		//插入头表
		long batch_num_id = insertBatchNum(operator);
		if(batch_num_id==0){
			throw new IandtopException("插入补贴明细失败","0");
		}
        // 插入明细表
        MealAllowanceModel bean = null;
		for (CardChargeRecordModel vo : vos) {
			bean = new MealAllowanceModel();
			//1:0
			bean.setAllowance_type(vo.getIsChecked().equals("true")?MealAllowanceModel.MealAllowacne_Type_Clear : MealAllowanceModel.MealAllowacne_Type_Add);//是否清零
			bean.setPk_staff(Integer.parseInt(vo.getPk_staff()));
			bean.setPk_allowance_num(batch_num_id);
			bean.setPk_card(vo.getPk_card());
			bean.setTs(currentTime);
			bean.setMoney_allowance(vo.getCharge_money());
			result = allowanceCardDao.insertMealAllowance(bean);
			if(result < 1){
				throw new IandtopException("0","插入补贴明细表失败");
	        }
		}
		return StatusCodeConstants.Success;
	}

	@Override
	public List<MealAllowanceNumModel> queryMainData(MealAllowanceNumModel vo) {
		List<MealAllowanceNumModel> result = null;

        /*if(vo.getDepartment_code() == null){
            return new ArrayList<CardModel>();
        }*/
        result = allowanceCardDao.queryMealAllowanceNumModelData(vo);
        return result;
	}

	@Override
	public List<MealAllowanceModel> queryDeatilData(MealAllowanceModel vo) {
		List<MealAllowanceModel> result = null;
        result = allowanceCardDao.queryMealAllowanceModelData(vo);
        return result;
	}

	@Override
	public PageInfo<MealAllowanceModel> queryDeatilDataByPage(MealAllowanceModel vo, Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		PageHelper.startPage(pageNo, pageSize);

		List<MealAllowanceModel> annlist = allowanceCardDao.queryMealAllowanceModelData(vo);
		List<MealAllowanceModel> list = annlist;
		PageInfo<MealAllowanceModel> page = new PageInfo<MealAllowanceModel>(list);
		return page;
	}

	@Override
	public List<MealAllowanceModel> queryDeatilDataSum(MealAllowanceModel vo) {
		return allowanceCardDao.queryDeatilDataSum(vo);
	}

	// TODO Auto-generated method stub
	@Override
	//补贴删除方法service实现
	public int deleteRecord(String ids) {
		int num1=0,num2=0;//用来记录操作数
		//实例化meal_allowance_num表实体类的对象
		MealAllowanceNumModel manm=new MealAllowanceNumModel();
		//设置前来的参数，根据前台选中的补贴批次IDS，用in删除出所有的选中的记录
		manm.setIds(ids);
		//实例化明细表实体类的对象
		MealAllowanceNumModel mam=new MealAllowanceNumModel();
		//设置前来的参数，根据前台选中的补贴批次IDS，用in删除出所有的选中的对应的明细表记录
		mam.setIds(ids);
		num1=allowanceCardDao.deleteByAllowanceNumIds(manm);
		num2=allowanceCardDao.deleteByAllowanceIds(mam);
		return num1;
	}
	//插入头表
	public long insertBatchNum(String operator){
		int result;
		String currentDate = DateUtils.formatDatetime(new Date(), "yyyyMMdd");
		//String currentTime = com.iandtop.utils.DateUtils.currentDatetime();
		String currentTime = DateUtils.currentDatetime();
		String max = allowanceCardDao.getMaxAllowanceNumCode(currentDate);
		long bathNo = 0;
		if(max != null && !"0".equals(max)){
			bathNo = Long.parseLong(max)+1;
		}else{
			bathNo = Long.parseLong(currentDate+"0001");
		}
		MealAllowanceNumModel model = new MealAllowanceNumModel();
		model.setAllowance_num_code(bathNo);
		model.setState(MealAllowanceNumModel.Allowance_State_No_Grant);//0 未发放 2 已发放
		model.setTs(currentTime);//登记时间
		//取当期操作人
		//String operator = vos.getOperator();
		model.setPk_staff(Long.parseLong(operator));
		result = allowanceCardDao.insertMealAllowanceNum(model);
        if(result < 1){
            return 0;
        }
        return model.getPk_allowance_num();
	}
	//导入
	@Override
	public int save(MealAllowanceModel vos,long batch_num_id) {
		//Sring 
				
				int result;
				String currentTime = DateUtils.currentDatetime();
		        // 插入明细表
		        MealAllowanceModel bean = null;
					bean = new MealAllowanceModel();
					//查出工号所对应的员工
					CardModel cd=cardDAO.retrieveByPk(vos.getPk_card());
					bean.setAllowance_type(vos.getAllowance_type());//是否清零
					bean.setPk_staff(Integer.parseInt(cd.getPk_staff()));
					bean.setPk_allowance_num(batch_num_id);
					bean.setPk_card(vos.getPk_card());
					bean.setTs(currentTime);
					bean.setMoney_allowance(vos.getMoney_allowance());
					result = allowanceCardDao.insertMealAllowance(bean);
					if(result < 1){
			            return 0;
			        }
				return result;
			}


}

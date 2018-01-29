package com.iandtop.controller;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.iandtop.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iandtop.model.card.CardChargeRecordModel;
import com.iandtop.model.card.CardModel;
import com.iandtop.model.meal.MealAllowanceModel;
import com.iandtop.model.meal.MealAllowanceNumModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.AllowanceCardService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.MD5;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import com.iandtop.utils.excel.ExcelDataFormatter;
import com.iandtop.utils.excel.ExcelUtils;
import com.iandtop.utils.excel.MealAllowanceExcel;
import com.iandtop.utils.excel.MealAllowanceNumExcel;

@Controller
@RequestMapping("/AllowanceCard")
public class AllowanceCardController {

	@Autowired
	private AllowanceCardService service;
	@Autowired
	private CardService cardService;
	// 根据部门查询查询状态为10的人
	@ResponseBody
	@RequestMapping(value="/retrieve")
	public String retrieve(CardModel vo, HttpServletRequest request) {

		List<CardModel> result = service.retrieveAllWithPage(vo);

		/// List<CardModel> resultAll = service.retrieveAllWithPageCount(vo);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total",9999);
		jsonObject.put("rows",JSONArray.toJSON(result));

		return JSONArray.toJSON(jsonObject).toString();
	}
	//补贴发放方法
	@ResponseBody
	@RequestMapping("/butieSend")
	public APIRestResponse butieSend(@RequestParam(value="ids",required=false) String ids,
									 @RequestParam(value="operator",required=false) String operator) {
		//前台获取选中的补贴批次ID,如果没有直接报错，不要往下走了
		//格式为为字符串，逗号拼接的形式:118,119,120
		if(ids==null || "".equals(ids)){
			return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.SAVE_DATA);
		}
		Integer num;
		//补贴发放service
		num = service.allowanceSend(ids,operator);
		if(num == StatusCodeConstants.Fail){
			return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.INSERT_DATA);
		}
		return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.INSERT_DATA);

	}
	//补贴登记方法
	@ResponseBody
	@RequestMapping("/butiecharge")
	public APIRestResponse batchcharge(@RequestBody List<CardChargeRecordModel> vos) {
		Integer num;
		//插入补贴头表记录//插入补贴明细表记录
		num = service.insertMealAllowanceNum(vos);
		if(num == StatusCodeConstants.Fail){
			return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.INSERT_DATA);
		}

		return ResponseUtils.getSuccessAPI(true,"true", RestOperateCode.INSERT_DATA);

	}
	//查询头部表格
	@ResponseBody
	@RequestMapping(value="/queryMainData")
	public String queryMainData(MealAllowanceNumModel vo, HttpServletRequest request) {

		List<MealAllowanceNumModel> result = service.queryMainData(vo);

		//  List<CardModel> resultAll = service.queryMainDataCount(vo);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total",9999);
		jsonObject.put("rows",JSONArray.toJSON(result));

		return JSONArray.toJSON(jsonObject).toString();
	}

	//查询明细表格
	@ResponseBody
	@RequestMapping(value="/queryDeatilData")
	public String queryDeatilData(MealAllowanceModel vo, HttpServletRequest request) {

		List<MealAllowanceModel> result = service.queryDeatilData(vo);//方法自己实现

		// List<CardModel> resultAll = service.queryDeatilDataCount(vo);//方法自己实现

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total",9999);
		jsonObject.put("rows",JSONArray.toJSON(result));

		return JSONArray.toJSON(jsonObject).toString();
	}

	//new查询明细表格
	@ResponseBody
	@RequestMapping(value="/newqueryDeatilData")
	public APIRestResponse newqueryDeatilData(MealAllowanceModel vo, Integer pageNo,Integer pageSize, HttpServletResponse response)throws ParseException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		PageInfo<MealAllowanceModel> result = service.queryDeatilDataByPage(vo,pageNo,pageSize);//方法自己实现
		if (result.getSize() > 0)
			return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, result);
		else
			return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);

	}
	//new查询明细补贴汇总
	@ResponseBody
	@RequestMapping(value="/queryDeatilDataSum")
	public APIRestResponse queryDeatilDataSum(MealAllowanceModel vo, HttpServletResponse response)throws ParseException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<MealAllowanceModel> result = service.queryDeatilDataSum(vo);
		if (result.size() > 0)
			return ResponseUtils.getSuccess(true, "查询成功", RestOperateCode.GET_DATA, result);
		else
			return ResponseUtils.getFailed("", "查询失败", RestOperateCode.GET_DATA);

	}

	@ResponseBody
	@RequestMapping("/deleteRecord")
	public APIRestResponse deleteRecord(@RequestParam(value="ids",required=false) String ids) {
		if(ids==null || "".equals(ids)){
			return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.SAVE_DATA);
		}
		Integer num;
		//补贴发放service
		num = service.deleteRecord(ids);
		if(num == 0){
			return ResponseUtils.getSuccessAPI(false,"false", RestOperateCode.INSERT_DATA);
		}
		return ResponseUtils.getSuccessAPI(true,num.toString(), RestOperateCode.INSERT_DATA);

	}
	//导入excel的方法
	@ResponseBody
	@RequestMapping("ULE")
	public APIRestResponse ule(@RequestParam MultipartFile file,@RequestParam(value="operator",required=false) String operator, HttpServletRequest request) {
		int status = 0;//用来记录成功的行数
		int row = 1;//用来记录excel表格里的总记录数
		String result = "";//错误详细信息
		ExcelDataFormatter edf = new ExcelDataFormatter();
		File newFile = null;
		try {
			String name = file.getOriginalFilename();
			String rootPath = request.getServletContext().getRealPath("/");

			//获取项目路径，创建临时文件
			//windows下
			if ("\\".equals(File.separator)) {
				newFile = new File(rootPath + "\\" + name);
			}
			//linux下
			if ("/".equals(File.separator)) {
				newFile = new File(rootPath + "/" + name);
			}
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
			stream.write(file.getBytes());
			stream.close();

			//读取Excel文件并解析
			List<MealAllowanceExcel> vos = new ExcelUtils<MealAllowanceExcel>(new MealAllowanceExcel()).readFromFile(edf, newFile);
			//MealAllowanceNumModel numModel = new MealAllowanceNumModel();
			long batch_num_id = service.insertBatchNum(operator);
			String str;
			String pk_code;
			for (MealAllowanceExcel tmp : vos) {
				if(tmp.getPkCard()==null || tmp.getPkCard()== ""){
					continue;
				}
				if(tmp.getAllowanceType()==null || tmp.getAllowanceType()== ""){
					continue;
				}
				if(tmp.getMoneyAllowance()==null || tmp.getMoneyAllowance()== ""){
					continue;
				}
				row++;
				//查出卡号所对应的卡
				str = tmp.getPkCard();
				int endIndex = str.indexOf(".");
				pk_code = endIndex>0?str.substring(0, endIndex):str;
				CardModel cd=cardService.selectByPkStaff(pk_code);
				//如果存在的话，卡对象的pk_card,pk_staff设置到model对象中
				if(cd!=null){
					Double Money_allowance = 0.0;
					MealAllowanceModel model = new MealAllowanceModel();
					model.setAllowance_type(tmp.getAllowanceType().indexOf("累加") > -1? MealAllowanceModel.MealAllowacne_Type_Add : MealAllowanceModel.MealAllowacne_Type_Clear  );
					try{
						Money_allowance = Double.parseDouble(tmp.getMoneyAllowance());
					}catch(Exception e)
					{
						result+="行号为“"+row+"”插入失败，工号为“"+tmp.getPkCard()+"”的当条数据,金额有错误,金额为:"+tmp.getMoneyAllowance()+"<br>";
						e.printStackTrace();
						continue;
					}
					model.setMoney_allowance(Money_allowance);
					model.setPk_card(cd.getPk_card());
					model.setPk_staff(Integer.parseInt(cd.getPk_staff()));
					model.setOperator(operator);
					 int line = service.save(model,batch_num_id);
					if(line == 0){
						result+="行号为“"+row+"”插入失败，工号为“"+tmp.getPkCard()+"”的当条数据.<br>";
						continue;
					}else{
						status += 1;
					}
				}else {
					result+="行号为“"+row+"”，工号“"+tmp.getPkCard()+"”与人员档案资料不一致.<br>";
					continue;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

			//删除临时文件
			if (newFile != null) {
				newFile.delete();
			}

			if (newFile != null) {
				newFile.delete();
			}
		}
		if(status == row-1){
			return ResponseUtils.getSuccessAPI(true,"成功导入"+status+"条.",result);
		}
		return ResponseUtils.getSuccessAPI(false,"成功导入"+status+"条,导入失败"+(row-1-status)+"条", RestOperateCode.INSERT_DATA,result);

	}
}
package com.iandtop.service.impl;

import com.iandtop.dao.PublicDAO;
import com.iandtop.dao.StaffDAO;
import com.iandtop.model.StaffModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private PublicDAO publicDAO;

    @Override
    public List<StaffModel> retrieveAll() {
        return staffDAO.retrieveAll();
    }

    @Override
    public List<StaffModel> retrieveAllWithPage(StaffModel vo) {

//        if(vo.getDepartment_code() == null || vo.getDepartment_code().trim() == ""){
//            return new ArrayList<StaffModel>();
//        }

        List<StaffModel> result = staffDAO.retrieveAllWithPage(vo);

        String sex;

        for (StaffModel model : result) {
            sex = model.getSex();
            model.setSex(StaffModel.Male.equals(sex) ? "男" : (StaffModel.FaMale.equals(sex) ? "女" : ""));
        }

        return result;
    }

    @Override
    public List<StaffModel> retrieveAllWithPageCount(StaffModel vo) {
        List<StaffModel> result = staffDAO.retrieveAllWithPageCount(vo);


        return result;
    }

    @Override
    public int insertByMo(StaffModel model) {

        Integer result;

        //插入前校验
        List<Map> temp = publicDAO.retrieveBySql("select pk_staff from db_staff where staff_code='"+model.getStaff_code()+"'");
        if(temp != null && temp.size() > 0){
            return StatusCodeConstants.Code_Repeat;
        }

        result = staffDAO.insertByMo(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int updateByPk(StaffModel model) {

        Integer result = staffDAO.updateByPk(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int deleteByPks(List<String> pks) {

        Integer result = staffDAO.deleteByPks(pks);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }
    @Override
    public int insertbyexcel(StaffModel model) {
        return staffDAO.insertbyexcel(model);
    }

    @Override
    public int batchupdate(StaffModel model) {

        return staffDAO.batchupdate(model);


    }

    @Override
    public List<StaffModel> findAllForupd(StaffModel model) {
        return staffDAO.findAllForupd(model);
    }
}



package com.iandtop.service.impl;

import com.iandtop.dao.DeviceDAO;
import com.iandtop.dao.MerchantDAO;
import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.meal.MerchantModel;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.service.DeviceService;
import com.iandtop.utils.APIRestResponse;
import com.iandtop.utils.ResponseUtils;
import com.iandtop.utils.RestOperateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lz on 2017/5/19.
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private MerchantDAO merchantDAO;

    @Override
    public List<DeviceModel> retrieveAll() {
        return deviceDAO.retrieveAll();
    }

    @Override
    public List<DeviceModel> retrieveAllWithPage(DeviceModel vo) {
        return deviceDAO.retrieveAllWithPage(vo);
    }

    @Override
    public APIRestResponse insertByMo(DeviceModel model) {

        //校验
        List<DeviceModel> checkResult = deviceDAO.retrieveByCode(model.getDevice_code());
        if(checkResult != null && checkResult.size() > 0){
            return  ResponseUtils.getSuccessAPI(false,"编码重复", String.valueOf(StatusCodeConstants.Code_Repeat));
        }

        Integer result = deviceDAO.insertByMo(model);

        if(result < 1){
            return  ResponseUtils.getSuccessAPI(false,"系统内部错误", String.valueOf(StatusCodeConstants.Sys_Error));
        }
        return  ResponseUtils.getSuccessAPI(true,"操作成功", String.valueOf(StatusCodeConstants.Success));
    }

    @Override
    public APIRestResponse updateByPk(DeviceModel model) {
        Integer result = deviceDAO.updateByPk(model);

        if(result < 1){
            return  ResponseUtils.getSuccessAPI(false,"系统内部错误", String.valueOf(StatusCodeConstants.Sys_Error));
        }
        return  ResponseUtils.getSuccessAPI(true,"操作成功", String.valueOf(StatusCodeConstants.Success));
    }

    @Override
    public int deleteByPks(List<String> pks) {
        Integer result = deviceDAO.deleteByPks(pks);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public List<MerchantModel> retrieveTree() {
        List<MerchantModel> all = merchantDAO.retrieveAll();

        for (MerchantModel mo : all) {
            mo.setTreeId(mo.getPk_merchant());
            mo.setTreeText(mo.getMerchant_name());
        }

        return all;
    }

    @Override
    public List<DeviceModel> findAllretrieve() {
        return deviceDAO.findAllretrieve();
    }
}

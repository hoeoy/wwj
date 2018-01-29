package com.iandtop.service;

import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.meal.MerchantModel;
import com.iandtop.utils.APIRestResponse;

import java.util.List;

/**
 * Created by lz on 2017/5/19.
 */
public interface DeviceService {

    /**
     * 查询全部
     * @return
     */
    List<DeviceModel> retrieveAll();

    /**
     * 分页查询,包括条件查询
     * @param vo
     * @return
     */
    List<DeviceModel> retrieveAllWithPage(DeviceModel vo);

    /**
     * 插入数据
     * @param model
     * @return
     */
    APIRestResponse insertByMo(DeviceModel model);

    /**
     * 更新数据
     * @param model
     * @return
     */
    APIRestResponse updateByPk(DeviceModel model);

    /**
     * 按pk批量删除
     * @param pks
     * @return
     */
    int deleteByPks(List<String> pks);

    /**
     * 查询商户目录
     * @return
     */
    List<MerchantModel> retrieveTree();


    List<DeviceModel> findAllretrieve();



}

package com.iandtop.dao;

import com.iandtop.model.device.DeviceModel;
import com.iandtop.model.meal.MerchantModel;

import java.util.List;

/**
 * Created by lz on 2017/5/12.
 */
public interface DeviceDAO {

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
    int insertByMo(DeviceModel model);

    /**
     * 更新数据
     * @param model
     * @return
     */
    int updateByPk(DeviceModel model);

    /**
     * 按pk批量删除
     * @param pks
     * @return
     */
    int deleteByPks(List<String> pks);

    /**
     * 按编码查询
     * @param device_code
     * @return
     */
    List<DeviceModel> retrieveByCode(String device_code);

    List<DeviceModel> findAllretrieve();
}

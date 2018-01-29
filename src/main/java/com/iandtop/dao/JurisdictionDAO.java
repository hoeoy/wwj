package com.iandtop.dao;

import com.iandtop.model.system.JurisdictionModel;

import java.util.List;

/**
 * Created by lz on 2017/5/18.
 */
public interface JurisdictionDAO {

    int insertByVO(JurisdictionModel model);

    int updateByVO(JurisdictionModel model);

    int insertByVOs(List<JurisdictionModel> models);

    int deleteByPks(List<String> pks);

}

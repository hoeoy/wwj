package com.iandtop.service;

import com.iandtop.model.system.JurisdictionModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lz on 2017/5/18.
 */

public interface JurisdictionService {

    int insertByVO(JurisdictionModel model);

    int updateByVO(JurisdictionModel model);

    int insertByVOs(List<JurisdictionModel> models);

    int deleteByPks(List<String> pks);

}

package com.iandtop.service.impl;

import com.iandtop.dao.RoleDAO;
import com.iandtop.model.statuscodeconstant.StatusCodeConstants;
import com.iandtop.model.system.RoleModel;
import com.iandtop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lz on 2017/5/18.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public List<RoleModel> retrieveAll() {
        return roleDAO.retrieveAll();
    }

    @Override
    public List<RoleModel> retrieveAllWithPage(RoleModel vo) {
        return roleDAO.retrieveAllWithPage(vo);
    }

    @Override
    public int insertByMo(RoleModel model) {

        Integer result = roleDAO.insertByMo(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int updateByPk(RoleModel model) {

        Integer result = roleDAO.updateByPk(model);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }

    @Override
    public int deleteByPks(List<String> pks) {

        Integer result = roleDAO.deleteByPks(pks);

        return result < 1 ? StatusCodeConstants.Fail : StatusCodeConstants.Success;
    }
}

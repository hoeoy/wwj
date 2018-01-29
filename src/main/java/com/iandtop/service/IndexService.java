package com.iandtop.service;

import com.iandtop.model.system.NavigationModel;
import com.iandtop.model.system.UserModel;

import java.util.List;

/**
 * Created by lz on 2017/5/18.
 */
public interface IndexService {

    List<NavigationModel> retrieveAllNavigation() throws RuntimeException;

    List<NavigationModel> retrieveWithUser(UserModel model) throws RuntimeException;

    List<NavigationModel> retrieveWithUserRole(UserModel model);

}

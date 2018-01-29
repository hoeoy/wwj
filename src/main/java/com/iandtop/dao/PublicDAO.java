package com.iandtop.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by lz on 2017/3/17.
 */
public interface PublicDAO {

    /**
     * 自定义查询sql
     * @param sql
     * @return
     * @throws RuntimeException
     */
    List<Map> retrieveBySql(String sql) throws RuntimeException;

    int createTable(String sql) throws RuntimeException;

    int insert(String sql) throws RuntimeException;

    int intfindBysql(String sql) throws RuntimeException;

}

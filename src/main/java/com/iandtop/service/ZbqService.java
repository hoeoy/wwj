package com.iandtop.service;

import com.github.pagehelper.PageInfo;
import com.iandtop.model.ZbqModel;

/**
 * User: Mr.zheng
 * Date: 2018/1/27
 * Time: 13:45
 */
public interface ZbqService {

    PageInfo<ZbqModel> queryOrderDetail(String stypeid,String styleid, String typename, String foodname, String start_ts, String end_ts, Integer pageNo, Integer pageSize);
}

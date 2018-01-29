package com.iandtop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iandtop.dao.DetailAllDAO;
import com.iandtop.model.ZbqModel;
import com.iandtop.service.ZbqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Mr.zheng
 * Date: 2018/1/27
 * Time: 13:47
 */
@Service
public class ZbqServiceImpl implements ZbqService {

    @Autowired
    private DetailAllDAO detailAllDAO;

    @Override
    public PageInfo<ZbqModel> queryOrderDetail(String stypeid,String styleid, String typename, String foodname, String start_ts, String end_ts, Integer pageNo, Integer pageSize) {
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);

        List<ZbqModel> annlist = detailAllDAO.queryOrderDetail(stypeid,styleid,typename,foodname,start_ts,end_ts);
        List<ZbqModel> list = annlist;
        System.out.println(list);
        PageInfo<ZbqModel> page = new PageInfo<ZbqModel>(list);
        return page;
    }
}

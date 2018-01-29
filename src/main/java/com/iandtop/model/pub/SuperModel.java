package com.iandtop.model.pub;

import java.util.List;

/**
 * Created by lz on 2017/5/4.
 */
public abstract class SuperModel {

    private String memo;
    private String def1;
    private String def2;
    private String def3;
    private String def4;
    private String def5;
    private String ts;

    private String start_ts;
    private String end_ts;

    //冗余字段,分页
    //从第多少条开始
    private Integer pageindex;

    //页面显示条数
    private Integer limit;

    //页码
    private Integer offset;

    //树桩结构数据存储子类
    private List children_data;

    //树节点显示属性
    private String treeText;
    private String treeId;
    private String treeNodeName;
    private Boolean checked;

    public String getStart_ts() {
        return start_ts;
    }

    public void setStart_ts(String start_ts) {
        this.start_ts = start_ts;
    }

    public String getEnd_ts() {
        return end_ts;
    }

    public void setEnd_ts(String end_ts) {
        this.end_ts = end_ts;
    }

    public String getTreeText() {
        return treeText;
    }

    public void setTreeText(String treeText) {
        this.treeText = treeText;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getTreeNodeName() {
        return treeNodeName;
    }

    public void setTreeNodeName(String treeNodeName) {
        this.treeNodeName = treeNodeName;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getPageindex() {
        return pageindex;
    }

    public void setPageindex(Integer pageindex) {
        this.pageindex = pageindex;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDef1() {
        return def1;
    }

    public void setDef1(String def1) {
        this.def1 = def1;
    }

    public String getDef2() {
        return def2;
    }

    public void setDef2(String def2) {
        this.def2 = def2;
    }

    public String getDef3() {
        return def3;
    }

    public void setDef3(String def3) {
        this.def3 = def3;
    }

    public String getDef4() {
        return def4;
    }

    public void setDef4(String def4) {
        this.def4 = def4;
    }

    public String getDef5() {
        return def5;
    }

    public void setDef5(String def5) {
        this.def5 = def5;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public List getChildren_data() {
        return children_data;
    }

    public void setChildren_data(List children_data) {
        this.children_data = children_data;
    }

    public abstract String getTableName();

}

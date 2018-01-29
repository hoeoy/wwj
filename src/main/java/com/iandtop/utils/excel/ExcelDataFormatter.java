package com.iandtop.utils.excel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lz on 2017/4/12.
 * Excel数据导入导出格式化<br>
 * 举例:<br>
 * 数据导出， {lock,{0:正常，1:锁定}}<br>
 * 数据导入,{lock,{正常:0，锁定:1}}
 * @author Goofy <a href="http://www.xdemo.org">http://www.xdemo.org</a>
 */
public class ExcelDataFormatter {

    private Map<String,Map<String,String>> formatter=new HashMap<String, Map<String,String>>();

    public void set(String key,Map<String,String> map){
        formatter.put(key, map);
    }

    public Map<String,String> get(String key){
        return formatter.get(key);
    }

}
package com.iandtop.utils;

import com.iandtop.model.pub.SuperModel;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @andyzhao
 */
public class BaseUtils {
    public static <T extends SuperModel> List<T> mapToBean(Class<T> cls, List<Map> src) {
        try {
            List<T> result = new ArrayList<T>();
            for (Map map : src) {
                T vo = null;
                vo = cls.newInstance();
                BeanUtils.populate(vo, map);
                result.add(vo);
            }
            return result;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int increaseNum = 0;
    public static String getPK(){
        String sss = System.nanoTime()+"";
        int num = 15-sss.length();
        if(num>0){
            for(int i=0;i<num;i++){
                sss+="0";
            }
        }
        String pre = sss.substring(0,15);
        increaseNum++;
        if(increaseNum>9999){
            increaseNum = 0;
        }
        String numStr = increaseNum+"";
        String result = pre+"00000".substring(0,5-numStr.length())+numStr;
        return result;
    }
    
    public static String ByParams(String tableName,Map<String,String> params) throws RuntimeException{
        String sql = "select * from "+tableName+" where 1=1 ";
        Set<Map.Entry<String,String>> entries = params.entrySet();
        if(entries != null && entries.size() > 0){
            for (Map.Entry<String, String> entry : entries) {
                sql += " and " + entry.getKey() + "='" + entry.getValue() + "' ";
            }

        }
        return sql;
    }

    /**
     * 验证数字
     * @xss
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            if(chr<48 || chr>57)
                return false;
        }
        return true;
    }

    public static boolean isNumAndLetter(String str){
        String regex = "^[A-Za-z0-9]+$";
        if(str.matches(regex)){
            return true;
        }else{
            return false;
        }
    }

    public static Connection getOrclConn(String url,String user,String password){
        String Driver = "oracle.jdbc.OracleDriver";
//        String url = "jdbc:oracle:thin:@"+ip+":1521:"+instance;
//        String USER = "scott";
//        String Password = "tiger";

        Connection conn = null;

        try {
            Class.forName(Driver);
            conn =  DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;

    }

}

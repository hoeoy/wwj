package com.iandtop.utils.excel;

/**
 * Created by lz on 2017/4/12.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lz
 * Excel注解，用以生成Excel表格文件
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Excel {

    //列名
    String name() default "";

    //宽度
    int width() default 20;

    //忽略该字段
    boolean skip() default false;

}
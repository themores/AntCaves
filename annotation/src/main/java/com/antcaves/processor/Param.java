package com.antcaves.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liyuan
 * @description 注解field
 * @email thisuper@qq.com
 * @date 17/3/2 下午4:22
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface Param {
    /**
     * 传递参数过程中的key值
     *
     * @return
     */
    String key() default "";
}

package com.antcaves.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/10 下午4:58
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Modules {
    String[] module() default "";
}

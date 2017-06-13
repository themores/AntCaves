package com.antcaves.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/6/13 上午10:56
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Module {
    String module() default "";
}

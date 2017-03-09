package com.antcaves.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/2 下午3:30
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Router {
    /**
     * 跳转url
     *
     * @return
     */
    String path();

    /**
     * 参数
     *
     * @return
     */
    String[] param() default "";
}

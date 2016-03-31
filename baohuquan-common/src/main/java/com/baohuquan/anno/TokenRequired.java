package com.baohuquan.anno;

import java.lang.annotation.*;

/**
 *
 *  验证uid的时候需要加上注解
 *  Created by wangdi5 on 2016/3/23.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenRequired {


}

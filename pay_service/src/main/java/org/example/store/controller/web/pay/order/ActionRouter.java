package org.example.store.controller.web.pay.order;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionRouter {
    String name() default "";
    Class paramType() ;
//    String paramType() default "";
}

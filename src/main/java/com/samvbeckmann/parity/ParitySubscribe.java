package com.samvbeckmann.parity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * parity, class made on 2015-07-02
 *
 * @author Sam Beckmann
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ParitySubscribe
{
    public @interface RegisterClasses
    {
        RegisterType value();
    }
}

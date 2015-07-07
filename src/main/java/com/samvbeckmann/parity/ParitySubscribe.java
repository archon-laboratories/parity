package com.samvbeckmann.parity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that allows extensions of Parity
 * to register their classes.
 *
 * @author Sam Beckmann & Nate Beckemeyer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ParitySubscribe
{
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface RegisterClasses
    {
        RegisterType value();
    }
}

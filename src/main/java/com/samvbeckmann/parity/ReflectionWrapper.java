package com.samvbeckmann.parity;

/**
 * parity, class made on 2015-07-02
 *
 * @author Sam Beckmann
 */
public class ReflectionWrapper
{
    private String classpath;
    private String name;

    public ReflectionWrapper(String classpath, String name)
    {
        this.classpath = classpath;
        this.name = name;
    }

    public String getClasspath()
    {
        return classpath;
    }

    public void setClasspath(String classpath)
    {
        this.classpath = classpath;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

package com.samvbeckmann.parity;

/**
 * Contains a String and Classpath.
 * Used to contain information about a registered class.
 *
 * @author Sam Beckmann & Nate Beckemeyer
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

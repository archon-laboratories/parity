package com.samvbeckmann.parity.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for an agent.
 *
 * @author Sam Beckmann
 */
public class AgentModel
{
    private StringProperty classpath;
    private DoubleProperty opinion;

    public AgentModel()
    {
        this(null, 0.5);
    }

    public AgentModel(String classpath, double opinion)
    {
        this.classpath = new SimpleStringProperty(classpath);
        this.opinion = new SimpleDoubleProperty(opinion);
    }

    public String getClasspath()
    {
        return classpath.get();
    }

    public StringProperty classpathProperty()
    {
        return classpath;
    }

    public void setClasspath(String classpath)
    {
        this.classpath.set(classpath);
    }

    public double getOpinion()
    {
        return opinion.get();
    }

    public DoubleProperty opinionProperty()
    {
        return opinion;
    }

    public void setOpinion(double opinion)
    {
        this.opinion.set(opinion);
    }
}

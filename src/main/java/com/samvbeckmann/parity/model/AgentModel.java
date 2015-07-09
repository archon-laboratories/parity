package com.samvbeckmann.parity.model;

import com.samvbeckmann.parity.ReflectionWrapper;
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
    private ReflectionWrapper wrapper;
    private DoubleProperty opinion;

    public AgentModel()
    {
        this(null, 0.5);
    }

    public AgentModel(ReflectionWrapper wrapper, double opinion)
    {
        this.wrapper = wrapper;
        this.opinion = new SimpleDoubleProperty(opinion);
    }

    public String getClasspath()
    {
        return wrapper.getClasspath();
    }

    public StringProperty getName()
    {
        return new SimpleStringProperty(wrapper.getName());
    }

    public double getOpinion()
    {
        return opinion.get();
    }

    public void setOpinion(double opinion)
    {
        this.opinion.set(opinion);
    }

    public DoubleProperty opinionProperty()
    {
        return opinion;
    }
}

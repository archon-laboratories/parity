package com.samvbeckmann.parity;

import java.util.List;

/**
 * parity, class made on 6/16/2015.
 *
 * @author Sam Beckmann
 */
public class Population
{
    public List<Community> communities;

    public double getAverageOpinion()
    {
        double total = 0;
        for (Community community : communities)
        {
            List<Double> opinions = community.getOpinions();
            for (double opinion : opinions)
            {
                total += opinion;
            }
        }
        return total / this.getPopulationSize();
    }

    public int getPopulationSize()
    {
        int total = 0;
        for (Community community : communities)
        {
            total += community.getCommunitySize();
        }
        return total;
    }

}

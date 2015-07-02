package com.samvbeckmann.parity.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods for generating and polling indices.
 *
 * @author Nate Beckemeyer & Sam Beckmann
 */
public final class IndexHelper
{
    /**
     * Creates a list of indices of the length specified.
     *
     * @param bound The bound of indices to be used.
     * @return An ArrayList consecutive integers of the length given.
     */
    public static List<Integer> generateIndices(int bound)
    {
        List<Integer> indices = new ArrayList<>(bound);
        for (int i = 0; i < bound; i++)
            indices.add(i);
        return indices;
    }
}

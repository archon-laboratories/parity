package com.samvbeckmann.parity;

import java.util.Map;

/**
 * parity, class made on 6/16/2015.
 *
 * @author Sam Beckmann
 */
public interface IInteractionHandler
{
    Map determineInteractions(Population p);

    int getColumnFeedback(Choices columnPlayer, Choices rowPlayer);

    int getRowFeedback(Choices columnPlayer, Choices rowPlayer);

}

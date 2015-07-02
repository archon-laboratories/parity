package main.java.com.samvbeckmann.parity.utilities;

import main.java.com.samvbeckmann.parity.Population;

import java.util.List;

/**
 * Created for parity by @author Nate Beckemeyer on 2015-07-02.
 */
public final class Shuffler <E>
{
    public void shuffleArray(E[] toShuffle) {
        toShuffle = copyRandom(toShuffle);
    }

    public E[] copyRandom(E[] toShuffle) {
        List<Integer> availability = IndexHelper.generateIndices(toShuffle.length);

        E[] copy = toShuffle.clone();

        while (availability.size() > 0) {
            int index = availability.remove(Population.rnd.nextInt(availability.size()));
            copy[availability.size()] = toShuffle[index];
        }

        return copy;
    }
}

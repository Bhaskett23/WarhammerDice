package birthday.warhammerdice;

import java.util.concurrent.ThreadLocalRandom;

public class MathEngine {

    int calculateNumberOfHits(int numberOfDice, int toHit, boolean reRollOnesToHit, boolean reRollAllHits)
    {
        return rollSimulator(numberOfDice, toHit, reRollOnesToHit, reRollAllHits);
    }

    int calculateNumberOfWounds(int numberOfHits, int toWound, boolean reRollOnesToWound, boolean reRollAllWounds)
    {
        return rollSimulator(numberOfHits, toWound, reRollOnesToWound, reRollAllWounds);
    }

    int rollSimulator(int numberOfDice, int successValue, boolean reRollOnes, boolean reRollAll) {

        int successes = 0;
        int misses = 0;
        int total = 0;

        for (int i = 0; i < numberOfDice; i++) {
            int dieRoll = ThreadLocalRandom.current().nextInt(1, 7);
            if (dieRoll >= successValue) {
                successes++;
            } else if (reRollAll || (reRollOnes & dieRoll == 1)) {
                misses++;
            }
        }

        total = successes;

        if (reRollAll || reRollOnes)
        {
            total += rollSimulator(misses, successValue, false, false);
        }

        return total;
    }

    public RollResultModel getRollResult(int numberOfDice, int toHit, int toWound, boolean reRollOnesToHit, boolean reRollAllHits, boolean reRollOnesToWound, boolean reRollAllWounds)
    {
        int hits = calculateNumberOfHits(numberOfDice, toHit, reRollOnesToHit, reRollAllHits);
        int wounds = calculateNumberOfWounds(hits, toWound, reRollOnesToWound, reRollAllWounds);
        return new RollResultModel(hits, wounds);
    }
}

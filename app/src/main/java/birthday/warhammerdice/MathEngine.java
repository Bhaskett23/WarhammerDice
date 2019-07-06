package birthday.warhammerdice;

import java.util.concurrent.ThreadLocalRandom;

public class MathEngine {

    private int calculateNumberOfHits(int numberOfDice, int toHit)
    {
        return rollSimulator(numberOfDice, toHit);
    }

    private int calculateNumberOfWounds(int numberOfHits, int toWound)
    {
        return rollSimulator(numberOfHits, toWound);
    }

    private int rollSimulator(int numberOfDice, int successValue)
    {
        int successes = 0;

        for (int i = 0; i <= numberOfDice; i++) {
            int dieRoll = ThreadLocalRandom.current().nextInt(1, 7);
            if (dieRoll >= successValue)
            {
                successes++;
            }
        }

        return successes;
    }

    public RollResultModel getRollResult(int numberOfDice, int toHit, int toWound)
    {
        int hits = calculateNumberOfHits(numberOfDice, toHit);
        int wounds = calculateNumberOfWounds(hits, toWound);
        return new RollResultModel(hits, wounds);
    }
}

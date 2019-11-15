package birthday.warhammerdice;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;

public class MathEngineTest {

    MathEngine _mathEngine;
    public MathEngineTest()
    {
        _mathEngine = Mockito.mock(MathEngine.class);
    }

    @Test
    public void GetRollResultCallsCalculateSavingThrows()
    {
        //Arrange
        int numberOfDice = 10;
        int toHit = 3;
        boolean reroll1s = false;
        boolean rerollAll = false;

        //Act
        Mockito.when(_mathEngine.getRollResult(numberOfDice, toHit, 0, reroll1s, rerollAll, false, false));

        //Assert
        Mockito.verify(_mathEngine, times(1)).calculateNumberOfHits(numberOfDice, toHit, reroll1s, rerollAll);
    }

}

package birthday.warhammerdice;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MathEngineTest {

    MathEngine _mathEngine;
    private final int _numberOfDice = 10;
    private final int _toHit = 4;
    private final int _toWound = 3;
    private final boolean _rerollOnesToHit = true;
    private final boolean _rerollAllToHit = true;
    private final boolean _rerollOnesToWound = true;
    private final boolean _rerollAllToWound = true;
    private final int _numberOfSuccessHits = -1;
    private final int _numberOfSuccessWounds = -2;

    public MathEngineTest() {
        _mathEngine = Mockito.spy(MathEngine.class);
    }

    @Test
    public void GetRollResultCallsCalculateNumberOfHits() {
        //Arrange
        //when(_mathEngine.calculateNumberOfHits(_numberOfDice, _toHit, _rerollOnesToHit, _rerollAllToHit)).thenReturn(_numberOfSuccessHits);
        //when(_mathEngine.calculateNumberOfWounds(_numberOfSuccessHits, _toWound, _rerollOnesToWound, _rerollAllToWound)).thenReturn(1);

        //Act
        when(_mathEngine.getRollResult(_numberOfDice, _toHit, _toWound, _rerollOnesToHit, _rerollAllToHit, _rerollOnesToWound, _rerollAllToWound)).thenReturn(new RollResultModel(1, 1));

        //Assert
        verify(_mathEngine, times(1)).calculateNumberOfHits(_numberOfDice, _toHit, _rerollOnesToHit, _rerollAllToHit);
    }

    @Test
    public void GetRollResultCallsCalculateNumberOfWounds() {
        //Arrange
        int numberOfHitDice = 5;
        int toWound = 2;
        boolean rerollOnes = true;
        boolean rerollAll = true;
        when(_mathEngine.calculateNumberOfHits(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyBoolean())).thenReturn(numberOfHitDice);
        //when(_mathEngine.calculateNumberOfWounds(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyBoolean())).thenReturn(1);

        //Act
        when(_mathEngine.getRollResult(0, 0, toWound, false, false, rerollOnes, rerollAll)).thenReturn(new RollResultModel(1, 1));

        //Assert
        verify(_mathEngine, times(1)).calculateNumberOfWounds(numberOfHitDice, toWound, rerollOnes, rerollAll);
    }

    @Test
    public void GetRollResultReturnsNumberOfHitsAndWounds() {
        //Arrange
        when(_mathEngine.calculateNumberOfHits(_numberOfDice, _toHit, _rerollOnesToHit, _rerollAllToHit)).thenReturn(_numberOfSuccessHits);
        when(_mathEngine.calculateNumberOfWounds(_numberOfSuccessHits, _toWound, _rerollOnesToWound, _rerollAllToWound)).thenReturn(_numberOfSuccessWounds);

        //Act
        //when(_mathEngine.getRollResult(_numberOfDice, _toHit, _toWound, _rerollOnesToHit, _rerollAllToHit, _rerollOnesToWound, _rerollAllToWound)).thenReturn(new RollResultModel(1,1));
        RollResultModel result = _mathEngine.getRollResult(_numberOfDice, _toHit, _toWound, _rerollOnesToHit, _rerollAllToHit, _rerollOnesToWound, _rerollAllToWound);

        //Assert
        Assert.assertEquals(result.getHits(), _numberOfSuccessHits);
        Assert.assertEquals(result.getWounds(), _numberOfSuccessWounds);
    }

    @Test
    public void CalculateNumberOfHitsCallsRollSimulatorOnce() {
        //Act
        _mathEngine.calculateNumberOfHits(_numberOfDice, _toHit, _rerollOnesToHit, _rerollAllToHit);

        //Assert
        verify(_mathEngine, times(1)).calculateNumberOfHits(_numberOfDice, _toHit, _rerollOnesToHit, _rerollAllToHit);
    }

    @Test
    public void CalculateNumberOfWoundsCallsRollSimulatorOnce() {
        //Arrange
        when(_mathEngine.calculateNumberOfHits(_numberOfDice, _toHit, _rerollOnesToHit, _rerollAllToHit)).thenReturn(_numberOfSuccessHits);

        //Act
        _mathEngine.calculateNumberOfWounds(_numberOfSuccessHits, _toWound, _rerollOnesToWound, _rerollAllToWound);

        //Assert
        verify(_mathEngine, times(1)).calculateNumberOfWounds(_numberOfSuccessHits, _toWound, _rerollOnesToHit, _rerollAllToHit);
    }

    @Test
    public void RollSimulatorCallsRandomNumberForEachDiceRoll() {
        //Arrange
        int numOfDice = 20;

        //Act
        _mathEngine.rollSimulator(numOfDice, _toHit, false, false);

        //Assert
        verify(_mathEngine, times(numOfDice)).randomNumber();
    }

    @Test
    public void RollSimulatorCallsItselfToRerollDiceIfRerollAllIsTrue()
    {
        //Arrange
        when(_mathEngine.randomNumber()).thenReturn(2);

        //Act
        _mathEngine.rollSimulator(_numberOfDice, _toWound, false, _rerollAllToHit);

        //Assert
        verify(_mathEngine, times(1)).rollSimulator(_numberOfDice, _toWound, false, false);
    }

    @Test
    public void RollSimulatorDoesNotCallItselfWhenBothRerollOptionsAreFalse()
    {
        //Arrange
        when(_mathEngine.randomNumber()).thenReturn(2);

        //Act
        _mathEngine.rollSimulator(_numberOfDice, _toWound, false, false);
        
        //Assert
        verify(_mathEngine, times(0)).rollSimulator(0, _toWound, false, false);
    }

    @Test
    public void RollSimulatorCallsItselfToRerollDiceIfReroll1sIsTrueAndOneDiceIsOne()
    {
        //Arrange
        when(_mathEngine.randomNumber()).thenReturn(1);

        //Act
        _mathEngine.rollSimulator(_numberOfDice, _toWound, _rerollOnesToHit, false);

        //Assert
        verify(_mathEngine, times(1)).rollSimulator(_numberOfDice, _toWound, false, false);
    }

    @Test
    public void RollSimulatorDoesNotCallItselfIfRerollOnesIsTrueButNoOnesAreRolled()
    {
        //Arrange
        when(_mathEngine.randomNumber()).thenReturn(2);

        //Act
        _mathEngine.rollSimulator(_numberOfDice, _toWound, _rerollOnesToHit, false);

        //Assert
        verify(_mathEngine, times(0)).rollSimulator(_numberOfDice, _toWound, false, false);
    }
}

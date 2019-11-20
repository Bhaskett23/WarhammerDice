package birthday.warhammerdice;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MathEngineTest {

    MathEngine _mathEngine;
    final int _numberOfDice = 10;
    final int _toHit = 4;
    final int _toWound = 3;
    final boolean _rerollOnesToHit = true;
    final boolean _rerollAllToHit = true;
    final boolean _rerollOnesToWound = true;
    final boolean _rerollAllToWound = true;
    final int _numberOfSuccessHits = -1;
    final int _numberOfSuccessWounds = -2;

    public MathEngineTest()
    {
        _mathEngine = Mockito.spy(MathEngine.class);
    }

    @Test
    public void GetRollResultCallsCalculateNumberOfHits()
    {
        //Arrange
        when(_mathEngine.calculateNumberOfHits(_numberOfDice, _toHit, _rerollOnesToHit, _rerollAllToHit)).thenReturn(_numberOfSuccessHits);
        when(_mathEngine.calculateNumberOfWounds(_numberOfSuccessHits, _toWound, _rerollOnesToWound, _rerollAllToWound)).thenReturn(1);

        //Act
        when(_mathEngine.getRollResult(_numberOfDice, _toHit, _toWound, _rerollOnesToHit, _rerollAllToHit, _rerollOnesToWound, _rerollAllToWound)).thenReturn(new RollResultModel(1,1));

        //Assert
        verify(_mathEngine, times(1)).calculateNumberOfHits(_numberOfDice, _toHit, _rerollOnesToHit, _rerollAllToHit);
    }

    @Test
    public void GetRollResultCallsCalculateNumberOfWounds()
    {
        //Arrange
        int numberOfHitDice = 5;
        int toWound = 2;
        boolean rerollOnes = true;
        boolean rerollAll = true;
        when(_mathEngine.calculateNumberOfHits(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyBoolean())).thenReturn(numberOfHitDice);
        when(_mathEngine.calculateNumberOfWounds(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyBoolean())).thenReturn(1);

        //Act
        when(_mathEngine.getRollResult(0, 0, toWound, false, false, rerollOnes, rerollAll)).thenReturn(new RollResultModel(1,1));

        //Assert
        verify(_mathEngine, times(1)).calculateNumberOfWounds(numberOfHitDice, toWound, rerollOnes, rerollAll);
    }

    @Test
    public void GetRollResultReturnsNumberOfHitsAndWounds()
    {
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
}

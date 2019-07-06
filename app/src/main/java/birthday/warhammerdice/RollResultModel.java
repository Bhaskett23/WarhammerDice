package birthday.warhammerdice;

public class RollResultModel {

    public RollResultModel(int hits, int wounds)
    {
        mHits = hits;
        mWounds = wounds;
    }

    private int mHits;
    private int mWounds;

    public int getHits()
    {
        return mHits;
    }

    public int getWounds()
    {
        return mWounds;
    }
}

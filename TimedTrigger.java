public class TimedTrigger
{
    private float ticks;
    private float ticksBeforeStartLooping;
    private float ticksBetweenTriggers;
    private int loopsToMake;
    private boolean finished;
    private boolean ticksBeforeStartLoopingReached;

    public TimedTrigger(float ticksBetweenTriggers)
    {
        this.ticksBetweenTriggers = ticksBetweenTriggers;
        this.ticksBeforeStartLooping = ticksBetweenTriggers;
        this.loopsToMake = 1;
    }

    //-------------------------- BUILDERS --------------------------
    //Only use if loopsToMake > 1
    public TimedTrigger ticksBeforeStartLooping(int ticksBeforeStartLooping)
    {
        this.ticksBeforeStartLooping = ticksBeforeStartLooping;
        return this;
    }

    public TimedTrigger loopsToMake(int loopsToMake)
    {
        this.loopsToMake = loopsToMake;
        return this;
    }

    public TimedTrigger loopInfinitely()
    {
        this.loopsToMake = -1;
        return this;
    }
    //-----------------------------------------------------------------

    /**
     * Run the timer and returns true if ticksBeforeStartLooping is reached and ticks > ticksBetweenTriggers
     * @param delta deltaTime
     */
    public boolean triggered(float delta)
    {
        if(finished)
            return false;

        this.ticks += delta;

        if(this.ticksBeforeStartLoopingReached && (this.ticks > this.ticksBetweenTriggers)
                || !this.ticksBeforeStartLoopingReached && (this.ticks > this.ticksBeforeStartLooping))
        {
            ticksBeforeStartLoopingReached = true;

            if(this.loopsToMake == -1)
            {
                this.ticks = 0;
                return true;
            }
            if(this.loopsToMake > 0)
            {
                this.ticks = 0;
                if(--this.loopsToMake == 0)
                {
                    finished = true;
                }
                return true;
            }
        }

        return false;
    }

    public void reset(int loopsToMake)
    {
        this.loopsToMake = loopsToMake;
        this.ticks = 0;
        this.finished = false;
        this.ticksBeforeStartLoopingReached = false;
    }

    //-------------------------- GETTERS/SETTERS --------------------------
    public float getTicks()
    {
        return ticks;
    }

    public float getTicksBeforeStartLooping()
    {
        return ticksBeforeStartLooping;
    }

    public float getTicksBetweenTriggers()
    {
        return ticksBetweenTriggers;
    }

    public boolean isFinished()
    {
        return finished;
    }

    public boolean isTicksBeforeStartLoopingReached()
    {
        return ticksBeforeStartLoopingReached;
    }

    public void setTicksBeforeStartLooping(float ticksBeforeStartLooping)
    {
        this.ticksBeforeStartLooping = ticksBeforeStartLooping;
    }

    public void setTicksBetweenTriggers(float ticksBetweenTriggers)
    {
        this.ticksBetweenTriggers = ticksBetweenTriggers;
    }

    public void setLoopsToMake(int loopsToMake)
    {
        this.loopsToMake = loopsToMake;
    }
    //---------------------------------------------------------------------
}

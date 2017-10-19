import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimedTrigger {
    private float ticks;
    private float ticksBeforeStartLooping;
    private List<Float> ticksToTrigger = new ArrayList<>();
    private int TTTIndex;
    private int loopsToMake;
    private int loopNumber;
    private boolean finished;

    public TimedTrigger(float ticksBetweenTriggers) {
        this.ticksToTrigger.add(ticksBetweenTriggers);
        this.loopsToMake = 1;
    }

    public TimedTrigger(float ticksBetweenTriggers, float... rest) {
        this.ticksToTrigger.add(ticksBetweenTriggers);
        for (Float ttt : rest)
            this.ticksToTrigger.add(ttt);
        Collections.sort(this.ticksToTrigger);

        this.loopsToMake = 1;
    }

    //-------------------------- BUILDERS --------------------------
    //Only use if loopsToMake > 1
    public TimedTrigger ticksBeforeStartLooping(int ticksBeforeStartLooping) {
        this.ticksBeforeStartLooping = ticksBeforeStartLooping;
        return this;
    }

    public TimedTrigger loopsToMake(int loopsToMake) {
        this.loopsToMake = loopsToMake;
        return this;
    }

    public TimedTrigger loopInfinitely() {
        this.loopsToMake = -1;
        return this;
    }
    //-----------------------------------------------------------------

    /**
     * Run the timer and returns true if ticksBeforeStartLooping is reached and ticks > ticksToTrigger
     * @param delta deltaTime
     */
    public boolean triggered(float delta) {
        if(finished || ticksToTrigger.isEmpty()) return false;

        ticks += delta;
        //Ticks reached
        if(ticks >= ticksToTrigger.get(TTTIndex) + ticksBeforeStartLooping) {
            if(++TTTIndex < ticksToTrigger.size())
                return true;
            else
                TTTIndex = 0;

            if(loopsToMake == -1) {
                ticks -= ticksToTrigger.get(ticksToTrigger.size() - 1);
                return true;
            }
            if(loopsToMake > 0) {
                ticks -= ticksToTrigger.get(ticksToTrigger.size() - 1);
                if(++loopNumber >= loopsToMake) {
                    finished = true;
                }
                return true;
            }
        }
        return false;
    }

    public void reset(boolean resetTicksBeforeStartLooping) {
        loopNumber = 0;
        ticks = resetTicksBeforeStartLooping ? 0 : ticksBeforeStartLooping;
        finished = false;
        TTTIndex = 0;
    }

    public float getTicks() {
        return ticks;
    }

    public float getTicksBeforeStartLooping() {
        return ticksBeforeStartLooping;
    }

    public List<Float> getTicksBetweenTriggers() {
        return ticksToTrigger;
    }

    public int getLoopNumber() {
        return loopNumber;
    }

    public boolean isFinished() {
        return finished;
    }


    public void setTicksBeforeStartLooping(float ticksBeforeStartLooping) {
        this.ticksBeforeStartLooping = ticksBeforeStartLooping;
    }

    public void addTicksBetweenTriggers(float ticks) {
        ticksToTrigger.add(ticks);
        Collections.sort(this.ticksToTrigger);
    }

    public void addTicksBetweenTriggers(float ticks, float... rest) {
        ticksToTrigger.add(ticks);
        for (Float ttt : rest)
            this.ticksToTrigger.add(ttt);

        Collections.sort(this.ticksToTrigger);
    }

    public void removeTicksBetweenTriggers(float ticks) {
        ticksToTrigger.add(ticks);
        Collections.sort(this.ticksToTrigger);
    }

    public void removeTicksBetweenTriggers(float ticks, float... rest) {
        ticksToTrigger.remove(ticks);
        for (Float ttt : rest)
            this.ticksToTrigger.remove(ttt);

        Collections.sort(this.ticksToTrigger);
    }

    public void setTicksBetweenTriggers(List<Float> ticksBetweenTriggers) {
        this.ticksToTrigger = ticksBetweenTriggers;
    }

    public void setLoopsToMake(int loopsToMake) {
        this.loopsToMake = loopsToMake;
    }
}

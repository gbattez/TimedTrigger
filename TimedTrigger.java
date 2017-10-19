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
    private boolean ticksBeforeStartLoopingReached;

    public TimedTrigger(float ticksBetweenTriggers) {
        this.ticksToTrigger.add(ticksBetweenTriggers);
        this.ticksBeforeStartLooping = ticksBetweenTriggers;
        this.loopsToMake = 1;
    }

    public TimedTrigger(float ticksBetweenTriggers, float... rest) {
        this.ticksToTrigger.add(ticksBetweenTriggers);
        for (Float ttt : rest)
            this.ticksToTrigger.add(ttt);
        Collections.sort(this.ticksToTrigger);


        this.ticksBeforeStartLooping = ticksBetweenTriggers;
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
        if(this.ticksBeforeStartLoopingReached && (ticks > ticksToTrigger.get(TTTIndex))
                || !ticksBeforeStartLoopingReached && (ticks > ticksBeforeStartLooping)) {
            ticksBeforeStartLoopingReached = true;

            if(++TTTIndex >= ticksToTrigger.size())
                TTTIndex = 0;
             else
                return true;

            if(loopsToMake == -1) {
                ticks = 0;
                return true;
            }
            if(loopsToMake > 0) {
                ticks = 0;
                if(++loopNumber >= loopsToMake) {
                    finished = true;
                }
                return true;
            }
        }

        return false;
    }

    public void reset() {
        loopNumber = 0;
        ticks = 0;
        finished = false;
        ticksBeforeStartLoopingReached = false;
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

    public boolean isTicksBeforeStartLoopingReached() {
        return ticksBeforeStartLoopingReached;
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

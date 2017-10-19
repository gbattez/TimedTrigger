import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimedTrigger {
    private float ticks;
    private float ticksBeforeStartLooping;
    private List<Float> ticksBetweenTriggers = new ArrayList<>();
    private int TTTIndex;
    private int loopsToMake;
    private int loopNumber;
    private boolean finished;
    private boolean ticksBeforeStartLoopingReached;

    public TimedTrigger(float ticksBetweenTriggers) {
        this.ticksBetweenTriggers.add(ticksBetweenTriggers);
        this.ticksBeforeStartLooping = ticksBetweenTriggers;
        this.loopsToMake = 1;
    }

    public TimedTrigger(float ticksBetweenTriggers, float... rest) {
        this.ticksBetweenTriggers.add(ticksBetweenTriggers);
        for (Float ttt : rest)
            this.ticksBetweenTriggers.add(ttt);
        Collections.sort(this.ticksBetweenTriggers);


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
     * Run the timer and returns true if ticksBeforeStartLooping is reached and ticks > ticksBetweenTriggers
     * @param delta deltaTime
     */
    public boolean triggered(float delta) {
        if(finished || ticksBetweenTriggers.isEmpty()) return false;

        ticks += delta;

        //Ticks reached
        if(this.ticksBeforeStartLoopingReached && (ticks > ticksBetweenTriggers.get(TTTIndex))
                || !ticksBeforeStartLoopingReached && (ticks > ticksBeforeStartLooping)) {
            ticksBeforeStartLoopingReached = true;

            if(++TTTIndex >= ticksBetweenTriggers.size())
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
        return ticksBetweenTriggers;
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
        ticksBetweenTriggers.add(ticks);
        Collections.sort(this.ticksBetweenTriggers);
    }

    public void addTicksBetweenTriggers(float ticks, float... rest) {
        ticksBetweenTriggers.add(ticks);
        for (Float ttt : rest)
            this.ticksBetweenTriggers.add(ttt);

        Collections.sort(this.ticksBetweenTriggers);
    }

    public void removeTicksBetweenTriggers(float ticks) {
        ticksBetweenTriggers.add(ticks);
        Collections.sort(this.ticksBetweenTriggers);
    }

    public void removeTicksBetweenTriggers(float ticks, float... rest) {
        ticksBetweenTriggers.remove(ticks);
        for (Float ttt : rest)
            this.ticksBetweenTriggers.remove(ttt);

        Collections.sort(this.ticksBetweenTriggers);
    }

    public void setTicksBetweenTriggers(List<Float> ticksBetweenTriggers) {
        this.ticksBetweenTriggers = ticksBetweenTriggers;
    }

    public void setLoopsToMake(int loopsToMake) {
        this.loopsToMake = loopsToMake;
    }
}

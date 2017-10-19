import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimedTrigger {
    private float ticks;
    private float ticksBeforeStartLooping;
    private List<Float> triggeringTicks = new ArrayList<>();
    private int triggeringTicksIndex;
    private int loopsToMake;
    private int loopNumber;
    private boolean finished;

    public TimedTrigger(float triggeringTicks) {
        this.triggeringTicks.add(triggeringTicks);
        this.loopsToMake = 1;
    }

    public TimedTrigger(float triggeringTicks, float... rest) {
        this.triggeringTicks.add(triggeringTicks);
        for (Float tti : rest)
            this.triggeringTicks.add(tti);
        Collections.sort(this.triggeringTicks);

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
     * Run the timer and returns true if ticksBeforeStartLooping is reached and ticks > triggeringTicks
     * @param delta deltaTime
     */
    public boolean triggered(float delta) {
        if(finished || triggeringTicks.isEmpty()) return false;

        ticks += delta;
        //Ticks reached
        if(ticks >= triggeringTicks.get(triggeringTicksIndex) + ticksBeforeStartLooping) {
            if(++triggeringTicksIndex < triggeringTicks.size())
                return true;
            else
                triggeringTicksIndex = 0;

            if(loopsToMake == -1) {
                ticks -= triggeringTicks.get(triggeringTicks.size() - 1);
                return true;
            }
            if(loopsToMake > 0) {
                ticks -= triggeringTicks.get(triggeringTicks.size() - 1);
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
        triggeringTicksIndex = 0;
    }

    public float getTicks() {
        return ticks;
    }

    public float getTicksBeforeStartLooping() {
        return ticksBeforeStartLooping;
    }

    public List<Float> getTriggeringTicks() {
        return triggeringTicks;
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

    public void addTriggeringTicks(float ticks) {
        triggeringTicks.add(ticks);
        Collections.sort(this.triggeringTicks);
    }

    public void addTriggeringTicks(float ticks, float... rest) {
        triggeringTicks.add(ticks);
        for (Float triggeringTicks : rest)
            this.triggeringTicks.add(triggeringTicks);

        Collections.sort(this.triggeringTicks);
    }

    public void removeTriggeringTicks(float ticks) {
        triggeringTicks.add(ticks);
        Collections.sort(this.triggeringTicks);
    }

    public void removeTriggeringTicks(float ticks, float... rest) {
        triggeringTicks.remove(ticks);
        for (Float triggeringTicks : rest)
            this.triggeringTicks.remove(triggeringTicks);

        Collections.sort(this.triggeringTicks);
    }

    public void setTriggeringTicks(List<Float> triggeringTicks) {
        this.triggeringTicks = triggeringTicks;
    }

    public void setLoopsToMake(int loopsToMake) {
        this.loopsToMake = loopsToMake;
    }
}

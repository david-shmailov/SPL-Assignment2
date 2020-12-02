package bgu.spl.mics.application.passiveObjects;

import java.util.List;


/**
 * Passive data-object representing an attack object.
 * You must not alter any of the given public methods of this class.
 * <p>
 * Do not add any additional members/method to this class (except for getters).
 */
public class Attack {
    private final List<Integer> serials;
    private final int duration;

    /**
     * Constructor.
     */
    public Attack(List<Integer> serialNumbers, int duration) {
        this.serials = serialNumbers;
        this.duration = duration;
    }

    /**
     *getters, where add
     */
    public List<Integer> getSerial(){return serials;}
    public int getDuration(){return duration;}
}

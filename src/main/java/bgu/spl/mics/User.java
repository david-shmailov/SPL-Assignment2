package bgu.spl.mics;

import java.util.Map;

public class User {
    public Map[] attacks;
    public long R2D2;
    public long Lando;
    public int Ewoks;

    public User(Map[] attacks,long R2D2, long Lando, int Ewoks) {
        this.R2D2 = R2D2;
        this.Lando = Lando;
        this.Ewoks = Ewoks;
        this.attacks = attacks;
    }

}

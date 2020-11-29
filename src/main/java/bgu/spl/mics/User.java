package bgu.spl.mics;

import com.google.gson.JsonElement;


public class User {
    public JsonElement[] attacks;
    public long R2D2;
    public long Lando;
    public int Ewoks;

    public User(JsonElement[] attacks,long R2D2, long Lando, int Ewoks) {
        this.R2D2 = R2D2;
        this.Lando = Lando;
        this.Ewoks = Ewoks;
        this.attacks=attacks;
        }
    }




package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {
    private long duration;
    public LandoMicroservice(long duration) {
        super("Lando");
        this.duration = duration;
    }

    @Override
    protected void initialize() {
        Callback<BombDestroyerEvent> bombDestroyerEventCallback = c -> {
            Thread.currentThread().sleep(duration);
            complete(c,true);
        };
        this.subscribeEvent(BombDestroyerEvent.class,bombDestroyerEventCallback);

        Callback<TerminateBroadcast> terminate= c -> terminate();
        this.subscribeBroadcast(TerminateBroadcast.class,terminate);
    }
}

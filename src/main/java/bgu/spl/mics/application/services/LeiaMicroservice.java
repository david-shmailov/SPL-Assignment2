package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
	private Attack[] attacks;
	private Diary diary;

    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
		diary=Diary.getInstance();
    }

    @Override
    protected void initialize() {
    	for (Attack attack : attacks){
    	    sendEvent(new AttackEvent(attack));
        }
        Callback<TerminateBroadcast> terminateBroadcastCallback= c -> {
    	    diary.setLeiaTerminate();
    	    terminate();};
        this.subscribeBroadcast(TerminateBroadcast.class,terminateBroadcastCallback);
    }
}

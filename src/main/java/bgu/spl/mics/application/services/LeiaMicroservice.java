package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.*;
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
    private AtomicInteger counter;

    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
		diary=Diary.getInstance();
        counter=new AtomicInteger(0);
    }

    @Override
    protected void initialize() {
    	for (Attack attack : attacks){
    	    attack.getSerial().sort(Comparator.comparingInt(o -> o)); //sorts the ewok serials to avoid dead-locks between Han and C3P0
    	    sendEvent(new AttackEvent(attack));
        }
    	sendBroadcast(new DoneSendingAttacksBroadcast()); //Leia sends a broadcast she has finished sending events.

    	this.subscribeEvent(AttacksCompletedEvent.class,c -> {
    	    complete(c,true);
            if (attacks.length==diary.getTotalAttacks().get()) {
                sendEvent(new DeactivationEvent());
            }
    	}); //once Leia was notified twice that attacks have been completed (both Han and C3PO transmitted they have finished) she can broadcast Deactivation


        this.subscribeBroadcast(TerminateBroadcast.class,c -> {
            diary.setLeiaTerminate();
            terminate();
        });
    }
}

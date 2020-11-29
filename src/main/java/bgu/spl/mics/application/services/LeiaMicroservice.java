package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;

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
	private int counter;

    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
		diary=Diary.getInstance();
		counter = 0;
    }

    @Override
    protected void initialize() {
    	for (Attack attack : attacks){
    	    sendEvent(new AttackEvent(attack));
        }
    	sendBroadcast(new DoneSendingAttacksBroadcast()); //Leia sends a broadcast she has finished sending events.
        notifyAll();
        //TODO double check what happens if this is sent before Han and C3Po register

    	this.subscribeEvent(AttacksCompletedEvent.class,c -> {
    	    counter++;
            if (counter == 2) {
                sendEvent(new DeactivationEvent());
                notifyAll();
            }
    	}); //once Leia was notified twice that attacks have been completed (both Han and C3PO transmitted they have finished) she can broadcast Deactivation


        this.subscribeBroadcast(TerminateBroadcast.class,c -> {
            diary.setLeiaTerminate();
            terminate();
        });
    }
}

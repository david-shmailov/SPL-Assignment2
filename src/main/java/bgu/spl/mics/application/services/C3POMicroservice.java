package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Ewoks;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {
    private Attack attack;
    private Ewoks ewoks;
	
    public C3POMicroservice() {
        super("C3PO");
    }
    public C3POMicroservice(Attack attack, Ewoks ewoks){
       super("C3PO");
       this.attack=attack;
       this.ewoks=ewoks;
    }


    @Override
    protected void initialize() {
        Callback<AttackEvent> callback1=new Callback<AttackEvent>() {
            @Override
            public void call(AttackEvent c) throws InterruptedException {
                for(Integer integer: attack.getSerial()){
                   while(!ewoks.EwokIsAvailable(integer.intValue())){};//todo probably this will block
                   ewoks.EwokIsAcquire(integer.intValue());
                }
                Thread.currentThread().sleep(attack.getDuration());//Attacking in process
                for(Integer integer: attack.getSerial()){
                    ewoks.EwokIsRelease(integer.intValue());
                }
                complete(c,true);
            }
        };
        this.subscribeEvent(AttackEvent.class,callback1);
        Callback<TerminateBroadcast> callback2=new Callback<TerminateBroadcast>() {
            @Override
            public void call(TerminateBroadcast c) throws InterruptedException {
                                  //todo add a call to Diary to take current time
                  terminate();
            }
        };
        this.subscribeBroadcast(TerminateBroadcast.class,callback2);


    }
}

package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.AttacksCompletedEvent;
import bgu.spl.mics.application.messages.DoneSendingAttacksBroadcast;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
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
    private Ewoks ewoks;
    private Diary diary;
	
    public C3POMicroservice() {
        super("C3PO");
    }//for Test
    public C3POMicroservice( Ewoks ewoks){
       super("C3PO");
       this.ewoks=ewoks;
       diary=Diary.getInstance();
    }


    @Override
    protected void initialize() {
        Callback<AttackEvent> callback1=new Callback<AttackEvent>(){
            @Override
            public void call(AttackEvent c) throws InterruptedException {
                Attack attack=c.getAttack();
                for(Integer integer: attack.getSerial()){
                   while(!ewoks.EwokIsAvailable(integer.intValue())){};//todo probably this will be dead-block
                   ewoks.EwokIsAcquire(integer.intValue());
                }
                Thread.currentThread().sleep(attack.getDuration());//Attacking in process
                for(Integer integer: attack.getSerial()){
                    ewoks.EwokIsRelease(integer.intValue());
                }
                complete(c,true);
                c.setDone();
                diary.addAttack();
            }
        };
        this.subscribeEvent(AttackEvent.class,callback1);
        Callback<TerminateBroadcast> callback2=new Callback<TerminateBroadcast>() {
            @Override
            public void call(TerminateBroadcast c) throws InterruptedException {
                   diary.setC3POTerminate();
                  terminate();
            }
        };
        this.subscribeBroadcast(TerminateBroadcast.class,callback2);

        /*
        When C3PO gets to the bottom of his message queue, he will find the DoneSendingAttacksBroadcast,
        which means C3PO can safely assume there are no more attacks that will need to be done by him, so he has finished
        and can transmit AttacksCompleted to let lea know he finished his part.
         */
        this.subscribeBroadcast(DoneSendingAttacksBroadcast.class, c -> {
            diary.setC3POFinish();
            sendEvent(new AttacksCompletedEvent());
        });


    }
}

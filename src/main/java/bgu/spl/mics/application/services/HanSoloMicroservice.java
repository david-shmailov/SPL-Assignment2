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
import java.util.concurrent.CountDownLatch;

/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {
    private Ewoks ewoks;
    private Diary diary;
    CountDownLatch latch;

    public HanSoloMicroservice() {
        super("Han");
    }// for test
    public HanSoloMicroservice(Ewoks ewoks, CountDownLatch latch){
        super("Han");
        this.ewoks=ewoks;
        diary=Diary.getInstance();
        this.latch=latch;
    }


    @Override
    protected void initialize() {
        Callback<AttackEvent> callback1=new Callback<AttackEvent>(){
            @Override
            public void call(AttackEvent c) throws InterruptedException {
                Attack attack=c.getAttack();
                for(Integer integer:attack.getSerial()){

                    ewoks.EwokIsAcquire(integer.intValue());////this method is blocking
                }
                Thread.currentThread().sleep(attack.getDuration());//Attacking in process
                for(Integer integer: attack.getSerial()){
                    ewoks.EwokIsRelease(integer.intValue());//this method notify the other service that need this ewok
                }
                //so C3P0 come out from wait
                complete(c,true);
                c.setDone();
                diary.addAttack();
            }
        };
        this.subscribeEvent(AttackEvent.class,callback1);
        Callback<TerminateBroadcast> callback2=new Callback<TerminateBroadcast>() {
            @Override
            public void call(TerminateBroadcast c) throws InterruptedException {
                diary.setHanSoloTerminate();
                terminate();
            }
        };
        this.subscribeBroadcast(TerminateBroadcast.class,callback2);
        /**
        When Han gets to the bottom of his message queue, he will find the DoneSendingAttacksBroadcast,
        which means han can safely assume there are no more attacks that will need to be done by him, so he has finished
        and can transmit AttacksCompleted to let lea know he finished his part.
         */
        this.subscribeBroadcast(DoneSendingAttacksBroadcast.class,c -> {
            diary.setHanSoloFinish();
            sendEvent(new AttacksCompletedEvent());

        });
        latch.countDown();
    }
}

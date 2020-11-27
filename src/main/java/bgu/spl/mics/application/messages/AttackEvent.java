package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.Future;

public class AttackEvent implements Event<Boolean> {
    private boolean done;


    public AttackEvent(){
        done=false;

    }



}

package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;

public class AttackEvent implements Event<Boolean> {

    private boolean done;

    public AttackEvent(){
        done=false;
    };

}

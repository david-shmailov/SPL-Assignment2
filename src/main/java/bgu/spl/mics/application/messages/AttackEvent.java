package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.Future;

public class AttackEvent implements Event<Boolean> {
    private boolean done;
    private Future<Boolean> result;

    public AttackEvent(){
        done=false;
        result=new Future<Boolean>();
    }

    public Future<Boolean> getFuture(){
        return result;
    }

}

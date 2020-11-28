package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.passiveObjects.Attack;

public class AttackEvent implements Event<Boolean> {
    private boolean done;
    private Attack attack;


    public AttackEvent(){// for test
        done=false;
    }
    public AttackEvent(Attack attack){
       done=false;
       this.attack=attack;
    }

    /**
     * getters
     *
     */
    public Attack getAttack(){ return attack;}

    /**
     * setters
     */
     public void setDone(){
         done=true;
     }


}

package bgu.spl.mics.application.passiveObjects;


import bgu.spl.mics.application.services.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
    private AtomicInteger totalAttacks=new AtomicInteger(0);
    private long HanSoloFinish;
    private long C3POFinish ;
    private long R2D2Deactivate;
    private long LeiaTerminate;
    private long HanSoloTerminate;
    private long C3POTerminate;
    private long R2D2Terminate;
    private long LandoTerminate;

    /**
     * constructor
     * @param
     */
    public Diary(){}

    /**
     * setters
     */
    public synchronized void addAttack(){totalAttacks.set(totalAttacks.get()+1);}
    public void setTerminate(C3POMicroservice c){C3POTerminate=System.currentTimeMillis();}
    public void setTerminate(HanSoloMicroservice c){HanSoloTerminate=System.currentTimeMillis();}
    public void setTerminate(LandoMicroservice c){LandoTerminate=System.currentTimeMillis();}
    public void setTerminate(LeiaMicroservice c){LeiaTerminate=System.currentTimeMillis();}
    public void setTerminate(R2D2Microservice c){R2D2Terminate=System.currentTimeMillis();}

    public void setFinish(HanSoloMicroservice c){HanSoloFinish=System.currentTimeMillis();}
    public void setFinish(C3POMicroservice c){C3POFinish=System.currentTimeMillis();}

    public void setDeactivate(R2D2Microservice c){R2D2Deactivate=System.currentTimeMillis();}


    /**
     * getters
     */
    public int getTotalAttacks(){return totalAttacks.get();}
    public long getHanSoloFinish(){return HanSoloFinish;}
    public long getC3POFinish(){return C3POFinish;}
    public long getR2D2Deactivate(){return R2D2Deactivate;}
    public long getLeiaTerminate(){return LeiaTerminate;}
    public long getHanSoloTerminate(){return HanSoloTerminate;}
    public long getC3POTerminate(){return C3POTerminate;}
    public long getR2D2Terminate(){return R2D2Terminate;}
    public long getLandoTerminate(){return LandoTerminate;}

}

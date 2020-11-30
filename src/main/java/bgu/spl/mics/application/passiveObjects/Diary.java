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
    private static volatile Diary diary=null; //TODO check if need volatile or synchronized in constructor
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
    private Diary(){}
    public static synchronized Diary getInstance(){
        if (diary==null) diary=new Diary();
        return diary;
    }

    /**
     * setters
     */
    public void addAttack(){totalAttacks.incrementAndGet();} //TODO check this update right
    public void setC3POTerminate(){C3POTerminate=System.currentTimeMillis();}
    public void setHanSoloTerminate(){HanSoloTerminate=System.currentTimeMillis();}
    public void setLandoTerminate(){LandoTerminate=System.currentTimeMillis();}
    public void setLeiaTerminate(){LeiaTerminate=System.currentTimeMillis();}
    public void setR2D2Terminate(){R2D2Terminate=System.currentTimeMillis();}

    public void setHanSoloFinish(){HanSoloFinish=System.currentTimeMillis();}
    public void setC3POFinish(){C3POFinish=System.currentTimeMillis();}

    public void setDeactivate(){R2D2Deactivate=System.currentTimeMillis();}


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

package bgu.spl.mics.application.passiveObjects;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
    private static class SingletonHolder{
        private static Diary diary=new Diary();
    }
  //  private static volatile Diary diary=null;
    private AtomicInteger totalAttacks=new AtomicInteger(0);
    private long HanSoloFinish=0;
    private long C3POFinish =0;
    private long R2D2Deactivate=0;
    private long LeiaTerminate=0;
    private long HanSoloTerminate=0;
    private long C3POTerminate=0;
    private long R2D2Terminate=0;
    private long LandoTerminate=0;

    /**
     * constructor
     */
    private Diary(){}
    public static Diary getInstance(){
      return SingletonHolder.diary;
    }

    /**
     * setters
     */
    public void addAttack(){totalAttacks.incrementAndGet();}
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
    public AtomicInteger getTotalAttacks(){return totalAttacks;}
    public long getHanSoloFinish(){return HanSoloFinish;}
    public long getC3POFinish(){return C3POFinish;}
    public long Finish(){return Math.abs(HanSoloFinish-C3POFinish);}
    public long getR2D2Deactivate(){return R2D2Deactivate;}
    public long getLeiaTerminate(){return LeiaTerminate;}
    public long getHanSoloTerminate(){return HanSoloTerminate;}
    public long getC3POTerminate(){return C3POTerminate;}
    public long getR2D2Terminate(){return R2D2Terminate;}
    public long getLandoTerminate(){return LandoTerminate;}


    /**
     * to string
     */

    @Override
    public String toString() {
        return "{\n" +
                "\"totalAttacks\":  " + totalAttacks +",\n"+
                "\"HanSoloFinish\":  " + HanSoloFinish +",\n"+
                "\"C3POFinish\":  " + C3POFinish +",\n"+
                "\"R2D2Deactivate\":  " + R2D2Deactivate +",\n"+
                "\"LeiaTerminate\":  "+ LeiaTerminate +",\n"+
                "\"HanSoloTerminate\":  " + HanSoloTerminate +",\n"+
                "\"C3POTerminate\":  " + C3POTerminate +",\n"+
                "\"R2D2Terminate\":  " + R2D2Terminate +",\n"+
                "\"LandoTerminate\":  " + LandoTerminate +"\n"+
                '}';
    }

    public void resetNumberAttacks() {
        totalAttacks=new AtomicInteger(0);
        HanSoloFinish=0;
        HanSoloTerminate=0;
        C3POFinish=0;
        C3POTerminate=0;
        LandoTerminate=0;
        LeiaTerminate=0;
        R2D2Deactivate=0;
        R2D2Terminate=0;

    }
}

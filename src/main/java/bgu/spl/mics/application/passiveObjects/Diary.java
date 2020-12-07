package bgu.spl.mics.application.passiveObjects;



import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
    private static volatile Diary diary=null;
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
    public static Diary getInstance(){
        if (diary==null) diary=new Diary();
        return diary;
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
    public int getTotalAttacks(){return totalAttacks.get();}
    public long getHanSoloFinish(){return HanSoloFinish;}
    public long getC3POFinish(){return C3POFinish;}
    public long Finish(){return Math.abs(HanSoloFinish-C3POFinish);}
    public long getR2D2Deactivate(){return R2D2Deactivate;}
    public long getLeiaTerminate(){return LeiaTerminate;}
    public long getHanSoloTerminate(){return HanSoloTerminate;}
    public long getC3POTerminate(){return C3POTerminate;}
    public long getR2D2Terminate(){return R2D2Terminate;}
    public long getLandoTerminate(){return LandoTerminate;}
    public long Terminate(){
        long max=Math.max(Math.max(Math.max(Math.max(LeiaTerminate,HanSoloTerminate),C3POTerminate),R2D2Terminate),
                LandoTerminate);
        long min=Math.max(HanSoloFinish,C3POFinish);
        return max-min;
    }

    /**
     * to string
     * @return
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
}

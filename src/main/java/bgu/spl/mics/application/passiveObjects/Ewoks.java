package bgu.spl.mics.application.passiveObjects;


import java.lang.reflect.Array;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks {
    private Ewok[] arrayOfEwok;

    public Ewoks(int total){
        arrayOfEwok=new Ewok[total+1];
        arrayOfEwok[0]=null;          //we dont have a Ewok with serial number 0
        for(int i=1 ; i<=total;i++){
            arrayOfEwok[i]=new Ewok(i); // create every single Ewok that needed at the program
        }
    }
    //////getters
    public Ewok getEwok(int seiralnum){return arrayOfEwok[seiralnum];}
    public boolean EwokIsAvailable (int serialnum){return arrayOfEwok[serialnum].available; }

    ///////setters
    public void EwokIsRelease(int serialnum){arrayOfEwok[serialnum].release();}
    public void EwokIsAcquire(int serialnum){arrayOfEwok[serialnum].acquire();}
}

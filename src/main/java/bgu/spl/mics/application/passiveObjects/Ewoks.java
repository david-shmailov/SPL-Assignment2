package bgu.spl.mics.application.passiveObjects;


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
    /**
       getters
     */
    public Ewok getEwok(int serial){return arrayOfEwok[serial];}
    public boolean EwokIsAvailable (int serial){return arrayOfEwok[serial].isAvailable(); }


    /**
     setters
     */
    public void EwokIsRelease(int serial){arrayOfEwok[serial].release();}
    public void EwokIsAcquire(int serial) throws InterruptedException {arrayOfEwok[serial].acquire();}
    //this method is blocking
}

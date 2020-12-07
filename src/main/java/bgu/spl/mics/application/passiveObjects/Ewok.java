package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
	private int serialNumber;
	private volatile boolean available;


	public Ewok (int serialNumber){
	    available = true;
	    this.serialNumber = serialNumber;
    }
  
    /**
     * Acquires an Ewok
     */
    public void acquire() throws InterruptedException {
        synchronized (this) {
            while (!available) {
                wait();
            }
            available = false;
        }
    }

    /**
     * release an Ewok
     */
    public void release() {
        if(!available) {
            available = true;
            synchronized(this){
                notifyAll();
            }
        }

    }

    /**
     * query if the ewok is available
     */
    public boolean isAvailable(){
        synchronized (this){
        return available;}
    }
    /**
     * getter where add
     */
    public int getSerialNumber(){return serialNumber;}
}

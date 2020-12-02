package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
	private int serialNumber;
	private boolean available;
	private Object lock=new Object();

	public Ewok (int serialNumber){
	    available = true;
	    this.serialNumber = serialNumber;
    }
  
    /**
     * Acquires an Ewok
     */
    public synchronized void acquire() throws InterruptedException {
            while (!available){lock.wait();}

            available=false;
    }

    /**
     * release an Ewok
     */
    public void release() { //Todo check if its necessary to add synchronized
        if(!available) {
            available = true;
            lock.notifyAll();
        }

    }

    /**
     * query if the ewok is available
     */
    public synchronized boolean isAvailable(){
        return available;
    }
    /**
     * getter where add
     */
    public int getSerialNumber(){return serialNumber;}
}

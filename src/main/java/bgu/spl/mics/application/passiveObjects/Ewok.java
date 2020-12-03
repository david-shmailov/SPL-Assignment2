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
	private Object lock=new Object();


	public Ewok (int serialNumber){
	    available = true;
	    this.serialNumber = serialNumber;
    }
  
    /**
     * Acquires an Ewok
     */
    public void acquire() throws InterruptedException {
        synchronized (lock) {
            while (!available) {
                lock.wait();
            }
            available = false;
        }
    }

    /**
     * release an Ewok
     */
    public void release() { //Todo check if its necessary to add synchronized (we put volatile)
        if(!available) {
            available = true;
            synchronized(lock){
            lock.notifyAll();}
        }

    }

    /**
     * query if the ewok is available
     */
    public boolean isAvailable(){
        synchronized (lock){
        return available;}
    }
    /**
     * getter where add
     */
    public int getSerialNumber(){return serialNumber;}
}

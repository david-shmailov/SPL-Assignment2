package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
	int serialNumber;
	boolean available;

	public Ewok (int serialNumber){
	    available = true;
	    this.serialNumber = serialNumber;
    }
  
    /**
     * Acquires an Ewok
     */
    public synchronized void acquire() {
        if(available)available=false;
    }

    /**
     * release an Ewok
     */
    public void release() { //Todo check if its necessary to add synchronized
        if(!available)available=true;
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

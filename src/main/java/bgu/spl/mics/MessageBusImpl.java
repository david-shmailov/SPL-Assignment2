package bgu.spl.mics;




import java.util.HashMap;
import java.util.LinkedList;

import java.util.Queue;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	private static MessageBusImpl bus=null;
	private HashMap<String,Queue<Message>> MapOfMicroService;
	private HashMap<Event,Future> MapOfFuture;
	private HashMap<Class<? extends Event>,Queue<MicroService>> MapOfEvents;
	private HashMap<Class<? extends Broadcast>,Queue<MicroService>> MapOfBroadcast;


	private MessageBusImpl() {// constructor
		MapOfMicroService = new HashMap<>();
		MapOfFuture = new HashMap<>();
		MapOfEvents = new HashMap<>();
		MapOfBroadcast = new HashMap<>();
	}


	public static synchronized MessageBusImpl getInstance(){
		if(bus==null) bus=new MessageBusImpl();
		return bus;
	}


	
	@Override
	public synchronized  <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		if( MapOfMicroService.containsKey(m.getName())){

			if(!MapOfEvents.containsKey(type)){//TODO add maybe synchronized
				Queue<MicroService> queue= new LinkedList<>();
				MapOfEvents.put(type, queue);
			}
			MapOfEvents.get(type).add(m);
		}
		else System.out.println("need to register before subscribe");
	}

	@Override
	public synchronized void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		if( MapOfMicroService.containsKey(m.getName())){
			if(!MapOfBroadcast.containsKey(type)) { //TODO add maybe synchronized
				Queue<MicroService> queue = new LinkedList<>();
				MapOfBroadcast.put(type,queue);
			}
			MapOfBroadcast.get(type).add(m);


		}
		else System.out.println("need to register before subscribe");
    }

	@Override
	public <T> void complete(Event<T> e, T result) {
		Future<T> future=MapOfFuture.get(e);
		future.resolve(result);
		MapOfFuture.remove(e);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		if (MapOfBroadcast.get(b.getClass()) != null) {
			Queue<MicroService> queueOfBroadcast = MapOfBroadcast.get(b.getClass());
			for (MicroService a : queueOfBroadcast) {
				MapOfMicroService.get(a.getName()).add(b);
			}
			synchronized (this) {
				notifyAll();
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		if (MapOfEvents.get(e.getClass()) != null) {
			Queue<MicroService> queueOfEvent = MapOfEvents.get(e.getClass());
			MicroService first = queueOfEvent.poll();               // round-robin implement
			queueOfEvent.add(first);                               // round-robin implement
			MapOfMicroService.get(first.getName()).add(e);
			Future<T> result = new Future<>();
			MapOfFuture.put(e, result);
			synchronized (this) {
				notifyAll();
			}
			return result;
		}
		return null;
	}

	@Override
	public void register(MicroService m) {
		Queue<Message> queue= new LinkedList<>();
		MapOfMicroService.put(m.getName(), queue);
	}

	@Override
	public void unregister(MicroService m) {
		MapOfMicroService.remove(m.getName());
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		synchronized (this) {
			while (MapOfMicroService.get(m.getName()).isEmpty()) {
				wait();
			}
			;// this call is blocking
			return MapOfMicroService.get(m.getName()).poll();
		}
	}
}

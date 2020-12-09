package bgu.spl.mics;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	private static class SingletonHolder{
		private static MessageBusImpl bus=new MessageBusImpl();
	}
	private HashMap<MicroService,Queue<Message>> MapOfMicroService;
	private HashMap<Event,Future> MapOfFuture;
	private HashMap<Class<? extends Event>,Queue<MicroService>> MapOfEvents;
	private HashMap<Class<? extends Broadcast>,Queue<MicroService>> MapOfBroadcast;
	private HashMap<MicroService,Vector<Class<? extends Broadcast>>> MapOfBroad;
	private HashMap<MicroService,Vector<Class<? extends Event>>> MapOfEvent;

	private MessageBusImpl() {// constructor
		MapOfMicroService = new HashMap<>();
		MapOfFuture = new HashMap<>();
		MapOfEvents = new HashMap<>();
		MapOfBroadcast = new HashMap<>();
		MapOfBroad=new HashMap<>();
		MapOfEvent=new HashMap<>();

	}


	public static MessageBusImpl getInstance(){
		return SingletonHolder.bus;
	}


	
	@Override
	public synchronized  <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		if( MapOfMicroService.containsKey(m)){
			if(!MapOfEvents.containsKey(type)){//TODO add maybe synchronized
				Queue<MicroService> queue= new LinkedList<>();
				MapOfEvents.put(type, queue);
			}
			MapOfEvents.get(type).add(m);
			MapOfEvent.get(m).add(type);

		}
		else System.out.println("need to register before subscribe");
	}

	@Override
	public synchronized void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		if( MapOfMicroService.containsKey(m)){
			if(!MapOfBroadcast.containsKey(type)) { //TODO add maybe synchronized
				Queue<MicroService> queue = new LinkedList<>();
				MapOfBroadcast.put(type,queue);
			}
			MapOfBroadcast.get(type).add(m);
			MapOfBroad.get(m).add(type);
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
				MapOfMicroService.get(a).add(b);
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
			MapOfMicroService.get(first).add(e);
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
	public synchronized void register(MicroService m) {
		Queue<Message> queue= new LinkedList<>();
		MapOfMicroService.put(m, queue);
		Vector<Class<? extends Event>> q=new Vector<>();
		MapOfEvent.put(m,q);
		Vector<Class<? extends Broadcast>> q1=new Vector<>();
		MapOfBroad.put(m,q1);
	}

	@Override
	public synchronized void unregister(MicroService m) {
		MapOfMicroService.remove(m);
		for(Class<? extends Event> Event:MapOfEvent.get(m)){
			MapOfEvents.get(Event).remove(m);
			if(MapOfEvents.get(Event).isEmpty())MapOfEvents.remove(Event);
		}
		for(Class<? extends Broadcast> bord:MapOfBroad.get(m) ){
			MapOfBroadcast.get(bord).remove(m);
			if(MapOfBroadcast.get(bord).isEmpty())MapOfBroadcast.remove(bord);
		}
		MapOfBroad.remove(m);
		MapOfEvent.remove(m);
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		synchronized (this) {
			while (MapOfMicroService.get(m).isEmpty()) {
				wait();;// this call is blocking
			}

			return MapOfMicroService.get(m).poll();
		}
	}
}

package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.services.C3POMicroservice;
import bgu.spl.mics.application.services.HanSoloMicroservice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {

    private MessageBusImpl messageBus;

    @BeforeEach
    void setUp() {
        messageBus= MessageBusImpl.getInstance();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void subscribeEvent() {// we check it twice on sendEvent test.

    }

    @Test
    void subscribeBroadcast() {// we check it twice on sendBroadcast test.
    }

    /**
    This test is meant to test the complete function. it also tests the register functions.
    the test registers 2 microservices, subscribes m2 to an AttackEvent, makes m1 send the event,
    and then tests that m2 completed it, indicating it received the event properly.
     */
    @Test
    void complete() {
        AttackEvent e= new AttackEvent();
        HanSoloMicroservice m1= new HanSoloMicroservice();
        C3POMicroservice m2= new C3POMicroservice();
        messageBus.register(m1);
        messageBus.register(m2);


        m2.subscribeEvent(e.getClass(), (event)->{});
        Future<Boolean> result = m1.sendEvent(e);

        m2.complete(e,true);
        try{
            assertTrue(result.get());
        }
        catch (Exception exp) {
            fail();
        }

    }
    /**
    This test is meant to test the sendBroadCast function.
    it initializes 3 microservices and one simple default anonymous broadcast message.
    it registers all 3 microservices, that way also tests the register function.
    subscribes m1 and m2 to the broadcast, and uses m3 to sent the broadcast.
    pulls the message awaiting for each m1 and m2 encapsulated in try catch to catch if the message queue is empty.
    (it is still unclear if awaitMessage throws exception, returns null or blocks the thread.)
    tests separately that m1 and m2 received the correct broadcast.
     */
    @Test
    void sendBroadcast() {
        Broadcast bor= new Broadcast(){};
        HanSoloMicroservice m1= new HanSoloMicroservice();
        HanSoloMicroservice m2= new HanSoloMicroservice();
        C3POMicroservice m3= new C3POMicroservice();
        messageBus.register(m1);
        messageBus.register(m2);
        messageBus.register(m3);



        m1.subscribeBroadcast(bor.getClass(),(bor1)-> {});
        m2.subscribeBroadcast(bor.getClass(),(bor1)->{});

        m3.sendBroadcast(bor);
        Message e1;
        try{

            e1 = messageBus.awaitMessage(m1);
        }
        catch (Exception exp){ e1=null;}

        Message e2;
        try{
            e2 = messageBus.awaitMessage(m2);
        }
        catch (Exception exp){ e2=null;}
        assertTrue(e1.equals(e2));
        assertTrue(e2.equals(bor));
    }

    /**
    this test also tests subscribeEvent, register and awaitMessage functions.
     */
    @Test
    void sendEvent() {
        AttackEvent e= new AttackEvent();
        HanSoloMicroservice m1= new HanSoloMicroservice();
        C3POMicroservice m2= new C3POMicroservice();
        messageBus.register(m1);
        messageBus.register(m2);
          m2.subscribeEvent(AttackEvent.class, new Callback<AttackEvent>() {
              @Override
              public void call(AttackEvent c) {

              }
          });
          m1.sendEvent(e);
          Message e2;
          try{
             e2 = messageBus.awaitMessage(m2);
          }
          catch (Exception exp){ e2=null;}

          assertTrue(e.equals(e2));
     }
    /**
    the following 3 functions have been tested in previous tests.
     */
    @Test
    void register() {

    }

    @Test
    void unregister() {
    }

    @Test
    void awaitMessage() {
    }
}
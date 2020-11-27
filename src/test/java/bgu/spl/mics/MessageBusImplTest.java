package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.services.C3POMicroservice;
import bgu.spl.mics.application.services.HanSoloMicroservice;
import bgu.spl.mics.application.services.LandoMicroservice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {

    private MessageBusImpl messageBus;

    @BeforeEach
    void setUp() {
        messageBus=new MessageBusImpl();
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

    @Test
    void complete() { //Todo complete this
        AttackEvent e= new AttackEvent();
        HanSoloMicroservice m1= new HanSoloMicroservice();
        C3POMicroservice m2= new C3POMicroservice();
        messageBus.register(m1);
        messageBus.register(m2);


        m2.subscribeEvent(AttackEvent.class, (event)->{});
        Future<Boolean> result = m1.sendEvent(e);

        m2.complete(e,true);
        assertTrue(result.get());

      //  messageBus.complete(e,result.get());// dont know yet what this fuc does, so i dont know what to check


    }

    @Test
    void sendBroadcast() {// TODO document to this test that we check also subscribeBoradcast,register and awaitMessage
        Broadcast bor= new Broadcast(){};
        HanSoloMicroservice m1= new HanSoloMicroservice();
        HanSoloMicroservice m2= new HanSoloMicroservice();
        C3POMicroservice m3= new C3POMicroservice();
        messageBus.register(m1);
        messageBus.register(m2);
        messageBus.register(m3);

        m1.subscribeBroadcast(bor.getClass(), (broadcast)->{});
        m2.subscribeBroadcast(bor.getClass(), (broadcast)->{});

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

    @Test
    void sendEvent() {// TODO document to this test that we check also subscribeEvent , register and awaitMessage
        AttackEvent e= new AttackEvent();
        HanSoloMicroservice m1= new HanSoloMicroservice();
        C3POMicroservice m2= new C3POMicroservice();
        messageBus.register(m1);
        messageBus.register(m2);

          m2.subscribeEvent(AttackEvent.class, (event)->{});
          m1.sendEvent(e);
          Message e2;
          try{
             e2 = messageBus.awaitMessage(m2);
          }
          catch (Exception exp){ e2=null;}

          assertTrue(e.equals(e2));
     }

    @Test
    void register() {// we check it twice on sendEvent and sendBroadcast tests.

    }

    @Test
    void unregister() { //we dont need to test it, as they wrote in forum.
    }

    @Test
    void awaitMessage() {// we check it twice on sendEvent and sendBroadcast tests.
    }
}
package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;


public class FutureTest {

    private Future<String> future;

    @BeforeEach
    public void setUp(){
        future = new Future<String>();

    }

    @AfterEach
    public void tearDown() {}
    @Test
    public void testResolve(){
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
        try {
            assertTrue(str.equals(future.get()));
        }catch (InterruptedException e){};
    }

    @Test
    void get() {
         future.resolve("someResult");
         try {
             assertNotNull(future.get());
             assertEquals(future.get(),"someResult");
         }catch (InterruptedException e){};

    }

    @Test
    void resolve() {
        assertFalse(future.isDone());
        future.resolve("someResult");
        assertTrue(future.isDone());
        try {
            assertEquals(future.get(),"someResult");
        }catch (InterruptedException e){};

    }

    @Test
    void isDone() {
        assertFalse(future.isDone());
        future.resolve("someResult");
        assertTrue(future.isDone());
    }
    //TODO make sure if we need to test get(timeout)


    @Test
    void testGet() {
        TimeUnit unit= TimeUnit.MICROSECONDS;
        try {
            unit.sleep(2);
        }
        catch (Exception e){};
        assertFalse(future.isDone());
        future.resolve("someResult");
        try {
            unit.sleep(2);
        }
        catch (Exception e){};
        assertTrue(future.isDone());
    }
}

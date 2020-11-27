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
        System.out.println("future testing");
    }

    @AfterEach
    public void tearDown() {}
    @Test
    public void testResolve(){
        String str = "someResult";
        //future = new Future<String>();
        future.resolve(str);
        assertTrue(future.isDone());
        assertTrue(str.equals(future.get()));
    }

    @Test
    void get() {
         future.resolve("someResult");
         assertNotNull(future.get());
         assertEquals(future.get(),"someResult");
    }

    @Test
    void resolve() {
        assertFalse(!future.isDone());
        future.resolve("someResult");
        assertTrue(future.isDone());
        assertEquals(future.get(),"someResult");
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
        TimeUnit unit= TimeUnit.SECONDS;
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

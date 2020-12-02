package bgu.spl.mics.application.passiveObjects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EwokTest {
    private Ewok ewok;
    @BeforeEach
    void setUp() {
        ewok = new Ewok(0);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void acquire() {
        try {
            ewok.acquire();
        }catch (InterruptedException e){}
        assertFalse(ewok.isAvailable());
    }

    @Test
    void release() {
        try {
            ewok.acquire();
        }catch (InterruptedException e){}

        ewok.release();
        assertTrue(ewok.isAvailable());
    }

    @Test
    void isAvailable() {
        assertTrue(ewok.isAvailable());
        try {
            ewok.acquire();
        }catch (InterruptedException e){}
        assertFalse(ewok.isAvailable());
        ewok.release();
        assertTrue(ewok.isAvailable());
    }
}
package bgu.spl.mics.application.passiveObjects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EwokTest {
    private Ewok ewok;
    @BeforeEach
    void setUp() {
        ewok = new Ewok();
        System.out.println("ewok testing");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void acquire() {
        ewok.acquire();
        assertFalse(ewok.isAvailable());
    }

    @Test
    void release() {
        ewok.acquire();
        ewok.release();
        assertTrue(ewok.isAvailable());
    }

    @Test
    void isAvailable() {
        assertTrue(ewok.isAvailable());
        ewok.acquire();
        assertFalse(ewok.isAvailable());
        ewok.release();
        assertTrue(ewok.isAvailable());
    }
}
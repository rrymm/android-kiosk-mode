package com.example.igorpavlov.myapplication;

import org.junit.Test;

import ru.spicedevelopers.igorpavlov.kioskmode.DropboxService;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    public void check_DropBoxConnection() {
        assertNotEquals(DropboxService.getInstance().VideoFiles().size(), 0);
    }
}
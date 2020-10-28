package com.crud.tasks.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class AttachmentsByTypeTest {

    @Test
    public void testGetTrello() {
        //Given
        AttachmentsByType attachmentsByType1 = new AttachmentsByType();

        Trello trello = new Trello(1, 2);
        AttachmentsByType attachmentsByType2 = new AttachmentsByType(trello);

        //When
        Trello retrievedTrello1 = attachmentsByType1.getTrello();
        Trello retrievedTrello2 = attachmentsByType2.getTrello();

        //Then
        assertEquals(null, retrievedTrello1);
        assertEquals(trello, retrievedTrello2);
    }
}
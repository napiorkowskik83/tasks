package com.crud.tasks.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class BadgesTest {

    @Test
    public void testGetters() {
        //Given
        Badges badges1 = new Badges();
        Trello trello = new Trello(1,2);
        AttachmentsByType attachmentsByType = new AttachmentsByType(trello);
        Badges badges2 = new Badges(1, attachmentsByType);

        //When
        int retrievedVotes1 = badges1.getVotes();
        AttachmentsByType attachmentsByType1 = badges1.getAttachmentsByType();
        int retrievedVotes2 = badges2.getVotes();
        AttachmentsByType attachmentsByType2 = badges2.getAttachmentsByType();

        //Then
        assertEquals(0, retrievedVotes1);
        assertEquals(null, attachmentsByType1);
        assertEquals(1, retrievedVotes2);
        assertEquals(attachmentsByType, attachmentsByType2);

    }
}
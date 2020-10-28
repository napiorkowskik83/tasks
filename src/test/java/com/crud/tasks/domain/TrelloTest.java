package com.crud.tasks.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrelloTest {

    @Test
    public void testGetters() {
        //Given
        Trello trello1  = new Trello();
        Trello trello2 = new Trello(1, 2);

        //When
        int board1 = trello1.getBoard();
        int card1 = trello1.getCard();
        int board2 = trello2.getBoard();
        int card2 = trello2.getCard();

        //Then
        assertEquals(0, board1);
        assertEquals(0, card1);
        assertEquals(1, board2);
        assertEquals(2, card2);
    }

}
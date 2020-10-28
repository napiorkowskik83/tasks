package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    TrelloService service;

    @Mock
    TrelloClient client;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void testFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task", trelloLists));

        when(client.getTrelloBoards()).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> retrievedTrelloBoards = service.fetchTrelloBoards();

        //Then
        assertNotNull(retrievedTrelloBoards);
        assertEquals(1, retrievedTrelloBoards.size());

        retrievedTrelloBoards.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("my_task", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void testCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_cardDto",
                "test description", "test position", "1" );
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "test_created_card", "test_URL");

        when(client.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto testCreatedCard = service.createTrelloCard(trelloCardDto);

        //Then
        assertNotNull(testCreatedCard);
        assertEquals("1", testCreatedCard.getId());
        assertEquals("test_created_card", testCreatedCard.getName());
        assertEquals("test_URL", testCreatedCard.getShortUrl());
    }
}
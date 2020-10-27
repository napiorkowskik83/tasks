package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {
    private static final String NAME_1 = "test name no. 1";
    private static final String NAME_2 = "test name no. 2";
    private static final String DESCRIPTION = "test description";
    private static final String POSITION = "test position";
    private static final String ID_1 = "1";
    private static final String ID_2 = "2";

    @Autowired
    TrelloMapper trelloMapper;


    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard(NAME_1, DESCRIPTION, POSITION, ID_1);
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals(NAME_1, trelloCardDto.getName());
        assertEquals(DESCRIPTION, trelloCardDto.getDescription());
        assertEquals(POSITION, trelloCardDto.getPos());
        assertEquals(ID_1, trelloCardDto.getListId());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(NAME_2, DESCRIPTION, POSITION, ID_2);
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals(NAME_2, trelloCard.getName());
        assertEquals(DESCRIPTION, trelloCard.getDescription());
        assertEquals(POSITION, trelloCard.getPos());
        assertEquals(ID_2, trelloCard.getListId());
    }

    @Test
    public void testMapToList() {
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto(ID_1, NAME_1, false);
        TrelloListDto trelloListDto2 = new TrelloListDto(ID_2, NAME_2, true);
        List<TrelloListDto> trelloListsDto = new ArrayList<>();
        trelloListsDto.add(trelloListDto1);
        trelloListsDto.add(trelloListDto2);

        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListsDto);

        //Then
        assertEquals(2, trelloLists.size());
        assertEquals(ID_1, trelloLists.get(0).getId());
        assertEquals(NAME_1, trelloLists.get(0).getName());
        assertFalse(trelloLists.get(0).isClosed());
        assertEquals(ID_2, trelloLists.get(1).getId());
        assertEquals(NAME_2, trelloLists.get(1).getName());
        assertTrue(trelloLists.get(1).isClosed());
    }

    @Test
    public void testMapToListDto() {
        //Given
        TrelloList trelloList1 = new TrelloList(ID_1, NAME_1, false);
        TrelloList trelloList2 = new TrelloList(ID_2, NAME_2, true);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList1);
        trelloLists.add(trelloList2);

        //When
        List<TrelloListDto> trelloListsDto = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertEquals(2, trelloListsDto.size());
        assertEquals(ID_1, trelloListsDto.get(0).getId());
        assertEquals(NAME_1, trelloListsDto.get(0).getName());
        assertFalse(trelloListsDto.get(0).isClosed());
        assertEquals(ID_2, trelloListsDto.get(1).getId());
        assertEquals(NAME_2, trelloListsDto.get(1).getName());
        assertTrue(trelloListsDto.get(1).isClosed());
    }

    @Test
    public void testMapToBoards() {
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto(ID_1, NAME_1, false);
        List<TrelloListDto> trelloListsDto1 = new ArrayList<>();
        trelloListsDto1.add(trelloListDto1);

        TrelloListDto trelloListDto2 = new TrelloListDto(ID_2, NAME_2, true);
        List<TrelloListDto> trelloListsDto2 = new ArrayList<>();
        trelloListsDto2.add(trelloListDto1);
        trelloListsDto2.add(trelloListDto2);

        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto(ID_1, NAME_1, trelloListsDto1);
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto(ID_2, NAME_2, trelloListsDto2);

        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(trelloBoardDto1);
        trelloBoardsDto.add(trelloBoardDto2);

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);

        //Then
        assertEquals(2, trelloBoards.size());
        assertEquals(ID_1, trelloBoards.get(0).getId());
        assertEquals(NAME_1, trelloBoards.get(0).getName());
        assertEquals(1, trelloBoards.get(0).getLists().size());
        assertEquals(ID_2, trelloBoards.get(1).getId());
        assertEquals(NAME_2, trelloBoards.get(1).getName());
        assertEquals(2, trelloBoards.get(1).getLists().size());
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        TrelloList trelloList1 = new TrelloList(ID_1, NAME_1, false);
        List<TrelloList> trelloLists1 = new ArrayList<>();
        trelloLists1.add(trelloList1);

        TrelloList trelloList2 = new TrelloList(ID_2, NAME_2, true);
        List<TrelloList> trelloLists2 = new ArrayList<>();
        trelloLists2.add(trelloList1);
        trelloLists2.add(trelloList2);

        TrelloBoard trelloBoard1 = new TrelloBoard(ID_1, NAME_1, trelloLists1);
        TrelloBoard trelloBoard2 = new TrelloBoard(ID_2, NAME_2, trelloLists2);

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertEquals(2, trelloBoardsDto.size());
        assertEquals(ID_1, trelloBoardsDto.get(0).getId());
        assertEquals(NAME_1, trelloBoardsDto.get(0).getName());
        assertEquals(1, trelloBoardsDto.get(0).getLists().size());
        assertEquals(ID_2, trelloBoardsDto.get(1).getId());
        assertEquals(NAME_2, trelloBoardsDto.get(1).getName());
        assertEquals(2, trelloBoardsDto.get(1).getLists().size());
    }
}
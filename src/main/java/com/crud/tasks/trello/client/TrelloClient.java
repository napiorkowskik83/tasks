package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String trelloUsername;


    public List<TrelloBoardDto> getTrelloBoards(){

        URI url = createUriForGetTrelloBoards();

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);

        if (Optional.ofNullable(boardsResponse).isPresent()) {
            return Arrays.asList(boardsResponse);
        }
        return new ArrayList<>();
    }

    private URI createUriForGetTrelloBoards() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/"
                + trelloUsername + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
        return url;
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto){

        URI url = createUriForCreateNewCard(trelloCardDto);

        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);

    }

    private URI createUriForCreateNewCard(TrelloCardDto trelloCardDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards/")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .queryParam("badges", trelloCardDto.getBadges())
                .queryParam("votes", trelloCardDto.getBadges().getVotes())
                .queryParam("attachmentsByType", trelloCardDto.getBadges().getAttachmentsByType())
                .queryParam("trello", trelloCardDto.getBadges().getAttachmentsByType().getTrello())
                .queryParam("board", trelloCardDto.getBadges().getAttachmentsByType().getTrello().getBoard())
                .queryParam("card", trelloCardDto.getBadges().getAttachmentsByType().getTrello().getCard()).build().encode().toUri();
        return url;
    }
}

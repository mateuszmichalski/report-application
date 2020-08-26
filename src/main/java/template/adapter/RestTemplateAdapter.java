package template.adapter;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import template.controller.QueryCriteria;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestTemplateAdapter {

    private static final String FILM_URL = "http://localhost:8080/api/films";
    private static final String PERSONS_URL = "http://localhost:8080/api/people";
    private static final String PLANETS_URL = "http://localhost:8080/api/planets";
    private static final String CHARACTER_PHRASE = "characterPhrase";
    private static final String PLANET_NAME = "planetName";
    private static final String ACCEPT = "Accept";
    private RestTemplate restTemplate = new RestTemplate();

    public List<FilmResponse> getFilmsData(QueryCriteria queryCriteria) {

        String uri = UriComponentsBuilder.fromHttpUrl(FILM_URL)
                .queryParam(CHARACTER_PHRASE, queryCriteria.getCharacterPhrase())
                .queryParam(PLANET_NAME, queryCriteria.getPlanetName())
                .toUriString();

        HttpEntity<FilmResponse[]> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                prepareEntity(),
                FilmResponse[].class);

        return Arrays.asList(response.getBody());
    }

    public Map<String, PersonResponse> getPersonData(String characterPhrase) {
        String uri = UriComponentsBuilder.fromHttpUrl(PERSONS_URL)
                .queryParam(CHARACTER_PHRASE, characterPhrase)
                .toUriString();

        HttpEntity<PersonResponse[]> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                prepareEntity(),
                PersonResponse[].class);

        return Arrays.stream(response.getBody()).collect(Collectors.toMap(PersonResponse::getCharacterId, o -> o));
    }

    public Map<String, String> getPlanetsData(String planetName) {
        String uri = UriComponentsBuilder.fromHttpUrl(PLANETS_URL)
                .queryParam(PLANET_NAME, planetName)
                .toUriString();

        HttpEntity<PlanetResponse[]> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                prepareEntity(),
                PlanetResponse[].class);

        return Arrays.stream(response.getBody()).collect(Collectors.toMap(PlanetResponse::getPlanetId, PlanetResponse::getPlanetName));
    }

    private HttpEntity<?> prepareEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}

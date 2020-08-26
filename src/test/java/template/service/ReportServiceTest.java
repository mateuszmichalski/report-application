package template.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import template.adapter.FilmResponse;
import template.adapter.PersonResponse;
import template.adapter.RestTemplateAdapter;
import template.controller.QueryCriteria;
import template.model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    @Mock
    RestTemplateAdapter restTemplateAdapter;
    @Mock
    ReportRepository reportRepository;
    @InjectMocks
    ReportService reportService = new ReportService(restTemplateAdapter,reportRepository);

    private static ReportDto expectedReportDto;
    private static ReportEntity expectedReportEntity;
    private static QueryCriteria queryCriteria;

    @BeforeAll
    static void setData() {
        expectedReportDto = ReportDto.builder()
                .id(1L)
                .queryCriteriaCharacterPhrase("testCharacterPhrase")
                .queryCriteriaPlanetName("testPlanetName")
                .resultEntityList(
                        Arrays.asList(ReportResultDto.builder()
                                .filmId("1")
                                .filmName("testFilmName")
                                .characterId("2")
                                .characterName("testCharacterName")
                                .planetId("3")
                                .planetName("testPlanetName")
                                .build()

                        ))
                .build();
        expectedReportEntity = ReportEntity.builder()
                .id(1L)
                .queryCriteriaCharacterPhrase("testCharacterPhrase")
                .queryCriteriaPlanetName("testPlanetName")
                .resultEntityList(
                        Arrays.asList(ReportResultEntity.builder()
                                .filmId("1")
                                .filmName("testFilmName")
                                .characterId("2")
                                .characterName("testCharacterName")
                                .planetId("3")
                                .planetName("testPlanetName")
                                .build()

                        ))
                .build();
        queryCriteria = new QueryCriteria("testCharacterPhrase", "testPlanetName");
    }

    @BeforeEach
    void setMockOutput() {
        lenient().when(reportRepository.findById(1L)).thenReturn(Optional.of(expectedReportEntity));
        lenient().when(reportRepository.findAll()).thenReturn(Arrays.asList(expectedReportEntity));
    }

    @Test
    void updateOrCreateReportWhenAlreadyExist() {
        FilmResponse expectedFilmResponse = new FilmResponse();
        expectedFilmResponse.setFilmId("1");
        expectedFilmResponse.setFilmName("testFilmName");
        expectedFilmResponse.setCharacterIds(Arrays.asList("1", "2", "3", "4"));

        HashMap expectedPersonResponse = new HashMap<String, PersonResponse>();
        expectedPersonResponse.put("2", new PersonResponse("2", "testName2", "3"));
        expectedPersonResponse.put("3", new PersonResponse("3", "testName3", "2"));
        expectedPersonResponse.put("4", new PersonResponse("4", "testName4", "4"));

        HashMap expectedPlanetResponse = new HashMap<String, String>();
        expectedPlanetResponse.put("3", "testPlanet3");
        expectedPlanetResponse.put("4", "testPlanet4");

        ReportEntity expectedReportEntity = new ReportEntity();
        expectedReportEntity.setQueryCriteriaCharacterPhrase(queryCriteria.getCharacterPhrase());
        expectedReportEntity.setQueryCriteriaPlanetName(queryCriteria.getPlanetName());
        expectedReportEntity.setResultEntityList(Arrays.asList( ReportResultEntity.builder()
                .filmId("1")
                .filmName("testFilmName")
                .characterId("2")
                .characterName("testName2")
                .planetId("3")
                .planetName("testPlanet3")
                .build(),
                ReportResultEntity.builder()
                .filmId("1")
                .filmName("testFilmName")
                .characterId("4")
                .characterName("testName4")
                .planetId("4")
                .planetName("testPlanet4")
                .build()));

        final ArgumentCaptor<ReportEntity> captor = ArgumentCaptor.forClass(ReportEntity.class);

        lenient().when(reportRepository.findById(1L)).thenReturn(Optional.of(expectedReportEntity));
        lenient().when(restTemplateAdapter.getFilmsData(queryCriteria)).thenReturn(Arrays.asList(expectedFilmResponse));
        lenient().when(restTemplateAdapter.getPersonData(queryCriteria.getCharacterPhrase())).thenReturn(expectedPersonResponse);
        lenient().when(restTemplateAdapter.getPlanetsData(queryCriteria.getPlanetName())).thenReturn(expectedPlanetResponse);

        reportService.updateOrCreateReport(1L, queryCriteria);

        verify(restTemplateAdapter, times(1)).getFilmsData(queryCriteria);
        verify(restTemplateAdapter, times(1)).getPersonData(queryCriteria.getCharacterPhrase());
        verify(restTemplateAdapter, times(1)).getPlanetsData(queryCriteria.getPlanetName());
        verify(reportRepository, times(1)).save(any());
        verify(reportRepository).save(captor.capture());

        assertEquals(expectedReportEntity, captor.getValue());
    }

    @Test
    void updateOrCreateReportWhenNotExist() {
        FilmResponse expectedFilmResponse = new FilmResponse();
        expectedFilmResponse.setFilmId("1");
        expectedFilmResponse.setFilmName("testFilmName");
        expectedFilmResponse.setCharacterIds(Arrays.asList("1", "2", "3", "4"));

        HashMap expectedPersonResponse = new HashMap<String, PersonResponse>();
        expectedPersonResponse.put("2", new PersonResponse("2", "testName2", "3"));
        expectedPersonResponse.put("3", new PersonResponse("3", "testName3", "2"));
        expectedPersonResponse.put("4", new PersonResponse("4", "testName4", "4"));

        HashMap expectedPlanetResponse = new HashMap<String, String>();
        expectedPlanetResponse.put("3", "testPlanet3");
        expectedPlanetResponse.put("4", "testPlanet4");

        ReportEntity expectedReportEntity = new ReportEntity();
        expectedReportEntity.setQueryCriteriaCharacterPhrase(queryCriteria.getCharacterPhrase());
        expectedReportEntity.setQueryCriteriaPlanetName(queryCriteria.getPlanetName());
        expectedReportEntity.setResultEntityList(Arrays.asList( ReportResultEntity.builder()
                        .filmId("1")
                        .filmName("testFilmName")
                        .characterId("2")
                        .characterName("testName2")
                        .planetId("3")
                        .planetName("testPlanet3")
                        .build(),
                ReportResultEntity.builder()
                        .filmId("1")
                        .filmName("testFilmName")
                        .characterId("4")
                        .characterName("testName4")
                        .planetId("4")
                        .planetName("testPlanet4")
                        .build()));

        final ArgumentCaptor<ReportEntity> captor = ArgumentCaptor.forClass(ReportEntity.class);

        lenient().when(reportRepository.findById(1L)).thenReturn(Optional.of(expectedReportEntity));
        lenient().when(restTemplateAdapter.getFilmsData(queryCriteria)).thenReturn(Arrays.asList(expectedFilmResponse));
        lenient().when(restTemplateAdapter.getPersonData(queryCriteria.getCharacterPhrase())).thenReturn(expectedPersonResponse);
        lenient().when(restTemplateAdapter.getPlanetsData(queryCriteria.getPlanetName())).thenReturn(expectedPlanetResponse);

        reportService.updateOrCreateReport(1L, queryCriteria);

        verify(restTemplateAdapter, times(1)).getFilmsData(queryCriteria);
        verify(restTemplateAdapter, times(1)).getPersonData(queryCriteria.getCharacterPhrase());
        verify(restTemplateAdapter, times(1)).getPlanetsData(queryCriteria.getPlanetName());
        verify(reportRepository, times(1)).save(any());
        verify(reportRepository).save(captor.capture());

        assertEquals(expectedReportEntity, captor.getValue());
    }

    @Test
    void getReportById() {
        ReportDto obtainedReportDto = reportService.getReportById(1L);

        assertEquals(expectedReportDto, obtainedReportDto);
        verify(reportRepository, times(1)).findById(1L);
    }

    @Test
    void getAllReports() {
        List<ReportDto> obtainedReportDtos = reportService.getAllReports();

        assertEquals(Arrays.asList(expectedReportDto), obtainedReportDtos);
        verify(reportRepository, times(1)).findAll();
    }

    @Test
    void deleteReportById() {
        reportService.deleteReportById(1L);

        verify(reportRepository, times(1)).delete(any());
    }

    @Test
    void deleteAllReports() {
        reportService.deleteAllReports();

        verify(reportRepository, times(1)).deleteAll();
    }
}
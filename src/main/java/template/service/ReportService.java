package template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import template.adapter.FilmResponse;
import template.adapter.PersonResponse;
import template.adapter.PlanetResponse;
import template.adapter.RestTemplateAdapter;
import template.controller.QueryCriteria;
import template.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class ReportService {

    private RestTemplateAdapter restTemplateAdapter;
    private ReportRepository reportRepository;

    @Autowired
    public ReportService(RestTemplateAdapter restTemplateAdapter,
                         ReportRepository reportRepository) {
        this.restTemplateAdapter = restTemplateAdapter;
        this.reportRepository = reportRepository;
    }

    public void updateOrCreateReport(Long reportId, QueryCriteria queryCriteria) {
        ReportEntity entity = reportRepository.findById(reportId)
                .map(reportEntity -> updateReportEntity(reportEntity, queryCriteria))
                .orElseGet(() -> createReportEntity(reportId, queryCriteria));
        reportRepository.save(entity);
    }

    public ReportDto getReportById(Long reportId) {
        return reportRepository.findById(reportId)
                .map(ReportDto::new).orElse(null);
    }

    public List<ReportDto> getAllReports() {
        Iterable<ReportEntity> reportEntities = reportRepository.findAll();
        return StreamSupport.stream(reportEntities.spliterator(), false)
                .map(ReportDto::new)
                .collect(Collectors.toList());
    }

    public void deleteReportById(Long reportId) {
        reportRepository.findById(reportId)
            .ifPresent(reportEntity -> reportRepository.delete(reportEntity));
    }

    public void deleteAllReports() {
        reportRepository.deleteAll();
    }

    private ReportEntity updateReportEntity(ReportEntity reportEntity, QueryCriteria queryCriteria) {
        return createReportEntity(reportEntity.getId(), queryCriteria);
    }

    private ReportEntity createReportEntity(Long reportId, QueryCriteria queryCriteria) {
        List<FilmResponse> filmsData = restTemplateAdapter.getFilmsData(queryCriteria);
        Map<String, PersonResponse> personResponses = restTemplateAdapter.getPersonData(queryCriteria.getCharacterPhrase());
        Map<String, String> planetResponses = restTemplateAdapter.getPlanetsData(queryCriteria.getPlanetName());

        List<ReportResultEntity> reportResultEntities = filmsData.stream()
                .flatMap(filmResponse -> prepareReportResult(filmResponse))
                .filter(reportResult -> personResponses.containsKey(reportResult.getCharacterId()))
                .map(reportResult -> fillPersonNameAndPlanetId(personResponses, reportResult))
                .filter(reportResult -> planetResponses.containsKey(reportResult.getPlanetId()))
                .map(reportResult -> fillPlanetName(planetResponses, reportResult))
                .collect(Collectors.toList());

        return ReportEntity.builder()
                .id(reportId)
                .queryCriteriaCharacterPhrase(queryCriteria.getCharacterPhrase())
                .queryCriteriaPlanetName(queryCriteria.getPlanetName())
                .resultEntityList(reportResultEntities)
                .build();
    }

    private Stream<ReportResultEntity> prepareReportResult(FilmResponse filmResponse) {
        return filmResponse.getCharacterIds().stream()
                .map(characterId -> ReportResultEntity.builder()
                        .filmId(filmResponse.getFilmId())
                        .filmName(filmResponse.getFilmName())
                        .characterId(characterId)
                        .build()
        );
    }

    private ReportResultEntity fillPersonNameAndPlanetId(Map<String, PersonResponse> personResponses, ReportResultEntity reportResultEntity) {
        PersonResponse personResponse = personResponses.get(reportResultEntity.getCharacterId());
        reportResultEntity.setCharacterName(personResponse.getCharacterName());
        reportResultEntity.setPlanetId(personResponse.getHomeLandId());
        return reportResultEntity;
    }

    private ReportResultEntity fillPlanetName(Map<String, String> planetResponses, ReportResultEntity reportResultEntity) {
        reportResultEntity.setPlanetName(planetResponses.get(reportResultEntity.getPlanetId()));
        return reportResultEntity;
    }
}

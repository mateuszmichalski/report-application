package template.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import template.model.ReportDto;
import template.model.ReportResultDto;
import template.service.ReportService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {


    @Mock
    private ReportService reportService;
    @InjectMocks
    private final ReportController reportController = new ReportController(reportService);

    private static ReportDto reportDto;
    private static QueryCriteria queryCriteria;

    @BeforeAll
    static void setData() {
        reportDto = ReportDto.builder()
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
        queryCriteria = new QueryCriteria("testCharacterPhrase", "testPlanetName");
    }

    @BeforeEach
    void setMockOutput() {
        lenient().when(reportService.getReportById(1L)).thenReturn(reportDto);
        lenient().when(reportService.getAllReports()).thenReturn(Arrays.asList(reportDto));
    }

    @DisplayName("Test putReport + helloRepository")
    @Test
    void putReport() {

        ResponseEntity report = reportController.putReport(1L, queryCriteria);

        assertEquals(HttpStatus.NO_CONTENT, report.getStatusCode());
        assertEquals(null, report.getBody());
    }

    @Test
    void getReport() {
        ResponseEntity report = reportController.getReport(1L);

        assertEquals(HttpStatus.OK, report.getStatusCode());
        assertEquals(reportDto, report.getBody());
    }

    @Test
    void getAllReports() {
        ResponseEntity report = reportController.getAllReports();

        assertEquals(HttpStatus.OK, report.getStatusCode());
        assertEquals(Arrays.asList(reportDto), report.getBody());
    }

    @Test
    void deleteReport() {
        ResponseEntity report = reportController.deleteReport(1L);

        assertEquals(HttpStatus.OK, report.getStatusCode());
        assertEquals(null, report.getBody());
    }

    @Test
    void deleteAllReports() {
        ResponseEntity report = reportController.deleteAllReports();

        assertEquals(HttpStatus.OK, report.getStatusCode());
        assertEquals(null, report.getBody());
    }
}
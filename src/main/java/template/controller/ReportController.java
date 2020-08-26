package template.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import template.model.ReportDto;
import template.service.ReportService;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@Slf4j
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PutMapping("/reports/{reportId}")
    public ResponseEntity putReport(@PathVariable Long reportId,
                            @RequestBody QueryCriteria queryCriteria) {
        try {
            reportService.updateOrCreateReport(reportId, queryCriteria);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/reports/{reportId}")
    public ResponseEntity getReport(@PathVariable Long reportId) {
        ReportDto reportDto;
        try {
            reportDto = reportService.getReportById(reportId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(reportDto);
    }

    @GetMapping("/reports")
    public ResponseEntity getAllReports() {
        List<ReportDto> reportDtos;
        try {
            reportDtos = reportService.getAllReports();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(reportDtos);
    }

    @DeleteMapping("/reports/{reportId}")
    public ResponseEntity deleteReport(@PathVariable Long reportId) {
        try {
            reportService.deleteReportById(reportId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reports")
    public ResponseEntity deleteAllReports() {
        try {
            reportService.deleteAllReports();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }
}

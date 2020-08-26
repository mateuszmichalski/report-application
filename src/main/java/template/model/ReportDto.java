package template.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ReportDto {

    @JsonProperty("report_id")
    private Long id;
    @JsonProperty("query_criteria_character_phrase")
    private String queryCriteriaCharacterPhrase;
    @JsonProperty("query_criteria_planet_name")
    private String queryCriteriaPlanetName;
    @JsonProperty("result")
    private List<ReportResultDto> resultEntityList;

    public ReportDto() {
    }

    public ReportDto(ReportEntity reportEntity) {
        this.id = reportEntity.getId();
        this.queryCriteriaCharacterPhrase = reportEntity.getQueryCriteriaCharacterPhrase();
        this.queryCriteriaPlanetName = reportEntity.getQueryCriteriaPlanetName();
        this.resultEntityList = reportEntity.getResultEntityList().stream()
                .map(ReportResultDto::new)
                .collect(Collectors.toList());
    }

    public ReportDto(Long id, String queryCriteriaCharacterPhrase, String queryCriteriaPlanetName, List<ReportResultDto> resultEntityList) {
        this.id = id;
        this.queryCriteriaCharacterPhrase = queryCriteriaCharacterPhrase;
        this.queryCriteriaPlanetName = queryCriteriaPlanetName;
        this.resultEntityList = resultEntityList;
    }
}

package template.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "report")
@Data
@Builder
public class ReportEntity {

    public ReportEntity() {
    }

    public ReportEntity(Long id, String queryCriteriaCharacterPhrase, String queryCriteriaPlanetName, List<ReportResultEntity> resultEntityList) {
        this.id = id;
        this.queryCriteriaCharacterPhrase = queryCriteriaCharacterPhrase;
        this.queryCriteriaPlanetName = queryCriteriaPlanetName;
        this.resultEntityList = resultEntityList;
    }

    @Id
    private Long id;
    @Column
    private String queryCriteriaCharacterPhrase;
    @Column
    private String queryCriteriaPlanetName;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ReportResultEntity> resultEntityList;
}

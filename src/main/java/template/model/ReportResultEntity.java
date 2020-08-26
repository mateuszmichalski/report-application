package template.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "report_result")
@Data
@Builder
public class ReportResultEntity {

    public ReportResultEntity() {
    }

    public ReportResultEntity(Long id,
                              String filmId,
                              String filmName,
                              String characterId,
                              String characterName,
                              String planetId,
                              String planetName,
                              ReportEntity reportEntity) {
        this.id = id;
        this.filmId = filmId;
        this.filmName = filmName;
        this.characterId = characterId;
        this.characterName = characterName;
        this.planetId = planetId;
        this.planetName = planetName;
        this.reportEntity = reportEntity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String filmId;
    @Column
    private String filmName;
    @Column
    private String characterId;
    @Column
    private String characterName;
    @Column
    private String planetId;
    @Column
    private String planetName;

    @ManyToOne
    private ReportEntity reportEntity;
}

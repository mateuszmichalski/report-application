package template.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportResultDto {

    @JsonProperty("film_id")
    private String filmId;
    @JsonProperty("film_name")
    private String filmName;
    @JsonProperty("character_id")
    private String characterId;
    @JsonProperty("character_name")
    private String characterName;
    @JsonProperty("planet_id")
    private String planetId;
    @JsonProperty("planet_name")
    private String planetName;

    public ReportResultDto(ReportResultEntity reportResultEntity) {
        this.filmId = reportResultEntity.getFilmId();
        this.filmName = reportResultEntity.getFilmName();
        this.characterId = reportResultEntity.getCharacterId();
        this.characterName = reportResultEntity.getCharacterName();
        this.planetId = reportResultEntity.getPlanetId();
        this.planetName = reportResultEntity.getPlanetName();
    }

    public ReportResultDto(String filmId,
                           String filmName,
                           String characterId,
                           String characterName,
                           String planetId,
                           String planetName) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.characterId = characterId;
        this.characterName = characterName;
        this.planetId = planetId;
        this.planetName = planetName;
    }
}

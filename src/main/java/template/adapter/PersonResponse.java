package template.adapter;

import lombok.Data;

@Data
public class PersonResponse {

    private String characterId;
    private String characterName;
    private String homeLandId;

    public PersonResponse() {
    }

    public PersonResponse(String characterId, String characterName, String homeLandId) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.homeLandId = homeLandId;
    }
}

package template.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QueryCriteria {

    @JsonProperty("query_criteria_character_phrase")
    private String characterPhrase;

    @JsonProperty("query_criteria_planet_name")
    private String planetName;

    public QueryCriteria() {
    }

    public QueryCriteria(String characterPhrase, String planetName) {
        this.characterPhrase = characterPhrase;
        this.planetName = planetName;
    }
}

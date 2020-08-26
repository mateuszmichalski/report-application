package template.adapter;

import lombok.Data;

import java.util.List;

@Data
public class FilmResponse {

    private String filmId;
    private String filmName;
    private List<String> characterIds;
}

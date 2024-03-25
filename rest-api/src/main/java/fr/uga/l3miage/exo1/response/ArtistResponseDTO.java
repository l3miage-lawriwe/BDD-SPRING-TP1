package fr.uga.l3miage.exo1.response;

import fr.uga.l3miage.exo1.enums.GenreMusical;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Représentation d'un artiste")
public class ArtistResponseDTO {
    @Schema(description = "nom de l'artiste")
    private String name;
    @Schema(description = "Description de l'artiste")
    private String biography;
    @Schema(description = "Genre musical de l'artiste")
    private GenreMusical genreMusical;
}

package fr.uga.l3miage.exo1.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Duration;

@Data
@Schema(description = "Représente un son")
public class SongResponseDTO {
    @Schema(description = "Titre du son")
    private String title;
    @Schema(description = "durée de la chanson")
    private Duration duration;
    @Schema(description = "Album lié à ce son")
    private AlbumResponseDTO albumEntity;
    @Schema(description = "artiste qui à créer ce son")
    private ArtistResponseDTO artistEntity;
}

package fr.uga.l3miage.exo1.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddPlaylistErrorResponse {
    @Schema(description = "end point call", example = "/api/drone/")
    private final String uri;
    @Schema(description = "error message", example = "La playlist n°1 n'existe pas")
    private final String errorMessage;
}

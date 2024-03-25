package fr.uga.l3miage.exo1.endpoints;

import fr.uga.l3miage.exo1.errors.AddPlaylistErrorResponse;
import fr.uga.l3miage.exo1.response.ArtistResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gestion artistes", description = "Tous les endpoint de gestion d'un artiste")
@RestController
@RequestMapping("/api/artist")
public interface ArtistEndpoints {

    @Operation(description = "Récuperer un artiste")
    @ApiResponse(responseCode = "200",description = "fait")
    @ApiResponse(responseCode = "404", description = "l'artiste son demandée n'a pas été trouvé",content = @Content(schema = @Schema(implementation = AddPlaylistErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idArtiste}")
    ArtistResponseDTO getArtist(@PathVariable(name = "idArtist")String idArtiste);

}

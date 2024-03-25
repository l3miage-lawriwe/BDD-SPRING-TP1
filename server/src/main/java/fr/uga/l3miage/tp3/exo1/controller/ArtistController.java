package fr.uga.l3miage.tp3.exo1.controller;

import fr.uga.l3miage.exo1.endpoints.ArtistEndpoints;
import fr.uga.l3miage.exo1.response.ArtistResponseDTO;
import fr.uga.l3miage.tp3.exo1.services.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ArtistController implements ArtistEndpoints {
    private final ArtistService artistService;


    @Override
    public ArtistResponseDTO getArtist(String name) {
        return artistService.getArtist(name);
    }


}

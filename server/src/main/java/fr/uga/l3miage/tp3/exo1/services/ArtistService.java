package fr.uga.l3miage.tp3.exo1.services;


import fr.uga.l3miage.exo1.response.ArtistResponseDTO;
import fr.uga.l3miage.tp3.exo1.components.ArtistComponent;
import fr.uga.l3miage.tp3.exo1.components.SongComponent;
import fr.uga.l3miage.tp3.exo1.exceptions.rest.AddingSongRestException;
import fr.uga.l3miage.tp3.exo1.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.tp3.exo1.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.tp3.exo1.exceptions.technical.NotFoundPlaylistEntityException;
import fr.uga.l3miage.tp3.exo1.exceptions.technical.NotFoundArtistEntityException;
import fr.uga.l3miage.tp3.exo1.exceptions.technical.NotFoundSongEntityException;
import fr.uga.l3miage.tp3.exo1.mappers.ArtistMapper;
import fr.uga.l3miage.tp3.exo1.models.ArtistEntity;
import fr.uga.l3miage.tp3.exo1.models.SongEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistComponent artistComponent;
    private final ArtistMapper artistMapper;



    public ArtistResponseDTO getArtist(String name){
        try {
            return artistMapper.toResponse(artistComponent.getArtist(name));
        } catch (NotFoundArtistEntityException e) {
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }


}

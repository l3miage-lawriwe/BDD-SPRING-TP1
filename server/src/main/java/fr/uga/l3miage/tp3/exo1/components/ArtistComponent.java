package fr.uga.l3miage.tp3.exo1.components;

import fr.uga.l3miage.tp3.exo1.exceptions.technical.NotFoundArtistEntityException;
import fr.uga.l3miage.tp3.exo1.models.ArtistEntity;
import fr.uga.l3miage.tp3.exo1.models.SongEntity;
import fr.uga.l3miage.tp3.exo1.repositories.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArtistComponent {
    private final ArtistRepository artistRepository;




    public ArtistEntity getArtist(String name) throws NotFoundArtistEntityException {
        return artistRepository.findById(name).orElseThrow(()-> new NotFoundArtistEntityException(String.format("L'artiste %s n'a pas été trouvé", name)));
    }

}

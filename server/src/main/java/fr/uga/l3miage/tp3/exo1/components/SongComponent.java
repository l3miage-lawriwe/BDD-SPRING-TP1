package fr.uga.l3miage.tp3.exo1.components;

import fr.uga.l3miage.tp3.exo1.exceptions.technical.NotFoundSongEntityException;
import fr.uga.l3miage.tp3.exo1.models.SongEntity;
import fr.uga.l3miage.tp3.exo1.repositories.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class SongComponent {
    private final SongRepository songRepository;

    public SongEntity getSongEntityById(String id) throws NotFoundSongEntityException {
        return songRepository.findById(id).orElseThrow(()->new NotFoundSongEntityException(String.format("Le song %s n'existe pas", id)));
    }

    public Set<SongEntity> getSetSongEntity(Set<String> ids){
        return songRepository.findAllByTitleIsIn(ids);
    }
}

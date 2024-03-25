package fr.uga.l3miage.tp3.exo1.components;

import fr.uga.l3miage.tp3.exo1.exceptions.technical.NotFoundPlaylistEntityException;
import fr.uga.l3miage.tp3.exo1.models.PlaylistEntity;
import fr.uga.l3miage.tp3.exo1.models.SongEntity;
import fr.uga.l3miage.tp3.exo1.repositories.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaylistComponent {
    private final PlaylistRepository playlistRepository;

    public PlaylistEntity addSong(String id,SongEntity songEntity) throws NotFoundPlaylistEntityException {
        PlaylistEntity playlistEntity = playlistRepository.findById(id)
                .orElseThrow(() -> new NotFoundPlaylistEntityException(String.format("La playlist %s n'a pas été trouvé", id)));
        playlistEntity.getSongEntities().add(songEntity);
        return playlistRepository.save(playlistEntity);
    }


    public PlaylistEntity getPlaylist(String name) throws NotFoundPlaylistEntityException {
        return playlistRepository.findById(name).orElseThrow(()-> new NotFoundPlaylistEntityException(String.format("La playlist %s n'a pas été trouvé", name)));
    }

    public PlaylistEntity createPlaylistEntity(PlaylistEntity playlistEntity){
        return playlistRepository.save(playlistEntity);
    }
}

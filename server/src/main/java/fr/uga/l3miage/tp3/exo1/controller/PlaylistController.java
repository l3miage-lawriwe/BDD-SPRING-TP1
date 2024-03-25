package fr.uga.l3miage.tp3.exo1.controller;

import fr.uga.l3miage.exo1.endpoints.PlaylistEndpoints;
import fr.uga.l3miage.exo1.requests.PlaylistCreationRequest;
import fr.uga.l3miage.exo1.response.PlaylistResponseDTO;
import fr.uga.l3miage.tp3.exo1.services.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PlaylistController implements PlaylistEndpoints {
    private final PlaylistService playlistService;
    @Override
    public PlaylistResponseDTO addSongInPlaylist(String idPlaylist, String idSong) {
        return playlistService.addSongInPlaylist(idPlaylist ,idSong);
    }

    @Override
    public PlaylistResponseDTO getPlaylist(String name) {
        return playlistService.getPlaylist(name);
    }

    @Override
    public PlaylistResponseDTO createPlaylist(PlaylistCreationRequest playlistCreationRequest) {
        return playlistService.createPlaylist(playlistCreationRequest);
    }
}

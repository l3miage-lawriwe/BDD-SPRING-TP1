package fr.uga.l3miage.exo1.requests;

import lombok.Data;

import java.util.Set;

@Data
public class PlaylistCreationRequest {
    private final String name;

    private final  String description;

    private final Set<String> songsIds;
}

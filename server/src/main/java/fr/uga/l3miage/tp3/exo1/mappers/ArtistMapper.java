package fr.uga.l3miage.tp3.exo1.mappers;

import fr.uga.l3miage.exo1.requests.PlaylistCreationRequest;
import fr.uga.l3miage.exo1.response.ArtistResponseDTO;
import fr.uga.l3miage.exo1.response.PlaylistResponseDTO;
import fr.uga.l3miage.tp3.exo1.models.ArtistEntity;
import fr.uga.l3miage.tp3.exo1.models.PlaylistEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ArtistMapper {

    ArtistResponseDTO toResponse(ArtistEntity ArtistEntity);

}

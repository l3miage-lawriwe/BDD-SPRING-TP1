package fr.uga.l3miage.tp3.exo1.models;

import fr.uga.l3miage.exo1.enums.GenreMusical;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
public class ArtistEntity {
    @Id
    private String name;

    private String biography;

    @Enumerated(EnumType.STRING)
    private GenreMusical genreMusical;

    @OneToMany(mappedBy = "artistEntity",cascade = CascadeType.ALL)
    private Set<AlbumEntity> albumEntities;
}

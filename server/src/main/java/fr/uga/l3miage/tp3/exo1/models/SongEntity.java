package fr.uga.l3miage.tp3.exo1.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.Duration;

@Entity
@Getter
@Setter
public class SongEntity {
    @Id
    private String title;

    private Duration duration;

    @ManyToOne
    private AlbumEntity albumEntity;

    @ManyToOne
    private ArtistEntity artistEntity;
}

# <u>L3 Miage - SPRING -TP1 - EXO 1</u>

* Pour pouvoir démarrer tous les TPs simplement, assurez-vous d'avoir tous les prérequis :
    * voir les [prérequis](prerequis.md)

# Objectif de l'exercice :

Dans cet exercice, vous allez apprendre à :

* Parcourir toute l'implémentation d'un endpoint

# Le modèle applicatif

* Dans cet exercice, nous allons être tout le long dans le paradigme `create`
* Nous allons reprendre la situation du TP3 de JPA.
  ![situation exo 1](doc/img_2.png)
* Nous partons du principe que les entities et repository sont déjà implémentés

* Ici, vous avez les premiers codes, cependant je vous conseille de créer une branche `feat/create-my-endpoint` afin de pouvoir essayer de le refaire.

# Exemple

Ici, je vais vous montrer <b style="color:red">le protocole à suivre lorsque vous implémentez un endpoint de votre serveur</b>

Dans un serveur web, on ne réfléchit plus en termes de `CRUD` sur une table, mais en termes de <b style="color:red">processus métier</b>.

Par exemple :

* récupérer un utilisateur
* se connecter au serveur
* enregistrer une commande
* changer l'état de la livraison d'un colis.


Dans l'exemple que je vais vous donner nous allons voir toutes les étapes pour :
* Ajouter un son dans une playlist, et la renvoyer modifiée
* Récupérer une playlist
* Créer une playlist

##### <b style="color:red">ceci doit être fait de manière procédurale</b>



## Définition d'une interface d'endpoints

1. Vous allez créer le package `fr.uga.l3miage.exo1.endpoints` dans le module `rest-api`
2. Vous allez créer l'interface `PlaylistEnpoint`
3. Nous allons ajouter l'annotation `@RequestMapping` qui permet de donner une base à tous les chemins définis dans cette classe.
```java
@RequestMapping("/api/playlist")
public interface PlaylistEndpoints {}
```
3. Nous allons ajouter l'annotation `@RestController` afin de spécifier que les endpoints déclarés sont des endpoints REST
```java
@RestController
@RequestMapping("/api/playlist")
public interface PlaylistEndpoints {}
```

4. Enfin nous allons ocumenter cet ensemble d'endpoint pour le swagger en ajoutant l'annotation `@Tag`
```java
@Tag(name = "Gestion playlist", description = "Tous les endpoint de gestion d'une playlist")
@RestController
@RequestMapping("/api/playlist")
public interface PlaylistEndpoints {}
```


### Définir et documenter son endpoint

1. Déclarer le prototype de notre endpoint dans l'interface faite précédemment :
```java
PlaylistResponseDTO addSongInPlaylist(@PathVariable(name = "idPlaylist")String idPlaylist, @RequestParam String idSong);
```
2. Déclarer le type dans le package `fr.uga.l3miage.exo1.responses` dans le module `rest-api` la classe `PlaylistResponseDTO` :
```java
@Data
@Schema(description = "Représente une playlist")
public class PlaylistResponseDTO {
    @Schema(description = "nom de la playlist")
    private String name;
    @Schema(description = "description de la playlist")
    private String description;
    @Schema(description = "le temps complet de la playlist")
    private Duration totalDuration;
    @Schema(description = "La liste des sons dans la playlist")
    private final Set<SongResponseDTO> songEntities;
}
```
Avec les @Schema qui sont utiles pour la documentation de votre serveur.
3. Déclarer les modèles liés dans le package `fr.uga.l3miage.exo1.responses`
```java
@Data
@Schema(description = "Représente un son")
public class SongResponseDTO {
    @Schema(description = "Titre du son")
    private String title;
    @Schema(description = "durée de la chanson")
    private Duration duration;
    @Schema(description = "Album lié à ce son")
    private AlbumResponseDTO albumEntity;
    @Schema(description = "artiste qui à créer ce son")
    private ArtistResponseDTO artistEntity;
}
```
```java
@Data
@Schema(description = "Représentation d'un album")
public class AlbumResponseDTO {
    @Schema(description = "Titre de l'album")
    private String title;
    @Schema(description = "Date de sortie")
    private Date releaseDate;
}
```
```java
@Data
@Schema(description = "Représentation d'un artiste")
public class ArtistResponseDTO {
    @Schema(description = "nom de l'artiste")
    private String name;
    @Schema(description = "Description de l'artiste")
    private String biography;
    @Schema(description = "Genre musical de l'artiste")
    private GenreMusical genreMusical;
}
```

Je vous laisse aller voir ce que fait l'annotation `@Data` de lombok

4. Terminons l'endpoint en ajoutant la méthode HTTP `PATCH` avec l'annotation `@PatchMapping`, et qui prend en paramètre le lien que nous voulons définir. Ici nous voulons pouvoir ajouter une playlist sur `/api/playlist/{idPlaylist}/add`
```java
@PatchMapping("/{idPlaylist}/add")
PlaylistResponseDTO addSongInPlaylist(@PathVariable(name = "idPlaylist")String idPlaylist, @RequestParam String idSong);
```
C'est grâce à l'annotation `@PathVariable` que nous sommes capables de récupérer dans le lien l'id de la playlist (exemple : on appelle `/api/playlist/1/add` alors `idPlaylist` il vaudra `1`). Le `@RequestParam` un paramètre qui va être mis à la fin d'un lien avec un `?` comme cela  `/api/playlist/1/add?idSong=monSon`

5. Ensuite on définit le code HTTP renvoyé directement, si aucune erreur n'est remontée à l'appel de ce controller. Pour cela, on utilise l'annotation `@ResponseStatus` : dans notre cas, on veut renvoyer OK si tout c'est bien passé.
```java
@PatchMapping("/{idPlaylist}/add")
@ResponseStatus(HttpStatus.OK)
PlaylistResponseDTO addSongInPlaylist(@PathVariable(name = "idPlaylist")String idPlaylist, @RequestParam String idSong);
```

6. Maintenant ajoutons la documentation de l'endpoint pour le swagger, en utilisant `@ApiResponse` et `@Operation`
```java
    @Operation(description = "Récuperer une playlist")
    @ApiResponse(responseCode = "200",description = "Le son à été ajouté à la playlist")
    @ApiResponse(responseCode = "404", description = "la playlist ou le son demandée n'a pas été trouvé",content = @Content(schema = @Schema(implementation = AddPlaylistErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idPlaylist}")
    PlaylistResponseDTO getPlaylist(@PathVariable(name = "idPlaylist")String idPlaylist);
```

7. Créer le package `fr.uga.l3miage.exo1.errors` dans le module `rest-api` la classe `AddPlaylistErrorResponse`
```java
@Builder
@Data
public class AddPlaylistErrorResponse {
    @Schema(description = "end point call", example = "/api/drone/")
    private final String uri;
    @Schema(description = "error message", example = "La playlist n°1 n'existe pas")
    private final String errorMessage;
}
```


Bravo, vous avez documenté votre premier endpoint
![bravo](https://tenor.com/fr/view/clapping-mr-bean-haha-ok-ok-clapped-gif-22940589.gif)


### Implémentation de l'endpoint

1. Créer le package `fr.uga.l3miage.exo1.controllers` dans le module `serveur`
2. Créer la classe `PlaylistController` qui implemente l'interface `PlaylistEndpoints`, et ajouter l'annotation `@Controller` afin que cette classe devienne un `bean`
```java
@Controller
@RequiredArgsConstructor
public class PlaylistController implements PlaylistEndpoints {
    
}
```
3. Créer le package `fr.uga.l3miage.exo1.services` dans le module `serveur`
4. Créer la classe `PlaylistService` dans ce package, avec l'annotation `@Service`, pour créer un `bean`
```java
@Service
@RequiredArgsConstructor
public class PlaylistService {}
```
4. Créer le package `fr.uga.l3miage.exo1.components` dans le module `serveur`
5. Créer la classe `PlaylistComponent` dans ce package, avec l'annotation `@Component`, pour créer un `bean`
```java
@Component
@RequiredArgsConstructor
public class PlaylistComponent {}
```
6. Injecter dans `PlaylistController` un `PlaylistService`
```java
@Controller
@RequiredArgsConstructor
public class PlaylistController implements PlaylistEndpoints {
    private final PlaylistService playlistService;
}
```

7. Définir la fonction d'ajout dans le component et dans le service en faisant toutes les injections possibles :

```java
@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistComponent playlistComponent;
    private final SongComponent songComponent;
    private final PlaylistMapper playlistMapper;

    public PlaylistResponseDTO addSongInPlaylist(String idPlaylist, String isSong) {
        try {
            SongEntity songEntity = songComponent.getSongEntityById(isSong);
            PlaylistEntity playlistEntity = playlistComponent.addSong(idPlaylist, songEntity);
            return playlistMapper.toResponse(playlistEntity);
        } catch (NotFoundSongEntityException | NotFoundPlaylistEntityException e) {
            throw new AddingSongRestException(e.getMessage());
        }
    }
}
```
Voici les modèles manquant :
Dans le package `components` :
```java
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
```

Créer le package `fr.uga.l3miage.exo1.exceptions` et ajouter les exceptions suivantes :
pour ces exceptions techniques créer un package `fr.uga.l3miage.exo1.exceptions.technicals`
```java
public class NotFoundSongEntityException extends Exception{
    public NotFoundSongEntityException(String message) {
        super(message);
    }
}
```
```java
public class NotFoundPlaylistEntityException extends Exception {
    public NotFoundPlaylistEntityException(String format) {
    }
}
```
Pour ces exceptions techniques créer un package `fr.uga.l3miage.exo1.exceptions.rest`
```java
public class AddingSongRestException extends RuntimeException {
    public AddingSongRestException(String message) {
        super(message);
    }
}
```

3. Déclarer le mapper de **mapStruct**
    1. Créer le package `fr.uga.l3miage.exo1.mappers`
    2. Créer l'interface `PlaylistMapper` avec l'annotation `@Mapper`de cette manière :
     ```java
        @Mapper
        public interface PlaylistMapper {

          PlaylistResponseDTO toResponse(PlaylistEntity playlistEntity);
      
          @Mapping(target = "songEntities", ignore = true)
          PlaylistEntity toEntity(PlaylistCreationRequest request);
        }
     ```


```java
@Component
@RequiredArgsConstructor
public class PlaylistComponent {
    private final PlaylistRepository playlistRepository;

    public PlaylistEntity addSong(String id, SongEntity songEntity) throws NotFoundPlaylistEntityException {
        PlaylistEntity playlistEntity = playlistRepository.findById(id)
                .orElseThrow(() -> new NotFoundPlaylistEntityException(String.format("La playlist %s n'a pas été trouvé", id)));
        playlistEntity.getSongEntities().add(songEntity);
        return playlistRepository.save(playlistEntity);
    }
}
```

### Gestion des erreurs

1. Ajoutons le package `fr.uga.l3miage.exo1.exceptions.handlers` afin de gérer les handlers d'exception
2. Créer la classe `AddingSongRestExceptionHandler`
```java
@Slf4j
@ControllerAdvice
public class ExceptionRestHandler {

    @ExceptionHandler(AddingSongRestException.class)
    public ResponseEntity<AddPlaylistErrorResponse> handle(HttpServletRequest httpServletRequest, Exception e){
        AddingSongRestException exception = (AddingSongRestException) e;
        final AddPlaylistErrorResponse response = AddPlaylistErrorResponse
                .builder()
                .uri(httpServletRequest.getRequestURI())
                .errorMessage(exception.getMessage())
                .build();
        log.warn(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }


}
```

Ici on utilise l'annotation `@ControllerAdvice` qui est un controller qui se met devant tous les autres.
On utilise aussi l'annotation `@ExceptionHandler` qui donne sur quel type d'exception la fonction handle va être exécutée.


# Analyser le code complet

## Récuperation d'une playlist
Dans `PlaylistEndpoints` dans `rest-api`
```java
    @Operation(description = "Récuperer une playlist")
    @ApiResponse(responseCode = "200",description = "Le son à été ajouté à la playlist")
    @ApiResponse(responseCode = "404", description = "la playlist ou le son demandée n'a pas été trouvé",content = @Content(schema = @Schema(implementation = AddPlaylistErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idPlaylist}")
    PlaylistResponseDTO getPlaylist(@PathVariable(name = "idPlaylist")String idPlaylist);
```

Dans `PlaylistController`
```java
@Controller
@RequiredArgsConstructor
public class PlaylistController implements PlaylistEndpoints {
    private final PlaylistService playlistService;

    //before

    @Override
    public PlaylistResponseDTO getPlaylist(String name) {
        return playlistService.getPlaylist(name);
    }

    //after
}
```

Dans le `PlaylistService`
```java
@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistComponent playlistComponent;
    private final SongComponent songComponent;
    private final PlaylistMapper playlistMapper;

    // before

    public PlaylistResponseDTO getPlaylist(String name) {
        try {
            return playlistMapper.toResponse(playlistComponent.getPlaylist(name));
        } catch (NotFoundPlaylistEntityException e) {
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }
    //after
}
```

Dans `PlaylistComponent`
```java
@Component
@RequiredArgsConstructor
public class PlaylistComponent {
    private final PlaylistRepository playlistRepository;

    //before
  
    public PlaylistEntity getPlaylist(String name) throws NotFoundPlaylistEntityException {
        return playlistRepository.findById(name).orElseThrow(() -> new NotFoundPlaylistEntityException(String.format("La playlist %s n'a pas été trouvé", name)));
    }
    
    //after
}
```

---

## Creation d'une playlist

Dans `PlaylistEndpoints` dans `rest-api`
```java
@Operation(description = "Création d'une playlist")
@ApiResponse(responseCode = "201",description = "La Playlist à bien été créé")
@ApiResponse(responseCode = "400",description = "La Playlist à bien été créé")
@ResponseStatus(HttpStatus.CREATED)
@PostMapping("/create")
PlaylistResponseDTO createPlaylist(@RequestBody  PlaylistCreationRequest playlistCreationRequest);
```

Dans `PlaylistController`
```java
@Controller
@RequiredArgsConstructor
public class PlaylistController implements PlaylistEndpoints {
    private final PlaylistService playlistService;

    //before

    @Override
    public PlaylistResponseDTO createPlaylist(PlaylistCreationRequest playlistCreationRequest) {
      return playlistService.createPlaylist(playlistCreationRequest);
    }

    //after
}
```

Dans le `PlaylistService`
```java
@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistComponent playlistComponent;
    private final SongComponent songComponent;
    private final PlaylistMapper playlistMapper;

    // before

    public PlaylistResponseDTO createPlaylist(PlaylistCreationRequest playlistCreationRequest){
      try {
        PlaylistEntity playlistEntity = playlistMapper.toEntity(playlistCreationRequest);
        playlistEntity.setSongEntities(songComponent.getSetSongEntity(playlistCreationRequest.getSongsIds()));
        return playlistMapper.toResponse(playlistComponent.createPlaylistEntity(playlistEntity));
      }catch (Exception e){
        throw new BadRequestRestException(e.getMessage());
      }
    }
    //after
}
```

Dans `PlaylistComponent`
```java
@Component
@RequiredArgsConstructor
public class PlaylistComponent {
    private final PlaylistRepository playlistRepository;

    //before

    public PlaylistEntity createPlaylistEntity(PlaylistEntity playlistEntity){
      return playlistRepository.save(playlistEntity);
    }
    
    //after
}
```

Dans `SongComponent`
```java
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
```

# Voir le résultat

Si vous démarrez le serveur, vous pouvez aller voir sur [swagger-ui](http://127.0.0.1:8080/swagger-ui/index.html) tous les endpoints documentés avec votre code.
Vous pouvez aussi aller voir dans `actuator` l'état de votre serveur (les endpoints définis, les beans créés, etc...)
![img.png](doc/img.png)


# A votre tour

* Après avoir testé la version ci-dessus, ajoutez les processus métiers :
    * Récupérer un artist par son id
    * Créer et ajouter un album à un artiste

![good luck](https://tenor.com/fr/view/johnny-depp-pirates-of-the-caribbean-good-luck-gif-26735885.gif)

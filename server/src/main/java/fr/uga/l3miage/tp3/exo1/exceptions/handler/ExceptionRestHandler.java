package fr.uga.l3miage.tp3.exo1.exceptions.handler;

import fr.uga.l3miage.exo1.errors.AddPlaylistErrorResponse;
import fr.uga.l3miage.tp3.exo1.exceptions.rest.AddingSongRestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

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

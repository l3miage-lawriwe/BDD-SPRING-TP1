package fr.uga.l3miage.tp3.exo1.exceptions.rest;

public class BadRequestRestException extends RuntimeException{
    public BadRequestRestException(String message) {
        super(message);
    }
}

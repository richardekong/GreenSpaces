package com.daveace.greenspaces.response;

import org.springframework.http.HttpStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@Accessors(chain=true)
public class GreenSpacesException extends RuntimeException {

    private HttpStatus status;

    public GreenSpacesException(@NotNull String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public GreenSpacesException(@NotNull HttpStatus status){
        super(status.getReasonPhrase());
        this.status = status;
    }

    public GreenSpacesException(String message){
        super(message);
    }
}


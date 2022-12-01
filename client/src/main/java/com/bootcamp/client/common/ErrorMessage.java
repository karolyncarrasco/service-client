package com.bootcamp.client.common;

public enum ErrorMessage {
    CLIENT_DUPLICATE("El cliente ya se encuentra registrado"),
    CLIENTTYPE_NOT_FOUND("El tipo de cliente indicado no existe"),
    IDENTITYTYPE_NOT_FOUNT ("El tipo de documento indicado no existe");

    private String value;
    ErrorMessage(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}

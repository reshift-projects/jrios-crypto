package com.novatronic.cripto.exception;

public class ParametersCryptoException extends CryptoException {

    private static final String ERROR = "04";

    public ParametersCryptoException(String message) {
        super(ERROR, message);
    }

    public ParametersCryptoException(String message, Throwable cause) {
        super(ERROR, message, cause);
    }

}

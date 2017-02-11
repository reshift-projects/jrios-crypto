package com.novatronic.cripto.exception;

public class CryptoException extends RuntimeException {

    public static final String ALGORITMO_INVALIDO = "01";
    public static final String OPERACION_INVALIDO = "02";
    public static final String MENSAJE_NO_EXISTE = "03";
    public static final String OBTENER_LLAVE_SIMETRICA = "05";
    public static final String OBTENER_LLAVE_PRIVADA = "06";
    public static final String OBTENER_LLAVE_PUBLICA = "07";
    public static final String CIFRAR = "08";
    public static final String DESCRIFRAR = "09";
    public static final String FIRMAR = "10";
    public static final String VERIFICAR = "11";
    public static final String OPERACION_NO_EXISTE = "12";

    public static final String GENERAL = "99";

    private final String codigo;

    @Deprecated
    public CryptoException(String message) {
        super(message);
        this.codigo = GENERAL;
    }

    @Deprecated
    public CryptoException(String message, Throwable cause) {
        super(message, cause);
        this.codigo = GENERAL;
    }

    public CryptoException(String codigo, String message) {
        super(message);
        this.codigo = codigo;
    }

    public CryptoException(String codigo, String message, Throwable cause) {
        super(message, cause);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}

package com.novatronic.components.crypto;

import java.util.Properties;

import com.novatronic.components.exceptions.CryptoException;

public interface Crypto<S,T> {

    public enum CryptoMode {
        ENCRYPT, DECRYPT, VERIFY, SIGN
    }
    
    public void config(Properties prop);
    
    /**
     * Metodo que firma flujo de bytes con las llaves cargadas en el metodo
     * config
     *
     * @param in flujo de bytes para firmar
     * @return flujo de bytes de la firma
     * @throws CryptoException
     */
    public T sign(S in) throws CryptoException;

    /**
     * Metodo que cifra un flujo de bytes con la llave cargada en el metodo
     * config
     *
     * @param in flujo de bytes para cifrar
     * @return flujo de bytes cifrados
     * @throws CryptoException
     */
    public T encrypt(S in) throws CryptoException;

    /**
     * Metodo que descifra un flujo de bytes con la llave cargada en el metodo
     * config
     *
     * @param in flujo de bytes para descifrar
     * @return flujo de bytes descifrados
     * @throws CryptoException
     */
    public T decrypt(S in) throws CryptoException;

    /**
     * Metodo que verifica la firma contra un flujo de bytes con la llave
     * cargada en el metodo config
     *
     * @param in flujo de bytes de la firma
     * @return valor boolean que indica el resultado de la verificacion
     * @throws CryptoException
     */
    public boolean verify(S in, T out) throws CryptoException;

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.formatter;

import com.novatronic.cripto.exception.CryptoException;
import com.novatronic.cripto.model.CipherOperation;
import com.novatronic.cripto.model.CryptoType;
import com.novatronic.cripto.model.Request;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Ricardo
 */
public class ByteToRequest {

    private static final String CHARSET = "UTF-8";
    private static final int TYPE_LENGTH = 1;
    private static final int OPERATION_LENGTH = 1;
    private static final int ALGORITMO_LENGTH = 10;
    private static final int DATA_LENGTH = 3;

    /**
     *
     * @param requestToFormat Arreglo de bytes donde se envia el requerimiento
     * mediante una estructura fija
     * @return Objeto de requerimiento
     */
    public Request format(byte[] requestToFormat) {
        try {
            ByteReader reader = new ByteReader(requestToFormat);
            String type = reader.readStringUntil(TYPE_LENGTH, CHARSET);
            String operation = reader.readStringUntil(OPERATION_LENGTH, CHARSET);
            String algoritmo = reader.readStringUntil(ALGORITMO_LENGTH, CHARSET);

            CipherOperation chiperOperation = CipherOperation.get(operation);
            Request request;
            if (chiperOperation == CipherOperation.VERIFY) {

                int dataLength = reader.readIntUntil(DATA_LENGTH, CHARSET);
                byte[] data = reader.readUntil(dataLength);

                int dataToVerifyLength = reader.readIntUntil(DATA_LENGTH, CHARSET);
                byte[] dataToVerify = reader.readUntil(dataToVerifyLength);

                request = new Request(CryptoType.get(type), chiperOperation, algoritmo, data);
                request.setDataToVerified(dataToVerify);
            } else {
                
                byte[] data = reader.readUntil();
                request = new Request(CryptoType.get(type), chiperOperation, algoritmo, data);
            }
            return request;
        } catch (UnsupportedEncodingException ex) {
            throw new CryptoException(CryptoException.GENERAL, "Error al interpretar mensaje de respuesta", ex);
        }
    }

}

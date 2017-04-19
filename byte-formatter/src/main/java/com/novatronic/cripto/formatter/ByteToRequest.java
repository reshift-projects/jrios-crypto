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

    /**
     *
     * @param requestToFormat Arreglo de bytes donde se envia el requerimiento
     * mediante una estructura fija
     * @return Objeto de requerimiento
     */
    public Request format(byte[] requestToFormat) {
        try {
            ByteReader reader = new ByteReader(requestToFormat);
            String type = reader.readType();
            String operation = reader.readOperation();
            String algoritmo = reader.readAlgorimo().trim();

            CipherOperation chiperOperation = CipherOperation.get(operation);
            Request request;
            if (chiperOperation == CipherOperation.VERIFY) {

                byte[] data = reader.readData();

                byte[] dataToVerify = reader.readData();

                request = new Request(CryptoType.get(type), chiperOperation, algoritmo, data);
                request.setDataToVerified(dataToVerify);
            } else {

                byte[] data = reader.readData();
                request = new Request(CryptoType.get(type), chiperOperation, algoritmo, data);
            }
            return request;
        } catch (UnsupportedEncodingException ex) {
            throw new CryptoException(CryptoException.GENERAL, "Error al interpretar mensaje de respuesta", ex);
        }
    }

}

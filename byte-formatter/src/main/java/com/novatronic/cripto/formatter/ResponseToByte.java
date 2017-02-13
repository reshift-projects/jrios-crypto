/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.formatter;

import com.novatronic.cripto.exception.CryptoException;
import com.novatronic.cripto.model.Response;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Ricardo
 */
public class ResponseToByte {

    private static final String CHARSET = "UTF-8";
    private static final int MIN_LENGTH = 52;

    /**
     *
     * @param response Objeto de respuesta
     * @return Arreglo de bytes generado por una estructura fija a partir del
     * requerimiento. Codigo Respuesta = 2 bytes Descripcion Respuesta = 50
     * bytes Data Respuesta = n bytes
     */
    public byte[] format(Response response) {
        try {

            byte[] byteResponseCode = response.getCodigoRespuesta().getBytes(CHARSET);
            byte[] byteResponseDesc = response.getDescripcionRespuesta().getBytes(CHARSET);
            byte[] byteDataResponse = response.getDataRespuesta();

            ByteBuilder builder = new ByteBuilder(MIN_LENGTH + byteDataResponse.length)
                    .add(byteResponseCode)
                    .add(byteResponseDesc)
                    .add(byteDataResponse);

            return builder.getContent();
        } catch (UnsupportedEncodingException ex) {
            throw new CryptoException(CryptoException.GENERAL, "Error al interpretar mensaje de respuesta", ex);
        }
    }

}
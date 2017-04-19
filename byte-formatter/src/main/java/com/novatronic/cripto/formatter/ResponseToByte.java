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
    private static final int DATA_LENGTH = 3;
    private static final String FILLER_50 = "                                                  ";
    private static final String ZERO_FILLER_3 = "000";

    /**
     *
     * @param response Objeto de respuesta
     * @return Arreglo de bytes generado por una estructura fija a partir del
     * requerimiento. Codigo Respuesta = 2 bytes Descripcion Respuesta = 50
     * bytes Data Respuesta = n bytes
     */
    public byte[] format(Response response) {
        try
        {
            byte[] byteDataResponse = response.getDataRespuesta();

            StringBuilder dataBuilder = append(response);
            ByteBuilder builder;
            if (byteDataResponse == null) {
                builder = format(dataBuilder);
            } else {
                builder = format(dataBuilder, byteDataResponse);
            }
            return builder.getContent();
        }
        catch (UnsupportedEncodingException ex)
        {
            throw new CryptoException(CryptoException.GENERAL, "Error al interpretar mensaje de respuesta", ex);
        }
    }

    private ByteBuilder format(StringBuilder dataBuilder, byte[] byteDataResponse)
            throws UnsupportedEncodingException
    {
        ByteBuilder builder = new ByteBuilder(dataBuilder.length() + byteDataResponse.length).add(dataBuilder.toString().getBytes(CHARSET)).add(byteDataResponse);

        return builder;
    }

    private ByteBuilder format(StringBuilder dataBuilder)
            throws UnsupportedEncodingException
    {
        ByteBuilder builder = new ByteBuilder(dataBuilder.length()).add(dataBuilder.toString().getBytes(CHARSET));

        return builder;
    }

    private StringBuilder append(Response response)
    {
        StringBuilder headerWithDataLength = new StringBuilder().append(response.getCodigoRespuesta()).append(formatDescResp(response.getDescripcionRespuesta())).append(formatDataLength(response.getDataRespuesta()));

        return headerWithDataLength;
    }

    private String formatDescResp(String descResp)
    {
        return new StringBuilder(FILLER_50).replace(0, descResp.length(), descResp).toString();
    }

    private String formatDataLength(byte[] dataResponse)
    {
        if (dataResponse != null)
        {
            String textLength = dataResponse.length + "";
            int endPos = DATA_LENGTH;
            int srcPos = DATA_LENGTH - textLength.length();
            return new StringBuilder(ZERO_FILLER_3).replace(srcPos, endPos, textLength).toString();
        }
        return ZERO_FILLER_3;
    }
}

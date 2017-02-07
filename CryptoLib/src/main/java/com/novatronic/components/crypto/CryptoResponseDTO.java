/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.components.crypto;

import java.util.Arrays;

/**
 *
 * @author Ricardo
 */
public class CryptoResponseDTO {

    private byte[] dataRespuesta;
    private String codigoRespuesta;
    private String descripcionRespuesta;

    public byte[] getDataRespuesta() {
        return dataRespuesta;
    }

    public void setDataRespuesta(byte[] dataRespuesta) {
        this.dataRespuesta = dataRespuesta;
    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

    public void setDescripcionRespuesta(String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }

    @Override
    public String toString() {
        return "CryptoResponseDTO{" + "dataRespuesta=" + Arrays.toString(dataRespuesta) + ", codigoRespuesta=" + codigoRespuesta + ", descripcionRespuesta=" + descripcionRespuesta + '}';
    }

}

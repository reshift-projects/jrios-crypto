/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.components.crypto;

/**
 *
 * @author Ricardo
 */
public class CryptoResponseDTO extends  CryptoDTO{
    
    private byte[] dataRespuesta;
    private String codigoRespuesta;
    private String descripcionRespuesta;

    public CryptoResponseDTO(CryptoDTO request) {
        super(request.getType(), request.getOperacion(), request.getAlgoritmo(), request.getData());
    }

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
        return "CryptoResponseDTO{" + "dataRespuesta=" + dataRespuesta + ", codigoRespuesta=" + codigoRespuesta + ", descripcionRespuesta=" + descripcionRespuesta + '}';
    }
    
    

    
}

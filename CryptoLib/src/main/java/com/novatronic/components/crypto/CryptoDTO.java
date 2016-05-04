/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.components.crypto;

import com.novatronic.components.support.CipherType;

/**
 *
 * @author Ricardo
 */
public class CryptoDTO {
    private CipherType type;
    private String operacion;
    private String algoritmo;
    private byte[] data;
    private byte[] dataToVerified;

    public CryptoDTO(CipherType type, String operacion, String algoritmo, byte[] data) {
        this.type = type;
        this.operacion = operacion;
        this.algoritmo = algoritmo;
        this.data = data;
    }

    public byte[] getDataToVerified() {
        return dataToVerified;
    }

    public void setDataToVerified(byte[] dataToVerified) {
        this.dataToVerified = dataToVerified;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    
    
    public CipherType getType() {
        return type;
    }

    public void setType(CipherType type) {
        this.type = type;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CryptoDTO{" + "type=" + type + ", operacion=" + operacion + ", algoritmo=" + algoritmo + ", data=" + data + ", dataToVerified=" + dataToVerified + '}';
    }

    
}

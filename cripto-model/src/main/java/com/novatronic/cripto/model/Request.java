/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.model;

import com.novatronic.cripto.exception.CryptoException;
import java.util.Arrays;

/**
 *
 * @author Ricardo
 */
public class Request {

    private CryptoType type;
    private CipherOperation operacion;
    private String algoritmo;
    private byte[] data;
    private byte[] dataToVerified;

    public Request(CryptoType type, String operacion, String algoritmo, byte[] data) {
        this(type, CipherOperation.get(operacion), algoritmo, data);
    }

    public Request(CryptoType type, CipherOperation operacion, String algoritmo, byte[] data) {
        this.type = type;
        this.operacion = operacion;
        this.algoritmo = algoritmo;
        this.data = data;
    }

    public void validar() {
        validarTipoCifrado();
        validarData();
    }

    private void validarTipoCifrado() {
        type.validarAlgoritmo(algoritmo);
        type.validarOperacion(operacion);
    }

    private void validarData() {
        if (data == null || data.length == 0) {
            throw new CryptoException(CryptoException.MENSAJE_NO_EXISTE, "Contenido del mensaje no existe");
        }
    }

    public byte[] getDataToVerified() {
        return dataToVerified;
    }

    public void setDataToVerified(byte[] dataToVerified) {
        this.dataToVerified = dataToVerified;
    }

    public CipherOperation getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = CipherOperation.get(operacion);
    }

    public CryptoType getType() {
        return type;
    }

    public void setType(CryptoType type) {
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
        return "CryptoDTO{" + "type=" + type + ", operacion=" + operacion + ", algoritmo=" + algoritmo + ", data=" + Arrays.toString(data) + ", dataToVerified=" + Arrays.toString(dataToVerified) + '}';
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.model;

import com.novatronic.cripto.exception.CryptoException;

/**
 *
 * @author Ricardo
 */
public enum CipherOperation {
    ENCRYPT("E"),
    DECRYPT("D"),
    SIGN("F"),
    VERIFY("V");

    private final String codigo;

    CipherOperation(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static CipherOperation get(String codigo) {
        for (CipherOperation operation : CipherOperation.values()) {
            if (operation.getCodigo().equalsIgnoreCase(codigo)) {
                return operation;
            }
        }
        throw new CryptoException(CryptoException.OPERACION_NO_EXISTE, "Operacion no existe [" + codigo + "]");
    }

}

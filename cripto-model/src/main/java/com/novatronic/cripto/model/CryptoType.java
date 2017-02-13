/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.model;

import com.novatronic.cripto.exception.CryptoException;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Ricardo
 */
public enum CryptoType {
    ASYMETRIC("A", new String[]{"SHA1withRSA", "SHA512withRSA", "MD2withRSA", "RIPEMD128withRSA", "RIPEMD256withRSA"}, new CipherOperation[]{CipherOperation.VERIFY, CipherOperation.SIGN}),
    SYMETRIC("S", new String[]{"DES", "AES"}, new CipherOperation[]{CipherOperation.ENCRYPT, CipherOperation.DECRYPT});

    private final String codigo;
    private final Map<String, String> algoritmos = new LinkedHashMap<String, String>();
    private final Map<CipherOperation, CipherOperation> operaciones = new EnumMap<CipherOperation, CipherOperation>(CipherOperation.class);

    CryptoType(String codigo, String[] algoritmos, CipherOperation[] operaciones) {
        this.codigo = codigo;
        for (String algoritmo : algoritmos) {
            this.algoritmos.put(algoritmo, algoritmo);
        }
        for (CipherOperation operacion : operaciones) {
            this.operaciones.put(operacion, operacion);
        }
    }

    public void validarAlgoritmo(String algoritmoABuscar) {
        if (!algoritmos.containsKey(algoritmoABuscar)) {
            throw new CryptoException(CryptoException.ALGORITMO_INVALIDO, "No se permite este algoritmo para [" + this + "]");
        }
    }

    public void validarOperacion(CipherOperation operacionABuscar) {
        if (!operaciones.containsKey(operacionABuscar)) {
            throw new CryptoException(CryptoException.OPERACION_INVALIDO, "No se permite esta operacion para [" + this + "]");
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public static CryptoType get(String codigo) {
        for (CryptoType type : CryptoType.values()) {
            if (type.getCodigo().equalsIgnoreCase(codigo)) {
                return type;
            }
        }
        throw new CryptoException(CryptoException.TIPO_NO_EXISTE, "Tipo no existe [" + codigo + "]");
    }
}

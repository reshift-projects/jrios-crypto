/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.components.support;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Ricardo
 */
public enum CipherType {
    ASYMETRIC(new String[]{"SHA1withRSA"}, new String[]{"V","F"}), 
    SYMETRIC(new String[]{"DES", "AES"}, new String[]{"E","D"});

    private final Map<String, String> algoritmos = new LinkedHashMap<String, String>();
    private final Map<String, String> operaciones = new LinkedHashMap<String, String>();

    CipherType(String[] algoritmos, String[] operaciones) {
        for (String algoritmo : algoritmos) {
            this.algoritmos.put(algoritmo, algoritmo);
        }
        for (String operacion : operaciones) {
            this.operaciones.put(operacion, operacion);
        }
    }

    public boolean hasAnAlgoritm(String algoritmoABuscar) {
        return algoritmos.containsKey(algoritmoABuscar);
    }

    public boolean hasAnOperation(String operacionABuscar) {
        return operaciones.containsKey(operacionABuscar);
    }

}

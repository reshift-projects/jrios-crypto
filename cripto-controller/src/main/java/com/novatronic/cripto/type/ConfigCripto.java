/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.type;

import com.novatronic.cripto.exception.ParametersCryptoException;
import java.util.Properties;

/**
 *
 * @author Ricardo
 */
public class ConfigCripto {

    private final Properties properties;

    public ConfigCripto(Properties properties) {
        this.properties = properties;
    }

    public void validate(String[] parameters) {
        for (String parameter : parameters) {
            if (!(properties.containsKey(parameter) && !properties.get(parameter).equals(""))) {
                throw new ParametersCryptoException("No se tienen suficientes valores para la operacion");
            }
        }
    }
    
    public String get(String param){
        return properties.getProperty(param);
    }

}

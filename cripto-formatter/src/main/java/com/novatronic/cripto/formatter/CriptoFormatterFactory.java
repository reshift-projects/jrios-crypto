/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ricardo
 */
public class CriptoFormatterFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CriptoFormatterFactory.class);

    public static CriptoFormatter create(String claz) {
        CriptoFormatter crypto;
        try {

            LOGGER.debug("Cargando clase : {}", claz);
            Class c = Class.forName(claz);
            LOGGER.debug("Retornando instancia");
            crypto = (CriptoFormatter) c.newInstance();
            return crypto;
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Error al instanciar la clase proveedora", ex);
        }

    }
}

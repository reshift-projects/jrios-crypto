/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.components.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rcastillejo
 */
public final class ParameterLoader {

    private static final Logger log = LoggerFactory.getLogger(ParameterLoader.class);
    private static Properties generalProperties = null;

    private ParameterLoader() {
    }

    /**
     * Carga el archivo properties que contiene los datos generales del proyecto
     *
     * @param url URL que apunta a la ruta con las propiedades a cargar
     */
    public static void init(URL url) {
        generalProperties = loadPropertiesFromURL(url);
    }

    /**
     * Se obtiene el valor asignado a key
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return getProperty(generalProperties, key);
    }

    private static Properties loadPropertiesFromIn(InputStream in) {
        if (in == null) {
            throw new IllegalArgumentException("InputStream no puede ser nulo");
        }
        try {
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException ex) {
            throw new IllegalArgumentException("", ex);
        }
    }

    private static Properties loadPropertiesFromURL(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("Url no puede ser nulo");
        }
        Properties properties;
        InputStream in = null;
        try {
            in = url.openStream();
            properties = loadPropertiesFromIn(in);
            safeClose(in);
            return properties;
        } catch (IOException ex) {
            throw new IllegalArgumentException("", ex);
        } finally {
            safeClose(in);
        }
    }

    private static void safeClose(InputStream in) {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException ex) {
            log.warn("Error al cerrar el stream", ex);
        }
    }

    private static String getProperty(Properties properties, String key) {
        if (properties == null) {
            return "";
        }
        if (key == null || key.isEmpty()) {
            return "no se encontro llave";
        }
        return properties.getProperty(key);
    }

    /**
     * Retorna las propiedades de configuracion
     *
     * @return
     */
    public static Properties getProperties() {
        return generalProperties;
    }
}

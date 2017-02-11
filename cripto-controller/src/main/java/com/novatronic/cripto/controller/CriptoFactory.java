package com.novatronic.cripto.controller;

import java.lang.reflect.Constructor;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.cripto.exception.CryptoException;

public class CriptoFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(CriptoFactory.class);
        
	/**
	 * Metodo estatico que retorna la implementacion de la clase Cripto sealada en la llave impl del archivo properties
	 * @param prop instancia properties cargada de archivo
	 * @return Cripto
	 * @throws CryptoException 
	 */
	public static Cripto getInstance(Properties prop) throws CryptoException {
		String claz;
		Cripto crypto; 
		try {

			claz = prop.getProperty("impl", "Crypto");
			LOGGER.debug("Cargando clase : {}", claz);

			Constructor<?> claze = Class.forName(claz).getConstructor();
			LOGGER.debug("Cargando constructor");

			LOGGER.debug("Retornando instancia");
			crypto = (Cripto) claze.newInstance();
			
			crypto.config(prop);

			return crypto;
		} catch (Exception ex) {
			throw new RuntimeException(
					"Error al instanciar la clase proveedora", ex);
		}
	}

}

package com.novatronic.components.crypto;

import java.lang.reflect.Constructor;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.components.exceptions.CryptoException;

public class CryptoFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(CryptoFactory.class);
        
	/**
	 * Metodo estatico que retorna la implementacion de la clase Crypto sealada en la llave impl del archivo properties
	 * @param prop instancia properties cargada de archivo
	 * @return Crypto
	 * @throws CryptoException 
	 */
	public static Crypto getInstance(Properties prop) throws CryptoException {
		String claz;
		Crypto crypto; 
		try {

			claz = prop.getProperty("impl", "Crypto");
			LOGGER.debug("Cargando clase : {}", claz);

			Constructor<?> claze = Class.forName(claz).getConstructor();
			LOGGER.debug("Cargando constructor");

			LOGGER.debug("Retornando instancia");
			crypto = (Crypto) claze.newInstance();
			
			crypto.config(prop);

			return crypto;
		} catch (Exception ex) {
			throw new RuntimeException(
					"Error al instanciar la clase proveedora", ex);
		}
	}

}

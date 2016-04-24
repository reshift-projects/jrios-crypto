package com.novatronic.components.crypto;

import java.lang.reflect.Constructor;
import java.util.Properties;

import com.novatronic.components.support.ResourceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.components.exceptions.CryptoException;

public class CryptoFactory {

	private static final Logger log = LoggerFactory.getLogger(CryptoFactory.class);

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
			log.debug("Cargando clase : {}", claz);

			Constructor<?> claze = Class.forName(claz).getConstructor();
			log.debug("Cargando constructor");

			log.debug("Retornando instancia");
			crypto = (Crypto) claze.newInstance();
			
			crypto.config(prop);

			return crypto;
		} catch (Exception ex) {
			throw new CryptoException(
					"Error al instanciar la clase proveedora", ex);
		}
	}

	public static Crypto getInstance(String fileName) throws CryptoException {
		String claz;
		Crypto crypto;
		Properties prop;
		try {

			prop = ResourceHelper.findAsProperties(fileName);
			claz = prop.getProperty("impl", "Crypto");
			log.debug("Cargando clase : {}", claz);

			Constructor<?> claze = Class.forName(claz).getConstructor();
			log.debug("Cargando constructor");

			log.debug("Retornando instancia");
			crypto = (Crypto) claze.newInstance();

			crypto.config(prop);

			return crypto;
		} catch (Exception ex) {
			throw new CryptoException(
					"Error al instanciar la clase proveedora", ex);
		}
	}

}

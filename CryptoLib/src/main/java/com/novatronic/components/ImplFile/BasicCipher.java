package com.novatronic.components.ImplFile;

import com.novatronic.components.crypto.Crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.components.exceptions.CryptoException;
import com.novatronic.components.exceptions.ParametersCryptoException;
import java.util.Properties;

public abstract class BasicCipher<S, T> implements Crypto<S, T> {

    protected Properties prop;

    public BasicCipher() {
    }

    @Override
    public void config(Properties props) throws CryptoException {
        this.prop = props;
    }

    protected void validarDatos(String[] enc_props) {
        for (String value : enc_props) {
            if (!(prop.containsKey(value) && !prop.get(value).equals(""))) {
                throw new ParametersCryptoException("No se tienen suficientes valores para la operacion");
            }
        }
    }
}

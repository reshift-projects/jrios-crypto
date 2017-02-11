/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.controller.operation;

import com.novatronic.cripto.model.Request;
import com.novatronic.cripto.model.Response;
import com.novatronic.cripto.exception.CryptoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.novatronic.cripto.controller.Cripto;

/**
 *
 * @author Ricardo
 */
public class VerifyOperation extends BasicOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyOperation.class);

    @Override
    protected Response customExecute(Cripto crypto, Request data) {
        boolean verifyError = !crypto.verify(data.getData(), data.getDataToVerified());
        if (verifyError) {
            throw new CryptoException(CryptoException.VERIFICAR, "Error al verificar");
        }
        LOGGER.debug("Firma del Mensaje Verificado Satisfactoriamente");
        return new Response();
    }

}

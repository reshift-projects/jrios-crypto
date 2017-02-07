/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.components.crypto.operation;

import com.novatronic.components.crypto.Crypto;
import com.novatronic.components.crypto.CryptoDTO;
import com.novatronic.components.crypto.CryptoResponseDTO;
import com.novatronic.components.exceptions.CryptoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ricardo
 */
public class VerifyOperation extends BasicOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyOperation.class);

    @Override
    protected CryptoResponseDTO customExecute(Crypto crypto, CryptoDTO data) {
        boolean verifyError = !crypto.verify(data.getData(), data.getDataToVerified());
        if (verifyError) {
            throw new CryptoException(CryptoException.VERIFICAR, "Error al verificar");
        }
        LOGGER.debug("Firma del Mensaje Verificado Satisfactoriamente");
        return new CryptoResponseDTO();
    }

}

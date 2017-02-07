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

/**
 *
 * @author Ricardo
 */
public abstract class BasicOperation {

    private final String SUCCESS = "00";

    public CryptoResponseDTO execute(Crypto crypto, CryptoDTO request) throws CryptoException {
        CryptoResponseDTO response;

        response = customExecute(crypto, request);

        response.setCodigoRespuesta(SUCCESS);

        return response;
    }

    protected abstract CryptoResponseDTO customExecute(Crypto crypto, CryptoDTO data);
}

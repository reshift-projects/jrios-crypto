/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.components.crypto.operation;

import com.novatronic.components.crypto.Crypto;
import com.novatronic.components.crypto.message.Request;
import com.novatronic.components.crypto.message.Response;
import com.novatronic.components.exceptions.CryptoException;

/**
 *
 * @author Ricardo
 */
public abstract class BasicOperation {

    private final String SUCCESS = "00";

    public Response execute(Crypto crypto, Request request) throws CryptoException {
        Response response;

        response = customExecute(crypto, request);

        response.setCodigoRespuesta(SUCCESS);

        return response;
    }

    protected abstract Response customExecute(Crypto crypto, Request data);
}

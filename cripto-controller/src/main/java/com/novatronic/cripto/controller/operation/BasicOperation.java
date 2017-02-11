/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.controller.operation;

import com.novatronic.cripto.model.Request;
import com.novatronic.cripto.model.Response;
import com.novatronic.cripto.exception.CryptoException;
import com.novatronic.cripto.controller.Cripto;

/**
 *
 * @author Ricardo
 */
public abstract class BasicOperation {

    private final String SUCCESS = "00";

    public Response execute(Cripto crypto, Request request) throws CryptoException {
        Response response;

        response = customExecute(crypto, request);

        response.setCodigoRespuesta(SUCCESS);

        return response;
    }

    protected abstract Response customExecute(Cripto crypto, Request data);
}

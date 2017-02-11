/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.controller.operation;

import com.novatronic.cripto.model.Request;
import com.novatronic.cripto.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.novatronic.cripto.controller.Cripto;

/**
 *
 * @author Ricardo
 */
public class DecryptOperation extends BasicOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(DecryptOperation.class);

    @Override
    public Response customExecute(Cripto crypto, Request data) {
        byte[] responseData;
        Response response = new Response();

        responseData = (byte[]) crypto.decrypt(data.getData());

        response.setDataRespuesta(responseData);

        LOGGER.debug("Mensaje Descrifrado Satisfactoriamente");
        return response;
    }

}

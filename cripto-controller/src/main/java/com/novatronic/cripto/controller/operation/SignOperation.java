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
public class SignOperation extends BasicOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignOperation.class);

    @Override
    public Response customExecute(Cripto crypto, Request data) {
        Response response = new Response();

        byte[] responseData = (byte[])crypto.sign(data.getData());

        response.setDataRespuesta(responseData);
        response.setDescripcionRespuesta("Mensaje Firmado Satisfactoriamente");

        LOGGER.debug(response.getDescripcionRespuesta());
        return response;
    }

}

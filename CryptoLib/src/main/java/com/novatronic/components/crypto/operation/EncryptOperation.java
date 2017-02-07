/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.components.crypto.operation;

import com.novatronic.components.crypto.Crypto;
import com.novatronic.components.crypto.message.Request;
import com.novatronic.components.crypto.message.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ricardo
 */
public class EncryptOperation extends BasicOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptOperation.class);

    @Override
    public Response customExecute(Crypto crypto, Request data) {
        byte[] responseData;
        Response response = new Response();

        responseData = (byte[]) crypto.encrypt(data.getData());

        response.setDataRespuesta(responseData);

        LOGGER.debug("Mensaje Cifrado Satisfactoriamente");
        return response;
    }

}

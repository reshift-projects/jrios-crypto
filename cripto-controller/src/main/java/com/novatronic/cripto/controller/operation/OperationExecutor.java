/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.controller.operation;

import com.novatronic.cripto.model.CipherOperation;
import com.novatronic.cripto.model.Request;
import com.novatronic.cripto.model.Response;
import com.novatronic.cripto.exception.CryptoException;
import com.novatronic.cripto.controller.Cripto;

/**
 *
 * @author Ricardo
 */
public class OperationExecutor {

    public static Response execute(Cripto crypto, Request request) {
        BasicOperation operation = getOperation(request.getOperacion());
        return operation.execute(crypto, request);
    }

    private static BasicOperation getOperation(CipherOperation operation) {
        switch (operation) {
            case ENCRYPT:
                return new EncryptOperation();
            case DECRYPT:
                return new DecryptOperation();
            case SIGN:
                return new SignOperation();
            case VERIFY:
                return new VerifyOperation();
        }

        throw new CryptoException(CryptoException.OPERACION_NO_EXISTE, "Operacion no existe [" + operation + "]");
    }

}

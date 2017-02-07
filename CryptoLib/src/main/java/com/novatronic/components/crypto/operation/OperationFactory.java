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
public class OperationFactory {

    public static CryptoResponseDTO executeOperation(Crypto crypto, CryptoDTO request) {
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

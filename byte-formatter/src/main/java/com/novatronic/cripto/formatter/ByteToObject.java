/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.formatter;

import com.novatronic.cripto.model.Request;

/**
 *
 * @author Ricardo
 */
public class ByteToObject implements CriptoFormatter<byte[], Request> {

    /**
     *
     * @param requestToFormat Arreglo de bytes donde se envia el requerimiento
     * mediante una estructura fija
     * @return Objeto de requerimiento
     */
    @Override
    public Request format(byte[] requestToFormat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.formatter;

import com.novatronic.cripto.model.Request;
import com.novatronic.cripto.model.Response;

/**
 *
 * @author Ricardo
 */
public interface CriptoFormatter<T> {

    Request unmarshall(T dataToFormat);

    T marshall(Response dataToFormat);
}

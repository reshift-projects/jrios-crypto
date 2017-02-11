/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.formatter;

/**
 *
 * @author Ricardo
 */
public interface CriptoFormatter<S, T> {

    T format(S dataToFormat);
}

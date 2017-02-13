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
public class ByteBuilder {

    private final byte[] content;
    private int currentPos;

    public ByteBuilder(int size) {
        this.content = new byte[size];
        this.currentPos = 0;
    }

    public ByteBuilder add(byte[] bytes) {
        System.arraycopy(bytes, 0, content, currentPos, bytes.length);
        currentPos += bytes.length;
        return this;
    }

    public byte[] getContent() {
        return content;
    }

}

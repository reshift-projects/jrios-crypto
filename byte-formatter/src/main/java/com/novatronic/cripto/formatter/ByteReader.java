/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.formatter;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author Ricardo
 */
public class ByteReader {

    private final byte[] content;
    private int currentPos;

    public ByteReader(byte[] content) {
        this.content = content;
        this.currentPos = 0;
    }

    public int readIntUntil(int size, String charset) throws UnsupportedEncodingException {
        String stringRead = readStringUntil(size, charset);
        return Integer.parseInt(stringRead);
    }

    public String readStringUntil(int size, String charset) throws UnsupportedEncodingException {
        byte[] bytesToRead = readUntil(size);
        return new String(bytesToRead, charset);
    }

    /**
     * Lee los bytes hasta el final restante
     *
     * @return bytes leidos
     */
    public byte[] readUntil() {
        int size = content.length;
        return readUntil(size);
    }

    /**
     * Lee hasta el tamanio indicado, manteniendo el indice leido
     *
     * @param size tamanio a leer
     * @return bytes leidos
     */
    public byte[] readUntil(int size) {
        byte[] bytesToRead = new byte[size];
        System.arraycopy(content, currentPos, bytesToRead, 0, size);
        currentPos += size;
        return bytesToRead;
    }

}

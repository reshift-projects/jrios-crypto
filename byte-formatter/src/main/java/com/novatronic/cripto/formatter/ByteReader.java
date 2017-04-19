/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.cripto.formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author Ricardo
 */
public class ByteReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ByteReader.class);
    private static final String CHARSET = "UTF-8";
    private static final int TYPE_LENGTH = 1;
    private static final int OPERATION_LENGTH = 1;
    private static final int ALGORITMO_LENGTH = 3;
    private static final int DATA_LENGTH = 3;

    private final byte[] content;
    private int currentPos;

    public ByteReader(byte[] content) {
        this.content = content;
        this.currentPos = 0;
    }

    public String readType()
            throws UnsupportedEncodingException {
        String type = readStringUntil(TYPE_LENGTH, CHARSET);
        LOGGER.debug("Tipo Leido[{}]", type);
        return type;
    }

    public String readOperation()
            throws UnsupportedEncodingException {
        String operation = readStringUntil(OPERATION_LENGTH, CHARSET);
        LOGGER.debug("operation Leido[{}]", operation);
        return operation;
    }

    public String readAlgorimo()
            throws UnsupportedEncodingException {
        int dataLength = readIntUntil(ALGORITMO_LENGTH, CHARSET);
        LOGGER.debug("algoritmo long Leido[{}]", Integer.valueOf(dataLength));
        String algoritmo = readStringUntil(dataLength, CHARSET);
        LOGGER.debug("algoritmo Leido[{}]", algoritmo);
        return algoritmo;
    }

    public byte[] readData()
            throws UnsupportedEncodingException {
        int dataLength = readIntUntil(DATA_LENGTH, CHARSET);
        LOGGER.debug("data long Leido[{}]", Integer.valueOf(dataLength));
        byte[] data = readUntil(dataLength);
        LOGGER.debug("data Leido[{}]", data);
        return data;
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

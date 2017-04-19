package com.novatronic.cripto.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Identificador
{
    private static int conta;
    private String ccode;
    private SimpleDateFormat sdf;
    private static Identificador instancia = null;

    public static Identificador getInstance(String controllerCode)
    {
        synchronized (Identificador.class)
        {
            if (instancia == null) {
                instancia = new Identificador(controllerCode);
            }
        }
        return instancia;
    }

    Identificador(String controllerCode)
    {
        int maxLength = 8;

        String aux = controllerCode + "        ";

        conta = 0;
        this.ccode = aux.substring(0, maxLength);

        this.sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    }

    public synchronized String getCode()
    {
        int len = 4;
        int max4Reset = 9999;

        String df = this.sdf.format(new Date());

        String aux = this.ccode + df + lpad(new StringBuilder().append("").append(conta).toString(), "0", len);

        conta += 1;
        if (conta > max4Reset) {
            conta = 0;
        }
        return aux;
    }

    private static String lpad(String src, String car, int len)
    {
        String strSrc = src;
        if (strSrc == null) {
            strSrc = "";
        }
        String res = "";
        int j = len - strSrc.length();
        for (int i = 0; i < j; i++) {
            res = res + car;
        }
        res = res + strSrc;

        return res;
    }
}

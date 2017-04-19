package com.novatronic.cripto.api;


import com.novatronic.cripto.controller.CriptoController;
import com.novatronic.cripto.exception.CryptoException;
import com.novatronic.cripto.formatter.ByteFormatter;
import com.novatronic.cripto.formatter.CriptoFormatter;
import com.novatronic.cripto.model.Request;
import com.novatronic.cripto.model.Response;
import java.util.Properties;

/**
 * Created by ricardo on 18/04/2017.
 */
public class CriptoApi
{
    private CriptoFormatter<byte[]> formatter;
    private CriptoController controller;

    public CriptoApi()
    {
        this.controller = new CriptoController();
        this.formatter = new ByteFormatter();
    }

    public CriptoApi(Properties configuration)
    {
        this.controller = new CriptoController(configuration);
        this.formatter = new ByteFormatter();
    }

    public CriptoApi(CriptoFormatter<byte[]> formatter, CriptoController controller)
    {
        this.formatter = formatter;
        this.controller = controller;
    }

    public byte[] process(byte[] request)
    {
        try
        {
            Request requestData = this.formatter.unmarshall(request);
            Response responseData = this.controller.process(requestData);
            return (byte[])this.formatter.marshall(responseData);
        }
        catch (CryptoException e)
        {
            Response responseError = new Response();
            responseError.setCodigoRespuesta(e.getCodigo());
            responseError.setDescripcionRespuesta(e.getMessage());
            return (byte[])this.formatter.marshall(responseError);
        }
    }
}

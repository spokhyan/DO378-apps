package com.redhat.exchange;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

@Path("/currencies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CurrencyResource {

    @GET
    public List<String> getCurrencyNames() {
        return List.of("EUR", "USD", "JPY");
    }

    public String getCurrencySign(final String currencyName){
        switch (currencyName){
            case "EUR": return "€";
            case "USD": return "$";
            case "JPY": return "¥";
        }
        return null;
    }
}

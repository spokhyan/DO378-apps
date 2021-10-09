package com.redhat.exchange;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.exchange.dto.Exchange;
import com.redhat.exchange.dto.ExchangeRequest;
import com.redhat.exchange.service.HistoryService;

import java.util.List;
import java.util.Collections;

@Path("/exchangeRate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExchangeResource {

    @Inject
    @RestClient
    HistoryService historyService;

    @Inject
    CurrencyResource currencyResource;

    @POST
    @Path("/historicalData")
    public List<Exchange> getHistoricalData(ExchangeRequest body) {
        try {
            return historyService.getCurrencyExchangeRates(body);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @POST
    @Path("/singleCurrency")
    public Exchange getExchangeRate(ExchangeRequest body) {
        Exchange latestExchange = historyService.getCurrencyExchangeRates(body).get(0);
        latestExchange.sign = currencyResource.getCurrencySign(body.target);
        return latestExchange;
    }

}

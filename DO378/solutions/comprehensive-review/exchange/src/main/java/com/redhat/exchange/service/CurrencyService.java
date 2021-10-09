package com.redhat.exchange.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.exchange.dto.Currency;

import java.util.List;

@Path("/")
@RegisterRestClient
public interface CurrencyService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getCurrencyNames();

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    Currency getCurrency(@PathParam("name") String name);
    
}

package com.redhat.exchange.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.exchange.dto.News;

import java.util.List;

@Path("/")
@RegisterRestClient
public interface NewsService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<News> getFinancialNews();
}

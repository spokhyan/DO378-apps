package org.acme.conference.session;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/speaker")
@RegisterRestClient
public interface SpeakerService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SpeakerFromService> listAll();
}
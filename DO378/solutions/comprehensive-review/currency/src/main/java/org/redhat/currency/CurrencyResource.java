package org.redhat.currency;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class CurrencyResource {

    @GET
    @Path("/all")
    @Transactional
    public List<Currency> list() {
        return Currency.listAll();
    }

    @RolesAllowed("confidential")
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") String id) {
        if (!Currency.deleteById(id)) throw new NotFoundException();
    }

    @GET
    @Path("/{id}")
    @Transactional
    public Currency getCurrency(@PathParam("id") String id) {
        return Currency.<Currency>findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException());
    }

    @GET
    @Transactional
    public List<String> getCurrencyNames() {
        return Currency.<Currency>streamAll()
            .map(c -> c.name).collect(Collectors.toList());
    }

}
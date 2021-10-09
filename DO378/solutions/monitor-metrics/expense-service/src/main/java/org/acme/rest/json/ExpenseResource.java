package org.acme.rest.json;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;

import io.quarkus.panache.common.Sort;


@Path("/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExpenseResource {
    @Inject
    public ExpenseService expenseService;

    @GET
    @Counted(
        name = "callsToExpenseList",
        description = "How many requests to list expenses have happened."
    )
    @Timed(
        name = "expenseListTimer",
        description = "How long it takes to list expenses.",
        unit = MetricUnits.MILLISECONDS
    )
    public List<Expense> list() {
        return expenseService.list();
    }

    @POST
    @Transactional
    @Counted(
        name = "callsToExpenseCreate",
        description = "How many requests have attempted creating an expense object."
    )
    @Timed(
        name = "expenseCreateTimer",
        description = "How long it takes to create an expense object.",
        unit = MetricUnits.MILLISECONDS
    )
    public Expense create(Expense expense) {
        return expenseService.create(expense);
    }

    @DELETE
    @Path("{uuid}")
    @Transactional
    public List<Expense> delete(@PathParam("uuid") UUID uuid) {
        return expenseService.delete(uuid);
    }

    @PUT
    @Transactional
    public void update(Expense expense) {
        if (expenseService.exists(expense.uuid)) {
            expenseService.update(expense);
        }
        else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Gauge(
        name = "mostExpensiveBuy",
        description = "Most expensive item reported so far.",
        unit = MetricUnits.NONE
    )
    @Transactional
    public Long largestExpenseAmount() {
        Expense expense = Expense.findAll(Sort.descending("amount")).firstResult();
        if (expense != null) {
            return expense.amount.longValue();
        } else {
            return 0l;
        }
    }
}
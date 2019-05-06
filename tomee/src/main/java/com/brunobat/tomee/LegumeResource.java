package com.brunobat.tomee;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static javax.persistence.PersistenceContextType.TRANSACTION;
import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/legumes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class LegumeResource {

    @PersistenceContext(unitName = "test", type = TRANSACTION)
    private EntityManager manager;

    @POST
    @Transactional
    public Response provision() {
        manager.merge(new Legume("Carrot", "Root vegetable, usually orange"));
        manager.merge(new Legume("Zucchini", "Summer squash"));
        return Response.status(CREATED).build();
    }

    @Fallback(fallbackMethod = "fallback") // better use FallbackHandler
    @Timeout(500)
    @GET
    public Response list() {
        return Response.ok(manager.createQuery("SELECT l FROM Legume l").getResultList()).build();
    }

    public Response fallback() {
        return Response.ok(Arrays.asList(new Legume("Broccoli", "This is the default legume, mate"))).build();
    }
}

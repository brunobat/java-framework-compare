package org.acme.rest.json;

import org.eclipse.microprofile.faulttolerance.Fallback;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/legumes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LegumeResource {

    @Inject
    private EntityManager manager;

    @POST
    @Transactional
    public Response provision() {
        manager.merge(new Legume("Carrot", "Root vegetable, usually orange"));
        manager.merge(new Legume("Zucchini", "Summer squash"));
        return Response.status(CREATED).build();
    }

    @GET
    @Fallback(fallbackMethod = "fallback") // better use FallbackHandler
//    @Timeout(500)
    public List<Legume> list() {
        final List resultList = manager.createQuery("SELECT l FROM Legume l").getResultList();
        return resultList;
    }

    public List<Legume> fallback() {
        return Arrays.asList(new Legume("Failed Legume", "Fallback answer due to timeout"));
    }
}

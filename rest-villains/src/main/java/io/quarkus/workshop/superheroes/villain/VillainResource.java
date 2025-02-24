package io.quarkus.workshop.superheroes.villain;

import io.quarkus.workshop.superheroes.villain.model.Villain;
import io.quarkus.workshop.superheroes.villain.services.VillainService;

import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
// import io.vertx.core.impl.logging.Logger; // Removed duplicate import
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;



import java.util.List;

import org.jboss.logging.Logger;

@Path("/api/villains")
public class VillainResource {

    Logger logger;
    VillainService service;


    public VillainResource(Logger logger, VillainService service) {
        this.service = service;
        this.logger = logger;
    }


    @GET
    @Path("random")
    public Response getRandomVillain() {
        Villain villain = service.findRandoVillain();
        logger.debug("Found random villain " + villain);
        return Response.ok(villain).build();
    }

    @GET
    public Response getAllVillains() {
        List<Villain> villains = service.findAllVillains(); // Pass an appropriate long argument
        logger.debug("Total number of villains " + villains.size());
        return Response.ok(villains).build();
    }

    @GET
    @Path("/{id}")
    public Response getVillain(@PathParam("id") Long id) {
        Villain villain = service.findVillainById(id);

        if (villain != null) {
            logger.debug("Found villain " + villain);
            return Response.ok(villain).build();
        } else {
            logger.debug("No villain found with id " + id);
            return Response.noContent().build();
        }
    }

    @POST
    public Response createVillain(@Valid Villain villain, @Context UriInfo uriInfo) {
        villain = service.persistVillain(villain);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(villain.id));
        logger.debug("New villain created with URI " + builder.build().toString());

        return Response.created(builder.build()).entity(villain).build();
    }

    @PUT
    public Response updateVillain(@Valid Villain villain) {
        villain = service.updateVillain(villain);
        logger.debug(("Villain updated with new valued " + villain));
        return Response.ok(villain).build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteVillain(@PathParam("id") Long id) {

        service.deleteVillain(id);
        logger.debug("Villain deleted with " + id);
        return Response.noContent().build();
    }
}

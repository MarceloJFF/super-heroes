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
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;


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
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Path("random")
    public RestResponse<Villain> getRandomVillain() {
        Villain villain = service.findRandoVillain();
        logger.debug("Found random villain " + villain);
        return RestResponse.ok(villain);
    }

    @GET
    public RestResponse<List<Villain>> getAllVillains() {
        List<Villain> villains = service.findAllVillains(); // Pass an appropriate long argument
        logger.debug("Total number of villains " + villains.size());
        return RestResponse.ok(villains);
    }
    @GET
    public RestResponse<Villain> getVillain(@RestPath Long id) {
        Villain villain = service.findVillainById(id);
        if (villain != null) {
            logger.debug("Found villain " + villain);
            return RestResponse.ok(villain);
        } else {
            logger.debug("No villain found with id " + id);
            return RestResponse.noContent();
        }
    }

    @POST
    public RestResponse<Void> createVillain(@Valid Villain villain, @Context UriInfo uriInfo) {
        villain = service.persistVillain(villain);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(villain.id));
        logger.debug("New villain created with URI " + builder.build().toString());

        return RestResponse.created(builder.build());
    }

    @PUT
    public RestResponse<Villain> updateVillain(@Valid Villain villain) {
        villain = service.updateVillain(villain);
        logger.debug(("Villain updated with new valued " + villain));
        return RestResponse.ok(villain);
    }
    @DELETE
    @Path("/{id}")
    public RestResponse<Void> deleteVillain(@RestPath long id) {
        service.deleteVillain(id);
        logger.debug("Villain deleted with " + id);
        return RestResponse.noContent();
    }
}

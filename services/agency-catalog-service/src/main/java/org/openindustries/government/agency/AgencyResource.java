package org.openindustries.government.agency;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/agencies")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Agencies", description = "Catalogue of public agencies and their service counts.")
public class AgencyResource {

    @Inject
    AgencyService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a agency")
    public Response create(@Valid CreateAgencyRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<Agency> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public Agency get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/suspend")
    @Operation(summary = "Suspend (lifecycle transition, no body)")
    public Agency suspend(@PathParam("id") UUID id) {
        return service.suspend(id);
    }
}

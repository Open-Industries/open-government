package org.openindustries.government.citizencase;

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

@Path("/api/v1/cases")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Citizen Cases", description = "Citizen case registry; the reference code (folio) is masked at intake.")
public class CitizenCaseResource {

    @Inject
    CitizenCaseService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a citizencase")
    public Response create(@Valid CreateCitizenCaseRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<CitizenCase> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public CitizenCase get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/assign")
    @Operation(summary = "Assign this citizencase (lifecycle transition, no body)")
    public CitizenCase assign(@PathParam("id") UUID id) {
        return service.assign(id);
    }

    @POST
    @Path("/{id}/resolve")
    @Operation(summary = "Resolve this citizencase (lifecycle transition, no body)")
    public CitizenCase resolve(@PathParam("id") UUID id) {
        return service.resolve(id);
    }
}

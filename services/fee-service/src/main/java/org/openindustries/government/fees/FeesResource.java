package org.openindustries.government.fees;

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

@Path("/api/v1/fees")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Fees", description = "Assessed-fee accounts per case and the payments applied to them.")
public class FeesResource {

    @Inject
    FeesService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Open a feeaccount")
    public Response open(@Valid OpenFeeAccountRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.open(request)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public FeeAccount get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/payments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Draw from the account (rejects if it would overdraw, 422)")
    public FeePayment consume(@PathParam("id") UUID id, @Valid CreateFeePaymentRequest request) {
        return service.consume(id, request);
    }

    @GET
    @Path("/{id}/payments")
    @Operation(summary = "List consumption records of an account")
    public List<FeePayment> records(@PathParam("id") UUID id) {
        return service.recordsOf(id);
    }
}

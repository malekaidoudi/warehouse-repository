package com.wh.reception.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/test")
public class TestResource {
    @GET
    public Response test() {
        return Response.ok("JAX-RS is working!").build();
    }
}
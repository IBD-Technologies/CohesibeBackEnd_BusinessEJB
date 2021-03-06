/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.web.Gateway.ClassEntity;

import javax.json.JsonObject;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author DELL
 */
@Path("/ClassEntity")
public class ClassEntitiesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ClassEntitiesResource
     */
    public ClassEntitiesResource() {
    }

    /**
     * Retrieves representation of an instance of com.ibd.cohesive.web.Gateway.ClassEntity.ClassEntitiesResource
     * @return an instance of javax.json.JsonObject
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * Sub-resource locator method for {ServiceName}
     */
    @Path("{ServiceName}")
    public ClassEntityResource getClassEntityResource(@PathParam("ServiceName") String ServiceName) throws NamingException {
        return ClassEntityResource.getInstance(ServiceName);
    }
}

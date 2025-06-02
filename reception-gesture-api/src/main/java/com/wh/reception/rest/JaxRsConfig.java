package com.wh.reception.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
@ApplicationPath("/api")
public class JaxRsConfig extends Application {
//	@Override
//    public Set<Class<?>> getClasses() {
//        Set<Class<?>> resources = new HashSet<>();
//        resources.add(CategoryResource.class);
//        return resources;
//    }

// Laisse vide pour que JAX-RS scanne automatiquement les ressources
}

package com.example.uemf;

import jakarta.enterprise.context.Initialized;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import model.Person;

import java.util.LinkedList;
import java.util.List;

@Path("/pat")
public class HelloResource {

    static {


        Person p1 = new Person("Flora", "Daka");
        Person p2= new Person("Ben", "Hepet");
        Person p3 = new Person("Herr", "Mann");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.getTransaction().commit();



    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Person uebergabe(@PathParam("id") int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        return p;
    }


    @GET
    @Path("setCookie/{id}")
    public Response setCookie(@PathParam("id") String id){
        NewCookie cookie2 = new NewCookie("Cookie-Test", id );
       return Response.status(200).cookie(cookie2).build();
    }

    @GET
    @Path("consumeCookie")
    public String consumeCookie(@CookieParam("Cookie-Test") String cookie)
    {
        return cookie;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{vorname}/{nachname}")
    public Person hochladen(@PathParam("vorname") String vn, @PathParam("nachname") String nn) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        Person p = new Person(vn, nn);
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();

        return p;
    }




}
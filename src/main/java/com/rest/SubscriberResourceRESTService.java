/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.data.SubscriberRepository;
import com.model.Subscriber;
import com.service.SubscriberSubscribe;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the members table.
 */
@Path("/members")
@RequestScoped
public class SubscriberResourceRESTService {

    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private SubscriberRepository repository;

    @Inject
    SubscriberSubscribe registration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Subscriber> listAllSubscribers() {
        return repository.findAllOrderedByName();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Subscriber lookupSubscriberById(@PathParam("id") long id) {
        Subscriber subscriber = repository.findById(id);
        if (subscriber == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return subscriber;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Subscriber lookupSubscriberByEmail(String email){
        Subscriber subscriber = repository.findByEmail(email);
        if(subscriber == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        System.out.println(email);
        return subscriber;
    }

    /**
     * Creates a new subscriber from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMember(Subscriber subscriber) {

        Response.ResponseBuilder builder = null;

        try {
            // Validates subscriber using bean validation
            validateMember(subscriber);

            registration.register(subscriber);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("email", "Email taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

 /*   /** Deletes one member existing in data with the email provided.

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteMember(String email){
        Response.ResponseBuilder builder = null;
        Subscriber memberToDelete = lookupSubscriberByEmail(email);
        System.out.println("MembResourceRESTService ^^");

        return builder.build();

    }  */

    /**
     * <p>
     * Validates the given Subscriber variable and throws validation exceptions based on the type of error. If the error is standard
     * bean validation errors then it will throw a ConstraintValidationException with the set of the constraints violated.
     * </p>
     * <p>
     * If the error is caused because an existing subscriber with the same email is registered it throws a regular validation
     * exception so that it can be interpreted separately.
     * </p>
     * 
     * @param subscriber Subscriber to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     * @throws ValidationException If subscriber with the same email already exists
     */
    private void validateMember(Subscriber subscriber) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Subscriber>> violations = validator.validate(subscriber);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        // Check the uniqueness of the email address
        if (emailAlreadyExists(subscriber.getEmail())) {
            throw new ValidationException("Unique Email Violation");
        }
    }

    /**
     * Creates a JAX-RS "Bad Request" response including a map of all violation fields, and their message. This can then be used
     * by clients to show violations.
     * 
     * @param violations A set of violations that needs to be reported
     * @return JAX-RS response containing all violations
     */
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

    /**
     * Checks if a member with the same email address is already registered. This is the only way to easily capture the
     * "@UniqueConstraint(columnNames = "email")" constraint from the Subscriber class.
     * 
     * @param email The email to check
     * @return True if the email already exists, and false otherwise
     */
    private boolean emailAlreadyExists(String email) {
        Subscriber subscriber = null;
        try {
            subscriber = repository.findByEmail(email);
        } catch (NoResultException e) {
            // ignore
        }
        return subscriber != null;
    }

}

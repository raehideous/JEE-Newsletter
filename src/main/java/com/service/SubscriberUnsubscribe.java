package com.service;

import com.data.SubscriberRepository;
import com.model.Subscriber;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created by szkolenie on 2016-08-19.
 */
@Stateless

public class SubscriberUnsubscribe {
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Subscriber> memberEventSrc;

    @Inject
    private SubscriberRepository repository;

    public void unsubscribe(String email) throws Exception {
        Subscriber subscriber = repository.findByEmail(email);
        Subscriber subscriberToDelete = em.find(Subscriber.class, subscriber.getId());

        em.remove(subscriberToDelete);
        em.flush();
    }

}

package com.service;

import com.data.SubscriberRepository;
import com.data.UserRepository;
import com.model.Subscriber;
import com.model.User;
import com.util.InconsistientEmailsException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

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
    private SubscriberRepository subscriberRepository;

    @Inject
    private UserRepository userRepository;

    public void unsubscribe(String email) throws Exception {

        Subject currentSubject = SecurityUtils.getSubject();
        String username = (String) currentSubject.getPrincipal();
        User currentUser = userRepository.findUserByUsername(username);

        //System.out.println("KURRENT USER XD: " + currentUser.getEmail());


        Subscriber subscriber = subscriberRepository.findByEmail(email);

        Subscriber subscriberToDelete = em.find(Subscriber.class, subscriber.getId());


        if(currentUser.getEmail().equals(subscriber.getEmail())){
            em.remove(subscriberToDelete);
            em.flush();
        }
        else{
            throw new InconsistientEmailsException("You written someone else email! Write yours...");
        }

    }

}

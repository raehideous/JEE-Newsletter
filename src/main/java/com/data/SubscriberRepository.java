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
package com.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import com.model.Subscriber;

@ApplicationScoped
public class SubscriberRepository {

    @Inject
    private EntityManager em;

    public Subscriber findById(Long id) {
        return em.find(Subscriber.class, id);
    }

    public Subscriber findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Subscriber> criteria = cb.createQuery(Subscriber.class);
        Root<Subscriber> subscriber = criteria.from(Subscriber.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
        criteria.select(subscriber).where(cb.equal(subscriber.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Subscriber> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Subscriber> criteria = cb.createQuery(Subscriber.class);
        Root<Subscriber> subscriber = criteria.from(Subscriber.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(subscriber).orderBy(cb.asc(subscriber.get(Member_.name)));
        criteria.select(subscriber).orderBy(cb.asc(subscriber.get("name")));
        return em.createQuery(criteria).getResultList();
    }

    /** Used for counting number of rows in database Subscriber table.
     * Just counting number of subscribed members.
     * */

    public Long getSubscribersNumber(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        Root<Subscriber> subscriber = criteria.from(Subscriber.class);

        criteria.select(cb.count(subscriber));
        Long count = em.createQuery(criteria).getSingleResult();

        return count;

    }


}

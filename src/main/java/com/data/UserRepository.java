package com.data;


import com.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by szkolenie on 2016-09-06.
 */
@ApplicationScoped
public class UserRepository {

    @Inject
    private EntityManager em;

    public User findUserByUsername(String username){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> user = criteria.from(User.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        criteria.select(user).where(cb.equal(user.get("username"), username));
        return em.createQuery(criteria).getSingleResult();
    }
}

package com.service;

import com.data.MemberRepository;
import com.model.Member;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created by szkolenie on 2016-08-19.
 */
@Stateless

public class MemberUnsubscription {
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Member> memberEventSrc;

    @Inject
    private MemberRepository repository;

    public void unsubscribe(String email) throws Exception {
        Member member = repository.findByEmail(email);
        Member memberToDelete = em.find(Member.class, member.getId());

        em.remove(memberToDelete);
        em.flush();
    }

}

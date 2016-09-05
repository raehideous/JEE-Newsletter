package com.controller;

import com.model.User;
import com.service.UserService;
import org.apache.shiro.crypto.hash.Sha256Hash;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by patryk on 03.09.16.
 */
@Named
@ViewScoped
public class UserRegistrationController implements Serializable {

    private User user;

    @EJB
    private UserService service;

    @PostConstruct
    public void init() {
        user = new User();
    }

    @Inject
    FacesContext facesContext;

    private String testPassword;

    public String submit() {
        try {
            user.setPassword(new Sha256Hash(user.getPassword()).toHex());

            service.create(user);
            System.out.println("USER:::: " + user.getUsername() + user.getPassword() + user.getEmail());
            user = new User();

            String message = "Registration suceed, new user ID is:" +  user.getId();
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", message);
            facesContext.addMessage(null, m);

            return "login";

        }
        catch (RuntimeException e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not registered!", e.getMessage());
            facesContext.addMessage(null, m);
            e.printStackTrace(); // TODO: logger.
            return null;
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public String getTestPassword() {
        return testPassword;
    }

    public void setTestPassword(String testPassword) {
        this.testPassword = testPassword;
    }
}

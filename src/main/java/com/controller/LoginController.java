package com.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;


/**
 * Created by szkolenie on 2016-08-31.
 */
@RequestScoped
@Named
public class LoginController {

    Subject currentUser = SecurityUtils.getSubject();

    Session session = currentUser.getSession();

    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method is String type for redirecting to index.xhtml page if user has correctly logged in.
     *
     */
    public String checkLogin(){
        if ( !currentUser.isAuthenticated() ) {
            ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
            username = ex.getRequestParameterMap().get("loginForm:username");
            password = ex.getRequestParameterMap().get("loginForm:password");

            //collect user principals and credentials in a gui specific manner
            //such as username/password html form, X509 certificate, OpenID, etc.
            //We'll use the username/password example here since it is the most common.
            //(do you know what movie this is from? ;)
            System.out.println(username  + password);
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //this is all you have to do to support 'remember me' (no config - built in!):
            token.setRememberMe(true);
            currentUser.login(token);
        }
        return "index";
    }

}

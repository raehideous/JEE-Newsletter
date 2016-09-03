package com.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;


import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.handler.MessageContext;


/**
 * Created by szkolenie on 2016-08-31.
 */
@RequestScoped
@Named
public class LoginController {

    private Subject currentUser = SecurityUtils.getSubject();

    public static final String HOME_URL = "index.xhtml";

    @Inject
    private FacesContext facesContext;

    private String username;
    private String password;

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
    public String submitLogin(){
         if ( !currentUser.isAuthenticated() ) {
             try {
                 ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
                 username = ex.getRequestParameterMap().get("loginForm:username");
                 password = ex.getRequestParameterMap().get("loginForm:password");

                 //collect user principals and credentials in a gui specific manner
                 //such as username/password html form, X509 certificate, OpenID, etc.
                 //We'll use the username/password example here since it is the most common.
                 //(do you know what movie this is from? ;)
                 System.out.println(username + password);
                 UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                 //this is all you have to do to support 'remember me' (no config - built in!):
                 token.setRememberMe(true);
                 currentUser.login(token);

                 return "index";


             }
             catch (AuthenticationException e) {
                 FacesMessage fm  = new FacesMessage("Unknown user or bad pass, please try again");
                 facesContext.addMessage(null, fm);
                // e.printStackTrace(); // TODO: logger.

                 return null;
             }
        }
        else{
            return "index"; //If someone is already logged in
         }

    }

    public String logout(){
        currentUser.logout();

        return "login";
    }

}

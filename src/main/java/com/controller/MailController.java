package com.controller;

import com.service.SendMailTLS;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * Created by szkolenie on 2016-08-26.
 */
@Model
public class MailController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private SendMailTLS sendMailTLS;

    public void sendMails() throws Exception{
        try{
            System.out.println("MemberController.sendMails()");
            sendMailTLS.prepareMailService();
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Messages sent!", "Gratz");
            facesContext.addMessage(null, m);
        }
        catch (Exception ex){
            String errorMessage = ex.getMessage();
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Niepowodzenie");
            facesContext.addMessage(null, m);
        }

    }

}

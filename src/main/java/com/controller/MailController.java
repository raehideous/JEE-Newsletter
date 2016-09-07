package com.controller;

import com.service.SendMailTLS;
import com.service.TimerForSendingMails;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.Date;
import java.util.Timer;

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
            SecurityUtils.getSubject().checkRole("ADMIN");
          //  System.out.println("SubscriberController.sendMails()");       //Info where am I...
            sendMailTLS.prepareMailService();
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Messages sent!", "Gratz");
            facesContext.addMessage(null, m);

        }
        catch (AuthorizationException authEx){
           System.out.println("NIE WYSLALEM< NIE JESTES ADMINEM");
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You're not admin!", "Only admin can send mails.");
            facesContext.addMessage(null, m);
        }
        catch (Exception ex){
            String errorMessage = ex.getMessage();
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Sending unsuccesful.");
            facesContext.addMessage(null, m);
        }

    }

    public void sendMailsAtParticularDate(Date date){
            //  System.out.println("MailController.sendMailsAtParticularDate(" + date + ")");     //Info where am I...
            //Now create the time and schedule it
        try {
            SecurityUtils.getSubject().checkRole("ADMIN");
            Timer timer = new Timer();

            //Use this if you want to execute it once
            timer.schedule(new TimerForSendingMails(), date);

            //Use this if you want to execute it repeatedly
            //int period = 10000;//10secs
            //timer.schedule(new MyTimeTask(), date, period);
        }
        catch (AuthorizationException authEx){
            System.out.println("NIE USTAWWILEM TIMERA, NIE JESTES ADMINEM");
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You're not admin!", "Only admin can send mails.");
            facesContext.addMessage(null, m);
        }
    }

}

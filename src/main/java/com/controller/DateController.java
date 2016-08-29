package com.controller;


import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import javax.enterprise.inject.Model;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by patryk on 29.08.16.
 */
@Model
public class DateController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private MailController mailController;


    private Date date = new Date();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        requestContext.update("dateForm:display");
        requestContext.execute("PF('dlg').show()");
    }

    public void setDateForMailSend(){
        try{
          //  System.out.println("Chosen date: " + date);
            mailController.sendMailsAtParticularDate(date);


        }
        catch (Exception ex){
            String errorMessage = ex.getMessage();
            System.out.println(errorMessage);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Error.");
            facesContext.addMessage(null, m);
        }


    }

}

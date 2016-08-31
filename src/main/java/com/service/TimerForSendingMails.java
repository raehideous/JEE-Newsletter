package com.service;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;
import java.util.Timer;

/**
 * Created by szkolenie on 2016-08-25.
 */
@Stateless
public class TimerForSendingMails extends TimerTask{

    private SendMailTLS sendMailTLS = new SendMailTLS();

    @Override
    public void run() {
        try {
            sendMailTLS.prepareMailService();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage() + ex.getLocalizedMessage() + ex.getCause());
        }
    }
}

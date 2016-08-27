package com.service;

import com.model.DateOfMailDispatch;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by szkolenie on 2016-08-25.
 */
@Stateless
public class TimerForSendingMails{

    @Inject
    SendMailTLS sendMailTLS;

    @Resource
    private SessionContext sessionCtx;



    public void initDateForSendingMails(String day, int hour) {
        Calendar now = Calendar.getInstance();
        System.out.println("cossss" + day + " " +  hour);
        TimerService timerService = sessionCtx.getTimerService();


    }

}

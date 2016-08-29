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

    @Inject
    SendMailTLS sendMailTLS;

    @Override
    public void run() {
        sendMailTLS.prepareMailService();
    }
}

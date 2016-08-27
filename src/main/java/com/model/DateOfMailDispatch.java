package com.model;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by szkolenie on 2016-08-26.
 */

public class DateOfMailDispatch implements Serializable {



    private String dayOfMailDispatch;

    private int hourOfMailDispatch;



    public int getHourOfMailDispatch() {
        return hourOfMailDispatch;
    }

    public void setHourOfMailDispatch(int hoursToMailDispatch) {
        this.hourOfMailDispatch = hoursToMailDispatch;
    }


    public String getDayOfMailDispatch() {
        return dayOfMailDispatch;
    }

    public void setDayOfMailDispatch(String dayOfMailDispatch) {
        this.dayOfMailDispatch = dayOfMailDispatch;
    }


}

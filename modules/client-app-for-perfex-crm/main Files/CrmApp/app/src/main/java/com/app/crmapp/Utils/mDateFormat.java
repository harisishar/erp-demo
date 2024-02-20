package com.app.crmapp.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class mDateFormat {
    public String mFormat(String dt) {
        //String format = "MMMM dd,yyyy";
         String format = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        // SimpleDateFormat df = new SimpleDateFormat("MMMM dd,yyyy");
        return sdf.format(new Date(dt.replaceAll("-", "/")));
    }
}

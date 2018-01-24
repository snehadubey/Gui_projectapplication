/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plot_data;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author Praveen
 */
public class timestamp {
    public static void main(String args[]){
        String time[] = "123/15:38:24:634:279".split("/");
        for(int k1=0;k1<time.length;k1++)
            System.out.println("time"+"["+k1+"]"+time[k1]);
        double hh = TimeUnit.HOURS.toMillis(Long.parseLong(time[0]));
                double  mm  =    +TimeUnit.MINUTES.toMillis(Long.parseLong(time[1]));
                double  sec  =   TimeUnit.SECONDS.toMillis(Long.parseLong(time[2]));
                double   milli =   TimeUnit.MILLISECONDS.toMillis(Long.parseLong(time[3]));
                 double micro_mili=TimeUnit.MICROSECONDS.toMillis(Long.parseLong(time[4]));
                 double   IRIG_time_calc =   hh+mm+sec+milli+micro_mili;
                 double   IRIG_time_calc2 =   hh+mm+sec+milli;
                System.out.println("micro_mili: "+IRIG_time_calc);
                System.out.println("mili: "+IRIG_time_calc2);
    }
}

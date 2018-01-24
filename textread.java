package Image_display;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class textread {
    public static void main(String args[]) throws IOException{
        
        FileReader  fr_para = new FileReader("F:\\Gui_Project\\New folder\\LSP4_ASCII.txt");// to display parameters in textarea
        FileWriter Irig_data = new FileWriter("F:\\Irig_edit_data\\LSP4_387_IRIG_data.txt");
        BufferedReader br_para = new BufferedReader(fr_para);
        String line;
        int count_line=0,i=0;
        String [] split_data;
        
       // String check_tab="hello\tkavya\tbency   hi\t\t";
        //split_data= check_tab.split("\t",-1);
         //for(i=0;i<split_data.length;i++){
               // System.out.println("Line"+i+" : "+split_data[i]);
         //   }
        /*br_para.readLine();
        br_para.readLine();
        br_para.readLine();
        br_para.readLine();
        br_para.readLine();
        br_para.readLine();
        br_para.readLine();
        br_para.readLine();
        br_para.readLine();*/
        int tabs=0;
         //while((line=br_para.readLine())!=null){
                //split_data=line.split("\t");
            // for(i=0;i<split_data.length;i++){
               //System.out.println("Line"+i+" : "+split_data[i]);
          //  }
        //}
         System.out.println("tab space: "+tabs);
        //System.out.println("Line 2: "+br_para.readLine().length());
        //System.out.println("Line 3: "+br_para.readLine().length());
            while((line=br_para.readLine())!=null){
                //line.split("\t");
                ++count_line;
               //if(count_line>=3&&count_line<=4400)
               //{
                    String[] split_head = line.split("\t");
                    //System.out.println("length of string: "+split_head.length);
                    for(i=0;i<split_head.length;i++){
                            //System.out.println("line data: "+i+""+split_head[i]);
                    //System.out.println("0th index: "+ split_head[0].trim());
                                        //System.out.println();
                                        //System.out.println("number of lines"+count_line);
                    }                    
               //}
            }
           // System.out.println("number of lines"+count_line);
           
           //269:15:38:24:603:252
           double hh = TimeUnit.HOURS.toMicros(15);
           double  mm  =    +TimeUnit.MINUTES.toMicros(38);
            double  sec  =   TimeUnit.SECONDS.toMicros(24);
            double   mili =   TimeUnit.MILLISECONDS.toMicros(603);
             double   time =   hh+mm+sec+mili+252;
             System.out.println("hh--->"+hh);
             System.out.println("mm--->"+mm);
             System.out.println("sec--->"+sec);
           System.out.println("mili--->"+mili);
            System.out.println("time--->"+time);
    }
}

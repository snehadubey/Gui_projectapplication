/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Image_display;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class edit_IRIG {
    
    public static void main(String args[]){
            try {
                    FileWriter new_data = new FileWriter("F:\\Irig_edit_data\\LSP7_data.txt");// to write the new file
                FileReader fr_para = null;
                FileReader fr_para_data = null;
                try {
                    fr_para = new FileReader("F:\\Irig_edit_data\\A_AVIONICS_LSP7.txt");//originl file
                    fr_para_data = new FileReader("F:\\Irig_edit_data\\LSP4_387_IRIG_data.txt");//IRIG data
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(edit_IRIG.class.getName()).log(Level.SEVERE, null, ex);
                }
                BufferedReader br_para = new BufferedReader(fr_para);
                 BufferedReader br_para_data = new BufferedReader(fr_para_data);
                String Irig_data,header,putdata;
               new_data.write(br_para.readLine()+System.lineSeparator());
                new_data.write(br_para.readLine()+System.lineSeparator());
                new_data.write(br_para.readLine()+System.lineSeparator());
             int i=0;
                while((Irig_data=br_para_data.readLine())!=null && (header=br_para.readLine())!=null){
                    String[] split_head = header.split(",",-1);
                     String[] replace_data = Irig_data.split(",",-1);
                     i++;
                     putdata =header.replaceFirst(split_head[0], replace_data[0]);
                   new_data.write(putdata+System.lineSeparator());
                }
                System.out.println("i:"+i);
                br_para.close();
                br_para_data.close();
                new_data.close();
            } catch (IOException ex) {
                Logger.getLogger(edit_IRIG.class.getName()).log(Level.SEVERE, null, ex);
            }
               
}
}

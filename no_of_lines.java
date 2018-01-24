/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Image_display;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Praveen
 */
public class no_of_lines {
     
    public static void main(String args[]) throws FileNotFoundException, IOException{
    FileReader fr_para = new FileReader("H:\\New folder\\LSP4_data.txt");//originl file
     BufferedReader br_para = new BufferedReader(fr_para);
     br_para.readLine();
     br_para.readLine();
     br_para.readLine();
     int i=0;
     while(br_para.readLine()!=null){
         i++;
        }
     System.out.println("lines in LSP4_data: "+i);
    }
}

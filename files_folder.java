/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Image_display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author KAVYA JANARDHANA
 */
public class files_folder {
      
    public static void main(String args[]) throws FileNotFoundException{
    
        File folder= new File("F:\\Gui_Project\\Images");
        //String [] extentions = new String []{"gif","png","bmp","jpg"};
        File[] file_list = folder.listFiles();
        String overlay_text = null;
        String overlay_text2 = null;
        String line = null;
        int pos,x=20,y=30;
        if(folder.isDirectory()){
             FileReader File_Reader = new FileReader("datafile.txt");
                    BufferedReader br2 = new BufferedReader(File_Reader);
            for(File file:file_list){
                if(file.isFile()){
                    //System.out.println(file);
                   
                    try {
                        line =br2.readLine();
                        System.out.println(line);
                    } catch (IOException ex) {
                        Logger.getLogger(files_folder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                             overlay_text = file.getName();
                             overlay_text2 = line;
                           // pos=overlay_text.lastIndexOf(".");
                            //overlay_text=overlay_text.substring(0,pos)+"    "+line;//extract from 0 to pos i.e it holds the filename without extension
                           // System.out.println("name return on file: "+overlay_text);
                            BufferedImage img = null;
                            try {
                                img= ImageIO.read(file);
                                System.out.println(file);
                                Graphics2D g2 = img.createGraphics();
                        
                                g2.setFont(g2.getFont().deriveFont(30f));
                                g2.setColor(Color.red);
                                //for(String line_overlay:overlay_text.split("\n")){
                                    //g2.drawString(overlay_text, x, y+=g2.getFontMetrics().getHeight());
                                    g2.drawString(overlay_text, x, y);
                                    g2.drawString(overlay_text2, x, 70);
                                //}
                                g2.dispose();
                                //note:  file.getName() with extension is used to create the file irrespective of the second parameter 
                                ImageIO.write(img,"avi",new File("C:\\Users\\rana\\Desktop\\Gui_Project\\C1S01_timestamp.avi"+file.getName()));
                            } catch (IOException ex) {
                                System.out.println("Problem with reading");
                                Logger.getLogger(files_folder.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    
                   
                }
            }
        }
        else
        {
            System.out.println("Not directory");
        
        }
    }
}

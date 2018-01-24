/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Image_display;

import java.io.File;
import java.util.Arrays;

/**
 *
 * @author Admin
 */
public class folder_read {
     private String[] Image_list;
     String Image_folder="H:\\F DRIVE\\Gui_Project\\Images\\test";//Path of images in folder
     folder_read(){
     int i=0;
        File folder= new File(Image_folder);
        System.out.println("folder: "+Image_folder);
        //String [] extentions = new String []{"gif","png","bmp","jpg"};
        if(folder.exists()){
            File[] file_list = folder.listFiles();
            Image_list = new String [file_list.length];
              Arrays.sort(file_list);
            if(folder.isDirectory()){
            for(File file:file_list){
                if(file.isFile()){
                    //System.out.println(file.getAbsolutePath()+" "+i);
                    Image_list[i]=file.getAbsolutePath();
                    System.out.println("Image_list:"+ " " +Image_list[i]);
                    i++;
                }
            }
          }
        }
     }
     public static void main(String args[]){
     
     new folder_read();
     }
}

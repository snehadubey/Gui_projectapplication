/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Image_display;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author KAVYA JANARDHANA 
 */
public final class IRIG_data {
    public String IRIG_data_line[];
    private int i=0;
    private String[] Image_list2 ;
    private String image_folder=null;
   // FileWriter Irig_data = new FileWriter("F:\\Irig_edit_data\\IRIG_data2.txt");
    IRIG_data(String Image_folder) throws IOException{
        
        image_folder=Image_folder;
        IRIG_data_values();
   
    }
    public void IRIG_data_values() throws FileNotFoundException, IOException{
        
        File folder= new File(image_folder);
       
        if(folder.exists()){
            File[] file_list = folder.listFiles((File dir, String name) -> name.toLowerCase().endsWith(".irg"));
           //System.out.println("length: "+file_list.length);
            int j=0;
            Image_list2 = new String [file_list.length];
            Arrays.sort(file_list);
            if(file_list.length==1){
            if(folder.isDirectory()){
            for(File file:file_list){
                if(file.isFile()){
                    Image_list2[j]=file.getAbsolutePath();
                    j++;
                }
            }
          }
        }
        else{
              JOptionPane.showMessageDialog(null, "More than one .irg file or no .irg file in the folder");
            }
        }
        
        RandomAccessFile raf = new RandomAccessFile(Image_list2[0], "r");
        int Frame_no=0, count=0,seek2=8,seek3=16;
        long s1=raf.length();
        int no_irig = (int)(s1)/20;
        IRIG_data_line=new String[no_irig];
        while(count<s1){
        Frame_no=Frame_no+1;
	    //System.out.println("\nFrame no: "+Frame_no);
	    //System.out.println("\ncount no: "+count);
	    raf.seek(seek2);
        seek2=seek2+20;
        byte[] dd = new byte[4];
        raf.read(dd, 0, 4);
        ByteBuffer wrapped = ByteBuffer.wrap(dd); // big-endian by default
        wrapped.order(ByteOrder.LITTLE_ENDIAN);
        int Day_of_year = wrapped.getInt();
       // System.out.println("Day_of_year: "+Day_of_year);
        
        
        //raf.seek(12);
        byte[] hh = new byte[4];
        raf.read(hh, 3, 1);
        ByteBuffer wrapped1 = ByteBuffer.wrap(hh); // big-endian by default
        //wrapped.order(ByteOrder.LITTLE_ENDIAN);
        int Hours = wrapped1.getInt();
        //System.out.println("\nHours: "+Hours);
        
        //raf.seek(13);
        byte[] mm = new byte[4];
        raf.read(mm, 3, 1);
        ByteBuffer wrapped2 = ByteBuffer.wrap(mm); // big-endian by default
        //wrapped.order(ByteOrder.LITTLE_ENDIAN);
        int min = wrapped2.getInt();
       // System.out.println("\nmin: "+min);
        
        byte[] sec = new byte[4];
        //raf.seek(14);
        raf.read(sec, 3, 1);
        ByteBuffer wrapped3 = ByteBuffer.wrap(sec); // big-endian by default
        //wrapped.order(ByteOrder.LITTLE_ENDIAN);
        int secs = wrapped3.getInt();
       // System.out.println("\nsec: "+secs);
        
     
        raf.seek(seek3);
        byte[] ms = new byte[4];
        raf.read(ms, 0, 4);
        ByteBuffer wrapped4 = ByteBuffer.wrap(ms); // big-endian by default
       wrapped4.order(ByteOrder.LITTLE_ENDIAN);
       int micro_sec_int = wrapped4.getInt();
       String micro_sec= String.format("%06d", micro_sec_int);
       String milli_sec = micro_sec.substring(0, 3);
       String micro_secs = micro_sec.substring(3, micro_sec.length());
       //System.out.println("micro_sec: "+micro_sec);
      // System.out.println("milli_sec: "+milli_sec);
      // System.out.println("micro_secs: "+micro_secs);
       
       //float micro_sec_float = wrapped4.getFloat();
       //System.out.println("micro_sec_int: "+micro_sec);
        //System.out.println("micro_sec_float: "+micro_sec_float);
        seek3=seek3+20;
        
       // System.out.println("\nmicro_sec: "+micro_sec);
        //System.out.println(BigDecimal.valueOf(sec).toPlainString());
        //System.out.println(Day_of_year+"/"+Hours+":"+min+":"+secs+"."+micro_sec);
      
            
            int index_IRIG=(2001*20)-20; 
            if(count>=index_IRIG){
                //System.out.println("i: "+i);
                    //System.out.println(Day_of_year+"/"+Hours+":"+min+":"+secs+"."+micro_sec);
                    IRIG_data_line[i]=Day_of_year+":"+Hours+":"+min+":"+secs+":"+milli_sec+":"+micro_secs;
                   // System.out.println("arr: "+"["+i+"]"+IRIG_data_line[i]);
                    //Irig_data.write(IRIG_data_line[i]+System.lineSeparator()); 
                    i++;
            }

         count=count+20;
       }
       //Irig_data.close();
    }
    public static void main(String args[]) throws IOException{
    IRIG_data obj=new IRIG_data("F:\\Gui_Project\\data\\");
    obj.IRIG_data_values();
    }

}

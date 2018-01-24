/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui_components;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.plaf.metal.MetalSliderUI;

/**
 *
 * @author Praveen
 */
public class slider {
    public static void main(String args[]){
         
        JFrame f= new JFrame();
        JSlider slider = new JSlider(JSlider.HORIZONTAL);
        f.add(slider);
        slider.setMinimum(0);
        slider.setMaximum(500);
        slider.setUI(new MetalSliderUI(){
            
            @Override
            protected void scrollDueToClickInTrack(int direction){
               
                int value= slider.getValue();
                if(slider.getOrientation()==JSlider.HORIZONTAL){
                    value = this.valueForXPosition(slider.getMousePosition().x);
                } else if(slider.getOrientation()==JSlider.VERTICAL){
                    value = this.valueForXPosition(slider.getMousePosition().y);
                }
                slider.setValue(value);
            }
        });
        f.pack();
        f.setVisible(true);
    }
}

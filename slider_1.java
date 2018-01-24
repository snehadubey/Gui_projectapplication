package Gui_components;


import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.plaf.metal.MetalSliderUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rana
 */
public class slider_1 {
    public static void main(String args[]){
      JFrame f= new JFrame();
        JSlider slider = new JSlider(JSlider.HORIZONTAL);
        f.add(slider);
        
slider.setUI(new MetalSliderUI() {
    //@Override
    protected void scrollDueToClickInTrack(int direction) {
        // this is the default behaviour, let's comment that out
        //scrollByBlock(direction);

        int value = slider.getValue(); 

        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            value = this.valueForXPosition(slider.getMousePosition().x);
        } else if (slider.getOrientation() == JSlider.VERTICAL) {
            value = this.valueForYPosition(slider.getMousePosition().y);
        }
        slider.setValue(value);
    }
});
        
        f.pack();
        f.setVisible(true);
        
    }
}

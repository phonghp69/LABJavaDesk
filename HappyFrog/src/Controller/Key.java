/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Administrator
 */
public class Key implements KeyListener{
    private boolean press=false;
    private boolean release=true;

    public boolean isPress() {
        return press;
    }

    public boolean isRelease() {
        return release;
    }

    public void setPress(boolean press) {
        this.press = press;
    }

    public void setRelease(boolean release) {
        this.release = release;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP){
        if(release){
                press = true;
                release = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        release=true;
         
    }
    
}

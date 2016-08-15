/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author gillmylady
 */
public class Apple {
    
    int position;
    boolean alive;
    
    public Apple(){
        this.position = 0;
        this.alive = true;
    }
    
    public Apple(Graphics g, ArrayList<LinkedList<Integer>> snakes){
        nextApple(g, snakes);
    }
    
    public Apple nextApple(Graphics g, ArrayList<LinkedList<Integer>> snakes){
        Random rd = new Random();
        int pos;
        boolean select;
        while(true){
            int randomP = rd.nextInt(PublicData.COL * PublicData.ROW);
            select = true;
            for(LinkedList<Integer> l : snakes){
                if(l.indexOf(randomP) != -1){
                    select = false;
                    break;
                }
            }
            if(select){
                pos = randomP;
                break;
            }
        }
        this.alive = true;
        this.position = pos;
        drawApple(g);
        return this;
    }
    
    public void drawApple(Graphics g){
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int x = position % PublicData.COL;
                int y = position / PublicData.COL;

                g.setColor(Color.black);
                g.fillRect(x * PublicData.SCALE, y * PublicData.SCALE, PublicData.SCALE, PublicData.SCALE);
            }
        });
         
    }
    
    public void remove(){
        alive = false;
    }
    
    public void setPosition(int position){
        this.position = position;
    }
    
    public int getPosition(){
        return this.position;
    }
    
    public boolean getStatus(){
        return this.alive;
    }
}

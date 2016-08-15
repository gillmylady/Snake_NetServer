/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author gillmylady
 */
public class OnePlayer {
    
    private String name;                    //name of this player
    private int direction;                //default direction
    private int lastDirection;            //default last direction
    private int newHead;                //new head of this snake
    private int applePosition;              //apple's position
    private int status;                     // 0-new player, 1-remove tail, 2-keep tail
    private Color color;                    //color of the snake body
    private LinkedList<Integer> snakeBodies;                //snake bodies
    
    public OnePlayer(Color color){          //construction class, set the color
        init();
        this.color = color;
    }
    /**
     * to initialize
     */
    public void init(){
        snakeBodies = new LinkedList<>();
        snakeBodies.add(0);          //from left up corner of the map to right
        snakeBodies.add(1);          //from left up corner of the map to right
        direction = PublicData.RIGHT;
        lastDirection = PublicData.RIGHT;
        this.status = PublicData.PLAYER_NEW;
    }
    
    /**
     * to clear the snake
     * @param g
     */
    public void clear(Graphics g){
        while(snakeBodies.size() > 0){
            removeSnakeTail(g);
            snakeBodies.remove();
        }
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setDirection(int direction){
        this.direction = direction;
    }
    
    public void setDirection(int direction, int lastDirection){
        if(         (direction == PublicData.UP && lastDirection != PublicData.DOWN)
                ||  (direction == PublicData.DOWN && lastDirection != PublicData.UP)
                ||  (direction == PublicData.LEFT && lastDirection != PublicData.RIGHT)
                ||  (direction == PublicData.RIGHT && lastDirection != PublicData.LEFT))
            this.direction = direction;
    }
    
    public int getDirection(){
        return direction;
    }
    
    public int getLastDirection(){
        return lastDirection;
    }
    
    public void setApplePosition(int position){
        this.applePosition = position;
    }
    
    public int getApplePosition(){
        return this.applePosition;
    }
    
    public int getSnakeSize(){
        return snakeBodies.size();
    }
    
    public LinkedList<Integer> getSnakeBodies(){
        return snakeBodies;
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public int getPlayerStatus(){
        return this.status;
    }
    
    public void calculateNewHead(){
        switch (direction) {
            case PublicData.UP:
                newHead = snakeBodies.getLast() - PublicData.COL;
                break;
            case PublicData.DOWN:
                newHead = snakeBodies.getLast() + PublicData.COL;
                break;
            case PublicData.LEFT:
                newHead = snakeBodies.getLast() - 1;
                break;
            default:                // if(direction == DOWN)
                newHead = snakeBodies.getLast() + 1;
                break;
        }
    }
    
    
    /**
     *  to paint the bodies of snake
     * @param g
     */
    public void paintSnakeBodies(Graphics g){
        for(int j : snakeBodies){
            paintOnePieceOfSnake(g, j, j == snakeBodies.getLast(), false);
        }
    }
    
    /**
     * 
     * @param g
     */
    public void removeSnakeTail(Graphics g){
        paintOnePieceOfSnake(g, snakeBodies.getFirst(), false, true);
    }
    
    
    /**
     * to paint one point (block) of the snake
     * @param g
     * @param position  position of the point
     * @param head      if this is the head of the snake
     * @param removeTail    if this is the tail of the snake to be removed
     */
    public void paintOnePieceOfSnake(Graphics g, int position, boolean head, boolean removeTail){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int x = position % PublicData.COL;
                int y = position / PublicData.COL;

                if(removeTail){
                    g.setColor(PublicData.BACKGROUND);
                }else if(head){
                    g.setColor(PublicData.HEADCOLOR);
                }else{
                    g.setColor(color);
                }
                g.fillRect(x * PublicData.SCALE, y * PublicData.SCALE, PublicData.SCALE, PublicData.SCALE);
            }
        });
    }
    
    /**
     *  check if the snake will hit the walls
     * @return 
     */
    public boolean hitWalls(){
        return (direction == PublicData.UP && (snakeBodies.getLast() / PublicData.COL) == 0) ||    //the first row
                (direction == PublicData.DOWN && (snakeBodies.getLast() / PublicData.COL) == (PublicData.ROW-1)) ||    //last row
                (direction == PublicData.LEFT && (snakeBodies.getLast() % PublicData.COL) == 0) ||         //first col
                (direction == PublicData.RIGHT && (snakeBodies.getLast() % PublicData.COL) == (PublicData.COL-1)) //last col
;
    }
    
    /**
     * 
     * @return 
     */
    public boolean hitItself(){
        return snakeBodies.indexOf(newHead) != -1;
    }
    
    /**
     * 
     * @param anotherSnakeBodies    another snake
     * @return 
     */
    public boolean hitOtherSnake(LinkedList<Integer> anotherSnakeBodies){
        return anotherSnakeBodies.indexOf(newHead) != -1;
    }
    
    /**
     * 
     * @param g
     * @return true if this snake eats the apple
     */
    public boolean checkMove(Graphics g){
        //System.out.printf("in CheckMove, direction=%d, newHead=%d\n", direction, newHead);
        lastDirection = direction;
        snakeBodies.add(newHead);
        if(newHead != applePosition){               //not eat the apple
            removeSnakeTail(g);
            snakeBodies.remove();
            this.status = PublicData.PLAYER_REMOVETAIL;
            return false;
        }else{
            this.status = PublicData.PLAYER_KEEPTAIL;
            return true;
        }
    }
    
    public String showAsString(){
        return name + ":" 
                + Integer.toString(direction) + ":" 
                + Integer.toString(lastDirection) + ":" 
                + Integer.toString(newHead) + ":" 
                + Integer.toString(applePosition) + ":" 
                + Integer.toString(status) + ":"
                + Integer.toString(color.getRGB()) + ":" 
                + snakeBodies.toString();
    }
    
    public boolean setFromString(String s){
        try{
            int index = 0;
            String[] data = s.split(":");
            
            this.name = data[index++];
            this.direction = Integer.parseInt(data[index++]);
            this.lastDirection = Integer.parseInt(data[index++]);
            this.newHead = Integer.parseInt(data[index++]);
            this.applePosition = Integer.parseInt(data[index++]);
            this.status = Integer.parseInt(data[index++]);
            this.color = new Color(Integer.parseInt(data[index++]));
            
            String subS = data[index].substring(1, data[index].length()-1);
            String[] nums = subS.split(", ");
            this.snakeBodies.clear();
            for(String item : nums){
                this.snakeBodies.add(Integer.parseInt(item));
            }
            
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
}

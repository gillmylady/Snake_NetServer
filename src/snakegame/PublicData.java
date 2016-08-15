/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;

/**
 *
 * @author gillmylady
 */
public class PublicData {
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 10;
    public static int ROW = HEIGHT / SCALE;
    public static int COL = WIDTH / SCALE;
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static int FREQUENCY = 200;            //how quick it flush
    public static int PAUSETIME = 10000;          //pause time
    public static int GAMEOVERTIME = 3000;        //game overe display time
    
    public static Color BACKGROUND = Color.green;
    public static Color HEADCOLOR = Color.red;
    public static Color APPLECOLOR = Color.black;
    
    public static int PLAYER_NEW = 0;
    public static int PLAYER_REMOVETAIL = 1;
    public static int PLAYER_KEEPTAIL = 2;
    
    public static final int port = 9002;                           //port for connection
    public static String newPlayer = "NEWPLAYER";
    public static String newDirection = "NEWDIRECTION";
    
}

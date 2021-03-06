/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;



/**
 *
 * @author gillmylady
 */
public class Server extends javax.swing.JFrame {

    private static final int sendFrenquency = 300;                  //0.3 s
    private HashMap<PrintWriter, OnePlayer> writersPlayers;
    private HashMap<Integer, Color> colors;
    private Apple apple;
    /**
     * Creates new form Server
     */
    public Server() {
        initComponents();
        initGame();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMap = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaPlayers = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaLog = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanelMapLayout = new javax.swing.GroupLayout(jPanelMap);
        jPanelMap.setLayout(jPanelMapLayout);
        jPanelMapLayout.setHorizontalGroup(
            jPanelMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelMapLayout.setVerticalGroup(
            jPanelMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );

        jTextAreaPlayers.setColumns(20);
        jTextAreaPlayers.setRows(5);
        jScrollPane1.setViewportView(jTextAreaPlayers);

        jTextAreaLog.setColumns(20);
        jTextAreaLog.setRows(5);
        jTextAreaLog.setText("Log:");
        jScrollPane2.setViewportView(jTextAreaLog);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelMap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelMap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void initGame(){
        jPanelMap.setBackground(Color.green);
        jPanelMap.setPreferredSize(new Dimension(PublicData.WIDTH, PublicData.HEIGHT));
        jTextAreaPlayers.setEditable(false);
        jTextAreaLog.setEditable(false);
        this.setVisible(true);
        
        this.writersPlayers = new HashMap<>();
        this.colors = new HashMap<>();
        colors.put(1, Color.blue);
        colors.put(2, Color.magenta);
        colors.put(3, Color.yellow);
        colors.put(4, Color.gray);
        colors.put(5, Color.ORANGE);
        colors.put(6, Color.PINK);
        
        apple = new Apple(jPanelMap.getGraphics() , getSnakes());
    }
    
    //assign new connection a unique color
    public synchronized Color assignColor(){
        boolean findFlag;
        for(Color color : colors.values()){
            findFlag = true;
            for(OnePlayer player : writersPlayers.values()){
                if(player.getColor() == color){
                    findFlag = false;
                    break;
                }
            }
            if(findFlag)
                return color;
        }
        return Color.white;
    }
    
    public void printLog(String s){
        jTextAreaLog.append("\n" + s);
    }
    
    public ArrayList<LinkedList<Integer>> getSnakes(){
        ArrayList<LinkedList<Integer>> snakes = new ArrayList<>();
        for(OnePlayer player : writersPlayers.values()){
            snakes.add(player.getSnakeBodies());
        }
        return snakes;
    }
    
    private void runThreads() throws Exception{
        printLog("server starts");
        BroadcastLiveData threadBroadcast = new BroadcastLiveData();
        threadBroadcast.start();
        ServerSocket listener = new ServerSocket(PublicData.port);
        try{
            while(true){
                new Handler(listener.accept()).start();
            }
        }finally{
            listener.close();
            threadBroadcast.shutdown();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //???????????????????????????????????????????????????????????????????????????
        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Server server = new Server();
                try {
                    server.runThreads();
                } catch (Exception ex) {
                    //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
*/
        Server server = new Server();
        try {
            server.runThreads();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    private class BroadcastLiveData extends Thread{
        
        private boolean running = true;
        
        public BroadcastLiveData(){
            printLog("broadcast thread begin");
        }
        
        public synchronized void run(){
            while(running){
                try {
                    this.wait(sendFrenquency);
                } catch (InterruptedException ex) {
                    printLog("InterruptedException of Broadcast thread");
                }
                judgeLiveData();
                sendLiveData();
                refreshLiveScore();
            }
        }
        
        public synchronized void shutdown(){
            running = false;
        }
    }
    
    //server as the judge, makes decisions of the next step of each snake
    private synchronized void judgeLiveData(){
        boolean falseFlag;
        for(OnePlayer player : writersPlayers.values()){
            falseFlag = true;
            player.calculateNewHead();
            if(player.hitWalls()){                  //if hit wall
                falseFlag = false;
            }
            if(falseFlag){
                for(OnePlayer p : writersPlayers.values()){
                    if(player.hitOtherSnake(p.getSnakeBodies())){       //if hit other snakes including itself
                        falseFlag = false;
                        break;
                    }
                }
            }
            
            if(falseFlag == false){                 //if hit wall of snake, die, clear and initialize
                printLog("client die");
                player.clear(jPanelMap.getGraphics());
                player.init();
            }else{
                if(player.checkMove(jPanelMap.getGraphics())){              //move, and check if apple diappear
                    apple = new Apple(jPanelMap.getGraphics() , getSnakes());   //if eat apple, make a new apple
                }
            }
            player.paintSnakeBodies(jPanelMap.getGraphics());               //paint these snakes
        }
        apple.drawApple(jPanelMap.getGraphics());                           //draw apple
        
        for(OnePlayer player : writersPlayers.values()){
            player.setApplePosition(apple.getPosition());                   //evaluate latest apple position to all snakes
        }
    }
    
    //send live data of all snakes to each client
    private synchronized void sendLiveData(){
        if(writersPlayers.isEmpty())
            return;
        String liveData = "";
        
        for(OnePlayer player : writersPlayers.values()){
            liveData = liveData + player.showAsString() + ";";
        }
        liveData = liveData.substring(0, liveData.length()-1);      //remove last ';'
        printLog("send livedata:" + liveData);
        for(PrintWriter writer : writersPlayers.keySet()){          //send to each client
            writer.println(liveData);
        }
    }
    
    //refresh live score in the left-down board/ text area
    private synchronized void refreshLiveScore(){
        String liveScore = "";
        
        for(OnePlayer player : writersPlayers.values()){
            liveScore = liveScore + player.getName() + ": " + Integer.toString(player.getSnakeSize()) + "\n";
        }
        jTextAreaPlayers.setText(liveScore);
    }
    
    //thread to deal with client's connection/ socket
    private class Handler extends Thread{
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        
        public Handler(Socket socket){
            this.socket = socket;
            printLog("one client comes");
        }
        
        public void run(){
            
            try{
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                while(true){
                    String input = in.readLine();
                    if(input.length() == 0)
                        continue;
                    
                    OnePlayer player = new OnePlayer(assignColor());
                    
                    synchronized(writersPlayers){
                        if(input.startsWith(PublicData.newPlayer)){            //comes a new player
                            printLog("add one player into hashmap");
                            String name = input.substring(PublicData.newPlayer.length() + 1, input.length());
                            player.setName(name);
                            writersPlayers.put(out, player);
                        }else{
                            printLog("reset player direction info into hashmap");
                            writersPlayers.get(out).setDirection(Integer.parseInt(input.split(":")[1]));
                        }
                    }
                }
                                
            }catch(IOException e){
                printLog("handler IOException");
            }finally{
                if(out != null){
                    writersPlayers.get(out).clear(jPanelMap.getGraphics());
                    writersPlayers.remove(out);
                }
                try{
                    printLog("one client quits");
                    socket.close();
                }catch(IOException e){
                    printLog("IOException at ending client socket");
                }
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelMap;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaLog;
    private javax.swing.JTextArea jTextAreaPlayers;
    // End of variables declaration//GEN-END:variables
}

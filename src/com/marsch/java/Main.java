package com.marsch.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by marsch on 10/4/16.
 */

class Game extends JFrame implements Runnable,MouseListener{
    int n;
    int size;
    int idx,idy;
    int arrso[][] = new int[10][10];
    int arrCheck[][] = new int[10][10];
    int[] track = new int[11];

    ArrayList<Point> dalat = new ArrayList<Point>();

    BufferedImage bufferedImage;
    Graphics bufferGraphics;
    boolean clicked = false;
    Point lastclick = new Point(-1,-1);
    int count=0;

    boolean needClear=true;

    ArrayList<Point> temp = new ArrayList<Point>();

    public Game(){
        setTitle("Lat So - Huynh Tu Thien");
        this.n = 9;
        this.size = 30;
        this.idx = 50;
        this.idy = 50;

        this.setSize(n * size + idx * 2, n * size + idy * 2);
        this.setLocation(500,100);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initArrSo();
        setVisible(true);
        this.addMouseListener(this);
    }

    private void initArrSo() {
        for(int i=0;i<11;i++)
            track[i] = 0;
        Random rand = new Random();
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++){
                int newNum = rand.nextInt(9);
                while (track[newNum+1] == 9){
                    newNum = rand.nextInt(9);
                }
                arrso[i][j] = newNum+1;
                track[newNum+1]++;
            }

            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++)
                    System.out.print(arrso[j][i]+" ");
                System.out.println();
            }

    }

    @Override
    public void paint(Graphics g) {
        System.out.println("Paint");
        if (bufferGraphics==null){
            bufferedImage = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_RGB);
            bufferGraphics = bufferedImage.getGraphics();
        }
        bufferGraphics.setColor(Color.RED);
        bufferGraphics.fillRect(0,0,this.getWidth(),this.getHeight());
        bufferGraphics.setColor(Color.BLACK);

        for(int i=0;i<=n;i++){
            bufferGraphics.drawLine(idx, idy + i*size, idx+n*size, idy + i*size); // Ngang
            bufferGraphics.drawLine(idx +  i*size, idy, idx+ i*size, idy+n*size); // Doc
        }
        bufferGraphics.setColor(Color.WHITE);
        String ch;

        bufferGraphics.setFont(new Font("Arial",Font.BOLD,17));

        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++){
                if (arrCheck[i][j]>0){
                    if (arrCheck[i][j]==2){
                        bufferGraphics.setColor(Color.WHITE);
                    } else {
                        bufferGraphics.setColor(Color.BLACK);
                    }
                    ch = "" + arrso[i][j];
                    bufferGraphics.drawString(ch,i*30+idx+10,j*30+idy+20);
                }
            }
        g.drawImage(bufferedImage,0,0,this);
    }

    @Override
    public void run() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int x = (e.getX() - idx)/size;
        int y = (e.getY() - idy)/size;

        if (x<0 || x>=n || y<0 || y>=n) return;
        Point point;
        while (temp.size()>0){
            point = temp.get(temp.size()-1);
            arrCheck[point.x][point.y] = 0;
            temp.remove(temp.size()-1);
        }

        if (!lastclick.equals(new Point(x,y)) && arrCheck[x][y]==0) {
            arrCheck[x][y] = 2;
            if (clicked) {
                if (arrso[lastclick.x][lastclick.y] == arrso[x][y]) {
                    clicked = false;
                    arrCheck[x][y]=2;
                    arrCheck[lastclick.x][lastclick.y] =2;
                    lastclick = new Point(-1,-1);

                } else {
                    temp.add(new Point(lastclick.x,lastclick.y));
                    temp.add(new Point(x,y));
                    arrCheck[x][y]=1;
                    arrCheck[lastclick.x][lastclick.y] =1;
                    clicked = false;
                    lastclick = new Point(-1,-1);
                }
            } else {
                clicked = true;
                lastclick = new Point(x, y);
            }
        }

        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
public class Main {
    public static void main(String[] agrs){
        new Game();
    }
}

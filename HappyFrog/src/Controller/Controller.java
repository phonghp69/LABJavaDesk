/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.Frog;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Administrator
 */
public class Controller {

    private Frog f;
    ImageIcon frog = new ImageIcon(getClass().getResource("../Image/frog.png"));
    ImageIcon tren = new ImageIcon(getClass().getResource("../Image/ongtren.png"));
    ImageIcon duoi = new ImageIcon(getClass().getResource("../Image/ongduoi.png"));
    private final List<JButton> list = new ArrayList<>();
    private Timer timer;
    private JLabel frogz = new JLabel(frog);
    private int yF = 60;

    private int score = 20;
    private String mess;
    Key key = new Key();
    private boolean checkSave = false;
    private Random r = new Random();
    private int space=100;
    
     
    public Controller(Frog f) {
        this.f = f;
        
        frogz.setBounds(70, yF, 40, 40);
        f.pnDisplay.add(frogz);
        frogz.addKeyListener(key);
        f.getBtnPause().addKeyListener(key);
        f.getBtnSave().addKeyListener(key);
        addCol();
        addCol();
        addCol();
        addCol();
    }

    public void addCol() {
        JButton btnUp = new JButton(tren);
        JButton btnDown = new JButton(duoi);  
       int height = r.nextInt(50) + 80;
        btnUp.setBounds(668 + list.size() * 100, 0, 40, 370-height-space);
        btnDown.setBounds(668 + list.size() * 100, 370 - height, 40,height );
        
        list.add(btnDown);
        list.add(btnUp);
        f.pnDisplay.add(btnUp);
        f.pnDisplay.add(btnDown);
    }
    boolean checkpause = false;

    public void pause() {
        if (checkpause == false) {
            timer.stop();
            f.btnPause.setText("Resume");
            checkpause = true;
        } else {
            timer.restart();
            f.btnPause.setText("Pause");
            checkpause = false;
        }
    }

    public void saveGame() {
        checkSave = true;
        try {
            FileWriter fw = new FileWriter("datasave.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (JButton o : list) {
                int x = o.getX();
                int y = o.getY();
                int w = o.getWidth();
                int h = o.getHeight();
                bw.write(x + ";" + y + ";" + w + ";" + h);
                bw.newLine();
            }
            bw.write(score + ";" + yF);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
        }
    }

    public boolean checkTouch() {
        //check cham tren cham duoi
        if (yF <= 0 || yF >= 330) {
            return true;
        }
        //check cham vao cot
        Rectangle frog = new Rectangle(frogz.getX(), frogz.getY(), frogz.getWidth(), frogz.getHeight());
        for (JButton btn : list) {
            Rectangle col = new Rectangle(btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight());
            if (frog.intersects(col)) {
                return true;
            }
        }
        return false;
    }

    public void Message() {
        int mark = score / 2;
        if (mark <= 10) {
            mess = "Your score:" + mark;
        }
        if (mark <= 20 && mark > 10) {
            mess = "Your rank is bronze.Your score:  " + mark;
        }
        if (mark <= 30 && mark > 20) {
            mess = "Your rank is silver.Your score:  " + mark;
        }
        if (mark <= 40 && mark > 30) {
            mess = "Your rank is gold.Your score:  " + mark;
        }
        if (mark > 40) {
            mess = "Your rank is platinum.Your score: " + mark;
        }
        if (!checkSave) {
            Object mes[] = {"New Game", "Exit"};
            int option = JOptionPane.showOptionDialog(null, "You're died." + mess + "\n Do you want to continue?",
                    "Notice!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, mes, mes[0]);
            if (option == 0) {
                f.pnDisplay.removeAll();
                f.pnDisplay.repaint();
                list.clear();
                score = 0;
                f.lblPoint.setText("Point: 0");
                yF = 60;
                f.getPnDisplay().add(frogz);
                addCol();
                addCol();
                addCol();
                addCol();
                frogz.requestFocus();
                timer.restart();
            }
            if (option == 1) {
                System.exit(0);
            }
        }
        if (checkSave) {
            Object mes[] = {"New Game", "Continue", "Exit"};
            int option = JOptionPane.showOptionDialog(null, "You're died." + mess + "\n Do you want to continue?",
                    "Notice!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, mes, mes[0]);
            if (option == 0) {
                f.pnDisplay.removeAll(); // delete all old button and frog
                f.pnDisplay.repaint();  //update new location
                list.clear(); // clear list
                score = 0;
                f.lblPoint.setText("Point: 0");
                yF = 60;
                addCol();
                addCol();
                addCol();
                addCol();
                frogz.requestFocus();
                f.getPnDisplay().add(frogz);
                timer.restart();
            }
            if (option == 1) {
                f.pnDisplay.removeAll();
                f.pnDisplay.repaint();
                list.clear();
                try {
                    FileReader fr = new FileReader("datasave.txt"); //read file
                    BufferedReader br = new BufferedReader(fr); // read data in file
                    String line = "";
                    do {
                        line = br.readLine().trim();
                        if (line == null) {
                            break;
                        }
                        JButton btn = new JButton(duoi); 
                        String txt[] = line.split(";");
                        int arr[] = new int[txt.length];
                        for (int i = 0; i < arr.length; i++) {
                            arr[i] = Integer.parseInt(txt[i]);
                        }
                        if (arr.length == 4) {
                            btn.setBounds(arr[0], arr[1], arr[2], arr[3]); //set button according to data
                            list.add(btn);
                            f.pnDisplay.add(btn);
                        } else {
                            score = arr[0]; // set score according to data
                            f.lblPoint.setText("Point: " + score / 2);
                            yF = arr[1]; // set Y of Frog according to data
                             f.pnDisplay.add(frogz);
                        }
                    } while (true);
                    br.close();
                } catch (Exception e) {
                }              
                timer.restart();
            }
            if (option == 2) {
                System.exit(0);
            }
        }
    }

    public void score() {
        for (JButton o : list) {
            if (o.getX() == frogz.getX()) {
                score++;
                f.lblPoint.setText("Point: " + score / 2); // because col have 2 button
            }
        }
    }

    public void run() {
        frogz.requestFocus();
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                int rd = r.nextInt(50) + 80;
                if (key.isPress()) {
                    yF = yF - 20; // frog fly up
                    key.setPress(false);
                }
                yF++; // frog fall down
                frogz.setBounds(70, yF, 40, 40); // set frog according to yF
                for (int i = 0; i < list.size(); i++) {
                    int x = list.get(i).getX() - 1; // button moves to the left
                    int y = list.get(i).getY();
                    list.get(i).setLocation(x, y); // set button move
                    if (x <= -110) {
                        
                        // after x<=-100. set button with new location and size
                       if(i%2==0){
                        list.get(i).setSize(list.get(i).getWidth(),rd); // set new size
                         list.get(i).setLocation(668, 370-rd);
                       }else{
                            list.get(i).setSize(list.get(i).getWidth(),370-rd-space);
                        list.get(i).setLocation(668, 0);
                       }
                      //  list.get(i).setLocation(668, y); // set new location
                    }
                }
                score();
                if (checkTouch()) {
                    timer.stop();
                    Message();
                }
            }
        });
        timer.start();
    }

}

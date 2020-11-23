import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.JPanel;

public class Panel extends JPanel implements Food, ActionListener{
    static final int lebarLayar = 500;
    static final int tinggiLayar = 500;
    static final int ukuranPetak = 25;
    static final int banyakPetak = (lebarLayar*tinggiLayar)/ukuranPetak;
    static final int kecepatanGerak = 80;
    final int x[] = new int[banyakPetak];
    final int y[] = new int[banyakPetak];
    char arah = 'D';
    boolean running = true;
    int panjangTubuh = 5;
    int makananX;
    int makananY;
    int termakan;
    Timer waktu;
    Random random;
    
    Panel(){
        random = new Random();
        this.setPreferredSize(new Dimension (lebarLayar,tinggiLayar));
        this.setBackground(Color.gray);
        this.setFocusable(true);
        this.addKeyListener(new KeyDirection());
        startGame();
    }

    public void startGame(){
        newFood();
        running = true;
        waktu = new Timer(kecepatanGerak,this);
        waktu.start();
    }

    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        for(int i = 0; i < tinggiLayar/ukuranPetak; i++){
            g.drawLine(i*ukuranPetak, 0, i*ukuranPetak, tinggiLayar);
            g.drawLine(0, i*ukuranPetak, lebarLayar, i*ukuranPetak);
        }
        g.setColor(Color.white);
        g.fillOval(makananX,makananY,ukuranPetak, ukuranPetak); 
        for(int i = 0; i<panjangTubuh; i++){
            if(i == 0){
                g.setColor(Color.pink);
                g.fillOval(x[i], y[i], ukuranPetak+1, ukuranPetak+1);
            }else{
                g.setColor(Color.black);
                g.fillOval(x[i], y[i], ukuranPetak+1, ukuranPetak+1);
            }
        }           
    }   

    @Override
    public void newFood(){
        makananX = random.nextInt((int)(lebarLayar/ukuranPetak))*ukuranPetak;
        makananY = random.nextInt((int)(lebarLayar/ukuranPetak))*ukuranPetak;
    }

    @Override
    public void checkFood(){
        if((x[0] == makananX) && (y[0] == makananY)){
            panjangTubuh++;
            termakan++;
            newFood();
        }
    }
    
    public void actionPerformed(ActionEvent e){
        MoveAbility ma = new MoveAbility();
        if(running){
            ma.move(panjangTubuh, x , y ,ukuranPetak, arah);
        }
        repaint();    
    }

    public class KeyDirection extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){

                case KeyEvent.VK_LEFT:
                    if(arah != 'R'){
                        arah = 'L';
                    }
                break;

                case KeyEvent.VK_RIGHT:
                    if(arah != 'L'){
                        arah = 'R';
                    }
                break;

                case KeyEvent.VK_UP:
                    if(arah != 'D'){
                        arah = 'U';
                    }
                break;

                case KeyEvent.VK_DOWN:
                    if(arah != 'U'){
                        arah = 'D';
                    }
                break;
            }
        }
    }
}
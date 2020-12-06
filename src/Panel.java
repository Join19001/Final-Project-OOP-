import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.JPanel;

public class Panel extends JPanel implements Food, ActionListener{
    static final int lebarLayar = 480;
    static final int tinggiLayar = 480;
    static final int ukuranPetak = 10;
    static final int banyakPetak = (lebarLayar*tinggiLayar)/ukuranPetak;
    static final int kecepatanGerak = 80;
    final int x[] = new int[banyakPetak];
    final int y[] = new int[banyakPetak];
    char arah = 'U';
    boolean running = true;
    int panjangTubuh = 6;
    int makananX;
    int makananY;
    int termakan;
    Timer waktu;
    Random random;
    int posisi = 0;

    
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
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
       if(running){
            for(int i = 0; i < tinggiLayar/ukuranPetak; i++){
                g.drawLine(i*ukuranPetak, 0, i*ukuranPetak, tinggiLayar);
                g.drawLine(0, i*ukuranPetak, lebarLayar, i*ukuranPetak);
            }
            g.setColor(Color.white);
            g.fillOval(makananX,makananY,ukuranPetak, ukuranPetak); 
            if (posisi == 0){
                x[5] = 250;
                x[4] = 250;
                x[3] = 250;
                x[2] = 250;
                x[1] = 250;
                x[0] = 250;

                y[5] = 300;
                y[4] = 290;
                y[3] = 280;
                y[2] = 270;
                y[1] = 260;
                y[0] = 250;

            }
            for(int i = 0; i<panjangTubuh; i++){
                if(i == 0){
                    g.setColor(Color.pink);
                    g.fillOval(x[i], y[i], ukuranPetak+1, ukuranPetak+1);
                }else{
                    g.setColor(Color.black);
                    g.fillOval(x[i], y[i], ukuranPetak+1, ukuranPetak+1);
                }
            }
            g.setColor(Color.red);
                g.setFont(new Font("Ink Free", Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score : "+termakan, (lebarLayar - metrics.stringWidth("Score : "+termakan))/2, g.getFont().getSize());
       }else{
           gameOver(g);
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
    public void gameOver(Graphics g){
        //Teks Score
        g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score : "+foodEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score : "+foodEaten))/2, g.getFont().getSize());

        //Teks GameOver
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }

    public void rules(){
        //Kepala menyentuh kepala
        for(int i = panjangTubuh; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }

        //Kepala menyentuh Frame Kiri
        if(x[0] < 0){
            running = false;
        }

        //Kepala menyentuh Frame Kanan
        if(x[0] > lebarLayar){
            running = false;
        }

        //Kepala menyentuh Frame Atas
        if(y[0] < 0){
            running = false;
        }

        //Kepala menyentuh Frame Bawah
        if(y[0] > tinggiLayar){
            running = false;
        }

        if(!running){
            waktu.stop();
        }

    }
    
    public void actionPerformed(ActionEvent e){
        MoveAbility ma = new MoveAbility();
        if(running){
            ma.move(panjangTubuh, x , y ,ukuranPetak, arah);
            rules();
            checkFood();
        }
        repaint();    
    }

    public class KeyDirection extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            posisi++;
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

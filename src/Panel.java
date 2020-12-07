import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Panel extends JPanel implements Food, ActionListener{
    static final int lebarLayar = 480;
    static final int tinggiLayar = 480;
    static final int ukuranPetak = 10;
    static final int banyakPetak = (lebarLayar*tinggiLayar)/ukuranPetak;
    static final int kecepatanGerak = 80;
    final int x[] = new int[banyakPetak];
    final int y[] = new int[banyakPetak];
    private char arah = 'R';
    private boolean running = false;
    private boolean isRunning = true;
    private boolean restart = false;
    private int panjangTubuh = 6;
    private int makananX;
    private int makananY;
    private int termakan = 0;
    Timer waktu;
    Random random;
    private int posisi = 0;
    ImageIcon image = new ImageIcon("..\\images\\siantarman.png");
    Image imageIcon = image.getImage(); // transform it 
    Image newimg = imageIcon.getScaledInstance(ukuranPetak+5, ukuranPetak+5,  java.awt.Image.SCALE_DEFAULT); // scale it the smooth way  
    ImageIcon imageIconFix = new ImageIcon(newimg); 
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
        waktu = new Timer(kecepatanGerak,this);
        waktu.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
       if(running){
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
                    imageIconFix.paintIcon(this, g, x[i], y[i]);
                }else{
                    g.setColor(Color.black);
                    g.fillOval(x[i], y[i], ukuranPetak+1, ukuranPetak+1);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Courier", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score : "+termakan, (lebarLayar - metrics.stringWidth("Score : "+termakan))/2, g.getFont().getSize());
       }else{
            if(isRunning){
                gameStart(g); 
            }else{
                gameOver(g);
                if(restart == true){
                    repaint(); 
                }
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
    public void gameOver(Graphics g){
        //Teks Score
        g.setColor(Color.red);
        g.setFont(new Font("Monospace", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score : "+termakan, (lebarLayar - metrics1.stringWidth("Score : "+termakan))/2, g.getFont().getSize());

        //Teks GameOver
        g.setColor(Color.red);
        g.setFont(new Font("Monospace", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (lebarLayar - metrics2.stringWidth("Game Over"))/2, tinggiLayar/2);
        

        g.setColor(Color.blue);
        g.setFont(new Font("Monospace", Font.PLAIN, 30));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press R to Restart", (lebarLayar - metrics3.stringWidth("Press R to Restart"))/2, (int)(tinggiLayar/1.5));
    }

    public void gameStart(Graphics g){
        g.setColor(Color.blue);
            g.setFont(new Font("Monospace", Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score : "+termakan, (lebarLayar - metrics1.stringWidth("Score : "+termakan))/2, g.getFont().getSize());

        //Teks GameOver
        g.setColor(Color.blue);
        g.setFont(new Font("Monospace", Font.BOLD, 50));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game START!", (lebarLayar - metrics2.stringWidth("Game START!"))/2, tinggiLayar/2);
        
        g.setColor(Color.blue);
        g.setFont(new Font("Monospace", Font.PLAIN, 30));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press Space to Start", (lebarLayar - metrics3.stringWidth("Press Space to Start"))/2, (int)(tinggiLayar/1.5));
    }

    public void rules(){
        //Kepala menyentuh kepala
        for(int i = panjangTubuh; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
                isRunning = false;
            }
        }

        //Kepala menyentuh Frame Kiri
        if(x[0] < 0){
            running = false;
            isRunning = false;
        }

        //Kepala menyentuh Frame Kanan
        if(x[0] >= lebarLayar){
            running = false;
            isRunning = false;
        }

        //Kepala menyentuh Frame Atas
        if(y[0] < 0){
            running = false;
            isRunning = false;
        }

        //Kepala menyentuh Frame Bawah
        if(y[0] >= tinggiLayar){
            running = false;
            isRunning = false;
        }

        if(!running){
            waktu.stop();
        }

    }
    
    public void actionPerformed(ActionEvent e){
        MoveAbility ma = new MoveAbility();
        if(running){
            ma.move(panjangTubuh, x , y ,ukuranPetak, arah);
            checkFood();
            rules();
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

                case KeyEvent.VK_SPACE:
                    if(running==false){
                        posisi=0;
                        running = true;
                    }
                    
                break;

                case KeyEvent.VK_R:
                    if(isRunning == false && running == false){
                        waktu.start();
                        termakan = 0;
                        arah = 'U';
                        panjangTubuh = 6;
                        isRunning = true;
                        posisi = 0;
                        running = true;
                        restart = true;
                    }                    
                break;
                
            }
        }
    }
}

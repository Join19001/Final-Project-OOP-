import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
    static final int lebarLayar = 500;
    static final int tinggiLayar = 500;

    Frame(){
        this.add(new Panel());
        this.setPreferredSize(new Dimension (lebarLayar,tinggiLayar));
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
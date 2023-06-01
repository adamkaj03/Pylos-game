package grafika;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GyozelemFrame extends JFrame{
    private JLabel szoveg;
    private JButton okGomb;
    public GyozelemFrame(String gyoztes){
        //ez a frame a jatek vegen jelenik meg
        super("Győzelmi ablak");
        //kiirja a gyoztest es egy ok gombot, mellyel a programot be lehet zarni 
        szoveg = new JLabel(gyoztes+" játékos nyerte a partit!");
        okGomb = new JButton("OK");
        okGomb.addActionListener((e)->System.exit(0));
        this.setLayout(new GridLayout(2, 1));
        this.add(szoveg, CENTER_ALIGNMENT);
        this.add(okGomb);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(200, 200));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
    }
}

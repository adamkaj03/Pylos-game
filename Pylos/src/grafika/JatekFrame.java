package grafika;

import fajlKezeles.JatekIO;
import fajlKezeles.JatekXMLIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JatekFrame extends JFrame{
    //jatek grafikai resze
    private PalyaGUI gui;
    //ebben a panelben jelennek meg a kulonbozo uzenetek a felhasznalonak
    private StatusPanel statusPanel;
    //fajlba mentes gombja
    private JButton mentesGomb;
    //csak a felrakott golyok latszodnak, ha lenyomjak
    private JButton felrakottGolyoNezetGomb;
    //minden latszodik
    private JButton mindenLatszodikGomb;
    //mindent kirajzol flag
    private boolean mindentKirajzol;
    private JButton passzGomb;
    
    public JatekFrame(PalyaGUI gui, JButton felrakottGolyoNezetGomb, JButton mindenLatszodikGomb, JButton passzGomb, JButton mentesGomb) {
        //felrakja a frame-re a megfelelo komponenseket
        mindentKirajzol = true;
        this.gui = gui;
        statusPanel = new StatusPanel();
        this.mentesGomb = mentesGomb;
        this.felrakottGolyoNezetGomb = felrakottGolyoNezetGomb;
        this.mindenLatszodikGomb = mindenLatszodikGomb;
        this.passzGomb = passzGomb;
        statusPanel.add(passzGomb);
        passzGomb.setVisible(false);
        this.add(this.felrakottGolyoNezetGomb);
        this.add(this.mindenLatszodikGomb);
        this.add(statusPanel);
        this.add(mentesGomb);
        mentesGomb.setBounds(0, 250, 300, 50);
        this.felrakottGolyoNezetGomb.setBounds(0, 200, 300, 50);
        this.mindenLatszodikGomb.setBounds(0, 200, 300, 50);
        this.mindenLatszodikGomb.setVisible(false);
        this.add(gui);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(500, 500));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }

    //metodus lefrissiti a gui tartalmat
    public void frissit(PalyaGUI gui){
        this.felrakottGolyoNezetGomb.setVisible(true);
        this.mindenLatszodikGomb.setVisible(false);
        this.getContentPane().add(gui);
        this.setPreferredSize(new Dimension(500, 500));
        //ez a ketto metodus lefrissiti a frame-et, ha nem hivtam meg, akkor villogott kattintasra
        //vagy nem is rajzolta ki csak akkor, ha talcara raktam a programot
        this.revalidate();
        this.repaint();
        this.pack();
    }
    
    //setterek es getterek
    public void setMindentKirajzol(boolean mindentKirajzol) {
        this.mindentKirajzol = mindentKirajzol;
    }

    public StatusPanel getStatusPanel() {
        return statusPanel;
    }

    public void setStatusPanel(StatusPanel statusPanel) {
        this.statusPanel = statusPanel;
    }
    
    public PalyaGUI getGui() {
        return gui;
    }

    public void setGui(PalyaGUI gui) {
        this.gui = gui;
    }
      
    public JButton getPasszGomb() {
        return passzGomb;
    }
    
}

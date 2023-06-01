package grafika;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import logika.Golyo;

public class StatusPanel extends JPanel{
    private JLabel szoveg = new JLabel();
    public StatusPanel(){
        this.setBackground(Color.LIGHT_GRAY);
        this.setBounds(200, 0, 300, 200);
        szoveg.setText("Fehér játékos jön!");
        this.add(szoveg);
    }
    //minden kor vegen kiirja ki a kovetkezo jatekos
    public void aktualisJatekosKiir(Golyo g){
        if(g == Golyo.FEHER){
            this.remove(szoveg);
            szoveg.setText("Fehér játékos jön!");
            this.add(szoveg);
        }
        else{
            this.remove(szoveg);
            szoveg.setText("Fekete játékos jön!");
            this.add(szoveg);
        }
    }
    
    //ha rosszul valasztott valamelyik jatekos egy poziciot akkor hivodik meg
    public void hibasPozicioKiir(){
        this.remove(szoveg);
        szoveg.setText("Nem megfelelő pozició! Próbálja újra!");
        this.add(szoveg);
        this.validate();
    }
    
    //ha kialakult egy szinu negyzet, akkor hivodik meg es irodik ki a szoveg
    public void levehetGolyoSzoveg(){
        this.remove(szoveg);
        szoveg.setText("<html>Kialakult egyszínű négyzet!<br/>Levehetsz akár két golyót!</html>");
        this.add(szoveg);
        this.validate();
    }
}

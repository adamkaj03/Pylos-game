package grafika;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import logika.Pozicio;

public abstract class PalyaElem extends JComponent {
    private int x;
    private int y;
    private int szelesseg;
    private int elemSzint;
    private Pozicio elemPozicio;
    //ez ket flag, az egyik jelzi, hogy szabad-e a mezo, masik pedig, hogy ki van-e jelove
    private boolean szabad;
    private boolean kijelolo;

    public PalyaElem(int x, int y, int szelesseg, int elemSzint, Pozicio elemPozicio, boolean  szabad, boolean  ki) {
        this.x = x;
        this.y = y;
        this.szelesseg = szelesseg;
        this.elemSzint = elemSzint;
        this.elemPozicio = elemPozicio;
        this.szabad = szabad;
        this.kijelolo = ki;
    }

    //kattintasra ez a metodus hivodik meg, ami minden palyaelembe megvan valositva
    public abstract void esemenytKezel(Controller c);
    
    //gui rajzolasnal ez hivodik meg, minden palyaelem-et mashogy rajzolunk ezert absztrakt
    public abstract void rajzol(Graphics2D g);
    
    //kattintasnal ezzel a metodussal tortenik az ellenorzes, hogy kit kattintott a felhasznalo
    public boolean contains(int x, int y){
        return x>=this.x-getSzelesseg()/2 && x<=this.x+getSzelesseg()/2 
                && y>=this.y-getSzelesseg()/2 && y<=this.y+getSzelesseg()/2;
    }
      
    //getterek es setterek
    public boolean isKijelolo() {
        return kijelolo;
    }
    
    public int getElemSzint() {
        return elemSzint;
    }

    public boolean isSzabad() {
        return szabad;
    }

    public void setSzabad(boolean szabad) {
        this.szabad = szabad;
    }

    public void setElemSzint(int elemSzint) {
        this.elemSzint = elemSzint;
    }

    public Pozicio getElemPozicio() {
        return elemPozicio;
    }

    public void setElemPozicio(Pozicio elemPozicio) {
        this.elemPozicio = elemPozicio;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSzelesseg() {
        return szelesseg;
    }
   
}

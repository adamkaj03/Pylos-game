package grafika;

import grafika.PalyaElem;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import logika.Pozicio;

public class PalyaElemNegyzet extends PalyaElem{
    //ilyen szinu a szabad pozicio
    private static  final Color halvanyZold = new Color(0.56f , 0.87f, 0.62f, 0.3f);
    //ilyen szinu lesz a negyzet, ha kerult ra golyo
    private static  final Color szurke = new Color(0.5f , 0.5f, 0.5f, 0.9f);
    //ilyen szinu lesz egy mezo ha kijeloli a felhasznalo
    private static final Color sarga = new Color(0.92f, 1f, 0.7f, 0.5f);
    
    public PalyaElemNegyzet(int x, int y, int szelesseg, int szint, Pozicio p, boolean szabad, boolean kijeloloNegyzet) {
        super(x, y, szelesseg, szint, p, szabad, kijeloloNegyzet);
    }

    //3 szinu negyzetet rajzol ki a program, flagek allapotatol fuggoen
    @Override
    public void rajzol(Graphics2D g) {
        if(isSzabad() && !isKijelolo()){
            g.setColor(halvanyZold);
        }
        else if(!isSzabad() && !isKijelolo()){
           g.setColor(szurke); 
        }
        else{
            g.setColor(sarga);
        }
        g.fillRect(getX()-getSzelesseg()/2, getY()-getSzelesseg()/2, getSzelesseg(), getSzelesseg());
        g.setColor(Color.BLACK);
        g.drawRect(getX()-getSzelesseg()/2, getY()-getSzelesseg()/2, getSzelesseg(), getSzelesseg());
    
    }

    //ket esemenyt kezel
    @Override
    public void esemenytKezel(Controller c) {
        //elso alapeset, golyo felrakasa
        if(c.getLevehetoGolyokSzama()==0 && c.getKijeloltSzint() == -1){
            c.golyoFelrakas(getElemSzint(), getElemPozicio());
        }
        //masodik magasabb szintre tesz egy kijelolt golyot
        else{
            c.golyoMagasabbSzintreRakas(c.getKijeloltSzint(), c.getKijeloltPozicio(), getElemSzint(), getElemPozicio());
        }
        
    }
}

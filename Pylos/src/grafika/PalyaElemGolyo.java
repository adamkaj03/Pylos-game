package grafika;

import grafika.PalyaElem;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import logika.Golyo;
import logika.Pozicio;
import org.jdom2.transform.XSLTransformer;

public class PalyaElemGolyo extends PalyaElem{
    private Color szin;
    private Golyo golyo;
    
    public PalyaElemGolyo(int x, int y, int szelesseg, int szint, Pozicio p,  boolean szabad, boolean ki, Color szin){
        super(x, y, szelesseg, szint, p, szabad, ki);
        this.szin = szin;
        if(this.szin == Color.WHITE){
            golyo = Golyo.FEHER;
        }
        else{
            golyo = Golyo.FEKETE;
        }
    }
    
    //rajzol hivodik meg a gui rajzolasnal
    @Override
    public void rajzol(Graphics2D g) {
        g.setColor(szin);
        g.fillOval(getX()-getSzelesseg()/2, getY()-getSzelesseg()/2, getSzelesseg(), getSzelesseg());
    }

    //kattintaskor ez hivodik meg, 3 dolog tortenhet golyora kattintaskor
    @Override
    public void esemenytKezel(Controller c) {
        //lehet golyot levenni ha a feltetel teljesul
        if(c.getLevehetoGolyokSzama()>0){
           c.golyoLevetel(getElemSzint(), getElemPozicio()); 
        }
        else{
            //kijelolest leszedni, ha megint a kijeloltre kattint a felhasznalo
            if(c.getKijeloltSzint() == getElemSzint() && c.getKijeloltPozicio().getSor() == getElemPozicio().getSor()
                    && c.getKijeloltPozicio().getOszlop() == getElemPozicio().getOszlop()){
                c.getFrame().getGui().kijeloloNegyzetTorlese();
                c.getFrame().frissit(c.getFrame().getGui());
            }
            //lehet golyot kijelolni kattintasra
            else if(c.getJsz().getAktualisJatekos().getGolyo() == this.golyo){
               c.golyoKijeloloNegyzetFelrakas(getElemSzint(), getElemPozicio()); 
            }  
        } 
    }

   
    
}

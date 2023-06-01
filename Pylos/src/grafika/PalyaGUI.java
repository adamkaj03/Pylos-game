package grafika;

import grafika.PalyaElemNegyzet;
import grafika.PalyaElemGolyo;
import grafika.PalyaElem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;
import logika.Golyo;
import logika.Pozicio;

public class PalyaGUI extends JPanel implements MouseListener{
    //jatekban lathato grafikai elemek taroloja
    //Map a szinteket tarolja
    //szinteken belul pedig egy lista tarolja az elemeket
    private Map<Integer, List<PalyaElem>> szintekPalyaElemei = new HashMap<>();
    private Controller controller;
    //elemek merete
    private static final int meret  = 50;
    
    public PalyaGUI(){
        //minden szintbe megy egy ArrayList
        for(int i=0; i<4; i++){
            szintekPalyaElemei.put(i, new ArrayList<>());
        }
        
        //kezdo palya elemek letrehozasa es a tarolo feltoltese ezekkel az elemekkel
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                PalyaElem p = new PalyaElemNegyzet(j*meret+25, i*meret+25, meret, 0, new Pozicio(i, j), true, false);
                szintekPalyaElemei.get(0).add(p);
            }
        }
        //mouse listener hozzaadas
        addMouseListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    //valamiert eloszor oszloppal kell szorozni, utana meg sorral
    //ez a metodus hivodik meg, amikor a felhasznalo golyot rak fel es annak hatasara
    //letrejonnek azok a palyaelemek, amelyeket kikell majd rajzolni
    public void golyoKirajzolasPoziciora(int szint, Pozicio p, Golyo g){
        if(g == Golyo.FEKETE){
            //elemek kirajzolasanak helye ki lett szamolva
            switch(szint){
                case 0:{
                    //szurke negyzet
                    szintekPalyaElemei.get(szint).add(new PalyaElemNegyzet(p.getOszlop()*meret+meret/2, p.getSor()*meret+meret/2, meret, 0, p, false, false));
                    //majd a golyo
                    szintekPalyaElemei.get(szint).add(new PalyaElemGolyo(p.getOszlop()*meret+meret/2, p.getSor()*meret+meret/2, meret, szint, p, false, false, Color.BLACK));
                }
                break;
                case 1: 
                    szintekPalyaElemei.get(szint).add(new PalyaElemNegyzet((1+p.getOszlop())*meret, (1+p.getSor())*meret, meret, szint, p, false, false));
                    szintekPalyaElemei.get(szint).add(new PalyaElemGolyo((1+p.getOszlop())*meret, (1+p.getSor())*meret, meret, szint, p, false, false, Color.BLACK));
                break;
                case 2: 
                    szintekPalyaElemei.get(szint).add(new PalyaElemNegyzet((1+p.getOszlop())*meret + meret/2, (1+p.getSor())*meret+meret/2, meret, szint, p, false, false));
                    szintekPalyaElemei.get(szint).add(new PalyaElemGolyo((1+p.getOszlop())*meret + meret/2, (1+p.getSor())*meret + meret/2, meret, szint, p, false, false, Color.BLACK));
                break;
            }
            
        }
        else{
            switch (szint) {
                case 0:{
                    szintekPalyaElemei.get(szint).add(new PalyaElemNegyzet(p.getOszlop()*meret+meret/2, p.getSor()*meret+meret/2, meret, 0, p, false, false));
                    szintekPalyaElemei.get(szint).add(new PalyaElemGolyo(p.getOszlop()*meret+meret/2, p.getSor()*meret+meret/2, meret, szint, p, false, false,  Color.white));
                }
                break;
                case 1:{
                    szintekPalyaElemei.get(szint).add(new PalyaElemNegyzet((1+p.getOszlop())*meret, (1+p.getSor())*meret, meret, szint, p, false, false));
                    szintekPalyaElemei.get(szint).add(new PalyaElemGolyo((1+p.getOszlop())*meret, (1+p.getSor())*meret, meret, szint, p, false, false, Color.white));
                }
                    break;
                case 2:{
                    szintekPalyaElemei.get(szint).add(new PalyaElemNegyzet((1+p.getOszlop())*meret + meret/2, (1+p.getSor())*meret+meret/2, meret, szint, p, false, false));
                    szintekPalyaElemei.get(szint).add(new PalyaElemGolyo((1+p.getOszlop())*meret + meret/2, (1+p.getSor())*meret + meret/2, meret, szint, p, false,false, Color.white));
                }
                    break;    
                    
            }
            
        }
    }
    
    //ha kijelol a felhaszmalo egy golyot, ezzel a metodussal jon letre az az elem, mely bekerul a kirajzolandok koze
    public void kijeloloNegyzetRajzolas(int szint, Pozicio p){
        //szintnek megfelelo pozicioval jonnek letre
         switch (szint) {
                case 0:{
                    szintekPalyaElemei.get(szint).add(new PalyaElemNegyzet(p.getOszlop()*meret+meret/2, p.getSor()*meret+meret/2, meret, 0, p, false, true));
                }
                break;
                case 1:{
                    szintekPalyaElemei.get(szint).add(new PalyaElemNegyzet((1+p.getOszlop())*meret, (1+p.getSor())*meret, meret, szint, p, false, true));
                }
                    break;
                case 2:{
                    szintekPalyaElemei.get(szint).add(new PalyaElemNegyzet((1+p.getOszlop())*meret + meret/2, (1+p.getSor())*meret+meret/2, meret, szint, p, false, true));
                }
                    break;    
         }
    }
    
    public void kijeloloNegyzetTorlese(){
        //osszes szintet megnezzi, hol van a kijelolt elem
        for(int szint = 0; szint<4; szint++){
            Iterator<PalyaElem> iter = szintekPalyaElemei.get(szint).iterator();
            //majd aztan megnezi, minden szint osszes elemet, ha valamelyik kijelolo, akkor leveszi
            while (iter.hasNext()) {
                PalyaElem p = iter.next();
                if (p.isKijelolo()) iter.remove();
            }
        }
        //kijelolt elem poziciojanak invalidra allitasa
        controller.setKijeloltSzint(-1);
        controller.setKijeloltPozicio(new Pozicio(-1, -1));
    }
    
    //letorli a kirajzolandok kozul a megfelelo elemet
    public void palyaElemLetorlese(int szint, Pozicio p){
        int keresettGolyo = -1;
        //visszafele megyunk vegig, mivel a legutoljara a kirajzolando elemek koze bekerult elemeket kell leszedni
        for(int i=szintekPalyaElemei.get(szint).size()-1; i>0; i--){   
            if(szintekPalyaElemei.get(szint).get(i).getElemPozicio().getSor() == p.getSor()
               && szintekPalyaElemei.get(szint).get(i).getElemPozicio().getOszlop()== p.getOszlop()){
                    keresettGolyo = i;
                    break;
            }
        }
        if(keresettGolyo != -1){
            //golyo letorlese
            szintekPalyaElemei.get(szint).remove(keresettGolyo);
            //szurke nyegzet letorlese
            szintekPalyaElemei.get(szint).remove(keresettGolyo-1);
            
        }
    }
    
    //ezt lehet nem hasznalom sehol
    public void szurkeNegyzetTorlese(int szint, Pozicio p){
        int keresettNegyzet = -1;
        for(int i=0; i<szintekPalyaElemei.get(szint).size(); i++){
            if(szintekPalyaElemei.get(szint).get(i).getElemPozicio().getSor() == p.getSor()
               && szintekPalyaElemei.get(szint).get(i).getElemPozicio().getOszlop()== p.getOszlop()){ 
                    keresettNegyzet = i;
                    break;
            }
        }
        if(keresettNegyzet != -1){
            szintekPalyaElemei.get(szint).remove(keresettNegyzet);
        }
    }
    
    //bekerulnek a szabad helyek is
    public void szabadHelyNegyzet(int szint, Pozicio p){
        switch (szint) {
            case 1:
                szintekPalyaElemei.get(szint).add(new PalyaElemNegyzet((1+p.getOszlop())*meret, (1+p.getSor())*meret, meret , szint, p, true, false));
                break;
            case 2:
                szintekPalyaElemei.get(szint).add(new PalyaElemNegyzet((1+p.getOszlop())*meret+meret/2, (1+p.getSor())*meret+meret/2, meret , szint, p, true, false));
                break;
        }
        
    }
    
    //rajzolast megvalosito metodus
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //minden szint, minden elemet kirajzolja
        for(PalyaElem p : szintekPalyaElemei.get(0)){
            p.rajzol((Graphics2D) g);
        }
        for(PalyaElem p : szintekPalyaElemei.get(1)){
            p.rajzol((Graphics2D) g);
        }
        for(PalyaElem p : szintekPalyaElemei.get(2)){
            p.rajzol((Graphics2D) g);
        }
    }

    //getter
    public Map<Integer, List<PalyaElem>> getSzintekPalyaElemei() {
        return szintekPalyaElemei;
    }
    
    //kattintas
    @Override
    public void mouseClicked(MouseEvent e) {
        PalyaElem p = null;
        //itt is ugyanugy visszafele megy vegig az elemeken
        for(int szint = 3; szint>=0; szint-- ){
            for(int i=szintekPalyaElemei.get(szint).size()-1; i>=0; i--){
                //ha benne van a kattintas x, y koordinataja a palya elembe
                if(szintekPalyaElemei.get(szint).get(i).contains(e.getX(), e.getY()) ){
                    p = szintekPalyaElemei.get(szint).get(i);
                    //akkor megvizsgaljuk, hogy nem e kijelolo, mert a kijelolo negyzetnek nincs esemenykezeloje
                    //csak az alatta talalhato golyonak ezert a negyzetet atkell ugrani
                    if(p!=null && p.isKijelolo()){
                    
                    }
                    //ha nem es nem is null, akkor break
                    else{
                       break; 
                    }
                    
                }
            }
            //majd aztan a szintek ciklusabol is break
            if(p != null){
                break;
            }
        }
        //legvegul meghivodik a kello metodus
        if(p != null){
           p.esemenytKezel(controller); 
        }
        
    }
    
    

    @Override
    public void mousePressed(MouseEvent e) {
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    
}

package logika;

import grafika.Controller;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Timer;

public class GepiJatekos implements  Jatekos{

    public Controller getC() {
        return c;
    }
    private Tabla aktualisTabla;
    private int db;
    private Golyo szin;
    private Controller c;
    public GepiJatekos(Tabla t, Golyo g){
        aktualisTabla = t;
        szin = g;
        db = 15;
    }

    public void setC(Controller c) {
        this.c = c;
    }
    
    @Override
    public Golyo getGolyo(){
        return szin;
    }
    @Override
    public int getDb(){
        return db;
    }
    
   @Override
    public void lepesKeszletbol(int szint, Pozicio p) {
        aktualisTabla.babuFelrakas(szint, p, szin);
        db--;
    }
    @Override
    public void golyoFelveteleTablarol(int szint, Pozicio p){
        aktualisTabla.babuLevetel(szint, p);
        db++;
    } 
    @Override
    public void golyoMagasabbSzintreHelyez(int kezdoSzint, Pozicio innen, int magasabbSzint, Pozicio ide) {
        aktualisTabla.babuLevetel(kezdoSzint, innen);
        aktualisTabla.babuFelrakas(magasabbSzint, ide, szin);
    }
    @Override
    public void setDb(int db) {
        this.db = db;
    }
    @Override
    public void dontestHoz() {
        //azert kell, hogy ne akadjon el a gep
        if(c.getLevehetoGolyokSzama() != 0){
            c.passzAkcio();
            return;
        }
        //random sorsol a valaszhato mezok kozul egy poziciot
        List<Integer> mezok = aktualisTabla.valaszthatoMezo();
        int mezokSzama = mezok.size()/3;
        Random rand = new Random();
        if(mezokSzama !=0){
            int lepes = rand.nextInt(mezokSzama);
            int szint = mezok.get(3*lepes);
            int sor = mezok.get(3*lepes+1);
            int oszlop = mezok.get(3*lepes+2);
            //egy ket masodperces delayt csinal
            //valamiert a Thread.sleep nem mukodott
            Timer t = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    c.golyoFelrakas(szint, new Pozicio(sor, oszlop));
                }
            };
            t.schedule(tt, 2000);
        }
        
    }

 
}

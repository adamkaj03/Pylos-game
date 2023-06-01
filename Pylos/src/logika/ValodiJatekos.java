package logika;

public class ValodiJatekos implements Jatekos{
    private int db;
    private Tabla aktualisTabla;
    private Golyo szin;
    
    public ValodiJatekos(Tabla t, Golyo g){
        db=15;
        aktualisTabla=t;
        szin = g;
    }
    
    //getterek
    @Override
    public Golyo getGolyo(){
        return szin;
    }
    @Override
    public int getDb(){
        return db;
    }
    
    //jatekos altal vegezheto 3 akcio
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
    public void dontestHoz() {
    }
    
    @Override
    public void setDb(int db) {
        this.db = db;
    }
}   


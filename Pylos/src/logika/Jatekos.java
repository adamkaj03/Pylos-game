package logika;

public interface Jatekos {
    public void lepesKeszletbol(int szint, Pozicio p);
    public void golyoFelveteleTablarol(int szint, Pozicio p);
    public void golyoMagasabbSzintreHelyez(int szint, Pozicio innen,int magasabbSzint, Pozicio ide);
    public Golyo getGolyo();
    public int getDb();
    public void dontestHoz();
    public void setDb(int db);
}

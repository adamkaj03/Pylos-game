package logika;

//az osztaly osszecsomagolja a jatek logikajaban hasznalt elemeket
public class JatekSzereploi {
    private Tabla t;
    private Jatekos j1;
    private Jatekos j2;
    private Jatekos aktualisJatekos;
    
    public JatekSzereploi(Tabla t, Jatekos j1, Jatekos j2){
        this.t = t;
        this.j1 = j1;
        this.j2 = j2;
        aktualisJatekos = j1;
    }

    public JatekSzereploi(Tabla t, Jatekos j1, Jatekos j2, Jatekos aktualisJatekos) {
        this.t = t;
        this.j1 = j1;
        this.j2 = j2;
        this.aktualisJatekos = aktualisJatekos;
    }

    public JatekSzereploi() {
    }

    public Tabla getT() {
        return t;
    }

    public void setT(Tabla t) {
        this.t = t;
    }

    public Jatekos getJ1() {
        return j1;
    }

    public void setJ1(Jatekos j1) {
        this.j1 = j1;
    }

    public Jatekos getJ2() {
        return j2;
    }

    public void setJ2(Jatekos j2) {
        this.j2 = j2;
    }

    public Jatekos getAktualisJatekos() {
        return aktualisJatekos;
    }

    public void setAktualisJatekos(Jatekos aktualisJatekos) {
        this.aktualisJatekos = aktualisJatekos;
    }
}

package fajlKezeles;

import fajlKezeles.JatekIO;
import logika.JatekSzereploi;
import logika.Tabla;

public abstract class JatekIOAbsztrakt implements JatekIO{
    private String fajlNev;
    public JatekIOAbsztrakt(String fajlNev){
        this.fajlNev = fajlNev;
    }
    public String getFajlNev(){
        return fajlNev;
    }
    public abstract void kiirFajlba(JatekSzereploi j);
    public abstract JatekSzereploi beolvasFajlbol();
    
}

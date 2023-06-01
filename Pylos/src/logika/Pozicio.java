package logika;

public class Pozicio {
    private int sor;
    private int oszlop;
 
    public Pozicio(int sor, int oszlop){
        this.sor = sor;
        this.oszlop = oszlop;
    }
    
    //getterek es setterek
    public int getSor(){
        return sor;
    }
    public int getOszlop(){
        return oszlop;
    }
    public void setSor(int sor){
        this.sor=sor;
    }
    public void setOszlop(int oszlop){
        this.oszlop=oszlop;
    }
    
}

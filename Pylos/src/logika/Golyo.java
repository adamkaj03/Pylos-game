package logika;

public enum Golyo {
    FEHER("feher"),
    FEKETE("fekete");
    
    private Golyo(String nev){
        this.nev = nev;
    }
    
    private String nev; 
    public String getNev() {
        return nev;
    }
    //ennek a fuggvenynek a segitsegevel lehet golyot letrehozni
    public static Golyo letrehoz(String nev){
        switch (nev) {
            case "feher":
                return FEHER;
            case "fekete":
                return FEKETE;
            default:
                throw new AssertionError();
        }
    }
}

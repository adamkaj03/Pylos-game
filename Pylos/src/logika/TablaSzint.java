package logika;

public class TablaSzint {
   private int eredetiMeret;
   private Golyo[][] tulIndexelhetoTomb;
   
   //minden szintet egy nagyobb 2d-s tomben tarolok, hogy ne kelljen vizsgalni, ha a golyo a szint szelen van
   //nem fogok kapni indexoutofboundsexception-t
   public TablaSzint(int eredetiMeret){
       this.eredetiMeret = eredetiMeret;
       tulIndexelhetoTomb = new Golyo[eredetiMeret+2][eredetiMeret+2];
       
   }
   
   //getterek
   public Golyo[][] getTulIndexelhetoTomb(){
       return this.tulIndexelhetoTomb;
   }
   public int getEredetiMeret(){
       return eredetiMeret;
   }
   
   //Pozicio igazitas a tulindexelheto tombhoz
   private Pozicio pozicioKonvertal(Pozicio p){
       return new Pozicio(p.getSor()+1, p.getOszlop()+1);
   }
   
   //adott babu lerakasa a szintre
   public void babuLehelyez(Pozicio p, Golyo g){
       //itteni tulindexelheto tombhoz igazitjuk a Pozicio-t
       Pozicio uj = pozicioKonvertal(p);
       tulIndexelhetoTomb[uj.getSor()][uj.getOszlop()]=g;
   }
   
   //babu levetele az adott szintrol
   public void babuLeszedes(Pozicio p){
       //itteni tulindexelheto tombhoz igazitjuk a Pozicio-t
       Pozicio uj = pozicioKonvertal(p);
       tulIndexelhetoTomb[uj.getSor()][uj.getOszlop()]=null;
   }
   //vissza adja az eredeti pozicionak megfelelo elemet
   public Golyo getBabu(Pozicio p){
       Pozicio uj = pozicioKonvertal(p);
       return tulIndexelhetoTomb[uj.getSor()][uj.getOszlop()];
   }
   
}

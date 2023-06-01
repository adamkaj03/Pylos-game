package grafika;

import fajlKezeles.JatekIO;
import fajlKezeles.JatekXMLIO;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.Timer;
import logika.Golyo;
import logika.JatekSzereploi;
import logika.Jatekos;
import logika.Pozicio;
import logika.Tabla;

public class Controller {
    private JatekSzereploi jsz;
    //grafikai reszt tartalmazo frame
    private JatekFrame frame;
    //ennek a gombnak a hatasara nem fog latszodni, hogy felsobb szinteken hova lehet rakni
    private JButton felrakottGolyoNezetGomb;
    //osszes golyo, osszes elerheto hely latszik
    private JButton mindenLatszodikGomb;
    //akkor jelenik meg, ha kialakult egy egyszinu negyzet
    private JButton passzGomb;
    private JButton mentesGomb;
    //szamolja, hogy hany golyo veheto le
    private int levehetoGolyokSzama; 
    //kijelolt golyonak a szintjet tarolja
    private int kijeloltSzint;
    //kijelolt golyonak a poziciojat tarolja
    private Pozicio kijeloltPozicio;

    public Controller(JatekSzereploi jsz, JatekFrame frame, JButton felrakottGolyoNezetGomb, JButton mindenLatszodikGomb, JButton mentesGomb) {
        //attributumok alaphelyzetbe allitasa
        this.jsz = jsz; 
        this.frame = frame;
        //this.aktualisJatekos = j1;
        this.jsz.setAktualisJatekos(jsz.getAktualisJatekos());
        this.felrakottGolyoNezetGomb = felrakottGolyoNezetGomb;
        this.mindenLatszodikGomb = mindenLatszodikGomb;
        this.mentesGomb = mentesGomb;
        this.levehetoGolyokSzama = 0;
        this.kijeloltSzint = -1;
        this.kijeloltPozicio= new Pozicio(-1, -1);
        
        //ha megnyomjuk csak a felrakott golyok fognak latszodni
        this.felrakottGolyoNezetGomb.addActionListener((e)->{
            mindenUresNegyzetLeszedese();
            this.frame.setGui(this.frame.getGui());
            this.frame.setMindentKirajzol(false);
            this.frame.frissit(this.frame.getGui());
            felrakottGolyoNezetGomb.setVisible(false);
            mindenLatszodikGomb.setVisible(true);
        });
        
        //ha megnyomjuk minden ujra latszodni fog
        this.mindenLatszodikGomb.addActionListener((e)->{
            mindenUresNegyzetKirajzolas();
            frame.frissit(frame.getGui());
            this.frame.setMindentKirajzol(true);
            felrakottGolyoNezetGomb.setVisible(true);
            mindenLatszodikGomb.setVisible(false);
        });
        
        this.mentesGomb.addActionListener((e)->{
            JatekIO fajlKezeles = new JatekXMLIO("Jatek.xml"); 
            fajlKezeles.kiirFajlba(jsz);
            System.exit(0);
        });
        //ha megnyomjuk az ellenfel jon es nem tudunk mar tobb golyot levenni
        frame.getPasszGomb().addActionListener((e)-> {
            passzAkcio();
        });
        frame.frissit(frame.getGui());
    }
    
    //erre a metodusra akkor van szukseg, ha ket gepi jatekos jatszik egymassal
    public void start(){
        jsz.getAktualisJatekos().dontestHoz();
    }

    //kattintaskor ez a fuggveny hivodik meg
    public void golyoFelrakas(int szint, Pozicio p){
        frame.getGui().kijeloloNegyzetTorlese();
        //nem megfelelo pozicio hiba uzenet
        if(!jsz.getT().validLepes(szint, p)){
            frame.getStatusPanel().hibasPozicioKiir();
        }
        else{
            //logikai tablara a golyo felrakasa
            jsz.getAktualisJatekos().lepesKeszletbol(szint, p);
            //vegul pedig a grafikai tablara felrakas
            frame.getGui().golyoKirajzolasPoziciora(szint, p, jsz.getAktualisJatekos().getGolyo());
            golyoFelrakasUtaniTevekenysegek(szint, p);
        }
    }
    
    //ha egy kijelolt golyot magasabb szintre helyezunk ez a fuggveny hivodik meg
    public void golyoMagasabbSzintreRakas(int szintInnen, Pozicio innen, int szintIde, Pozicio ide){
        //leszedi a babu-t, mert kulonben a tabla resze lenne meg mindig a golyo
        //ezaltal a validLepes nem jol mukodne mert a oda is rakhatnank, ahol azert van alatamasztas mert 
        //leszedni kivant golyo meg ott van
        jsz.getT().babuLevetel(szintInnen, innen);
        if(szintInnen < szintIde && jsz.getT().validLepes(szintIde, ide)){
            //vissza rakjuk azt, amit leszedtunk
            jsz.getT().babuFelrakas(szintInnen, innen, jsz.getAktualisJatekos().getGolyo());
            frame.getGui().kijeloloNegyzetTorlese();
            //majd a jatekos elvegzi, ezt a fajta akciot
            jsz.getAktualisJatekos().golyoMagasabbSzintreHelyez(szintInnen, innen, szintIde, ide);
            //gui-ról letorli az elemet
            frame.getGui().palyaElemLetorlese(szintInnen, innen);
            //gui-ra kirajzolja az elemet
            frame.getGui().golyoKirajzolasPoziciora(szintIde, ide, jsz.getAktualisJatekos().getGolyo());
            golyoFelrakasUtaniTevekenysegek(szintIde, ide);
        }
        else{
            //ha nem megfelelo mezot valasztottunk, akkor vissza rakjuk azt, amit leszedtunk
            jsz.getT().babuFelrakas(szintInnen, innen, jsz.getAktualisJatekos().getGolyo());
            //majd elvegezi a szokasos lepeseket
            frame.getGui().kijeloloNegyzetTorlese();
            frame.frissit(frame.getGui());
            frame.getStatusPanel().hibasPozicioKiir();
        }
    }
    
    //ha kialakult egyszinu negyzet es a felhasznalo kattintott akkor hivodik meg
    public void golyoLevetel(int szint, Pozicio p){
        //megvizsgalja, hogy ne ala tamasztast vagy az ellenfel golyojat akarja leszedni
        if(!jsz.getT().isBabuLeveheto(szint, p, jsz.getAktualisJatekos().getGolyo())){
            frame.getStatusPanel().hibasPozicioKiir();
        }
        else{
            //leszedi a logikai tablarol
            jsz.getAktualisJatekos().golyoFelveteleTablarol(szint, p);
            //letorli a gui-rol
            frame.getGui().palyaElemLetorlese(szint, p);
            levehetoGolyokSzama--;
            if(levehetoGolyokSzama == 0){
                frame.getPasszGomb().setVisible(false);
                mentesGomb.setVisible(true);
                uresNegyzetJeloloFrissitese();
                golyoLevetelUtaniTevekenysegek();
            }
           frame.frissit(frame.getGui());
        }     
    }
    
    //akkor hivodik meg, ha a jatekos sajat golyojara kattint
    public void golyoKijeloloNegyzetFelrakas(int szint, Pozicio p){
        //elozonek kijelolt elem torlese
       frame.getGui().kijeloloNegyzetTorlese();
       if(jsz.getT().isBabuLeveheto(szint, p, Golyo.FEHER) || jsz.getT().isBabuLeveheto(szint, p, Golyo.FEKETE)){
            //uj kijelolo rajzolasa es a kijelolt elem poziciojanak tarolasa
            frame.getGui().kijeloloNegyzetRajzolas(szint, p);
            uresNegyzetJeloloFrissitese();
            kijeloltSzint = szint;
            kijeloltPozicio = new Pozicio(p.getSor(), p.getOszlop());
            frame.frissit(frame.getGui());
       }  
    }
    
    //a fuggveny akkor hivodik meg, ha kialakult egyszinu negyzet
    public void egySzinuNegyzetVizsgalat(int szint, Pozicio p){
        //logikai tablanak az ezt a vizsgalatot elvegzo fuggvenyet hivja meg
        if(jsz.getT().kialalultEgySzinuNegyzet(szint, p)){
            mentesGomb.setVisible(false);
            //beallitja, hogy 2 golyo leveheto, aminek a segitsegevel vizsgalja a leszedo fuggveny, hogy szedhet-e meg le
            levehetoGolyokSzama = 2;
            //passz gomb, hogyha a jatekos nem akar tobb golyot leszedni, akkor ezt kell megnyomni
            frame.getPasszGomb().setVisible(true);
            mindenUresNegyzetLeszedese();
            this.frame.getStatusPanel().levehetGolyoSzoveg();
            frame.frissit(frame.getGui());
        }
    }

    //ha egy jatekos golyot rakott fel ezek az esemenyek futnak le a vegen
    public void golyoFelrakasUtaniTevekenysegek(int szint, Pozicio p){
        gyozelemVizsgalat();
        //ez kirajzolja az uj szabadhelyeket, amik a lepes hatasara kialakulhattak
        uresNegyzetJeloloFrissitese();
        egySzinuNegyzetVizsgalat(szint, p);
        if(levehetoGolyokSzama==0){
            kovetkezoJatekos();
            frame.getStatusPanel().aktualisJatekosKiir(jsz.getAktualisJatekos().getGolyo());
        }
        //frissiti a gui-t, az uj elemek kirajzolasaval
        frame.frissit(frame.getGui());
        //ha az aktualis jatekos egy gepi jatekos, akkor leszimulalodik a lepese
        jsz.getAktualisJatekos().dontestHoz();
        
    }
    
    //megcsinalja, azokat a dolgokat, amelyeket minden golyo levetel utan elkell vegezni
    public void golyoLevetelUtaniTevekenysegek(){
        uresNegyzetJeloloFrissitese();
        kovetkezoJatekos();
        frame.getStatusPanel().aktualisJatekosKiir(jsz.getAktualisJatekos().getGolyo());
        jsz.getAktualisJatekos().dontestHoz();
    }
    
    //hozzaadja a kirajzolando elemekhez, az osszes uresnegyzetet
    public void mindenUresNegyzetKirajzolas(){
        // egy listat ad vissza, melyben 3k-on elemek a szintet, 3k+1 sor-t, 3k+2 oszlopot adja vissza
        for(int i = 0; i<this.jsz.getT().uresNegyzetOsszes().size(); i=i+3){
            frame.getGui().szabadHelyNegyzet((int) this.jsz.getT().uresNegyzetOsszes().get(i),
                new Pozicio((int) this.jsz.getT().uresNegyzetOsszes().get(i+1), (int) this.jsz.getT().uresNegyzetOsszes().get(i+2)));
        }
    }
    
    //eltavolitja az osszes 0. szintnel magasabb elerheto negyzet PalyaElemet
    public void mindenUresNegyzetLeszedese(){
        //vegig iteral a 0-at kiveve az osszes szinten
        for(int szint = 1; szint<4; szint++){
            Iterator<PalyaElem> iter = this.frame.getGui().getSzintekPalyaElemei().get(szint).iterator();
            //majd aztan megnezi, minden szint osszes elemet, ha valamelyik szabad, akkor leveszi
            while (iter.hasNext()) {
                PalyaElem p = iter.next();
                if (p.isSzabad()) iter.remove();
            }
        }
    }
    
    //frissiti a kepernyon lathato ures negyzet jeloloket 
    public void uresNegyzetJeloloFrissitese(){
        //eloszor mindet leszedi, hogy ne legyen olyan negyzet amit ketszer rajzol ki
        mindenUresNegyzetLeszedese();
        //ezutan osszes negyzet hozzaadas, a kirajzolando elemek koze
        mindenUresNegyzetKirajzolas();
        frame.frissit(frame.getGui());
    }
    
    //megvizsgalja, hogy nyert-e valaki
    public void gyozelemVizsgalat(){
        //ha az egyik jatekos golyojainak darabja nulla, akkor megjelenit egy gyozelmi framet
        if(jsz.getAktualisJatekos().getDb() == 0){
            kovetkezoJatekos();
            this.frame.setVisible(false);
            if(jsz.getAktualisJatekos().getGolyo()==Golyo.FEHER){
                GyozelemFrame gyF = new GyozelemFrame("Fehér");
            }
            else{
                GyozelemFrame gyF = new GyozelemFrame("Fekete");
            }
        }
    }
    
    //lecsereli az aktualis jatekost
    public void kovetkezoJatekos(){
        if(jsz.getAktualisJatekos() == jsz.getJ1()){
            jsz.setAktualisJatekos(jsz.getJ2());
        }
        else{
            jsz.setAktualisJatekos(jsz.getJ1());
        }
    }

    
    
    //passz gomb megnyomasa soran ezeket az akciokat kell vegrehajtani
    public void passzAkcio(){
        levehetoGolyokSzama = 0;
        frame.getPasszGomb().setVisible(false);
        mentesGomb.setVisible(true);
        uresNegyzetJeloloFrissitese();
        kovetkezoJatekos();
        frame.getStatusPanel().aktualisJatekosKiir(jsz.getAktualisJatekos().getGolyo());
        frame.frissit(frame.getGui());
        jsz.getAktualisJatekos().dontestHoz();
    }
    
    public void guiFrissit(){
        frame.frissit(frame.getGui());
    }
    //getterek es setterek
    

    public JatekFrame getFrame() {
        return frame;
    }
    
    public JatekSzereploi getJsz() {
        return jsz;
    }
    
    public int getLevehetoGolyokSzama() {
        return levehetoGolyokSzama;
    }
    
    public int getKijeloltSzint() {
        return kijeloltSzint;
    }

    public Pozicio getKijeloltPozicio() {
        return kijeloltPozicio;
    }

    public void setKijeloltSzint(int kijeloltSzint) {
        this.kijeloltSzint = kijeloltSzint;
    }

    public void setKijeloltPozicio(Pozicio kijeloltPozicio) {
        this.kijeloltPozicio = kijeloltPozicio;
    }
}

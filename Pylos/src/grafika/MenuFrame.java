package grafika;

import fajlKezeles.JatekIO;
import fajlKezeles.JatekXMLIO;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import logika.GepiJatekos;
import logika.Golyo;
import logika.JatekSzereploi;
import logika.Pozicio;
import logika.Tabla;
import logika.ValodiJatekos;

public class MenuFrame extends JFrame{
    private JButton gepiSzimulacio;
    private JButton egyJatekosMod;
    private JButton ketJatekosMod;
    private JButton mentettJatekFolytatas;
    
    public MenuFrame(){
        super("Pylos");
        gepiSzimulacio = new JButton("Szimulációs mód");
        egyJatekosMod = new JButton("Egy játékos mód");
        ketJatekosMod = new JButton("Két játékos mód");
        mentettJatekFolytatas = new JButton("Mentett játék folytatása");
        gepiSzimulacio.addActionListener((e)->{
            this.setVisible(false);
            PalyaGUI gui = new PalyaGUI();
            JButton mindenLatszikGomb = new JButton("Minden látszódik újra!");
            JButton felrakottGolyoNezetGomb = new JButton("Csak a felrakott golyók látszódnak!");
            JButton passzGomb = new JButton("Pasz, nem akarok több golyót levenni!");
            JButton mentesGomb = new JButton("Játék aktuális állásának mentése");
            JatekFrame frame = new JatekFrame(gui, felrakottGolyoNezetGomb, mindenLatszikGomb, passzGomb, mentesGomb);
            frame.setTitle("Szimulációs mód");
            mentesGomb.setVisible(false);
            Tabla t = new Tabla();
            GepiJatekos g1 = new GepiJatekos(t, Golyo.FEHER);
            GepiJatekos g2 = new GepiJatekos(t, Golyo.FEKETE);
            JatekSzereploi jsz = new JatekSzereploi(t, g1, g2);
            Controller controller = new Controller(jsz, frame, felrakottGolyoNezetGomb, mindenLatszikGomb, mentesGomb);
            g1.setC(controller);
            g2.setC(controller);
            gui.setController(controller);
            controller.start();});
        egyJatekosMod.addActionListener((e)->{
            this.setVisible(false);
            PalyaGUI gui = new PalyaGUI();
            JButton mindenLatszikGomb = new JButton("Minden látszódik újra!");
            JButton felrakottGolyoNezetGomb = new JButton("Csak a felrakott golyók látszódnak!");
            JButton passzGomb = new JButton("Pasz, nem akarok több golyót levenni!");
            JButton mentesGomb = new JButton("Játék aktuális állásának mentése");
            JatekFrame frame = new JatekFrame(gui, felrakottGolyoNezetGomb, mindenLatszikGomb, passzGomb, mentesGomb);
            frame.setTitle("Egy játékos mód");
            Tabla t = new Tabla();
            ValodiJatekos g1 = new ValodiJatekos(t, Golyo.FEHER);
            GepiJatekos g2 = new GepiJatekos(t, Golyo.FEKETE);
            JatekSzereploi jsz = new JatekSzereploi(t, g1, g2);
            Controller controller = new Controller(jsz, frame, felrakottGolyoNezetGomb, mindenLatszikGomb, mentesGomb);
            g2.setC(controller);
            gui.setController(controller);});
        ketJatekosMod.addActionListener((e)->{
            this.setVisible(false);
            PalyaGUI gui = new PalyaGUI();
            JButton mindenLatszikGomb = new JButton("Minden látszódik újra!");
            JButton felrakottGolyoNezetGomb = new JButton("Csak a felrakott golyók látszódnak!");
            JButton passzGomb = new JButton("Pasz, nem akarok több golyót levenni!");
            JButton mentesGomb = new JButton("Játék aktuális állásának mentése");
            JatekFrame frame = new JatekFrame(gui, felrakottGolyoNezetGomb, mindenLatszikGomb, passzGomb, mentesGomb);
            frame.setTitle("Két játékos mód");
            Tabla t = new Tabla();
            ValodiJatekos g1 = new ValodiJatekos(t, Golyo.FEHER);
            ValodiJatekos g2 = new ValodiJatekos(t, Golyo.FEKETE);
            JatekSzereploi jsz = new JatekSzereploi(t, g1, g2);
            Controller controller = new Controller(jsz, frame, felrakottGolyoNezetGomb, mindenLatszikGomb, mentesGomb);
            gui.setController(controller);});
        
        mentettJatekFolytatas.addActionListener((e)->{
            this.setVisible(false);
            PalyaGUI gui = new PalyaGUI();
            JButton mindenLatszikGomb = new JButton("Minden látszódik újra!");
            JButton felrakottGolyoNezetGomb = new JButton("Csak a felrakott golyók látszódnak!");
            JButton passzGomb = new JButton("Pasz, nem akarok több golyót levenni!");
            JButton mentesGomb = new JButton("Játék aktuális állásának mentése");
            JatekIO fajlKezelo = new JatekXMLIO("Jatek.xml");
            JatekSzereploi jsz = fajlKezelo.beolvasFajlbol();
            for(int i=0; i<jsz.getT().foglaltMezok().size(); i=i+3){
                int seged = jsz.getT().foglaltMezok().get(i);
                int seged2 = jsz.getT().foglaltMezok().get(i+1);
                int seged3 = jsz.getT().foglaltMezok().get(i+2);
                if(jsz.getT().getElem(seged, new Pozicio(seged2, seged3))==Golyo.FEHER){
                    gui.golyoKirajzolasPoziciora(seged, new Pozicio(seged2, seged3), Golyo.FEHER);
                }
                else if(jsz.getT().getElem(seged, new Pozicio(seged2, seged3))==Golyo.FEKETE){
                    gui.golyoKirajzolasPoziciora(seged, new Pozicio(seged2, seged3), Golyo.FEKETE);
                }
            }
            jsz.getJ1().setDb(15-jsz.getT().adottSzinuGolyokSzama(Golyo.FEHER));
            jsz.getJ2().setDb(15-jsz.getT().adottSzinuGolyokSzama(Golyo.FEKETE));
            JatekFrame frame = new JatekFrame(gui, felrakottGolyoNezetGomb, mindenLatszikGomb, passzGomb, mentesGomb);
            frame.setTitle("Mentett játék folytatása");
            Controller controller = new Controller(jsz, frame, felrakottGolyoNezetGomb, mindenLatszikGomb, mentesGomb);
            if(jsz.getJ2() instanceof GepiJatekos){
                GepiJatekos seged = (GepiJatekos)jsz.getJ2();
                seged.setC(controller);
                controller.getJsz().setJ2(seged);
            }
            gui.setController(controller);
        });
        
        this.setLayout(new GridLayout(2,2));
        this.add(gepiSzimulacio);
        this.add(egyJatekosMod);
        this.add(ketJatekosMod);
        this.add(mentettJatekFolytatas);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(500, 500));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import logika.Golyo;
import fajlKezeles.JatekIO;
import fajlKezeles.JatekXMLIO;
import logika.JatekSzereploi;
import logika.Jatekos;
import logika.Pozicio;
import logika.Tabla;
import logika.ValodiJatekos;

public class JatekXMLIOTest {
    
    public JatekXMLIOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void kiirBeolvasTest(){
        Tabla t = new Tabla();
        Jatekos j1 = new ValodiJatekos(t, Golyo.FEHER);
        Jatekos j2 = new ValodiJatekos(t, Golyo.FEKETE);
        JatekSzereploi jsz = new JatekSzereploi(t, j1, j2);
        t.babuFelrakas(0, new Pozicio(1, 0), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(2, 3), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(1, 2), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(3, 3), Golyo.FEHER);
        t.babuFelrakas(1, new Pozicio(1, 1), Golyo.FEHER);
        t.babuFelrakas(1, new Pozicio(0, 1), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(0, 0), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(2, 1), Golyo.FEKETE);
        t.babuFelrakas(1, new Pozicio(2, 2), Golyo.FEHER);
        t.babuFelrakas(2, new Pozicio(1, 0), Golyo.FEKETE);
        t.babuFelrakas(3, new Pozicio(0, 0), Golyo.FEHER);
        t.babuFelrakas(2, new Pozicio(1, 1), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(1, 1), Golyo.FEHER);
        jsz.setAktualisJatekos(j2);
        JatekIO fajlKezelo = new JatekXMLIO("Jatek.xml");
        fajlKezelo.kiirFajlba(jsz);
        JatekSzereploi beolvasott = fajlKezelo.beolvasFajlbol();
        assertEquals("Megkell egyeznie 1", Golyo.FEHER, beolvasott.getT().getElem(0, new Pozicio(1, 0)));
        assertEquals("Megkell egyeznie 2", Golyo.FEHER, beolvasott.getT().getElem(0, new Pozicio(2, 3)));
        assertEquals("Megkell egyeznie 3", Golyo.FEKETE, beolvasott.getT().getElem(0, new Pozicio(1, 2)));
        assertEquals("Megkell egyeznie 4", Golyo.FEHER, beolvasott.getT().getElem(0, new Pozicio(0, 0)));
        assertEquals("Megkell egyeznie 5", Golyo.FEHER, beolvasott.getT().getElem(1, new Pozicio(1, 1)));
        assertEquals("Megkell egyeznie 6", Golyo.FEKETE, beolvasott.getT().getElem(1, new Pozicio(0, 1)));
        assertEquals("Megkell egyeznie 7", Golyo.FEHER, beolvasott.getT().getElem(0, new Pozicio(0, 0)));
        assertEquals("Megkell egyeznie 8", Golyo.FEKETE, beolvasott.getT().getElem(0, new Pozicio(2, 1)));
        assertEquals("Megkell egyeznie 9", Golyo.FEHER, beolvasott.getT().getElem(1, new Pozicio(2, 2)));
        assertEquals("Megkell egyeznie 10", Golyo.FEKETE, beolvasott.getT().getElem(2, new Pozicio(1, 0)));
        assertEquals("Megkell egyeznie 11", Golyo.FEHER, beolvasott.getT().getElem(3, new Pozicio(0, 0)));
        assertEquals("Megkell egyeznie 12", null, beolvasott.getT().getElem(2, new Pozicio(0, 0)));
        assertEquals(beolvasott.getJ1().getGolyo(), Golyo.FEHER);
        assertEquals(beolvasott.getJ2().getGolyo(), Golyo.FEKETE);
        assertEquals(beolvasott.getAktualisJatekos().getGolyo(), Golyo.FEKETE);
        
    }
}


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import logika.Golyo;
import logika.Pozicio;
import logika.TablaSzint;

public class TablaSzintTest {
    
    public TablaSzintTest() {
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
    public void letrehozTest(){
        TablaSzint tsz = new TablaSzint(4);
        assertEquals("Nem jo a konstruktorban az ertekadas", 4, tsz.getEredetiMeret());
        assertEquals("Nem megfelelo meretu a tomb sora", 6, tsz.getTulIndexelhetoTomb().length);
        assertEquals("Nem megfelelo meretu a tomb oszlopa", 6, tsz.getTulIndexelhetoTomb()[0].length);        
    }
    @Test
    public void babuLerakTest(){
        TablaSzint tsz = new TablaSzint(4);
        tsz.babuLehelyez(new Pozicio(0, 0), Golyo.FEHER);
        tsz.babuLehelyez(new Pozicio(2, 3), Golyo.FEKETE);
        assertEquals("Nincs ott a golyo, ahol kene",Golyo.FEHER, tsz.getBabu(new Pozicio(0, 0)));
        assertEquals("Nincs ott a golyo, ahol kene",Golyo.FEKETE ,tsz.getBabu(new Pozicio(2, 3)));
        assertEquals("Nem ott semminek lennie", null,tsz.getBabu(new Pozicio(0, 1)));  
    }
    @Test
    public void babuLeszedesTest(){
        TablaSzint tsz = new TablaSzint(4);
        tsz.babuLehelyez(new Pozicio(0, 0), Golyo.FEHER);
        tsz.babuLehelyez(new Pozicio(2, 3), Golyo.FEKETE);
        tsz.babuLeszedes(new Pozicio(0, 0));
        assertEquals("Nem kellene ott golyonak lennie",null, tsz.getBabu(new Pozicio(0, 0)));
        assertEquals("Golyonak kene ott lennie",Golyo.FEKETE, tsz.getBabu(new Pozicio(2, 3)));
        tsz.babuLeszedes(new Pozicio(2, 3));
        assertEquals("Nem kellene ott golyonak lennie",null, tsz.getBabu(new Pozicio(2, 3)));
    }
    @Test
    public void pozicioKonvertalTest(){
        TablaSzint tsz = new TablaSzint(3);
        tsz.babuLehelyez(new Pozicio(2, 3), Golyo.FEHER);
        assertEquals("Null-t kell kapni",null, tsz.getTulIndexelhetoTomb()[2][3]);
        assertEquals("Az eredmeny az eltolt mezoben kell, hogy legyen",Golyo.FEHER, tsz.getTulIndexelhetoTomb()[3][4]);    
    }
    
}

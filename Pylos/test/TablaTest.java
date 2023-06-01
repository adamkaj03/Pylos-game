import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import logika.Golyo;
import logika.Pozicio;
import logika.Tabla;

public class TablaTest {
    
    public TablaTest() {
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
    public void babuFelrakasTest(){
        Tabla t = new Tabla();
        t.babuFelrakas(0, new Pozicio(0, 0), Golyo.FEHER);
        assertEquals("Nincs ott a golyo vagy nem olyan mint, amilyen kell", Golyo.FEHER, t.getElem(0, new Pozicio(0, 0)));
        t.babuFelrakas(3, new Pozicio(0, 0), Golyo.FEKETE);
        assertEquals("Nincs ott a golyo vagy nem olyan mint, amilyen kell", Golyo.FEKETE, t.getElem(3, new Pozicio(0, 0)));
    }
    @Test
    public void babuLevetelTest(){
        Tabla t = new Tabla();
        t.babuFelrakas(0, new Pozicio(3, 1), Golyo.FEHER);
        assertEquals("Nincs ott a golyo vagy nem olyan mint, amilyen kell", Golyo.FEHER, t.getElem(0, new Pozicio(3, 1)));
        t.babuLevetel(0, new Pozicio(3, 1));
        assertEquals("Nem szabad ott golyonak lennie", null, t.getElem(0, new Pozicio(3, 1)));
        t.babuFelrakas(3, new Pozicio(0, 0), Golyo.FEKETE);
        t.babuLevetel(0, new Pozicio(0, 0));
        assertEquals("Nem szabad, hogy egy masik szint valtozzon levetel miatt", Golyo.FEKETE, t.getElem(3, new Pozicio(0, 0)));
    }
    @Test
    public void isUresNegyzetTest1(){
        Tabla t = new Tabla();
        t.babuFelrakas(0, new Pozicio(0, 0), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(0, 1), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(1, 0), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(1, 1), Golyo.FEKETE);
        assertEquals("Kellene lennie ures negyzetnek", 3, t.uresNegyzetOsszes().size());
        assertEquals("Kellene lennie ures negyzetnek", 1, t.uresNegyzetekLepesUtan(0, new Pozicio(1, 1)).get(0));
        assertEquals("Kellene lennie ures negyzetnek", 0, t.uresNegyzetekLepesUtan(0, new Pozicio(1, 1)).get(1));
        assertEquals("Kellene lennie ures negyzetnek", 0, t.uresNegyzetekLepesUtan(0, new Pozicio(1, 1)).get(2));
        t.babuLevetel(0, new Pozicio(1, 1));
        assertEquals("Nem szabadna ures negyzetet jeleznie", true, t.uresNegyzetOsszes().isEmpty());
        t.babuFelrakas(0, new Pozicio(1, 1), Golyo.FEKETE);
        t.babuFelrakas(1, new Pozicio(0, 0), Golyo.FEKETE);
        assertEquals("Nem szabadna ures negyzetet jeleznie", true, t.uresNegyzetOsszes().isEmpty());
        assertEquals("Nem szabadna ures negyzetet jeleznie", true, t.uresNegyzetekLepesUtan(1, new Pozicio(0, 0)).isEmpty());
    }
    @Test
    public void isUresNegyzetTest2(){
        Tabla t = new Tabla();
        t.babuFelrakas(0, new Pozicio(1, 2), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(3, 0), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(2, 0), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(1, 1), Golyo.FEKETE);
        assertEquals("Meg egy ures negyzet sincs", true, t.uresNegyzetOsszes().isEmpty());
        t.babuFelrakas(0, new Pozicio(2, 2), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(3, 1), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(2, 1), Golyo.FEHER);
        
        assertEquals("Ketto ures negyzet is van", 6, t.uresNegyzetOsszes().size());
        t.babuFelrakas(1, new Pozicio(1, 1), Golyo.FEKETE);
        
        assertEquals("Kell lennie egy ures negyzetnek", 3, t.uresNegyzetOsszes().size());
        t.babuFelrakas(1, new Pozicio(2, 0), Golyo.FEHER);
        assertEquals("Egy ures negyzet sincs", true, t.uresNegyzetOsszes().isEmpty());
        t.babuFelrakas(0, new Pozicio(3, 2), Golyo.FEKETE);
        for(int i=0; i<t.uresNegyzetekLepesUtan(0, new Pozicio(3, 2)).size(); i++){
            System.out.println(t.uresNegyzetekLepesUtan(0, new Pozicio(3, 2)).get(i));
        }
        assertEquals("Kell lennie egy ures negyzetnek", 3, t.uresNegyzetOsszes().size());
        t.babuFelrakas(0, new Pozicio(1, 0), Golyo.FEHER);
        t.babuFelrakas(1, new Pozicio(1, 0), Golyo.FEKETE);
        t.babuFelrakas(1, new Pozicio(2, 1), Golyo.FEHER);
        assertEquals("Kell lennie egy ures negyzetnek", 3, t.uresNegyzetOsszes().size());
        t.babuFelrakas(2, new Pozicio(1, 0), Golyo.FEHER);
        assertEquals("Egy ures negyzet sincs", true, t.uresNegyzetOsszes().isEmpty());
    }
    @Test
    public void validLepesTest(){
        Tabla t = new Tabla();
        assertEquals("Invalidnak kell lennie ennek a mezonek", false, t.validLepes(5, new Pozicio(0, 0)));
        assertEquals("Invalidnak kell lennie ennek a mezonek", false, t.validLepes(2, new Pozicio(2, 2)));
        assertEquals("Nem megfelelo, mert nincs ala tamasztas", false, t.validLepes(1, new Pozicio(2, 2)));
        assertEquals("Nem megfelelo, mert nincs ala tamasztas", false, t.validLepes(3, new Pozicio(0, 0)));
        assertEquals("Lehet rakni, a 0.szintre", true, t.validLepes(0, new Pozicio(2, 2)));
    }
    @Test
    public void validLepesTest2(){
        Tabla t = new Tabla();
        t.babuFelrakas(0, new Pozicio(1, 1), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(2, 1), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(2, 0), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(1, 0), Golyo.FEHER);
        assertEquals("Lehet rakni, a 1.szintnek erre a mezojere", true, t.validLepes(1, new Pozicio(1, 0)));
        t.babuFelrakas(1, new Pozicio(1, 0), Golyo.FEHER);
        assertEquals("Nem lehet mostmar rakni, mert foglalt", false, t.validLepes(1, new Pozicio(1, 0)));
        assertEquals("Nem lehetfelette levo mezore rakni, mert nincs alatamasztas", false, t.validLepes(1, new Pozicio(0, 0)));
        t.babuFelrakas(0, new Pozicio(3, 0), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(3, 1), Golyo.FEHER);
        assertEquals("Lehet rakni, a 1.szintnek erre a mezojere", true, t.validLepes(1, new Pozicio(2, 0)));
        t.babuFelrakas(1, new Pozicio(2, 0), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(3, 2), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(2, 2), Golyo.FEKETE);
        assertEquals("Lehet rakni, a 1.szintnek erre a mezojere", true, t.validLepes(1, new Pozicio(2, 1)));
        assertEquals("Ide meg nem lehet rakni", false, t.validLepes(1, new Pozicio(1, 1)));
        t.babuFelrakas(1, new Pozicio(2, 1), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(1, 2), Golyo.FEKETE);
        assertEquals("Lehet rakni, a 1.szintnek erre a mezojere", true, t.validLepes(1, new Pozicio(1, 1)));
        assertEquals("Ide meg nem lehet rakni", false, t.validLepes(2, new Pozicio(1, 0)));
        t.babuFelrakas(1, new Pozicio(1, 1), Golyo.FEHER);
        assertEquals("Mostmar a 2. szintre is lehet ralni", true, t.validLepes(2, new Pozicio(1, 0)));
    }
   
    @Test
    public void kiNyertTest(){
        Tabla t = new Tabla();
        assertEquals("Nincs gyoztes",null, t.kiNyert());
        t.babuFelrakas(3, new Pozicio(0, 0), Golyo.FEHER);
        assertEquals("Feher nyert",Golyo.FEHER, t.kiNyert());
    }
    
    @Test
    public void isBabuLevehetoTest(){
        Tabla t = new Tabla();
        t.babuFelrakas(0, new Pozicio(1, 1), Golyo.FEHER);
        assertEquals("Leveheto a babu", true, t.isBabuLeveheto(0, new Pozicio(1, 1), Golyo.FEHER));
        t.babuFelrakas(0, new Pozicio(1, 0), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(2, 0), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(2, 1), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(3, 0), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(3, 1), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(1, 2), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(2, 2), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(3, 2), Golyo.FEHER);
        t.babuFelrakas(1, new Pozicio(1, 0), Golyo.FEKETE);
        assertEquals("Nem veheto le", false, t.isBabuLeveheto(0, new Pozicio(1, 1), Golyo.FEHER));
        t.babuFelrakas(1, new Pozicio(2, 0), Golyo.FEHER);
        t.babuFelrakas(1, new Pozicio(1, 1), Golyo.FEKETE);
        assertEquals("Nem veheto le", false, t.isBabuLeveheto(0, new Pozicio(3, 0), Golyo.FEHER));
        assertEquals("Nem veheto le", false, t.isBabuLeveheto(0, new Pozicio(3, 1), Golyo.FEKETE));
        assertEquals("Leveheto", true, t.isBabuLeveheto(0, new Pozicio(3, 2), Golyo.FEHER));
        assertEquals("Nem veheto le", false, t.isBabuLeveheto(0, new Pozicio(2, 2), Golyo.FEKETE));
        t.babuFelrakas(1, new Pozicio(2, 1), Golyo.FEHER);
        t.babuFelrakas(2, new Pozicio(1, 0), Golyo.FEKETE);
        assertEquals("Nem veheto le", false, t.isBabuLeveheto(0, new Pozicio(3, 2), Golyo.FEHER));
        assertEquals("Nem veheto le", false, t.isBabuLeveheto(0, new Pozicio(2, 2), Golyo.FEKETE));
        assertEquals("Nem veheto le", false, t.isBabuLeveheto(1, new Pozicio(2, 1), Golyo.FEHER));
        assertEquals("Nem veheto le", false, t.isBabuLeveheto(1, new Pozicio(2, 0), Golyo.FEHER));
   }
   
    @Test
   public void kialalultEgySzinuNegyzetTest(){
       Tabla t = new Tabla();
        t.babuFelrakas(0, new Pozicio(1, 1), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(1, 2), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(2, 1), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(2, 2), Golyo.FEKETE);
        assertEquals("Nem egy szinu a negyzet", false, t.kialalultEgySzinuNegyzet(0, new Pozicio(2, 2)));
        assertEquals("Kell lennie egy ures negyzetnek", true, t.uresNegyzetOsszes());
        t.babuFelrakas(0, new Pozicio(0, 1), Golyo.FEHER);
        t.babuFelrakas(0, new Pozicio(0, 2), Golyo.FEHER);
        assertEquals("Van egy szinu negyzet", true, t.kialalultEgySzinuNegyzet(0, new Pozicio(0, 2)));
        t.babuFelrakas(0, new Pozicio(2, 0), Golyo.FEKETE);
        t.babuFelrakas(0, new Pozicio(1, 0), Golyo.FEHER);
        assertEquals("Nem egy szinu a negyzet", false, t.kialalultEgySzinuNegyzet(0, new Pozicio(1, 0)));
        t.babuFelrakas(0, new Pozicio(0, 0), Golyo.FEHER);
        assertEquals("Van egy szinu negyzet", true, t.kialalultEgySzinuNegyzet(0, new Pozicio(0, 0)));
        t.babuFelrakas(1, new Pozicio(0, 0), Golyo.FEHER);
        t.babuFelrakas(1, new Pozicio(0, 1), Golyo.FEHER);
        t.babuFelrakas(1, new Pozicio(1, 0), Golyo.FEHER);
        t.babuFelrakas(1, new Pozicio(1, 1), Golyo.FEKETE);
        assertEquals("Nem egy szinu a negyzet", false, t.kialalultEgySzinuNegyzet(1, new Pozicio(0, 0)));
        t.babuLevetel(1, new Pozicio(1, 1));
        t.babuFelrakas(1, new Pozicio(1, 1), Golyo.FEHER);
        assertEquals("Egy szinu a negyzet", true, t.kialalultEgySzinuNegyzet(1, new Pozicio(0, 0)));
   }
   
   @Test
   public void adottSzinuGolyokSzamaTest(){
       Tabla t = new Tabla();
       t.babuFelrakas(0, new Pozicio(1, 1), Golyo.FEHER);
       t.babuFelrakas(0, new Pozicio(1, 0), Golyo.FEKETE);
       t.babuFelrakas(0, new Pozicio(2, 0), Golyo.FEHER);
       t.babuFelrakas(0, new Pozicio(2, 1), Golyo.FEKETE);
       t.babuFelrakas(0, new Pozicio(3, 0), Golyo.FEHER);
       t.babuFelrakas(0, new Pozicio(3, 1), Golyo.FEKETE);
       t.babuFelrakas(0, new Pozicio(1, 2), Golyo.FEHER);
       t.babuFelrakas(0, new Pozicio(2, 2), Golyo.FEKETE);
       t.babuFelrakas(0, new Pozicio(3, 2), Golyo.FEHER);
       t.babuFelrakas(1, new Pozicio(1, 0), Golyo.FEKETE);
       t.babuFelrakas(2, new Pozicio(0, 0), Golyo.FEKETE);
       t.babuFelrakas(2, new Pozicio(1, 0), Golyo.FEKETE);
       assertEquals(5, t.adottSzinuGolyokSzama(Golyo.FEHER));
       assertEquals(7, t.adottSzinuGolyokSzama(Golyo.FEKETE));
   }
    
}

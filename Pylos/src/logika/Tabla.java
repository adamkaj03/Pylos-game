package logika;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tabla {
    //tarolast ugy kell elkepzelni, hogy a kulcs a szinteket jelzi
    //minden szinthez pedig van egy TablaSzint, ami a szintet jelkepezi 
    private Map<Integer, TablaSzint> tabla;
    
    public Tabla(){
        tabla = new HashMap<>();
        tabla.put(0, new TablaSzint(4));
        tabla.put(1, new TablaSzint(3));
        tabla.put(2, new TablaSzint(2));
        tabla.put(3, new TablaSzint(1)); 
    }
    
    public Tabla(List<TablaSzint> lista){
        tabla = new HashMap<>();
        tabla.put(0, lista.get(0));
        tabla.put(1, lista.get(1));
        tabla.put(2, lista.get(2));
        tabla.put(3, lista.get(3));
    }
    
    //felrakja a tabla adott poziciojara az elemet
    public void babuFelrakas(int szint, Pozicio p, Golyo g){
        tabla.get(szint).babuLehelyez(p, g);
    }
    
    //lekerul a babu a tablarol
    public void babuLevetel(int szint, Pozicio p){
        tabla.get(szint).babuLeszedes(p);
    }
    
    //vissza adja az adott pozicion levo elemet
    public Golyo getElem(int szint, Pozicio p){
        //Golyo g = tabla.get(szint)[p.getSor()][p.getOszlop()];
        Golyo g = tabla.get(szint).getBabu(p);
        return g;
    }
    
    //egy adott lepes utani helyen megnezi, hogy alakult-e ki ures negyzet
    //ha alakult ki, akkor a egy listaba rakja annak helyet
    //lista 3k-dik eleme a szint, 3k+1-dik a sor, a 3k+2-dik az oszlop
    public List uresNegyzetekLepesUtan(int szint, Pozicio p){
        List<Integer> uresNegyzetek = new ArrayList<>();
        //negy esetet vizsgal, ahol lehet a lerakott golyo (bal felso, bal also, jobb felso, jobb also)
        if(getElem(szint, new Pozicio(p.getSor(), p.getOszlop()-1)) != null 
                && getElem(szint, new Pozicio(p.getSor()-1, p.getOszlop())) != null
                && getElem(szint, new Pozicio(p.getSor()-1, p.getOszlop()-1)) != null){
                    uresNegyzetek.add(szint+1);
                    uresNegyzetek.add(p.getSor()-1);
                    uresNegyzetek.add(p.getOszlop()-1);
        }
        
        if(getElem(szint, new Pozicio(p.getSor(), p.getOszlop()+1)) != null 
                && getElem(szint, new Pozicio(p.getSor()-1, p.getOszlop())) != null
                && getElem(szint, new Pozicio(p.getSor()-1, p.getOszlop()+1)) != null){
                    uresNegyzetek.add(szint+1);
                    uresNegyzetek.add(p.getSor()-1);
                    uresNegyzetek.add(p.getOszlop());
        }
        if(getElem(szint, new Pozicio(p.getSor(), p.getOszlop()-1)) != null 
                && getElem(szint, new Pozicio(p.getSor()+1, p.getOszlop())) != null
                && getElem(szint, new Pozicio(p.getSor()+1, p.getOszlop()-1)) != null){
                    uresNegyzetek.add(szint+1);
                    uresNegyzetek.add(p.getSor());
                    uresNegyzetek.add(p.getOszlop()-1);
        }
        if(getElem(szint, new Pozicio(p.getSor(), p.getOszlop()+1)) != null 
                && getElem(szint, new Pozicio(p.getSor()+1, p.getOszlop())) != null
                && getElem(szint, new Pozicio(p.getSor()+1, p.getOszlop()+1)) != null){
                    uresNegyzetek.add(szint+1);
                    uresNegyzetek.add(p.getSor());
                    uresNegyzetek.add(p.getOszlop());
        }
        return uresNegyzetek;
    }
    
    //az osszes ures negyzetet, keresi es ezek poziciojat kigyujti, ugyanugy list-be
    public List<Integer> uresNegyzetOsszes(){
        List<Integer> uresNegyzetek = new ArrayList<>();
        for(int szint=0; szint<3; szint++){
            for(int sor=0; sor<4-szint; sor++){
                for(int oszlop=0; oszlop<4-szint; oszlop++){
                    if(getElem(szint, new Pozicio(sor, oszlop-1)) != null
                            && getElem(szint, new Pozicio(sor-1, oszlop)) != null
                            && getElem(szint, new Pozicio(sor-1, oszlop-1)) != null 
                            && getElem(szint, new Pozicio(sor, oszlop)) != null
                            && getElem(szint+1, new Pozicio(sor-1, oszlop-1)) == null){
                                uresNegyzetek.add(szint+1);
                                uresNegyzetek.add(sor-1);
                                uresNegyzetek.add(oszlop-1);
                    } 
                }
            }
        }
        return uresNegyzetek;  
    }
    
    //kilistazza azokat a mezoket, melyre lehet lerakni golyot
    public List<Integer> valaszthatoMezo(){
        List<Integer> valaszthatoMezok = new ArrayList<>();
        for(int sor = 0; sor<4; sor++){
            for(int oszlop = 0; oszlop<4; oszlop++){
                if(getElem(0, new Pozicio(sor, oszlop))==null){
                    valaszthatoMezok.add(0);
                    valaszthatoMezok.add(sor);
                    valaszthatoMezok.add(oszlop);
                }
            }
        }
        valaszthatoMezok.addAll(uresNegyzetOsszes());
        return valaszthatoMezok;
    }
    
    //kilistazza azokat a mezoket, melyen mar van golyo
    public List<Integer> foglaltMezok(){
        List<Integer> foglaltMezok = new ArrayList<>();
         for(int szint=0; szint<3; szint++){
            for(int sor=0; sor<4-szint; sor++){
                for(int oszlop=0; oszlop<4-szint; oszlop++){
                    if(getElem(szint, new Pozicio(sor, oszlop))!=null){
                        foglaltMezok.add(szint);
                        foglaltMezok.add(sor);
                        foglaltMezok.add(oszlop);
                    }
                }
            }
        }
        return foglaltMezok;
    }
    
    //megnezi, hogy a lepest kovetoen alakut-e ki egy szinu negyzet
    public boolean kialalultEgySzinuNegyzet(int szint, Pozicio p){
        Golyo keresettSzin = getElem(szint, p);
        //System.out.println("keresett: "+ getElem(szint, p).getNev());
        //lepes utan 4 helyen lehet a golyo, ezeket az eseteket vizsgalja
        if(getElem(szint, new Pozicio(p.getSor(), p.getOszlop()-1)) == keresettSzin 
                && getElem(szint, new Pozicio(p.getSor()-1, p.getOszlop())) == keresettSzin
                && getElem(szint, new Pozicio(p.getSor()-1, p.getOszlop()-1)) == keresettSzin){
            //System.out.println("a");
            return true;
        }
        else if(getElem(szint, new Pozicio(p.getSor(), p.getOszlop()+1)) == keresettSzin 
                && getElem(szint, new Pozicio(p.getSor()-1, p.getOszlop())) == keresettSzin
                && getElem(szint, new Pozicio(p.getSor()-1, p.getOszlop()+1)) == keresettSzin){
            /*System.out.println("aa");
            System.out.println(getElem(szint, new Pozicio(p.getSor(), p.getOszlop()+1)).getNev());
            System.out.println(getElem(szint, new Pozicio(p.getSor()-1, p.getOszlop())).getNev());
            System.out.println(getElem(szint, new Pozicio(p.getSor()-1, p.getOszlop()+1)).getNev());*/
            return true;
        }
        else if(getElem(szint, new Pozicio(p.getSor(), p.getOszlop()-1)) == keresettSzin 
                && getElem(szint, new Pozicio(p.getSor()+1, p.getOszlop())) == keresettSzin
                && getElem(szint, new Pozicio(p.getSor()+1, p.getOszlop()-1)) == keresettSzin){
            //System.out.println("aaa");
            return true;
        }
        else if(getElem(szint, new Pozicio(p.getSor(), p.getOszlop()+1)) == keresettSzin 
                && getElem(szint, new Pozicio(p.getSor()+1, p.getOszlop())) == keresettSzin
                && getElem(szint, new Pozicio(p.getSor()+1, p.getOszlop()+1)) == keresettSzin){
            //System.out.println("aaaa");
            return true;
        }
        else{
            return false;
        }
    }
    
    
    //megnezi, hogy a megadott pozico valid-e
    private boolean pozicioValidalas(int szint, Pozicio p){
        switch (szint) {
            case 0:{
                return p.getSor()<4 && p.getOszlop()<4;
            }
            case 1:{
                return p.getSor()<3 && p.getOszlop()<3;
            }
            case 2:{
                return p.getSor()<2 && p.getOszlop()<2;
            }
            case 3:{
                return p.getSor()<1 && p.getOszlop()<1;
            }
                
        }
        return false;
    }
    
    //ellenorzi, hogy a megadott lepes megfelelo volt-e
    public boolean validLepes(int szint, Pozicio p){
        //megvizsgalom, hogy a megadott pozicio letezik-e
        if(pozicioValidalas(szint, p)){
            //0.szinten csak azt kell vizsgalni, hogy a pozicioban null volt-e
            if(szint == 0 ){
                return getElem(szint, p)==null;
            }
            else if(szint != 0){
                //vizsgalni kell, hogy megvan-e rendesen az ala tamasztas es szabad-e a pozicio
                return getElem(szint-1, p) != null 
                        && getElem(szint-1, new Pozicio(p.getSor(), p.getOszlop()+1)) != null
                        && getElem(szint-1, new Pozicio(p.getSor()+1, p.getOszlop()))!=null 
                        && getElem(szint-1, new Pozicio(p.getSor()+1, p.getOszlop()+1))!=null
                        && getElem(szint, p)==null;
            }
        }
        return false;
    }
    
    //megvizsgalja, hogy a levenni kívánt babu nem-e egy alatamasztas
    public boolean isBabuLeveheto(int szint, Pozicio p, Golyo g){
        //megnezi, hogy az adott pozicion ugyanolyan szinu a golyo, mint amilyet leakarunk venni
        //majd aztan megnezi, hogy felette levo szinten 4 golyonak lehet kitamasztasa es hogy azok nullok-e
        return tabla.get(szint).getBabu(p) == g 
                && tabla.get(szint+1).getBabu(new Pozicio(p.getSor()-1, p.getOszlop()-1)) ==null
                && tabla.get(szint+1).getBabu(new Pozicio(p.getSor()-1, p.getOszlop()))==null
                && tabla.get(szint+1).getBabu(new Pozicio(p.getSor(), p.getOszlop()-1)) ==null 
                && tabla.get(szint+1).getBabu(p)==null;
    }
    
    //megnezi van-e ertelmes elem a tabla tetejen, ha igen akkor valaki gyozott
    public Golyo kiNyert(){
        return getElem(3, new Pozicio(0, 0));
    }
    
    //megszamolja, hogy egy adott tipusu golyobol mennyi van a tablan
    public int adottSzinuGolyokSzama(Golyo g){
        int db = 0;
        for(int szint=0; szint<3; szint++){
            for(int sor=0; sor<4-szint; sor++){
                for(int oszlop=0; oszlop<4-szint; oszlop++){
                    if(getElem(szint, new Pozicio(sor, oszlop))==g){
                        db++;
                    }
                        
                }
            }
        }
        return db;
    }
    
}

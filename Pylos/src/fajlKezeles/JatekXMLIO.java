package fajlKezeles;

import fajlKezeles.JatekIOAbsztrakt;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logika.GepiJatekos;
import logika.Golyo;
import logika.JatekSzereploi;
import logika.Jatekos;
import logika.Pozicio;
import logika.Tabla;
import logika.TablaSzint;
import logika.ValodiJatekos;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class JatekXMLIO extends JatekIOAbsztrakt{
    public JatekXMLIO(String fajlNev){
        super(fajlNev);
    }
    @Override
    public void kiirFajlba(JatekSzereploi j) {
        //dokumentum letrehozasa
        Document jdomDoc = new Document();
        //gyokerelem letrehozasa
        Element rootElement = new Element("jatek");
        //gyoker beallitasa
        jdomDoc.setRootElement(rootElement);
        Element tablaElement = new Element("tabla");
        for(int i=0; i<3; i++){
            Element jatekos = new Element("jatekos");
            switch (i) {
                case 0:{
                    if(j.getJ1() instanceof ValodiJatekos){
                        jatekos.addContent("ValodiJatekos");
                    }
                    else{
                        jatekos.addContent("GepiJatekos");
                    }
                } 
                break;
                case 1:{
                    if(j.getJ2() instanceof ValodiJatekos){
                        jatekos.addContent("ValodiJatekos");
                    }
                    else{
                        jatekos.addContent("GepiJatekos");
                    }
                } 
                break;
                case 2:{
                    jatekos.addContent(j.getAktualisJatekos().getGolyo().getNev());
                } 
                break;
            }
            
        Element elemCopy1 = (Element)jatekos.clone();
        elemCopy1.detach();
        rootElement.addContent(elemCopy1);
        }
        
        for(int szint=0; szint<4; szint++){
            Element tablaSzint = new Element("tablaSzint");
            //szint valtozo segitsegevel a szintAttribute beallitasa 
            Attribute szintAttribute = new Attribute("szint", Integer.toString(szint));
            tablaSzint.setAttribute(szintAttribute);
            Element poziciok = new Element("poziciok");
            for(int sor = 0 ; sor< 4-szint; sor++){
                for(int oszlop = 0; oszlop<4-szint; oszlop++){
                    Element pozicio = new Element("pozicio");
                    //sor es oszlop beallitasa a
                    Attribute sorAttribute = new Attribute("sor", Integer.toString(sor));
                    Attribute oszlopAttribute = new Attribute("oszlop", Integer.toString(oszlop));
                    pozicio.setAttribute(oszlopAttribute);
                    pozicio.setAttribute(sorAttribute);
                    Element golyo = new Element("golyo");
                    //adott poziciohoz tartozo golyo beirasa a golyo Element-be
                    if(j.getT().getElem(szint, new Pozicio(sor, oszlop)) != null){
                        golyo.addContent(j.getT().getElem(szint, new Pozicio(sor, oszlop)).getNev());
                    }
                    else{
                       golyo.addContent("null"); 
                    }
                    pozicio.addContent(golyo);
                    poziciok.addContent(pozicio);
                }
            }
            
            tablaSzint.addContent(poziciok);
            tablaElement.addContent(tablaSzint);
            
            
        }

            Element elemCopy = (Element)tablaElement.clone();
        elemCopy.detach();
            rootElement.addContent(elemCopy);
        //az osszeallitott dokumentum fajlba irasa
        XMLOutputter xml = new XMLOutputter();
        xml.setFormat(Format.getPrettyFormat());
        try {
            PrintWriter pw = new PrintWriter(this.getFajlNev());
            pw.println(xml.outputString(jdomDoc));
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JatekXMLIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public JatekSzereploi beolvasFajlbol() {
        SAXBuilder jdomBuilder = new SAXBuilder();
        JatekSzereploi jsz = new JatekSzereploi();
        try {
            Document jdomDocument = jdomBuilder.build(this.getFajlNev());
            Element rootElement = jdomDocument.getRootElement();
            //tablaSzintek listaja
            List<Element> childrenElement = rootElement.getChildren();
            Tabla t = new Tabla();
            List<Element> tablaElementjei = childrenElement.get(3).getChildren();
            List<TablaSzint> szintekLista = new ArrayList<>();
            //vegig megy a tablaSzinten
            for(int i=0; i<tablaElementjei.size(); i++){
                //mindig egy megfelelo meretu szintet hoz letre
                TablaSzint sz = new TablaSzint(tablaElementjei.size()-i);
                //szint erteke
                int szint = Integer.parseInt(tablaElementjei.get(i).getAttributeValue("szint"));
                Element poziciokTag = tablaElementjei.get(i).getChild("poziciok");
                //pozicio lista lekerese
                List<Element> pozicioList = poziciokTag.getChildren("pozicio");
                //pozicioList-en vegig iteral lekeri az adott pozicio-t es hogy mit tartalmaz
                for(Element e : pozicioList){
                    int sor = Integer.parseInt(e.getAttributeValue("sor"));
                    int oszlop = Integer.parseInt(e.getAttributeValue("oszlop"));
                    Element golyo = e.getChild("golyo");
                    //ha nem null akkor az adott pozicion levo text alapjan letrehoz egy olyan golyot
                    if(!golyo.getText().equals("null")){
                        sz.babuLehelyez(new Pozicio(sor, oszlop), Golyo.letrehoz(golyo.getText()));
                    }
                    else{
                       sz.babuLehelyez(new Pozicio(sor, oszlop), null);
                    }  
                }
                //megfelelo szinthez adja
                szintekLista.add(i, sz);
            }
            //az adott szint listaval letrehoz egy tablat
            t = new Tabla(szintekLista);
            Jatekos j1;
            Jatekos j2;
            Jatekos aktualisJatekos;
            if(childrenElement.get(0).getText().equals("ValodiJatekos")){
                j1 = new ValodiJatekos(t, Golyo.FEHER);
            }
            else{
                j1 = new GepiJatekos(t, Golyo.FEHER);
            }
            if(childrenElement.get(1).getText().equals("ValodiJatekos")){
                j2 = new ValodiJatekos(t, Golyo.FEKETE);
            }
            else{
                j2 = new GepiJatekos(t, Golyo.FEKETE);
            }
            if(childrenElement.get(2).getText().equals("feher")){
                aktualisJatekos = j1;
            }
            else{
                aktualisJatekos = j2;
            }
            jsz = new JatekSzereploi(t, j1, j2, aktualisJatekos);
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(JatekXMLIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsz;
        
    }
    
}

package pt.c03ensaios.denisecamila;

import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;

public class App {

    public static void main(String[] args) {

        IEnquirer ea = new Enquirer();
        IResponder ra;
       
        ra = new Responder("beija-flor");
        ea.connect(ra);
    }
}

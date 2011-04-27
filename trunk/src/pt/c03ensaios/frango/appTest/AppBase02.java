package pt.c03ensaios.frango.appTest;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class AppBase02 {

    public static void main(String[] args) {
        String nomeAnimal;

        BaseConhecimento bc = new BaseConhecimento();
        String listaNomes[] = bc.listaNomes();

        for (int i = 0; i < listaNomes.length; i++) {
            nomeAnimal = listaNomes[i];
            System.out.print("Basic:    ");
            enquirerBasicTest(nomeAnimal);
            System.out.print("Advanced: ");
            enquirerAdvancedTest(nomeAnimal);
        }
    }

    public static void enquirerBasicTest(String nomeAnimal) {
        IEnquirer eb = new EnquirerBasic();
        IResponder rb = new Responder(nomeAnimal);

        eb.connect(rb);
    }

    public static void enquirerAdvancedTest(String nomeAnimal) {
        IEnquirer ea = new EnquirerAdvanced();
        IResponder ra = new Responder(nomeAnimal);

        ea.connect(ra);
    }
}

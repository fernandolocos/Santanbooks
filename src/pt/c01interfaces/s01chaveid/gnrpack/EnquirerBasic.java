package pt.c01interfaces.s01chaveid.gnrpack;

import java.util.Hashtable;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerBasic implements IEnquirer {
    private IObjetoConhecimento[] obj;
    private String[] animal;
    private Hashtable<String, String> propriedade;
    
    public EnquirerBasic() {
        IBaseConhecimento bc = new BaseConhecimento();
        propriedade = new Hashtable<String, String>();
        animal = bc.listaNomes();
        obj = new IObjetoConhecimento[animal.length];
        
        for (int i = 0; i < animal.length; i++) {
        	obj[i] = bc.recuperaObjeto(animal[i]);
        }
    }
    
    public void connect(IResponder responder) {
        boolean acertei = false;

        for (int i = 0; i < animal.length; i++) {
            boolean candidato = true;

            for (IDeclaracao dec = obj[i].primeira(); dec != null && candidato; dec = obj[i].proxima()) {
                String propr = dec.getPropriedade();
                String val = dec.getValor();

                if (propriedade.containsKey(propr)) {
                    if (!propriedade.get(propr).equalsIgnoreCase(val)) {
                        candidato = false;
                    }
                } else {
                    String resp = responder.ask(propr);
                    
                    if (!resp.equalsIgnoreCase(val)) {
                        candidato = false;
                    }
                    
                    propriedade.put(propr, resp);
                }
            }
            
            if (candidato) {
                acertei = responder.finalAnswer(animal[i]);
                break;
            }
        }
        
        if (acertei) {
            System.out.println("Oba! Acertei!");
        } else {
            System.out.println("fuem! fuem! fuem!");
        }
    }
}


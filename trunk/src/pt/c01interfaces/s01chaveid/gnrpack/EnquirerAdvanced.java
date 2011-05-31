package pt.c01interfaces.s01chaveid.gnrpack;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerAdvanced implements IEnquirer {
    String chute;
    
    public EnquirerAdvanced() {
        IBaseConhecimento bc = new BaseConhecimento();
        chute = bc.listaNomes()[0];
    }
    
    public void connect(IResponder responder) {
        boolean acertei = false;
        
        acertei = responder.finalAnswer(chute);
        
        if (acertei) {
            System.out.println("Oba! Acertei!");
        } else {
            System.out.println("fuem! fuem! fuem!");
        }
    }
}


package pt.c01interfaces.s01chaveid.naxxramas;

import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerAdvanced implements IEnquirer {
    private GrafoPerguntasAnimais grafo;

	public EnquirerAdvanced() {
        this.grafo = new GrafoPerguntasAnimais();
    }

	@Override
	public void connect(IResponder responder) {
		String animal = this.grafo.bfsForAnimal(responder);
		if (animal != null) {
		    this.showAnswer(responder.finalAnswer(animal), animal);
		}
		else {
			responder.finalAnswer(null);
		    this.showAnswer(false, null);
		}
	}
	
	private void showAnswer(boolean acertou, String animal) {
	    if (animal != null) {
    		if (acertou)
    			System.out.println("Oba! Acertei! O animal eh " + animal + "!");
    		else
    			System.out.println("fuem! fuem! fuem! Achei que o animal era "  + animal + "...");
    	    }
	    else {
	        System.out.println("fuem! fuem! fuem! Nao sei qual eh o animal...");
	    }
	}
}
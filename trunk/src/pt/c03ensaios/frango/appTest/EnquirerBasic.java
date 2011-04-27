package pt.c03ensaios.frango.appTest;

import java.util.HashMap;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerBasic implements IEnquirer {
	
	//as variaveis são estaticas para cachear os valores para a proxima execução do enquirer
    private static String[] listaNomes;
    private static HashMap<String, IObjetoConhecimento> objs = new HashMap<String, IObjetoConhecimento>();


    public EnquirerBasic() {
    	
    }

    public void connect(IResponder responder) {
        BancoRespostas respostas = new BancoRespostas();
        String animal = null;

        IBaseConhecimento base = new BaseConhecimento();

        if (listaNomes == null) {
            listaNomes = base.listaNomes();
        }

        //varre toda a lista de animais fazendo todas as pertuntas, e quando encontrar um animal que tenha todas as perguntas
        for (int i = 0; (i < listaNomes.length && animal == null); i++) {
            IObjetoConhecimento obj = objs.get(listaNomes[i]);
            if (obj == null) {
                obj = base.recuperaObjeto(listaNomes[i]);
                objs.put(listaNomes[i], obj);
            }

            IDeclaracao decl = obj.primeira();
            boolean igual = true;
            while (decl != null && igual == true) {
                if (!(respostas.getResposta(decl.getPropriedade()) != null)) {
                    respostas.setResposta(decl.getPropriedade(), responder.ask(decl.getPropriedade()));
                }

                if (decl.getValor().equalsIgnoreCase(respostas.getResposta(decl.getPropriedade()))) {
                    decl = obj.proxima();
                } else {
                    igual = false;
                }
            }

            if (decl == null) {
                animal = listaNomes[i];
            }
        }

        boolean acertei;

        if (animal != null) {
            acertei = responder.finalAnswer(animal);
        } else {
            acertei = responder.finalAnswer("nao sei");
        }

        if (acertei) {
            System.out.println("Oba! Acertei! - " + animal);
        } else {
            System.out.println("fuem! fuem! fuem!");
        }
    }
}

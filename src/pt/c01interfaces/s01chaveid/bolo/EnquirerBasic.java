package pt.c01interfaces.s01chaveid.bolo;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerBasic implements IEnquirer
{
    IObjetoConhecimento obj;
    IBaseConhecimento bc;
    private String animais[];
	private String perguntas[];
	private String respostas[];
	private int pontos[];
	
	public EnquirerBasic()
	{
        bc = new BaseConhecimento();
		animais = bc.listaNomes();
		pontos = new int[animais.length];
	}
	
	public String pergunta(String perg) {
		if(perguntas == null) {
			perguntas = new String[1];
			perguntas[0] = perg;
			respostas = new String[1];
			return null;
		}
		
		else {
			for (int i = 0; i < perguntas.length; i++) {
				if(perg.equalsIgnoreCase(perguntas[i])) return respostas[i];
			}

			String aux[] = new String[perguntas.length+1];
			String aux2[] = new String[respostas.length+1];
			for (int i = 0; i < perguntas.length; i++) {
				aux[i] = perguntas[i];
				aux2[i] = respostas[i];
			}
			aux[aux.length-1] = perg;
			perguntas = aux;
			respostas = aux2;
			return null;
		}
	}
	
	public void reset() {
		perguntas = null;
		respostas = null;
		for (int i = 0; i < animais.length; i++) {
			pontos[i] = 0;
		}
	}
	
	public void connect(IResponder responder) {
		int animalCerto=0;
		reset();
		for (int i = 0; i < animais.length; i++) {
			pontos[i] = ehAnimal(i,responder);
		}
		for (int i = 0; i < pontos.length; i++) {
			if(pontos[i] > pontos[animalCerto]) animalCerto = i;
		}
		System.out.print(animais[animalCerto]+"? ");
		System.out.println(responder.finalAnswer(animais[animalCerto]));
	}
	
	public int ehAnimal(int posicao, IResponder responder)
	{
		int pontos = 0;
		obj = bc.recuperaObjeto(animais[posicao]);
		IDeclaracao decl = obj.primeira();
		boolean acertouResposta = true;
		
		while (decl != null && acertouResposta == true)
		{
			String perg = decl.getPropriedade();
			String res = pergunta(perg);
			
			if(res == null) {
				respostas[respostas.length-1] = responder.ask(perg);
				acertouResposta = respostas[respostas.length-1].equalsIgnoreCase(decl.getValor());
			}
			else {
				acertouResposta = res.equalsIgnoreCase(decl.getValor());
			}
			
			decl = obj.proxima();
			pontos++;
		}
		
		if(acertouResposta == true) return pontos;
		else return 0;
	}
}

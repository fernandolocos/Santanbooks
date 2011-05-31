package pt.c01interfaces.s01chaveid.bolo;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

class AnimaisDaPergunta
{
	private int animais[];
	private int ultimo;
	
	public AnimaisDaPergunta(int IDanimal) {
		animais = new int[1];
		animais[0] = IDanimal;
		ultimo = 0;
	}
	
	public void addAnimal(int IDanimal) {
		int[] aux = new int[animais.length+1];
		for (int i = 0; i < animais.length; i++) {
			aux[i] = animais[i];
		}
		animais = aux;
		animais[animais.length-1] = IDanimal;
	}
	
	public int getAnimal() {
		return animais[ultimo++];
	}
	
	public void primeiro() {
		ultimo = 0;
	}
	
	public int getSize() {
		return animais.length;
	}
}

public class EnquirerAdvanced implements IEnquirer
{
	/*
	 * Objetivo principal: utilizar o minimo de vezes possivel a funcao ask
	 * Resultado: quase todos animais identificados com menos de 6 perguntas
	*/
    IObjetoConhecimento obj;
    IBaseConhecimento bc;
    private String animais[];
	private String perguntas[];
	private String respostas[];
	private int prioridadePergunta[];
	private boolean animalValido[];
	private boolean quemAcertou[];
	private AnimaisDaPergunta vetorPerguntas[]; // quais animais possuem tal pergunta
	
	public EnquirerAdvanced()
	{
        bc = new BaseConhecimento();
		animais = bc.listaNomes();
		animalValido = new boolean[animais.length];
		quemAcertou = new boolean[animais.length];
		for (int i = 0; i < animais.length; i++) {
			animalValido[i] = true;
		}
	}
	
	public int perguntaExiste(String perg) {
		if(perguntas != null)
		for (int i = 0; i < perguntas.length; i++) {
			if(perg.equalsIgnoreCase(perguntas[i])) return i;
		}
		return -1;
	}
	
	public void aumentaVetores() { // alocacao dinamica das perguntas,respostas,e outros
		if(perguntas == null) {	
			perguntas = new String[1];
			prioridadePergunta = new int[1];
			vetorPerguntas = new AnimaisDaPergunta[1];
			return;
		}
		String aux[] = new String[perguntas.length+1];
		int aux2[] = new int[prioridadePergunta.length+1];
		AnimaisDaPergunta[] aux3 = new AnimaisDaPergunta[vetorPerguntas.length+1];
		for (int i = 0; i < perguntas.length; i++) {
			aux[i] = perguntas[i];
			aux2[i] = prioridadePergunta[i];
			aux3[i] = vetorPerguntas[i];
		}
		perguntas = aux;
		prioridadePergunta = aux2;
		vetorPerguntas = aux3;
	}
	
	public void obtemPerguntas() { // guarda todas perguntas e quantas vezes aparecem
		IDeclaracao decl;

		for (int j = 0; j < animais.length; j++) {
			obj = bc.recuperaObjeto(animais[j]);
			decl = obj.primeira();
			while(decl != null) {
				String str = decl.getPropriedade();
				int i = perguntaExiste(str);
				if(i != -1) {
					prioridadePergunta[i]++; // aumenta prioridade da pergunta
					if(vetorPerguntas[i] == null) 
						vetorPerguntas[i] = new AnimaisDaPergunta(j);
					else vetorPerguntas[i].addAnimal(j);
				}
				else {
					aumentaVetores();
					perguntas[perguntas.length-1] = str;
					prioridadePergunta[perguntas.length-1] = 1;
					if(vetorPerguntas[perguntas.length-1] == null) 
						vetorPerguntas[perguntas.length-1] = new AnimaisDaPergunta(j);
					else vetorPerguntas[perguntas.length-1].addAnimal(j);
				}
				
				decl = obj.proxima();
			}
		}
	}
	
	public int quantosValidos() {
		int animaisValidos=0;
		for (int i = 0; i < animalValido.length; i++) {
			if(animalValido[i])
				animaisValidos++;
		}
		return animaisValidos;
	}

	public float valorPergunta(int i) {
		if(prioridadePergunta[i] == 0)
			return 999999;
		int animaisValidosComPergunta = 0;
		float valorEsperado = (float)quantosValidos() * 2/3 - 1/100000;
		//System.out.println(valorEsperado);
		/* supondo que para cada pergunta 1/3 dos animais respondam 'nao', 1/3 'sim' e 1/3 'nao sei'
		** para eliminar mais animais com cada pergunta 2/3 dos animais validos devem possui-la
		** essa funcao retorna a diferenca ate esse valor esperado. */
		for (int j = 0; j < vetorPerguntas[i].getSize(); j++) {
			if(animalValido[vetorPerguntas[i].getAnimal()]) {
				animaisValidosComPergunta++;
			}
		}
		vetorPerguntas[i].primeiro();
		if (valorEsperado > animaisValidosComPergunta)
			return valorEsperado - animaisValidosComPergunta;
		else
			return animaisValidosComPergunta - valorEsperado;
	}
	
	public boolean perguntaChave(int i) {
		String res = null,res2 = null;
		for (int j = 0; j < animalValido.length; j++) {
			if(animalValido[j]) {
				obj = bc.recuperaObjeto(animais[j]);
				IDeclaracao decl = obj.primeira();
				while(decl != null) {
					if(decl.getPropriedade().equalsIgnoreCase(perguntas[i])) {
						if(res == null) {
							res = decl.getValor();
						}
						else res2 = decl.getValor();
					}
					decl = obj.proxima();
				}
			}
		}
		if(res.equalsIgnoreCase(res2)) return false;
		else return true;
	}
	
	public int proximaPergunta(IResponder responder) { // perguntas genericas tem prioridade
		int proximaPergunta = 0;
		for (int i = 1; i < perguntas.length; i++) {
			if(valorPergunta(i) <= valorPergunta(proximaPergunta)) {
				proximaPergunta = i;
			}
		}
		
		if(quantosValidos() == 2) { // procura a pergunta que elimina 1 animal no caso de 2 animais
			if(perguntaChave(proximaPergunta)) {
				prioridadePergunta[proximaPergunta] = 0;
				return proximaPergunta;
			}
			else {
				prioridadePergunta[proximaPergunta] = 0;
				return proximaPergunta(responder);
			}
		}
		
		for (int j = 0; j < vetorPerguntas[proximaPergunta].getSize(); j++) {
			if(animalValido[vetorPerguntas[proximaPergunta].getAnimal()]) {
				if(quantosValidos() == 2)
					System.out.println("per:" + j);
				prioridadePergunta[proximaPergunta] = 0;
				vetorPerguntas[proximaPergunta].primeiro();
				System.out.println(quantosValidos());
				return proximaPergunta;
			}
		}
		if(respostaFinal() != -1) return respostaFinal() * -1;
		prioridadePergunta[proximaPergunta] = 0;
		vetorPerguntas[proximaPergunta].primeiro();
		return proximaPergunta(responder);
	}
	
	public String responde(String perg, int animal) {
		obj = bc.recuperaObjeto(animais[animal]);
		IDeclaracao decl = obj.primeira();
		while(!decl.getPropriedade().equalsIgnoreCase(perg))
			decl = obj.proxima();
		return decl.getValor();
	}
	
	public int respostaFinal() {
		int animaisValidos=0, posicao = -1;
		for (int i = 0; i < animalValido.length; i++) {
			if(animalValido[i]) {
				animaisValidos++;
				posicao = i;
			}
		}
		if(animaisValidos == 1)
			return posicao;
		return -1;
	}
	
	public boolean alguemAcertou() {
		for (int i = 0; i < animais.length; i++) {
			if(quemAcertou[i])
				return true;
		}
		return false;
	}
	
	public int obtemRespostas(IResponder responder) {
		respostas = new String[perguntas.length];
		for (int i = 0; i < respostas.length; i++) {
			for (int k = 0; k < animais.length; k++) {
				quemAcertou[k] = false;
			}
			int perg = proximaPergunta(responder);
			if(perg < 0) return perg * -1;
			respostas[perg] = responder.ask(perguntas[perg]);
			for (int j = 0; j < vetorPerguntas[perg].getSize(); j++) {
				int animal = vetorPerguntas[perg].getAnimal();
				if(!respostas[perg].equalsIgnoreCase(responde(perguntas[perg],animal))) {
					animalValido[animal] = false; // desvalida animais por causa de resposta errada
					if(respostaFinal() != -1) return respostaFinal();
				}
				else { //acertou a resposta
					quemAcertou[animal] = true;
					if(vetorPerguntas[perg].getSize() == 1) return animal;
				}
			}
			vetorPerguntas[perg].primeiro();
			// Se um animal acertar a resposta, elimina todos outros animais sem aquela pergunta
			if(alguemAcertou()) {
				for (int j = 0; j < animais.length; j++) {
					if(animalValido[j] && !quemAcertou[j])
						animalValido[j] = false;
				}
			}
		}
		return -103556;
	}
	
	public void reset() {
		perguntas = null;
		respostas = null;
		vetorPerguntas = null;
		prioridadePergunta = null;
		animalValido = new boolean[animais.length];
		quemAcertou = new boolean[animais.length];
		for (int i = 0; i < animais.length; i++) {
			quemAcertou[i] = false;
			animalValido[i] = true;
		}
	}
	
	public void connect(IResponder responder) {
		reset();
		obtemPerguntas();
		int animalCerto = obtemRespostas(responder);
		if(animalCerto != 103556) 
			System.out.println(animais[animalCerto]+"? "+responder.finalAnswer(animais[animalCerto]));
		else System.out.println("database invalida?");
	}
	
}

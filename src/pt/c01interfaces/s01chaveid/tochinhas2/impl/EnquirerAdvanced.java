package pt.c01interfaces.s01chaveid.tochinhas2.impl;

import java.util.Random;

/**
 * @author Alexandre Irmao Faltz - 108411
 * @author Davi Costa Clemente   - 083413
 *
 */

import java.util.Hashtable;

import pt.c01interfaces.s01chaveid.s01base.inter.*;
import pt.c01interfaces.s01chaveid.s01base.impl.*;

public final class EnquirerAdvanced implements IEnquirer {
	private IBaseConhecimento baseRox;
	private String[] animais, perguntas_filtradas, filtradas;
	private Hashtable<String,String> hs_respostas;

	public EnquirerAdvanced() {
		this.baseRox = new BaseConhecimento();
		this.animais = this.baseRox.listaNomes();
		this.hs_respostas = new Hashtable<String, String>();
		this.perguntas_filtradas = new String[3];
	}

	private void randomizator(IResponder responder){
		Random rand = new Random();
		int i, flag = 0;
		String resp, quest;

		while (flag == 0){
			i = Math.abs(rand.nextInt()) % this.animais.length;
			/* Com base num numero aleatorio dentre os index do vetor de animais, pegamos um para iniciar a filtragem */
			IObjetoConhecimento obj = this.baseRox.recuperaObjeto(this.animais[i]);
			IDeclaracao declaracao = obj.primeira();

			if(declaracao != null){
				quest = declaracao.getPropriedade();
				if(!this.hs_respostas.containsKey(quest)){
					/* Guarda a primeira pergunta no vetor */
					this.perguntas_filtradas[0] = quest;
					/* Faz a pergunta ao usuario e guarda sua resposta na hashtable */
					resp = responder.ask(this.perguntas_filtradas[0]);
					this.hs_respostas.put(this.perguntas_filtradas[0], resp);
				}
				declaracao = obj.proxima();
			}


			if(declaracao != null){

				quest = declaracao.getPropriedade();
				if(!this.hs_respostas.containsKey(quest)){
					/* Guarda a segunda pergunta no vetor */
					this.perguntas_filtradas[1] = quest;
					/* Faz a pergunta ao usuario e guarda sua resposta na hashtable */
					resp = responder.ask(this.perguntas_filtradas[1]);
					this.hs_respostas.put(this.perguntas_filtradas[1], resp);
				}
				declaracao = obj.proxima();
			}


			if(declaracao != null){
				quest = declaracao.getPropriedade();
				if(!this.hs_respostas.containsKey(quest)){
					/* Guarda a terceira pergunta no vetor */
					this.perguntas_filtradas[2] = quest;
					/* Faz a pergunta ao usuario e guarda sua resposta na hashtable */
					resp = responder.ask(this.perguntas_filtradas[2]);
					this.hs_respostas.put(this.perguntas_filtradas[2], resp);
				}
			}

			/* Sanity check 
			 * Verifica se pelo menos uma das perguntas eh 'sim' ou 'nao', para fins de otimizacao na filtragem
			 * Se as 3 perguntas forem 'nao sei', realiza um novo processo de aleatoriedade, tomando cuidado para nao repetir perguntas */
			for(int k = 0; k < 3; k++){
				if(!(this.hs_respostas.get(this.perguntas_filtradas[k])).equalsIgnoreCase("nao sei"))
					flag = 1;
			}
		}
	}

	private void filtrator() {
		this.filtradas = new String[this.animais.length];
		int aux = 0;

		proximoAnimal:
			for (String ani : this.animais) {
				IObjetoConhecimento obj = this.baseRox.recuperaObjeto(ani);
				IDeclaracao declaracao = obj.primeira();
				int counter = 0;

				do {
					/* Procura se a pergunta feita ja foi respondida pelo randomizator */
					if (this.hs_respostas.containsKey(declaracao.getPropriedade()))
						/* Verifica se a resposta do animal bate com a do randomizator dentro da hashtable */

						if (this.hs_respostas.get(declaracao.getPropriedade()).equalsIgnoreCase("nao sei"))
							/* Se encontrar uma resposta como 'nao sei' para uma pergunta feita pelo animal, pula imediatamente para o proximo animal */
							continue proximoAnimal;

						else
							if( this.hs_respostas.get(declaracao.getPropriedade()).equalsIgnoreCase(declaracao.getValor()))
								counter++;

					declaracao = obj.proxima();
					/* Enquanto as perguntas do animal em questao nao acabarem */	
				} while(declaracao != null);

				/* Se chegou ateh aqui, verifica se ha alguma pergunta com resposta 'nao sei' nas perguntas filtradas e incrementa counter para cada uma delas
				 * Isso eh valido pois se chegou ateh aqui, nenhuma pergunta com resposta 'nao sei' (no hash) foi feita, logo, cada uma delas eh considerada como correta */
				for (int x = 0; x < 3; x++){
					if (this.hs_respostas.get(this.perguntas_filtradas[x]).equalsIgnoreCase("nao sei"))
						counter++;
				}

				/* Se por acaso o animal teve 3 perguntas corretas, ele eh passivel de estar certo e eh adicionado ao vetor */
				if (counter == 3)
					filtradas[aux++] = ani;
			}
	}

	@Override
	public void connect(IResponder responder) {

		String resposta_ws, respostaFinal = "";
		int aux, tamanhoResposta = 0;

		/* Processo de randomizacao */
		randomizator(responder);
		
		filtrator();

		proximoAnimal:
			/* Busca que percorre todos animais do database, até encontrar o que procura */
			for (String a : this.filtradas) {
				if (a != null) {
					/* Carrega o objeto do database em obj e carrega a primeira pergunta dele dele */
					IObjetoConhecimento obj = this.baseRox.recuperaObjeto(a);
					IDeclaracao declaracao = obj.primeira();

					/* Verifica se a pergunta nao foi feita ainda na Hashtable
					 * Se ela nao estiver la, anota qual foi a resposta e a inclui na Hashtable */
					if (!this.hs_respostas.containsKey(declaracao.getPropriedade())) {
						resposta_ws = responder.ask(declaracao.getPropriedade());
						this.hs_respostas.put(declaracao.getPropriedade(), resposta_ws);
					}

					/* Senao, verifica qual foi a resposta encontrada na Hashtable e carrega ela */
					else {
						resposta_ws = this.hs_respostas.get(declaracao.getPropriedade());
					}

					/* Verifica se a resposta dada pelo usuario coincide com a resposta do database */
					if (declaracao.getValor().equalsIgnoreCase(resposta_ws)) {
						declaracao = obj.proxima();

						/* Se a primeira pergunta foi respondida corretamente, continua perguntando ate que
						 * as perguntas do animal acabem ou ate que alguma delas seja respondida errada */
						aux = 1;
						while (declaracao != null) {
							/* Verifica se a pergunta ja foi feita*/
							if (!this.hs_respostas.containsKey(declaracao.getPropriedade())) {
								resposta_ws = responder.ask(declaracao.getPropriedade());
								this.hs_respostas.put(declaracao.getPropriedade(), resposta_ws);
							}
							else {
								resposta_ws = this.hs_respostas.get(declaracao.getPropriedade());
							}

							/* Se a resposta do usuario nao bater com a do database do animal,
							 * significa que ele nao eh o correto e avanca-se no vetor de animais */
							if (!declaracao.getValor().equalsIgnoreCase(resposta_ws))
								continue proximoAnimal;

							declaracao = obj.proxima();
							aux++;
						}

						/* Se chegou ateh aqui, significa que acabaram-se as perguntas sobre o animal
						 * e todas foram respondidas corretamente, guardando seu nome e o numero de perguntas
						 * Ao fim, sobrara o animal cujo as perguntas foram respondidas corretamente e com o maior numero de respostas */
						if (aux > tamanhoResposta) {
							tamanhoResposta = aux;
							respostaFinal = a;
						}
					}
				}
			}
		responder.finalAnswer(respostaFinal);
	}
}

package pt.c01interfaces.s01chaveid.tochinhas2.impl;

/**
 * @author Alexandre Irmao Faltz - 108411 
 * @author Davi Costa Clemente   - 083413
 *
 */

import pt.c01interfaces.s01chaveid.s01base.impl.*;
import pt.c01interfaces.s01chaveid.s01base.inter.*;
import java.util.*;

public final class EnquirerBasic implements IEnquirer {

	private IBaseConhecimento baseRox;
	private String[] animais;
	private Hashtable<String,String> respostas;

	public EnquirerBasic() {
		this.baseRox = new BaseConhecimento();
		this.animais = this.baseRox.listaNomes();
		this.respostas = new Hashtable<String, String>();
	}

	@Override
	public void connect(IResponder responder) {

		proximoAnimal:
			/* Busca que percorre todos animais do database, até encontrar o que procura */
			for (String a : this.animais) {

				/* Carrega o objeto do database em obj e carrega a primeira pergunta dele dele */
				IObjetoConhecimento obj = this.baseRox.recuperaObjeto(a);
				IDeclaracao declaracao = obj.primeira();
				String resposta;

				/* Verifica se a pergunta nao foi feita ainda na Hashtable
				 * Se ela nao estiver la, anota qual foi a resposta e a inclui na Hashtable */
				if (!respostas.containsKey(declaracao.getPropriedade())) {
					resposta = responder.ask(declaracao.getPropriedade());
					this.respostas.put(declaracao.getPropriedade(), resposta);
				}
				/* Senao, verifica qual foi a resposta encontrada na Hashtable e carrega ela */
				else {
					resposta = this.respostas.get(declaracao.getPropriedade());
				}

				/* Verifica se a resposta dada pelo usuario coincide com a resposta do database */
				if (declaracao.getValor().equalsIgnoreCase(resposta)) {
					declaracao = obj.proxima();					

					/* Se a primeira pergunta foi respondida corretamente, continua perguntando ate que
					 * as perguntas do animal acabem ou ate que alguma delas seja respondida errada */
					while (declaracao != null) {
						/* Verifica se a pergunta ja foi feita*/
						if (!respostas.containsKey(declaracao.getPropriedade())) {
							resposta = responder.ask(declaracao.getPropriedade());
							this.respostas.put(declaracao.getPropriedade(), resposta);
						}
						else {
							resposta = this.respostas.get(declaracao.getPropriedade());
						}
						/* Se a resposta do usuario nao bater com a do database do animal,
						 * significa que ele nao eh o correto e avanca-se no vetor de animais */
						if (!declaracao.getValor().equalsIgnoreCase(resposta))
							continue proximoAnimal;

						declaracao = obj.proxima();
					}
					/* Se chegou ateh aqui, significa que acabaram-se as perguntas sobre o animal
					 * e todas foram respondidas corretamente, retornando portanto o nome do animal */
					responder.finalAnswer(a);
					break;
				}
			}
	}
}

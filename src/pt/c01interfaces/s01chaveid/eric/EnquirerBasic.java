/*
Eric O. Lopes RA:076624
Implementação do enquirerBasic
*/
package pt.c01interfaces.s01chaveid.eric;

import java.util.HashMap;
import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerBasic implements IEnquirer
{
    private IBaseConhecimento base = new BaseConhecimento();
    private final String[] NOMES = base.listaNomes();
    private final int TAMANHO = NOMES.length;
    private final int EXPECTATIVA_PERGUNTAS_POR_ANIMAL = 2;
    private String animalEscolhido = "nao conheco";
    private int quantidadeAcertada = -1;
    private HashMap<String, String> perguntasFeitas = 
    	new HashMap<String, String>(EXPECTATIVA_PERGUNTAS_POR_ANIMAL*TAMANHO, (float) 0.75);
	
	@Override
	public void connect(IResponder responder){
		IObjetoConhecimento animal;
	    IDeclaracao pergunta;
	    int acertos = 0;
	    for(String nomeAnimal : NOMES){
			try{
				animal = base.recuperaObjeto(nomeAnimal);
				for(pergunta = animal.primeira(); pergunta != null; pergunta = animal.proxima()){
					if(verifica(pergunta, responder))
						acertos++;
					else{
						acertos--;
						break;
					}
				}
				if(acertos > quantidadeAcertada){
					setQuantidadeAcertada(acertos);
					setAnimalEscolhido(nomeAnimal);
				}
			}
			catch(Exception err){
				System.out.println("animal: "+nomeAnimal+" está com erro!!!");
			}
			finally{
				acertos = 0;
			}
		}
	    exibeResultado(responder);

	}
	private boolean verifica(IDeclaracao decPergunta, IResponder canal){
		
		String perguntaAnimal = decPergunta.getPropriedade().toLowerCase();
		String respostaAnimal = decPergunta.getValor().toLowerCase();
		String respostaBanco = perguntasFeitas.get(perguntaAnimal);
		boolean retorno = false;
		
		if(respostaBanco == null){
			respostaBanco = canal.ask(perguntaAnimal);
			perguntasFeitas.put(perguntaAnimal, respostaBanco);
		}
		
		if(!respostaBanco.equalsIgnoreCase(respostaAnimal)){
			retorno = false;
		}else
			if(respostaBanco.equalsIgnoreCase(respostaAnimal)){
				retorno = true;
			}
			
		return retorno;
	}
	private void setAnimalEscolhido(String animalEscolhido) {
		this.animalEscolhido = animalEscolhido;
	}
	private void setQuantidadeAcertada(int quantidadeAcertada) {
		this.quantidadeAcertada = quantidadeAcertada;
	}
	private void exibeResultado(IResponder responder){
	    boolean acertei = false;
		acertei = responder.finalAnswer(animalEscolhido);
		System.out.println(" >"+animalEscolhido);
		if (acertei)
			System.out.println("=) Oba! Acertei!");
		else
			System.out.println("=( Que chato! errei esse, mas o próximo eu acerto!");
	}

}

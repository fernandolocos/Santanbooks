/*
Eric O. Lopes RA:076624
Implementação do enquirerAdvanced
*/
package pt.c01interfaces.s01chaveid.eric;

import java.util.HashMap;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerAdvanced implements IEnquirer
{
/*
 * Definição das constantes para utilização interna
 */
    private IBaseConhecimento base = new BaseConhecimento();
    private final String[] NOMES = base.listaNomes();
    private final int TAMANHO = NOMES.length;
/*
 * EXPECTATIVA_PERGUNTAS_POR_ANIMAL leva em conta que a grande maioria dos animais serão exluidos com apenas uma
 * ou duas perguntas, essa constante ajuda a setar o tamanho inicial da hash e consequentemente otimizar a utilização
 * do espaço de memória
 */
    private final int EXPECTATIVA_PERGUNTAS_POR_ANIMAL = 2;
    
/*
 * variaveis auxiliares para retorno
 */
    private String animalEscolhido = "nao conheco";
    private int quantidadeAcertada = -1;

/*
 * usando HashMap por ser a estrutura mais otimizada para alocação e retorno de valores, a outra opção era HashTable, 
 * mas ela é melhor em aplicações com muitas threads, como essa aplicação não terá thread, HashMap é mais aconselhável
 * (JAVA o guia essencial, O'\reilly, 5 ed. , pag:726)
 */
    private HashMap<String, String> perguntasFeitas = 
    	new HashMap<String, String>(EXPECTATIVA_PERGUNTAS_POR_ANIMAL*TAMANHO, (float) 0.75);
	
	private boolean verifica(IDeclaracao decPergunta, IResponder canal){
		/*
		 * essa função verifica se a pergunta digitada existe na hash, se sim compara com a pergunta
		 * e automaticamente dá a resposta, sem perguntar nada para o usuário
		 * senão, ele pergunta ao usuário e já coloca a resposta no hash
		 * o lowercase é para eliminar repetições de frases por diferença apenas de maiusculas e minusculas
		 */
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
	
	@Override
	public void connect(IResponder responder){
		
		/*base declarada aqui para poupar a memória, antes de ser feita uma pergunta
		 * e atualizar o banco de animais a cada novo connect, pois pode ser que o usuário tenha incluido um novo
		 * arquivo txt entre as execuções de connect
		 */
		IObjetoConhecimento animal;
	    IDeclaracao pergunta;
	    int acertos = 0;
	    
	    /*
	     * para cada nome da lista pergunte ao Hash (com verifica) se a resposta está correta, se sim aumenta o
	     * acumulador, senão o decrementa, isso garante que no final, o animal mais provável será o escolhido
	     * caso realmente não saiba que animal seja
	     */
	    for(String nomeAnimal : NOMES){
			try{
				animal = base.recuperaObjeto(nomeAnimal);
				for(pergunta = animal.primeira(); pergunta != null; pergunta = animal.proxima()){
					if(verifica(pergunta, responder))
						acertos++;
					else{
						acertos--;
						/*se estiver na situação em que o responder pode errar
						 * basta comentar o break abaixo, assim o enquirer vai percorrer
						 * todas as perguntas de todos os animais e a resposta
						 * será o animal que estatisticamente tiver mais perguntas certas que
						 * perguntas erradas, inicialmente foi setado para dar break pois isso
						 * economiza muito processamento e como especificado, o responder não
						 * mente!
						 */
						break;
					}
				}

				/*
				 * se este for o animal com mais acertos seguidos, então ele é o animal escolhido
				 */
				if(acertos > quantidadeAcertada){
					setQuantidadeAcertada(acertos);
					setAnimalEscolhido(nomeAnimal);
				}
			}
			/*
			 * pula todos os arquivos com erro
			 */
			catch(Exception err){
				System.out.println("animal: "+nomeAnimal+" está com erro!!!");
			}
			finally{
				//importante para evitar que um animal "subconjunto" do procurado seja dado como certo
				acertos = 0;
			}
		}
	    /*
	     * verifica se a saída está correta
	     */
	    exibeResultado(responder);

	}
/*
 * getters e setters para as variáveis básicas
 */
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

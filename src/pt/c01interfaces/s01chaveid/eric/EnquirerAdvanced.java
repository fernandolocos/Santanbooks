/*
Eric O. Lopes RA:076624
Implementa��o do enquirerAdvanced
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
 * Defini��o das constantes para utiliza��o interna
 */
    private IBaseConhecimento base = new BaseConhecimento();
    private final String[] NOMES = base.listaNomes();
    private final int TAMANHO = NOMES.length;
/*
 * EXPECTATIVA_PERGUNTAS_POR_ANIMAL leva em conta que a grande maioria dos animais ser�o exluidos com apenas uma
 * ou duas perguntas, essa constante ajuda a setar o tamanho inicial da hash e consequentemente otimizar a utiliza��o
 * do espa�o de mem�ria
 */
    private final int EXPECTATIVA_PERGUNTAS_POR_ANIMAL = 2;
    
/*
 * variaveis auxiliares para retorno
 */
    private String animalEscolhido = "nao conheco";
    private int quantidadeAcertada = -1;

/*
 * usando HashMap por ser a estrutura mais otimizada para aloca��o e retorno de valores, a outra op��o era HashTable, 
 * mas ela � melhor em aplica��es com muitas threads, como essa aplica��o n�o ter� thread, HashMap � mais aconselh�vel
 * (JAVA o guia essencial, O'\reilly, 5 ed. , pag:726)
 */
    private HashMap<String, String> perguntasFeitas = 
    	new HashMap<String, String>(EXPECTATIVA_PERGUNTAS_POR_ANIMAL*TAMANHO, (float) 0.75);
	
	private boolean verifica(IDeclaracao decPergunta, IResponder canal){
		/*
		 * essa fun��o verifica se a pergunta digitada existe na hash, se sim compara com a pergunta
		 * e automaticamente d� a resposta, sem perguntar nada para o usu�rio
		 * sen�o, ele pergunta ao usu�rio e j� coloca a resposta no hash
		 * o lowercase � para eliminar repeti��es de frases por diferen�a apenas de maiusculas e minusculas
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
		
		/*base declarada aqui para poupar a mem�ria, antes de ser feita uma pergunta
		 * e atualizar o banco de animais a cada novo connect, pois pode ser que o usu�rio tenha incluido um novo
		 * arquivo txt entre as execu��es de connect
		 */
		IObjetoConhecimento animal;
	    IDeclaracao pergunta;
	    int acertos = 0;
	    
	    /*
	     * para cada nome da lista pergunte ao Hash (com verifica) se a resposta est� correta, se sim aumenta o
	     * acumulador, sen�o o decrementa, isso garante que no final, o animal mais prov�vel ser� o escolhido
	     * caso realmente n�o saiba que animal seja
	     */
	    for(String nomeAnimal : NOMES){
			try{
				animal = base.recuperaObjeto(nomeAnimal);
				for(pergunta = animal.primeira(); pergunta != null; pergunta = animal.proxima()){
					if(verifica(pergunta, responder))
						acertos++;
					else{
						acertos--;
						/*se estiver na situa��o em que o responder pode errar
						 * basta comentar o break abaixo, assim o enquirer vai percorrer
						 * todas as perguntas de todos os animais e a resposta
						 * ser� o animal que estatisticamente tiver mais perguntas certas que
						 * perguntas erradas, inicialmente foi setado para dar break pois isso
						 * economiza muito processamento e como especificado, o responder n�o
						 * mente!
						 */
						break;
					}
				}

				/*
				 * se este for o animal com mais acertos seguidos, ent�o ele � o animal escolhido
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
				System.out.println("animal: "+nomeAnimal+" est� com erro!!!");
			}
			finally{
				//importante para evitar que um animal "subconjunto" do procurado seja dado como certo
				acertos = 0;
			}
		}
	    /*
	     * verifica se a sa�da est� correta
	     */
	    exibeResultado(responder);

	}
/*
 * getters e setters para as vari�veis b�sicas
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
			System.out.println("=( Que chato! errei esse, mas o pr�ximo eu acerto!");
	}

}

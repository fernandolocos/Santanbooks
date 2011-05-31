package pt.c01interfaces.s01chaveid.debolacha;

import java.util.Set;
import java.util.Hashtable;

import pt.c01interfaces.s01chaveid.s01base.impl.*;
import pt.c01interfaces.s01chaveid.s01base.inter.*;

public class EnquirerAdvanced implements IEnquirer{

	private String[] animal;
	private Hashtable<String, Lista[]> pergunta;
	private static final int SIM = 0, NAO = 1, NS = 2;
	
	/*Constroi um hash de perguntas e um hash de animais*/
	public EnquirerAdvanced(){
    	/*Carrega a base de dados*/
		BaseConhecimento base = new BaseConhecimento();
        animal = base.listaNomes();
        /*Variaveis usadas na construcao dos hashes*/
        IObjetoConhecimento obj;
        IDeclaracao decl;
        Lista todos;
        
        
        /*Cria os hashes*/
        pergunta = new Hashtable<String, Lista[]>(4*animal.length);
        
        /*Carrega os hashes com os objetos de conhecimento*/
        for(int i = 0; i < animal.length; i++){
 
        	/*Adiciona as propriedades do animal ao hash de perguntas*/
        	obj = base.recuperaObjeto(animal[i]);
        	decl = obj.primeira();
        	while(decl != null){
        		
        		/*Caso a propriedade ja exista no hash pergunta,
        		 *a lista apropriada recebe um ponteiro para o animal*/
    			if(pergunta.containsKey(decl.getPropriedade()))
    				pergunta.get(decl.getPropriedade())[index(decl.getValor())].add(animal[i]);
    			
    			/*Se a propriedade nao existir, eh criado um elemento no hash*/
    			else{
    				/*Instancia um objeto Lista[3] com seus elementos instanciados*/
    				Lista[] aux;
    				aux = new Lista[3];
    				for(int j = 0; j < aux.length; j++)
    					aux[j] = new Lista();
    				/*Insere o animal e o objeto no hash*/
    				pergunta.put(decl.getPropriedade(), aux);
    				pergunta.get(decl.getPropriedade())[index(decl.getValor())].add(animal[i]);
    			}
 
    			/*Vai para a proxima propriedade*/
    			decl = obj.proxima();
    		}
       }
        
        /*Caso um animal nao possua uma pergunta, ele sera 
         *apontado pela pergunta atraves da lista nao_sei*/
        todos = new Lista(animal);
        
        /*Cria um vetor com as chaves do hash de perguntas*/
        Set<String> p = pergunta.keySet();
        String[] pergs = p.toArray(new String[0]);
        
        /*Confere os animais que nao possuem resposta pra cada pergunta e colocam-no na lista "nao sei"*/
		for(int i = 0; i < pergs.length; i++)
			pergunta.get(pergs[i])[NS] = pergunta.get(pergs[i])[SIM].uniao(pergunta.get(pergs[i])[NAO]).complementar(todos);
	}
	
	/*Retorna o indice correspondente a um valor*/
	public static int index(String valor){
		int i;
		if(valor.equalsIgnoreCase("sim"))
			i = SIM;
		else if(valor.equalsIgnoreCase("nao"))
			i = NAO;
		else
			i = NS;
		
		return i;
	}
	
	
	/*Faz as perguntas ao responder e descobre o animal ao qual as perguntas se referem*/
	public void connect(IResponder responder){
		Lista solucao = new Lista(animal);
		boolean acertei = false;
		String resp;
		
		/*Cria um vetor com as chaves do hash de perguntas*/
        Set<String> p = pergunta.keySet();
        String[] perg = p.toArray(new String[0]);
        
        /*Faz uma pergunta ao responder e armazena uma inteseccao dos animais que ainda nao 
         *foram descartados com os que possuem valor igual ao retornado pelo metodo ask()*/
        for(int i = 0; i < perg.length; i++){
        	resp = responder.ask(perg[i]);
        	solucao = solucao.intersec(pergunta.get(perg[i])[index(resp)].uniao(pergunta.get(perg[i])[NS]));
        }

		/*Caso a base de dados seja correta, havera um unico animal que satifaz todas as propriedades*/
		if(solucao.size() == 1)
			acertei = responder.finalAnswer(solucao.get(0));
		
		/*Se alguma pergunta for respondida erroneamente,
		 *pode ser que o animal nao exista na base de dados*/
		else if(solucao.isEmpty())
			acertei = responder.finalAnswer("nenhum");
		
		/*Caso as propriedades de um animal estejam contidas nas propriedades de outros animais, todos
		 *aparecerao na Lita solucao. Se algum animal "contido" em outro tivesse sido o alvo da entre-
		 *vista, os animais "que o contem" seriam descartados quando o responder retornar "nao sei" ao
		 *inves de seu valor. Logo, o animal alvo eh o que todos os outros (ou que possui o maior numero
		 *de perguntas).*/
		else{
			int[] nPerguntas = new int[solucao.size()];
			IBaseConhecimento base = new BaseConhecimento();
			IObjetoConhecimento obj;
			IDeclaracao decl;
			
			/*Conta as pergutnas de cada animal*/
			for(int i = 0; i < solucao.size(); i++){
				obj = base.recuperaObjeto(solucao.get(i));
				decl = obj.primeira();
				nPerguntas[i] = 0;
				/*Conta as perguntas do animal e armazena num vetor*/
				while(decl != null){
					nPerguntas[i]++;
					decl = obj.proxima();
				}
			}
			
			/*Encontra o animal com mais perguntas*/
			int iMax = 0, nMax = 0;
			for(int i = 0; i < solucao.size(); i++)
				if(nPerguntas[i] > nMax){
					nMax = nPerguntas[i];
					iMax = i;
				}
			
			/*Confere se o animal eh o alvo da entrevista*/
			acertei = responder.finalAnswer(solucao.get(iMax));
		}
		

		if(acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");
	}
}
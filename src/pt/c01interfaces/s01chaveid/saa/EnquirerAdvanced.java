package pt.c01interfaces.s01chaveid.saa;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerAdvanced implements IEnquirer 
{
	private IBaseConhecimento bc = new BaseConhecimento();
	private String[] banco_animal;
	private IObjetoConhecimento obj;
	private Vector<String> banco_dados;
	private boolean candidato[];
	
	public EnquirerAdvanced()
	{
		// Cria uma lista com o nome dos animais no banco de dados
		banco_animal = bc.listaNomes();
		// Cria o array candidato[] e atribui todos como true
		candidato = new boolean[banco_animal.length];
		for(int i = 0; i < banco_animal.length; i++){
			candidato[i] = true;
		}
		// Cria o banco_dados
		banco_dados = new Vector<String>();
		for(int i = 0; i < banco_animal.length; i++){
			// Recupera um animal
			obj =  bc.recuperaObjeto(banco_animal[i]);
			// Recupera a primira proriedade
			IDeclaracao dec = obj.primeira();
			while(dec != null){
				// Verifica se a propriedade já foi cadastrada
				int aux = banco_dados.indexOf(dec.getPropriedade());
				// Em caso positivo
				if(aux != -1){
					// Atualiza valor
					banco_dados.setElementAt(dec.getValor(),aux +  i + 1);
				}
				// Em caso negativo
				else{
					// Adiciona nova propriedade
					banco_dados.add(dec.getPropriedade());
					// Adiciona novos valores
					for(int j = 0; j < banco_animal.length; j++){
						String retorno = (j == i)? dec.getValor() : "nao sei";
						banco_dados.add(retorno);
					}
				}
				// Passa a próxima propriedade
				dec = obj.proxima();
			}
		}
	}
	public void connect(IResponder responder) 
	{
		// Auxiliar para guardar a respostar de uma pergunta
		String resposta;
		// Auxiliar para o tamanho do vetor de dados
		int n = banco_dados.size();
		// Auxiliares para controle de possíveis animais
		int min = 0, max = banco_animal.length - 1;
		// Auxiliares para controle do vetor
		int i = 0, j = 0;
		while(min <= max && i < n){
			// Grava a resposta da pergunta
			resposta = responder.ask(banco_dados.get(i));
			for(j = min; j <= max; j++){
				// Comparacao da resposta com o banco de dados do animal j
				if(!resposta.equalsIgnoreCase(banco_dados.get(i + j + 1)))
					// Se a resposta for diferente descartamos o animal
					candidato[j] = false;
			}
			// Eliminamos os animais descartados que estao no inicio do vetor de candidatos
			while(min <= max && !candidato[min])
				min++;
			// Eliminamos os animais descartados que estao no fim do vetor de candidatos
			while(max >= min && !candidato[max])
				max--;
			// Atualizamos a pergunta
			i = i + banco_animal.length + 1;
		}
		// Resposta padrao
		resposta = "nenhum";
		// Testamos se ainda existe algum animal
		if(candidato[min])
			// Se existe esse eh o animal selecionado
			resposta = banco_animal[min];
		// Imprime animal selecionado
		//System.out.println(resposta);
		// Retorna animal selecionado
		responder.finalAnswer(resposta);		
	}
}
                                                   
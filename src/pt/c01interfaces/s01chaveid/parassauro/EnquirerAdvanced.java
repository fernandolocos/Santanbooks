package pt.c01interfaces.s01chaveid.parassauro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import anima.annotation.Component;
import anima.component.base.ComponentBase;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c03ensaios.optimizers.IEnquirerSupports;

/**
 * Perguntador avançado.
 * A idéia é montar um vetor de {@link Pergunta}, de tal forma que as perguntas
 * mais perguntadas fiquem com prioridade, a fim de tentar chegar a uma
 * conclusão mais rápido.
 * Os animais que tiverem respostas diferentes do {@link IResponder} serão
 * eliminados, até que reste um.
 */
@Component(id = "<http://purl.org/dcc/pt.c01interfaces.s01chaveid.parassauro.EnquirerAdvanced>",
        provides = {"<http://purl.org/dcc/pt.c03ensaios.optimizers.IEnquirerSupports>"})
public class EnquirerAdvanced extends ComponentBase implements IEnquirer,IEnquirerSupports{
	
	/**
	 * Recebe um {@link IResponder} e descobre o animal através de um inquérito.
	 * 
	 * @param responder O respondedor.
	 */
	@Override
	public void connect(IResponder responder){
		
		IBaseConhecimento base=new BaseConhecimento();
		String[] nomesAnimais=base.listaNomes();
		ArrayList<Pergunta> perguntas=new ArrayList<Pergunta>();
		HashSet<String> bichos=new HashSet<String>();
		
		IObjetoConhecimento animal;
		IDeclaracao declaracao;
		boolean cria;
		
		// para cada animal
		for(int x=0;x<nomesAnimais.length;x++){
			// o adiciona na lista de bixos
			bichos.add(nomesAnimais[x]);
			animal=base.recuperaObjeto(nomesAnimais[x]);
			
			// e para cada declaração dele
			declaracao=animal.primeira();
			do{
				// insere na pergunta se já existir
				cria=true;
				for(int y=0;y<perguntas.size();y++)
					if(perguntas.get(y).insertDeclaracao(nomesAnimais[x],declaracao)){
						cria=false;
						break;
					}
				// ou cria a pergunta e insere nela
				if(cria){
					Pergunta pergunta=new Pergunta(declaracao.getPropriedade());
					pergunta.insertDeclaracao(nomesAnimais[x],declaracao);
					perguntas.add(pergunta);
				}
			}
			while((declaracao=animal.proxima())!=null);
		}
		
		// ordenar pra começar pelas mais frequentes.
		// inquérito
		String resposta;
		while(bichos.size()>1 && perguntas.size()>0){
			Collections.sort(perguntas);
			if(perguntas.get(0).size()==0)
				break;
			resposta=responder.ask(perguntas.get(0).getEnunciado());
			desclassifica(bichos,perguntas.get(0),resposta,perguntas);
			perguntas.remove(0);
		}
		
		if(bichos.size()==1)
			responder.finalAnswer(bichos.iterator().next());
		else
			responder.finalAnswer("nao sei");
	}
	
	/**
	 * Desclassifica animais com respostas diferentes do {@link IResponder}.
	 * 
	 * @param bichos Lista de bichos.
	 * @param question {@link Pergunta} de onde sabemos quem excluir.
	 * @param resposta Resposta do {@link IResponder}.
	 * @param perguntas As perguntas.
	 */
	private void desclassifica(HashSet<String> bichos,Pergunta question,String resposta,ArrayList<Pergunta> perguntas){
		if(resposta.equals("nao sei")){
			remove(bichos,question.getSim(),perguntas);
			remove(bichos,question.getNao(),perguntas);
		}
		else if(resposta.equals("nao"))
			removeMenos(bichos,question.getNao(),perguntas);
		else
			removeMenos(bichos,question.getSim(),perguntas);
	}
	
	/**
	 * Remove da lista de bichos os animais da lista resp.
	 * 
	 * @param bichos Lista de bichos.
	 * @param resp animais a serem remividos.
	 * @param perguntas As perguntas. 
	 */
	private void remove(HashSet<String> bichos,HashSet<String> resp,ArrayList<Pergunta> perguntas){
		String[] aRemover=resp.toArray(new String[resp.size()]);
		for(int x=0;x<aRemover.length;x++){
			bichos.remove(aRemover[x]);
			for(Pergunta per:perguntas)
				per.removeAnimal(aRemover[x]);
		}
	}
	
	/**
	 * Remove todos os animais de bichos que não estejam em aManter.
	 * 
	 * @param bichos Lista de bichos.
	 * @param aManter Animais que serão mantidos.
	 * @param perguntas As perguntas.
	 */
	private void removeMenos(HashSet<String> bichos,HashSet<String> aManter,ArrayList<Pergunta> perguntas){
		String[] lista=bichos.toArray(new String[bichos.size()]);
		for(int x=0;x<lista.length;x++)
			if(!aManter.contains(lista[x])){
				bichos.remove(lista[x]);
				for(Pergunta per:perguntas)
					per.removeAnimal(lista[x]);
			}
	}
}
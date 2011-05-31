package pt.c01interfaces.s01chaveid.proj01;

import java.util.Vector;
import pt.c01interfaces.s01chaveid.s01base.inter.*;
import pt.c01interfaces.s01chaveid.s01base.impl.*;

public class EnquirerAdvanced implements IEnquirer
{	
	private Vector<IDeclaracao> perguntasFeitas;
	private IObjetoConhecimento obj[];
    private String nomes[];
    
    private class Chute
    {
    	private String nome;
    	private int nPerguntasFeitas;
    	
    	public Chute()
    	{
    		this("nao conheco");
    	}
    	
    	public Chute(String nome)
    	{
    		this.setNome(nome);
    		this.setNPerguntasFeitas(0);
    	}
    	
    	public String getNome()
    	{
    		return this.nome;
    	}
    	
    	public void setNome(String nome)
    	{
    		this.nome = nome;
    	}
    	
    	public int getNPerguntasFeitas()
    	{
    		return this.nPerguntasFeitas;
    	}
    	
    	public void setNPerguntasFeitas(int n)
    	{
    		this.nPerguntasFeitas = n;
    	}
    }
    
    	
	public EnquirerAdvanced()
	{
		IBaseConhecimento bc = new BaseConhecimento();
		
        nomes = bc.listaNomes();  // Recupera os nomes dos animais.
        obj = new IObjetoConhecimento[nomes.length]; // Inicializa o array de animais.
        
		for (int i = 0; i < nomes.length; i++)
			obj[i] = bc.recuperaObjeto(nomes[i]); // Preenche o array de animais.
		
		perguntasFeitas = new Vector<IDeclaracao>();
	}
	
	private int jaFoiFeita(String pergunta)
	{
		boolean jafoi;
		int posicao;

		for (jafoi = false, posicao = 0; posicao < perguntasFeitas.size() && !jafoi; posicao++)
			if (perguntasFeitas.get(posicao).getPropriedade().equalsIgnoreCase(pergunta)) // Se achou uma pergunta igual
				jafoi = true;
		
		return jafoi ? posicao : -1; // Se não achou, retorna -1; se achou, retorna a posição
	}
	
	private boolean valorCoincide(IDeclaracao decl, IResponder responder)
	{
		int pos;
		boolean acertou;
		String valorAnimal = decl.getValor();
		
		// Se a pergunta não foi feita
		if ((pos = jaFoiFeita(decl.getPropriedade())) == -1)
		{
			String resposta = responder.ask(decl.getPropriedade());
			acertou = resposta.equalsIgnoreCase(valorAnimal);
			
			IDeclaracao aSerAdicionada = new Declaracao(decl.getPropriedade(), resposta);
			perguntasFeitas.add(aSerAdicionada); // Adiciono a pergunta na lista
		}
		else // Se já foi feita, lembro a resposta
			acertou = valorAnimal.equalsIgnoreCase(perguntasFeitas.get(pos-1).getValor());
		
		return acertou;
	}
	
	@Override
	public void connect(IResponder responder)
	{
		IObjetoConhecimento atual = null; // Início do primeiro animal
		Chute chute = new Chute();
			
		// Percorro os animais
		for(int i = 0; i < nomes.length; i++)
		{
			atual = obj[i];
			IDeclaracao decl = atual.primeira(); // Primeira propriedade do animal.
			boolean acertou = false;
			
			int n = 0;
			// Percorro as propriedades do animal
			loopDePerguntas: while (decl != null)
			{
				acertou = valorCoincide(decl, responder);
				
				if (!acertou)
					break loopDePerguntas; // Não é esse animal

				// Vejo a próxima pergunta
				decl = atual.proxima();
				
				n++; // Acumulo quantas perguntas fiz
			}
			
			// Se percorri todas as propriedades do animal e obtive acerto em todas as perguntas
			if (decl == null)
				// Se eu fiz mais perguntas do que o candidato original, esse deve ser o novo candidato
				if (n > chute.getNPerguntasFeitas()) 
				{
					chute.setNPerguntasFeitas(n);
					chute.setNome(nomes[i]); // O animal é um candidato
				}
		}

		// Chuto um animal e imprimo a resposta
		imprimeResposta(responder.finalAnswer(chute.getNome()));
	}
	
	private void imprimeResposta(boolean acerto)
	{
		System.out.println(acerto? "Acerto!" : "Fuem fuem fuem");
	}
}

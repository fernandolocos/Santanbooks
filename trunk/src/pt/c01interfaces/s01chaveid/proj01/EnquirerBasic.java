package pt.c01interfaces.s01chaveid.proj01;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerBasic implements IEnquirer
{
	private String nomesDosAnimais[];
	private Vector<String> perguntasFeitas;
	private Vector<String> respostasDadas;
	
    IObjetoConhecimento obj[];
	
	public EnquirerBasic()
	{
        IBaseConhecimento bc = new BaseConhecimento();
        
        perguntasFeitas = new Vector<String>();
        respostasDadas = new Vector<String>();

        // Recupera os nomes de todos os animais.
        // Isso ser� usado para se recuperar os objetos de conhecimento.
        nomesDosAnimais = bc.listaNomes(); 
        obj = new IObjetoConhecimento[nomesDosAnimais.length];
        
        // Coloca todos os objetos de conhecimento no vetor obj.
		for (int i = 0; i < nomesDosAnimais.length; i++)
			obj[i] = bc.recuperaObjeto(nomesDosAnimais[i]);
	}
	
	@Override
	public void connect(IResponder responder)
	{
		String chute = "nao conheco";
		int nPerguntas = 0;
		IObjetoConhecimento atual = obj[0];
		boolean acerto = false;
		int i = 0;
		
		// Percorro os animais
		while (i < nomesDosAnimais.length)
		{
			int n = 0;
			atual = obj[i];
			IDeclaracao decl = atual.primeira();
			boolean acertou = false;
			
			// Percorro as propriedades do animal
			while (decl != null)
			{
				// Verificando se a pergunta j� foi feita
				boolean jafoi = false;
				
				int j;
				for (j = 0; j < perguntasFeitas.size() && !jafoi; j++)
					if (perguntasFeitas.get(j).equalsIgnoreCase(decl.getPropriedade()))
						jafoi = true;
				
				String respostaValor = decl.getValor(); // Resposta do animal
				
				// Se n�o foi feita, pergunto
				if (!jafoi)
				{
					acertou = responder.ask(decl.getPropriedade()).equalsIgnoreCase(respostaValor);
					perguntasFeitas.add(decl.getPropriedade());
					respostasDadas.add(responder.ask(decl.getPropriedade()));
					
					// Se as respostas n�o batem, n�o � esse animal
					if (!acertou)
					{
						n = 0;
						break;
					}
				}
				else // Se foi feita, vejo a resposta
				{
					String respostaAnterior = respostasDadas.get(j-1); // Resposta j� obtida
					acertou = respostaValor.equalsIgnoreCase(respostaAnterior);
					
					if (!acertou)
					{
						n = 0;
						break;
					}
				}
				
				n++;
				// Vejo a pr�xima pergunta
				decl = atual.proxima();
			}
			
			if (decl == null)
			{
				if (n > nPerguntas)
				{
					nPerguntas = n;
					chute = nomesDosAnimais[i];
				}
			}
			
			i++;
		}
		
		if (i == nomesDosAnimais.length)
			acerto = responder.finalAnswer(chute);

		if (acerto)
			System.out.println("Acerto!");
		else
			System.out.println("Fuem fuem fuem");
	}
}

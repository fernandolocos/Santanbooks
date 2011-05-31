package pt.c01interfaces.s01chaveid.archer;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;

public class Auxiliar {
	
	int indicePerguntas = 0;
	
	public Propriedade[] maiorOcorrencia(String animais[], int numero) 
	{
		Propriedade vetor[] = new Propriedade[100];
		for(int i = 0; i < 100; i++)
			vetor[i] = null;
		
		IBaseConhecimento base = new BaseConhecimento();
		//JOptionPane.showMessageDialog(null, "Come�o");
		for(int i = 0; i < numero; i++) {
			//JOptionPane.showMessageDialog(null, animais[i]);
			IObjetoConhecimento objeto = base.recuperaObjeto(animais[i]);
			IDeclaracao linha = objeto.primeira();
			
			//JOptionPane.showMessageDialog(null, "Come�a");
			//JOptionPane.showMessageDialog(null, animais[i] + " and " + linha.getPropriedade());
			//Come�o da busca e insercao do vetor de propriedades
			
			while(linha != null) {
				String pergunta = linha.getPropriedade();
				int posicao = ocorrenciaPropriedade(vetor, pergunta);
				
				if( posicao >= 0 ) {
					vetor[posicao].iOcorrencia();
				}else {
					int j;
					for(j = 0; vetor[j] != null; j++) {
					}
					
					if(vetor[j] == null) {
						Propriedade prop = new Propriedade(pergunta);
						vetor[j] = prop;
						//JOptionPane.showMessageDialog(null, vetor[j].getString());
					}
				}
				linha = objeto.proxima();			
			}
			//Fim da busca e insercao do vetor de propriedades
		}
		
		return vetor;
	}
		
	
		public String[] novaLista(String[] lista, String pergunta, String resposta) {
			
			//JOptionPane.showMessageDialog(null, "Nova Lista");
			String[] newList = new String[lista.length];
			
			int j = 0;
			//JOptionPane.showMessageDialog(null, "come�o");
			for(int i = 0; i < newList.length; i++) {
					//JOptionPane.showMessageDialog(null, lista[i]);
				if (lista[i] == null) continue;
					IResponder responder = new Responder(lista[i]);
					//JOptionPane.showMessageDialog(null, "Animal: " + lista[i] + " Pergunta: " + pergunta +
					//                             " responder: " +  responder.ask(pergunta)  + " resposta: " + resposta);
					if(responder.ask(pergunta).equalsIgnoreCase(resposta)) {
						newList[j] = lista[i];
						j++;
				}
			}
			
			while(j < newList.length) {
				newList[j] = null;
				j++;
			}
			
			return newList;
		}
		
		public int numeroAnimais(String[] lista) {
			int numAnimais = 0;
			for(int i = 0; i < lista.length; i++)
				if(lista[i] != null)
					numAnimais++;
			
			return numAnimais;
		}
		
		public boolean ocorrenciaPergunta(String lista[], String pergunta) {
			
			boolean retorno = false;
			
			for (int i =0; lista[i] != null; i++) {
				if(pergunta.equalsIgnoreCase(lista[i]) == true) {
					retorno = true;
					break;
				}
			}
			
			return retorno;
		}
		
		public int ocorrenciaPropriedade(Propriedade lista[], String pergunta) {
			
			int posicao = -1;
			for(int i = 0; lista[i] != null; i++) {
				if( (lista[i].getString()).equalsIgnoreCase(pergunta) == true ) {
					posicao = i;
					break;
				}
			}
			
			return posicao;
		}
		
		public void organizaPropriedade(Propriedade lista[]) {
			
			for(int i = 0; lista[i] != null; i++) {
				for(int j = i + 1; lista[j] != null; j++) {
					if(lista[j].getOcorrencia() > lista[i].getOcorrencia()) {
						Propriedade tmp = new Propriedade(lista[j].getString());
						tmp.setOcorrencia(lista[j].getOcorrencia());
						
						lista[j] = lista[i];
						lista[i] = tmp;
					}
				}
			}
		}
}

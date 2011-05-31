package pt.c01interfaces.s01chaveid.archer;

import javax.swing.JOptionPane;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerAdvanced implements IEnquirer
{
    IObjetoConhecimento obj;
	
	public EnquirerAdvanced()
	{
        IBaseConhecimento bc = new BaseConhecimento();
		
		obj = bc.recuperaObjeto("tiranossauro");
	}
	
	
	@Override
	public void connect(IResponder responder)
	{
IBaseConhecimento base = new BaseConhecimento();
        
        String lista[] = base.listaNomes();
        /*for (int i = 0; i < lista.length; i++)
            System.out.println(lista[i]);
        
        IObjetoConhecimento obj = base.recuperaObjeto("tiranossauro");
        
        IDeclaracao decl = obj.primeira();
        while (decl != null) {
        	System.out.println(decl);
        	decl = obj.proxima();
        }*/

        Auxiliar aux = new Auxiliar();
        int numAnimais = aux.numeroAnimais(lista);
        
        Propriedade prop[] = aux.maiorOcorrencia(lista, numAnimais);
        aux.organizaPropriedade(prop);
        String respostaFinal = null;
        
        String perguntas[] = new String[40];
        String pergunta, resposta;
        for(int i = 0; i < perguntas.length; i++)
        	perguntas[i] = null;
        
        String newList[] = lista;
        int indicePergunta = 0;
        while(numAnimais > 1) {
        	//JOptionPane.showMessageDialog(null, "controle");
        	//Analisando qual pergunta fazer
        	int i = 0;
        	while(aux.ocorrenciaPergunta(perguntas, prop[i].getString()) == true) 
        		i++;
        	//JOptionPane.showMessageDialog(null, "Depois1");
        	pergunta = prop[i].getString();
        	perguntas[indicePergunta] = pergunta; indicePergunta++;
        	//JOptionPane.showMessageDialog(null, pergunta);
        	
        	//Pergunta ao responder
        	resposta = responder.ask(pergunta);
        	//JOptionPane.showMessageDialog(null, resposta);
        	//faz a nova lista
        	//JOptionPane.showMessageDialog(null, "Lista Animais");
        	newList = aux.novaLista(newList, pergunta, resposta);
        	/*JOptionPane.showMessageDialog(null, "Come�a: ");
        	for(int j = 0; newList[j] != null; j++)
    			JOptionPane.showMessageDialog(null, newList[j]);*/
        	numAnimais = aux.numeroAnimais(newList);
        	prop = aux.maiorOcorrencia(newList, numAnimais);
        	/*JOptionPane.showMessageDialog(null, "Come�a: ");
        	for(int j = 0; prop[j] != null; j++)
    			JOptionPane.showMessageDialog(null, prop[j].getString());*/
        	aux.organizaPropriedade(prop);
        	respostaFinal = newList[0];
        }
        
        if(responder.finalAnswer(respostaFinal))
        	JOptionPane.showMessageDialog(null, "Acertei!");
        else
        	JOptionPane.showMessageDialog(null, "Errei!");

	}

}

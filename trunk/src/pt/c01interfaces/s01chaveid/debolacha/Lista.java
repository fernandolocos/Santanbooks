package pt.c01interfaces.s01chaveid.debolacha;

import java.util.ArrayList;

public class Lista extends ArrayList<String>{
	
	private static final long serialVersionUID = 1L;
	
	/*Este construtor cria uma lista vazia*/
	public Lista(){
		this.clear();
	}
	
	/*Este construtor recebe um vetor de strings como
	 *parametro e cria uma lista com seus elementos*/
	public Lista(String[] listaNomes){
		for(int i =0; i < listaNomes.length; i++){
			this.add(listaNomes[i]);
		}
	}
	
	/*Retorna uma lista com os elementos em comum de duas listas*/
	public Lista intersec (Lista outra){
		Lista alist = new Lista();
		
		/*Adiciona a lista de retorno os elementos que estao nas duas listas*/
		int j;
		for(j=0;j<this.size();j++){
			if(outra.contains(this.get(j))){
				alist.add(this.get(j));
			}
		}

	return alist;
	}
	
	/*Retorna uma lista com os elementos que aparecem em pelo menos uma das listas*/
	public Lista uniao (Lista outra){
		/*Coloca em uma lista os elementos que estao em outra, mas nao estao em this.
		 *Assim, nao havera repeticao dos elemtentos de this no objeto retornado*/
		Lista alist = this.complementar(outra);

		/*Adiciona a lista2 a lista final*/
		for(int i=0;i<this.size();i++){
			alist.add(this.get(i));
		}
		
		return alist;
		
	}
	
	/*Retorna a lista complementar de lista2 em lista1*/
	public Lista complementar(Lista W){
		Lista alist = new Lista();
		
		/*Adiciona a lista a ser retornada os elementos
		 *que estao em W, mas nao estao em this*/
		for(int i=0; i< W.size(); i++)
			if(!this.contains(W.get(i)))
				alist.add(W.get(i));
		
		return alist;
	}	
}


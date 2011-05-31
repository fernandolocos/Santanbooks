package pt.c01interfaces.s01chaveid.hayasida;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.impl.Declaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import java.util.Vector;

public class EnquirerAdvanced implements IEnquirer
{

	private IObjetoConhecimento objeto;
    private IBaseConhecimento base;
	private String animal;
	private String[] vAnimals;
	private int animalIndex = 0;
	private Vector<IDeclaracao> validas = new Vector<IDeclaracao>();
	private Vector<IDeclaracao> nValidas = new Vector<IDeclaracao>();
	
	/**
	 * Construtor, instancia uma base, e recebe a lista de animais.
	 */
	public EnquirerAdvanced()
	{
        this.base = new BaseConhecimento();
        this.vAnimals = this.base.listaNomes();
        this.setNextAnimal();
	}
	
	/**
	 * Pega o pr�ximo animal da lista fornecida pela Base. Retorna "fim" se a lista acabou.	
	 * @return String
	 */
	private String getNextAnimal(){
		String r = "fim";
		//System.out.println(" " + this.animalIndex + " " + this.vAnimals.length);
		if((this.animalIndex) < this.vAnimals.length )
		{
			r = this.vAnimals[this.animalIndex];
			this.animalIndex += 1;
		}
		return r;
	}
	
	/**
	 * Passa para o pr�ximo animal a ser analisado. Retorna true se a lista acabou.
	 * @return boolean
	 */
	private boolean setNextAnimal(){
		boolean fim = false;
		
		this.animal = this.getNextAnimal();
		//System.out.println("\nSetou animal: " + this.animal);
		
		if(this.animal.equalsIgnoreCase("fim")){ fim = true; }
		else{this.objeto = base.recuperaObjeto(this.animal); }
		
		
		return fim;
	}
	
	/**
	 * Verifica se um objeto � v�lido para prosseguir com o questionamento. 
	 * @param objeto
	 * @return boolean
	 */
	private boolean isValid(IObjetoConhecimento objeto){
		boolean valid = false, stop = false;
		IDeclaracao decl = objeto.primeira();
		
		if(this.nValidas.size() == 0 && this.validas.size() == 0) { 
			valid = true;
		} else {
			
			while(decl != null && !stop){
				/** 
				 * Verifica se a propriedade est� na lista de propriedades v�lidas. Caso esteja, verificar se os valores s�o iguais.
				 * Se forem, o objeto � valido.
				 */
				for(int i = 0; i < this.validas.size() && !valid; i++){											
					if(decl.getPropriedade().equalsIgnoreCase(this.validas.get(i).getPropriedade()))
					{
						if(decl.getValor().equalsIgnoreCase(this.validas.get(i).getValor())) valid = true; stop = true;
					}
				}
				
				/**
				 * Se a propriedade n�o est� na lista de v�lidas, mas estiver na lista de inv�lidas,
				 * o objeto � inv�lido.
				 */
				if(!valid && !stop){
					valid = true;
					for(int i = 0; i < this.nValidas.size() && valid; i++){
						if(decl.getPropriedade().equalsIgnoreCase(this.nValidas.get(i).getPropriedade())){
							valid = false; 
							stop = true;
						}
					}						
				}
				
				if(!stop) decl = objeto.proxima();
			}
		}
		
		//if(valid) System.out.println("Animal " + this.animal + " eh valido");
		//else System.out.println("Animal " + this.animal + " nao eh valido. motivo: " + decl.getPropriedade());
		return valid;
	}

	private boolean isValidQuestion(IDeclaracao decl){
		boolean valid = true;
		
		if(this.nValidas.size() == 0 && this.validas.size() == 0){		
			/** 
			 * Verifica se a pergunta j� foi perguntada. Se sim, a pergunta n�o � valida.
			 */
			for(int i = 0; i < this.validas.size() && valid; i++){					
				
				if(decl.getPropriedade().equalsIgnoreCase(this.validas.get(i).getPropriedade())) valid = false;
			}
			if(valid){
				for(int i = 0; i < this.nValidas.size() && valid; i++){
					if(decl.getPropriedade().equalsIgnoreCase(this.nValidas.get(i).getPropriedade())) valid = false;
				}
				
			}				
		}
		return valid;			
	}
	
	public void connect(IResponder responder)
	{
		boolean fim = false, achado = false;
		
		while(!fim && !achado){
			
			/**
			 * Se o objeto for v�lido, inicia questionamento. Caso contr�rio, passa para o pr�ximo objeto.
			 */
			if(this.isValid(this.objeto)){
				IDeclaracao decl = this.objeto.primeira();					
				
				achado = true;
				while (decl != null && achado){
					
					if(this.isValidQuestion(decl)){
						String resposta = responder.ask(decl.getPropriedade());
						//System.out.println("Pergunta: " + decl.getPropriedade() + " | Responder: " + resposta + " | Animal: " + decl.getValor());
						if(resposta.equalsIgnoreCase(decl.getValor()) || !resposta.equalsIgnoreCase("nao sei")){
							this.validas.add(new Declaracao(decl.getPropriedade(), resposta));
							//System.out.println("Nova declaracao valida: " + this.validas[this.validasIndex-1].getPropriedade() + " = " + this.validas[this.validasIndex-1].getValor());
							if(!resposta.equalsIgnoreCase(decl.getValor())) achado = false;
						} else {
							this.nValidas.add(new Declaracao(decl.getPropriedade(), resposta)); 
							//System.out.println("Nova declaracao invalida: " + this.nValidas[this.nValidasIndex-1].getPropriedade() + " = " + this.nValidas[this.nValidasIndex-1].getValor());
							achado = false;
						}
					}
					
					if(achado) decl = objeto.proxima();
				}
				
				if(achado){
					responder.finalAnswer(this.animal);
				}
			} 
			if(!achado){
				fim = this.setNextAnimal();
			}
			
		}


	}

}
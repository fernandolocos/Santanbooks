package pt.c03ensaios.dratini.pickBestCharacteristic;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c03ensaios.dratini.pickBestCharacteristic.Caracteristica;


/**
 * Creates a list with animals' characteristics
 * 
 * @author Kamila Galvani
 */
public class CharacteristicsListComponent
{
	protected BaseConhecimento baseC = new BaseConhecimento();
	private int pos = 0;
	Vector<Caracteristica> caracteristicas = new Vector<Caracteristica>();

	
	public CharacteristicsListComponent() {
		super();
		criaListaCaracteristicas(criarVetorAnimais());
	}
	
	public CharacteristicsListComponent(boolean empty) {
		super();
		if (!empty)
			criaListaCaracteristicas(criarVetorAnimais());
	}
	
	protected Vector<String> criarVetorAnimais(){
		String[] nomes = baseC.listaNomes();
		Vector<String> animais = new Vector<String>();
		for (int i=0; i<nomes.length; i++){
			animais.add(nomes[i]);
		}
		return animais;
	}

	protected void criaListaCaracteristicas(Vector<String> nomes){
		if (nomes==null || nomes.size()<1)
			return;
		boolean found = false;
		for (int i=0; i<nomes.size(); i++){
			IObjetoConhecimento obj = baseC.recuperaObjeto(nomes.get(i));
			IDeclaracao declara = obj.primeira();
			while (declara != null){
				Caracteristica car = new Caracteristica(declara.getPropriedade());
				for (int j=0; j<caracteristicas.size(); j++){
					if (caracteristicas.get(j).getNome().equalsIgnoreCase(car.getNome())){
						found = true;
					}
				}
				if (!found){
					caracteristicas.add(car);
				}
				found = false;
				declara = obj.proxima();
			}
		}
	}
	
	public void calculateEntropy() {
		double entropia;
		int a, b, c;
		Caracteristica car = this.first();
		while (car != null){
			a = car.getNaos();
			b = car.getNaoseis();
			c = car.getSims();
			entropia = calculaPassoEntropia(a) + calculaPassoEntropia(b) + calculaPassoEntropia(c);
			car.setEntropia(entropia);
			car = this.getNext();
		}
	}
	
	private double calculaPassoEntropia(int a){
		if (a==0)
			return 0;
		double value = -a*(Math.log(a)/Math.log(2));
		return value;
		
	}
	
	private void add(Caracteristica car){
		this.caracteristicas.add(car);
	}

	public CharacteristicsListComponent copy() {
		CharacteristicsListComponent chara = new CharacteristicsListComponent(true);
		for (int i=0; i<caracteristicas.size(); i++){
			chara.add(caracteristicas.get(i));
		}
		return chara;
	}

	public Caracteristica first() {
		pos = 0;
		return caracteristicas.get(pos);
	}

	public Caracteristica getNext() {
		pos = pos+1; 
		if (pos<caracteristicas.size())
			return caracteristicas.get(pos);
		return null;
	}

	public void resetCharacteristics() {
		for (int i=0; i<caracteristicas.size(); i++){
			caracteristicas.get(i).setEntropia(0);
			caracteristicas.get(i).setNaos(0);
			caracteristicas.get(i).setSims(0);
			caracteristicas.get(i).setNaoseis(0);			
		}
	}

	public Caracteristica selectBestEntropy() {
		double max = caracteristicas.get(0).getEntropia();
		Caracteristica maxC = caracteristicas.get(0);
		for (int i=0; i<caracteristicas.size(); i++){
			if (caracteristicas.get(i).getEntropia()>max){
				max = caracteristicas.get(i).getEntropia();
				maxC = caracteristicas.get(i);
			}	
		}
		return maxC;
	}

	public void sumAnswers2Characteristics(String nome_animal) {
		IObjetoConhecimento animal = baseC.recuperaObjeto(nome_animal);
		IDeclaracao decl;
		boolean found = false;
		for (int i=0; i<caracteristicas.size(); i++){
			decl = animal.primeira();
			while (decl != null){
				if(decl.getPropriedade().equalsIgnoreCase(caracteristicas.get(i).getNome())){
					String valor = decl.getValor();
					if(valor.equalsIgnoreCase("nao")){
						caracteristicas.get(i).setNaos(caracteristicas.get(i).getNaos()+1);
					}
					else if (valor.equalsIgnoreCase("sim")){
						caracteristicas.get(i).setSims(caracteristicas.get(i).getSims()+1);
					}
					found = true;
					break;
				}
				decl=animal.proxima();
			}
			if (!found)
				caracteristicas.get(i).setNaoseis(caracteristicas.get(i).getNaoseis()+1);
			found = false;
		}
	}
}

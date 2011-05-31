package pt.c03ensaios.dratini.characteristicsList;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c03ensaios.dratini.characteristicsList.Caracteristica;

import anima.annotation.Component;
import anima.component.base.ComponentBase;

/**
 * Creates a list with animals' characteristics
 * 
 * @author Kamila Galvani
 */
@Component(id = "<http://purl.org/dcc/pt.c03ensaios.dratini.characteristicsList.CharacteristicsListComponent>",
           provides = {"<http://purl.org/dcc/pt.c03ensaios.dratini.characteristicsList.ICharacteristicsList>"})
public class CharacteristicsListComponent extends ComponentBase implements ICharacteristicsList
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
	
	@Override
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

	@Override
	public ICharacteristicsList copy() {
		CharacteristicsListComponent chara = new CharacteristicsListComponent(true);
		for (int i=0; i<caracteristicas.size(); i++){
			chara.add(caracteristicas.get(i));
		}
		return chara;
	}

	@Override
	public Caracteristica first() {
		pos = 0;
		return caracteristicas.get(pos);
	}

	@Override
	public Caracteristica getNext() {
		pos = pos+1; 
		return caracteristicas.get(pos);
	}

	@Override
	public void resetCharacteristics() {
		for (int i=0; i<caracteristicas.size(); i++){
			caracteristicas.get(i).setEntropia(0);
			caracteristicas.get(i).setNaos(0);
			caracteristicas.get(i).setSims(0);
			caracteristicas.get(i).setNaoseis(0);			
		}
	}

	@Override
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

	@Override
	public void sumAnswers2Characteristics(IObjetoConhecimento animal) {
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

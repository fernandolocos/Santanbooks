package pt.c01interfaces.s01chaveid.dratini;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerAdvanced extends Enquirer implements IEnquirer {

	private void calculaEntropia(Vector<Caracteristica> cars){
		double entropia;
		int a, b, c;
		for (int i=0; i<cars.size(); i++){
			a = cars.get(i).getNaos();
			b = cars.get(i).getNaoseis();
			c = cars.get(i).getSims();
			entropia = calculaPassoEntropia(a) + calculaPassoEntropia(b) + calculaPassoEntropia(c);
			cars.get(i).setEntropia(entropia);
		}
	}
	
	private double calculaPassoEntropia(int a){
		if (a==0)
			return 0;
		double value = -a*(Math.log(a)/Math.log(2));
		return value;
	}
	
	private No criarArvore(Vector<String> animais, Vector<Caracteristica> cars){
		if (animais.size() == 0)
			return null;
		else if (animais.size()==1 || cars == null || cars.size()==0){
			No no = new No(animais.get(0));
			return no;
		}

		resetCaracteristicas(cars);
		for (int i=0; i<animais.size(); i++){
			IObjetoConhecimento animal = baseC.recuperaObjeto(animais.get(i));
			somaCaracteristica(animal, cars);
		}
		
		calculaEntropia(cars);
		Caracteristica maior = pegaMaiorEntropia(cars);
		No tree = new No(maior.getNome());
		Vector<Vector<String>> separados = separaPorCategoria(tree, animais);
		Vector<Caracteristica> novaCars = copia(cars);
		novaCars.remove(maior);
		tree.setNaos(criarArvore(separados.get(0), cars));
		tree.setSims(criarArvore(separados.get(1), cars));
		tree.setNaoseis(criarArvore(separados.get(2), cars));
		return tree;
	}
	
	public EnquirerAdvanced(){
		super();
	}
	
	private void resetCaracteristicas(Vector<Caracteristica> cars) {
		for (int i=0; i<cars.size(); i++){
			cars.get(i).setEntropia(0);
			cars.get(i).setNaos(0);
			cars.get(i).setSims(0);
			cars.get(i).setNaoseis(0);			
		}
	}

	private Caracteristica pegaMaiorEntropia(Vector<Caracteristica> cars){
		double max = cars.get(0).getEntropia();
		Caracteristica maxC = cars.get(0);
		for (int i=0; i<cars.size(); i++){
			if (cars.get(i).getEntropia()>max){
				max = cars.get(i).getEntropia();
				maxC = cars.get(i);
			}	
		}
		return maxC;
	}
	
	protected No createTree(){
		Vector<String> animais = criarVetorAnimais();
		Vector<Caracteristica> caracteristicas = criaListaCaracteristicas(animais);

		No arvore = criarArvore(animais, caracteristicas);
		return arvore;
	}
	
	public void connect(IResponder responder) {
		super.connect(responder);
		
	}
	
	protected void somaCaracteristica(IObjetoConhecimento animal, Vector<Caracteristica> cars){
		IDeclaracao decl;
		boolean found = false;
		for (int i=0; i<cars.size(); i++){
			decl = animal.primeira();
			while (decl != null){
				if(decl.getPropriedade().equalsIgnoreCase(cars.get(i).getNome())){
					String valor = decl.getValor();
					if(valor.equalsIgnoreCase("nao")){
						cars.get(i).setNaos(cars.get(i).getNaos()+1);
					}
					else if (valor.equalsIgnoreCase("sim")){
						cars.get(i).setSims(cars.get(i).getSims()+1);
					}
					found = true;
					break;
				}
				decl=animal.proxima();
			}
			if (!found)
				cars.get(i).setNaoseis(cars.get(i).getNaoseis()+1);
			found = false;
		}
	}
	
	
}

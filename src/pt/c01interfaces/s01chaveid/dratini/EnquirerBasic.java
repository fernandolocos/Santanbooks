package pt.c01interfaces.s01chaveid.dratini;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerBasic extends Enquirer implements IEnquirer {

	
	protected No createTree(){
		Vector<String> animais = criarVetorAnimais();
		Vector<Caracteristica> caracteristicas = criaListaCaracteristicas(animais);

		No arvore = criarArvore(caracteristicas, 0, animais);
		return arvore;
	}
	
	public EnquirerBasic(){
		super();
	}
	
	protected No criarArvore(Vector<Caracteristica> cars, int index, Vector<String> animais){		
		if (animais.size()==1){
			No no = new No(animais.get(0));
			return no;
		}
		else if (animais == null || animais.size() == 0)
			return null;
		if (index >= cars.size()){
			No no = new No(animais.get(0));
			return no;
		}
		No no = new No(cars.get(index).getNome());
		Vector<Vector<String>> separados = separaPorCategoria(no, animais);
		no.setNaos(criarArvore(cars, index+1, separados.get(0)));
		no.setSims(criarArvore(cars, index+1, separados.get(1)));
		no.setNaoseis(criarArvore(cars, index+1, separados.get(2)));
		
		return no;
	}

	
	@Override
	public void connect(IResponder responder) {
		super.connect(responder);
		
	}
	
	

	
}

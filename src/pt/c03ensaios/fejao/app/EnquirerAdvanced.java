package pt.c03ensaios.fejao.app;

import java.util.ArrayList;
import java.util.List;
import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c03ensaios.fejao.PossibleAnimalsHash;

public class EnquirerAdvanced implements IEnquirer{

	private IBaseConhecimento baseConhecimento;
	private static String[] listaNomes;
	private static List<String> listaPerguntas = new ArrayList<String>();
	
	public EnquirerAdvanced() {
		baseConhecimento = new BaseConhecimento();
		listaNomes = baseConhecimento.listaNomes();

		// guarda uma lista de todas as perguntas
		listaPerguntas = new ArrayList<String>();

		for (int i = 0; (i < listaNomes.length); i++) {
			IObjetoConhecimento obj;
			obj = baseConhecimento.recuperaObjeto(listaNomes[i]);

			IDeclaracao decl = obj.primeira();

			while (decl != null) {
				if (!isQuestion(decl.getPropriedade())) {
					addQuestion(decl.getPropriedade());
				}
				decl = obj.proxima();
			}
		}
		
	}

	public void connect(IResponder responder) {
		boolean acertei, encontrado = false;
		String nomeAnimal = null;
		PossibleAnimalsHash hashAnimais = new PossibleAnimalsHash();

		// utiliza a lista de perguntas que foi montada anteriormente e vai
		// perguntando até os animais possiveis para a resposta seja somente 1
		for (int i = 0; ((i < listaPerguntas.size()) && (!encontrado)); i++) {
			String resposta = responder.ask((String) listaPerguntas.get(i));

			// determina uma nova lista de possiveis animais
			hashAnimais.DeterminesPossibleAnimals(listaPerguntas.get(i), resposta);
			
			// se hashAnimais = 1 retorna resultado
			if (hashAnimais.getNumberOfAnimals() == 1) {
				nomeAnimal = hashAnimais.getPossibleAnimalsList().get(0);
				encontrado = true;
			}
		}

		if (nomeAnimal != null) {
			acertei = responder.finalAnswer(nomeAnimal);
		} else {
			acertei = responder.finalAnswer("nao sei");
		}

		if (acertei) {
			System.out.println("Oba! Acertei! - " + nomeAnimal);
		} else {
			System.out.println("fuem! fuem! fuem!");
		}
	}

	private void addQuestion(String question) {
		listaPerguntas.add(question);
	}

	private boolean isQuestion(String question) {
		return (listaPerguntas.contains(question));
	}
}

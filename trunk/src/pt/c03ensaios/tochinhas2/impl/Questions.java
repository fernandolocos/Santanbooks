package pt.c03ensaios.tochinhas2.impl;

import java.util.Vector;

import anima.annotation.Component;
import anima.component.base.ComponentBase;
import anima.component.IRequires;
import pt.c03ensaios.linnaeus.*;

/**
 * @author:
 * Alexandre Faltz (RA 	108411)
 * and 
 * Davi Costa Clemente (RA 083413)
 */

@Component(id="<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>",
		provides={"<http://purl.org/dcc/pt.c03ensaios.fish.tochinhas2.impl.IQuestions>"})
		public class Questions extends ComponentBase implements IQuestions, IRequires<IAnimalsDatabase> {

	private IAnimalsDatabase database;
	private Vector<String> vetor_perguntas;

	@Override
	public String[] vectorToArray(Vector<String> v) {
		String[] n = new String[v.size()];

		return v.toArray(n);
	}

	@Override
	public Vector<String> getQuestions() {

		/* Check if questions vector is null and if so, create it */
		if (this.vetor_perguntas == null) {
			this.vetor_perguntas = new Vector<String>();

			IAnimalData animais[] = this.database.getAnimais();

			/* For each animal get from database, add all it's questions that wasn't added before */
			for (IAnimalData animal : animais) {
				String perguntas[] = animal.getPerguntas();

				for (String pergunta : perguntas) {
					if (!this.vetor_perguntas.contains(pergunta)) {
						this.vetor_perguntas.addElement(pergunta);
					}
				}
			}
		}

		return this.vetor_perguntas;
	}

	@Override
	public Vector<String> getQuestions(Vector<String> animals) {
		return this.getQuestions(this.vectorToArray(animals));
	}

	@Override
	public Vector<String> getQuestions(String[] animals) {
		Vector<String> questions = new Vector<String>();
		IAnimalData a = null;

		/* For each animal contained on @animals, add all its questions that wasn't added before */
		for (String animal : animals) {
			a = this.database.getAnimal(animal);
			String perguntas[] = a.getPerguntas();

			for (String pergunta : perguntas) {
				if (!questions.contains(pergunta)) {
					questions.addElement(pergunta);
				}
			}
		}

		return questions;
	}

	@Override
	public Vector<String> keywordSearch(Vector<String> keys) {
		return this.keywordSearch(this.vectorToArray(keys));
	}

	@Override
	public Vector<String> keywordSearch(String[] keys) {
		Vector<String> questions = new Vector<String>(),
		all       = this.getQuestions();

		/* For each string contained in @keys, search it as a substring through all questions, and, if it's found, 
		 * add that question on a vector  */
		for (int i = 0; i < all.size(); i++) {
			for (String key : keys) {
				if (all.get(i).indexOf(key) != -1)
					questions.add(all.get(i));
			}
		}
		
		return questions;
	}

	@Override
	public int keywordReferences(String key) {
		int references = 0;
		Vector<String> all = this.getQuestions();

		/* For each question, search for the @key substring and return how many cases where found */
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).indexOf(key) != -1) {
				references++;
			}
		}
		
		return references;
	}

	@Override
	public String getAnswer(String question, String animal) {
		return this.database.getAnimal(animal).getResposta(question);
	}

	@Override
	public Vector<String> baseFiltration(String question) {
		Vector<String> animals = new Vector<String>();

		/* For each animal from database, search if it have the passed question and add it to a vector */
		for (IAnimalData a : this.database.getAnimais()) {
			marotops: for (String pergunta : a.getPerguntas()) {
				if (pergunta.equals(question)) {
					animals.add(a.getNome());
					// Stop searching on that animal if the question is found
					break marotops;
				}
			}
		}
		
		return animals;
	}

	@Override
	public Vector<String> baseFiltration(String question, String answer) {
		Vector<String> animals = new Vector<String>();

		/* For each animal from database, search if it have the passed question, and check if it's answer
		 * matches the passed answer, if so, add it to a vector */
		for (IAnimalData a : this.database.getAnimais()) {
			marotops: for (String pergunta : a.getPerguntas()) {
				if (pergunta.equals(question) && a.getResposta(pergunta).equals(answer)) {
					animals.add(a.getNome());
					break marotops;
				}
			}
		}
		
		return animals;
	}

	@Override
	public void connect(IAnimalsDatabase base) {
		this.database = base;
	}
	
}
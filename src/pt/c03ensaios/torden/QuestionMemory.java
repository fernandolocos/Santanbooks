package pt.c03ensaios.torden;

import java.util.ArrayList;


import anima.annotation.Component;
import anima.component.base.ComponentBase;

/**
 * 
 * @author Waldir Rodrigues de Almeida
 *
 */
@Component(id = "<http://purl.org/dcc/pt.c03ensaios.torden.QuestionMemory>",
        provides = {"<http://purl.org/dcc/pt.c03ensaios.torden.IQuestionMemory>"})
public class QuestionMemory extends ComponentBase implements IQuestionMemory {

	private ArrayList<String[]> answeredQuestions = new ArrayList<String[]>();
	
	@Override
	public String[][] getAllAnsweredQuestions() {
		String[][] a = null;
		
		if (answeredQuestions.size() > 0) {
			a = new String[answeredQuestions.size()][2];
			for (int i = 0; i < answeredQuestions.size(); i++) {
				a[i] = answeredQuestions.get(i);
			}
		}
		
		return a;
	}

	@Override
	public String[] getQuestionAt(int n) {
		String[] answeredPair = null;
		
		if (n < answeredQuestions.size() && n >= 0)
			answeredPair = answeredQuestions.get(n);
		
		return answeredPair;
	}

	@Override
	public void putNewAnsweredQuestion(String question, String answer) {
		answeredQuestions.add(new String[] {question, answer});
	}

	@Override
	public void clear() {
		answeredQuestions.clear();
	}

}

package pt.c03ensaios.gnrpack;

import java.util.Arrays;
import java.util.Vector;

import anima.annotation.Component;
import anima.component.base.ComponentBase;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c01interfaces.s01chaveid.s01base.inter.IStatistics;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Statistics;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.gnrpack.QuestionChooserEvaluator>",
		   provides = "<http://purl.org/dcc/pt.c03ensaios.gnrpack.IQuestionChooserEvaluator>",
		   requires = "<http://purl.org/dcc/pt.c03ensaios.gnrpack.IBestQuestionChooser>")
		   
public class QuestionChooserEvaluator extends ComponentBase implements IQuestionChooserEvaluator {
	String resp;
	Vector<IBestQuestionChooser> viq;
	
	public QuestionChooserEvaluator(String resp) {
		super();
		viq = new Vector<IBestQuestionChooser>();
		this.resp = resp;
	}
	
	public void register(IBestQuestionChooser iq) {
		viq.add(iq);
	}
	
	public Vector<Results> evaluate() {
		Vector<Results> results = new Vector<Results>();
		
		if (resp == "" || resp == null || viq.size() == 0) {
			return null;
		}
		
		for (int i = 0; i < viq.size(); i++) {
			IStatistics stat;
			IResponder responder;
			stat = new Statistics();
			responder = new Responder(stat, resp);
			BaseConhecimento bc = new BaseConhecimento();
			Vector<String> animals = new Vector<String>(Arrays.asList(bc.listaNomes()));
			Results result = new Results();
			int numQuestions = 0;
			long totalTime = 0;
			boolean correct = false;
			
			while (animals.size() > 1 && numQuestions < 100) {
				System.out.println( animals.toString() );
				long t1 = System.currentTimeMillis();
				String question = viq.elementAt(i).selectBestQuestion(bc, (String[]) animals.toArray(new String[animals.size()]));
				long t2 = System.currentTimeMillis();
				result.addAskedQuestion(question);
				totalTime += t2 - t1;
				System.out.println(question);
				numQuestions++;
				
				Vector<String> prune = new Vector<String>();
				String correct_answer = responder.ask(question);
				
				for (int j = 0; j < animals.size(); j++) {
					IObjetoConhecimento obj1 = bc.recuperaObjeto(animals.elementAt(j));
					
					boolean has_dec = false;
					for (IDeclaracao dec = obj1.primeira(); dec != null; dec = obj1.proxima()) {
						if (question.equalsIgnoreCase(dec.getPropriedade())) {
							has_dec = true;
							
							if (!dec.getValor().equalsIgnoreCase(correct_answer)) {								
								prune.add(animals.elementAt(j));
								break;
							}
						}
					}
					
					if (!correct_answer.equalsIgnoreCase("nao sei") && !has_dec) {
						prune.add(animals.elementAt(j));
					}
				}				
				
				for (int j = 0; j < prune.size(); j++) {
					animals.remove(prune.elementAt(j));
				}
			}
			
			if (animals.size() == 1 && numQuestions < 100) {
				correct = responder.finalAnswer(animals.elementAt(0));
			}
			
			if (correct) {
				result.setNumIter(numQuestions);
				result.setTotalTime(totalTime);
			} else {
				//
			}
			
			results.add(result);
		}
		
		return results;
	}
}

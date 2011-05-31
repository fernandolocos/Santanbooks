package pt.c03ensaios.torden;

import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public class QuestionMemoryTest {
	
	public static void main(String[] args) {
		
			try {
				
				IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
				
	            factory.registerPrototype(QuestionMemory.class);
				
				IQuestionMemory qm = 
					factory.createInstance("<http://purl.org/dcc/pt.c03ensaios." +
										"torden.QuestionMemory>");
				
				qm.putNewAnsweredQuestion("Abra", "Cadabra");
				qm.putNewAnsweredQuestion("Who are the patriots", "Lalilulelo");
				qm.putNewAnsweredQuestion("Who killed Osama", "Obama");
				
				System.out.println(qm.getQuestionAt(1)[0] + 
									" - " + qm.getQuestionAt(1)[1]);
				
				System.out.println();
				String[][] answers = qm.getAllAnsweredQuestions();
				for (int i = 0; i < answers.length; i++) {
					System.out.println(answers[i][0] + " - " + answers[i][1]);
				}
			
			} catch (ContextException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FactoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			


	}

}
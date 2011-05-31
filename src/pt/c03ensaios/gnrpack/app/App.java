package pt.c03ensaios.gnrpack.app;

import java.util.Scanner;
import java.util.Vector;

import pt.c03ensaios.dratini.pickBestCharacteristic.PickBestCharacteristic;
import pt.c03ensaios.gnrpack.IBestQuestionChooser;
import pt.c03ensaios.gnrpack.IQuestionChooserEvaluator;
import pt.c03ensaios.gnrpack.QuestionChooserEvaluator;
import pt.c03ensaios.gnrpack.Results;
import pt.c03ensaios.pizza.QuestionChooser;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public class App {
	public static void main(String[] args) {
		IGlobalFactory factory;
		Vector<IBestQuestionChooser> viq;
		Vector<Results> results;
		
		Scanner input = new Scanner(System.in);
		System.out.print("Animal: ");
		
		try {
			factory = ComponentContextFactory.createGlobalFactory();
			
			factory.registerPrototype(QuestionChooserEvaluator.class);
			IQuestionChooserEvaluator iq = factory.createInstance(
					"<http://purl.org/dcc/pt.c03ensaios.gnrpack.QuestionChooserEvaluator",
					"<http://purl.org/dcc/pt.c03ensaios.gnrpack.IQuestionChooserEvaluator");
			iq = new QuestionChooserEvaluator(input.nextLine());
			
			viq = new Vector<IBestQuestionChooser>();
			
			factory.registerPrototype(PickBestCharacteristic.class);
			viq.add((IBestQuestionChooser) factory.createInstance(
					"<http://purl.org/dcc/pt.c03ensaios.dratini.pickBestCharacteristic.PickBestCharacteristic>",
					"<http://purl.org/dcc/pt.c03ensaios.dratini.pickBestCharacteristic.IQuestionChooser>"));
			
			factory.registerPrototype(QuestionChooser.class);
			viq.add((IBestQuestionChooser) factory.createInstance(
					"<http://purl.org/dcc/pt.c03ensaios.pizza.QuestionChooser>",
					"<http://purl.org/dcc/pt.c03ensaios.pizza.IQuestionChooser>"));
			
			for (int i = 0; i < viq.size(); i++) {
				iq.register(viq.get(i));
			}			
			
			results = iq.evaluate();
			
			for (int i = 0; i < results.size(); i++) {
				System.out.println(viq.get(i).toString() + "-> #ITER: " + results.get(i).getNumIter() + " TIME (ms): " + results.get(i).getTotalTime());
			}
			
		} catch (ContextException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

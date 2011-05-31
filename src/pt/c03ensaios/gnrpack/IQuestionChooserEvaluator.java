package pt.c03ensaios.gnrpack;

import java.util.Vector;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.gnrpack.IQuestionChooserEvaluator>")
public interface IQuestionChooserEvaluator extends ISupports {
	public void register(IBestQuestionChooser iq);
	
	public Vector<Results> evaluate();
}

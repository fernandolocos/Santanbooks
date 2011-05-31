package pt.c03ensaios.gnrpack;

import anima.annotation.ComponentInterface;
import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.gnrpack.IBestQuestionChooser>")
public interface IBestQuestionChooser {
	public String selectBestQuestion(BaseConhecimento base, String[] animals);
}

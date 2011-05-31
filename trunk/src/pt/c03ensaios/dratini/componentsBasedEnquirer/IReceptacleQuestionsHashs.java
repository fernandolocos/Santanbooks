package pt.c03ensaios.dratini.componentsBasedEnquirer;

import anima.component.IReceptacle;
import pt.c03ensaios.frango.IQuestionsHash;

public interface IReceptacleQuestionsHashs extends IReceptacle{
	public void connect(IQuestionsHash hashSim, IQuestionsHash hashNao, IQuestionsHash hashTalvez);
}

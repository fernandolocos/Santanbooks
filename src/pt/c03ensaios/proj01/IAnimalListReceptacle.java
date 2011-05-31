package pt.c03ensaios.proj01;

import pt.c03ensaios.debolacha.inter.IAnimalList;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.proj01.IAnimalListReceptacle>")
public interface IAnimalListReceptacle extends IReceptacle{
	public void connect(IAnimalList myList);
}

package pt.c03ensaios.archer;

import pt.c03ensaios.naxxramas.INaxxramas;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.archer.INaxxramasReceptacle>")
public interface INaxxramasReceptacle extends IReceptacle {
	public void connect(INaxxramas managerGraphics);
}

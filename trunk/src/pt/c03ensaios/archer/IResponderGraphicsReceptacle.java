package pt.c03ensaios.archer;

import pt.c03ensaios.lobo.inter.IResponderGraphics;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.archer.IResponderGraphicsReceptacle>")
public interface IResponderGraphicsReceptacle extends IReceptacle {
	public void connect(IResponderGraphics responder);
}

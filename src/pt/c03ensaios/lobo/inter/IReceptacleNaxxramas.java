package pt.c03ensaios.lobo.inter;

import pt.c03ensaios.naxxramas.INaxxramas;
import anima.annotation.ComponentReceptacle;
import anima.component.IReceptacle;

@ComponentReceptacle("<http://purl.org/dcc/pt.c03ensaios.naxxramas.INaxxramas>")
public interface IReceptacleNaxxramas extends IReceptacle
{
	public void connect(INaxxramas provider);
}

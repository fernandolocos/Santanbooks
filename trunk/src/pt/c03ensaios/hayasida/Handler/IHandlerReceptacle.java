package pt.c03ensaios.hayasida.Handler;
import anima.component.IReceptacle;

public interface IHandlerReceptacle extends IReceptacle{
	public void connect(IHandler provider);
}
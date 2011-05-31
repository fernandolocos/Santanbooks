package pt.c03ensaios.optimizers;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.optimizers.IOptimizer>")

public interface IOptimizer extends ISupports {
	public abstract IEnquirerSupports getBest();
	public abstract String getOPTType();
	public abstract String getOPTURI();
}

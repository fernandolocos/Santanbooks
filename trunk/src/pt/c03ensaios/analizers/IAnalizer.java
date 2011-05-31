package pt.c03ensaios.analizers;

import pt.c03ensaios.optimizers.IEnquirerSupports;
import anima.annotation.ComponentInterface;
import anima.component.ISupports;
import java.util.Vector;


/**
 * 
 * @author Eric Lopes & Raniere Gaia
 *
 */

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.analizers.IAnalizer>")


public interface IAnalizer extends ISupports{
	public IEnquirerSupports getBest(String tag);
	public IEnquirerSupports getBest();
	public Vector<String> getOPTTypes();
	public Vector<String> getOPTList();
}

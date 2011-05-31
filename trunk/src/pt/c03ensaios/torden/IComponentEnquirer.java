package pt.c03ensaios.torden;

import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Just a "componentized" Enquirer.
 * 
 * @author Waldir Rodrigues de Almeida
 *
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.torden.IComponentEnquirer>")
public interface IComponentEnquirer extends ISupports, IEnquirer {

}

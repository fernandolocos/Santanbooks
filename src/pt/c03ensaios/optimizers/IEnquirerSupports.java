package pt.c03ensaios.optimizers;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.optimizers.IEnquirerSupports>")
public interface IEnquirerSupports extends ISupports, IEnquirer
{
    public void connect(IResponder responder);
}

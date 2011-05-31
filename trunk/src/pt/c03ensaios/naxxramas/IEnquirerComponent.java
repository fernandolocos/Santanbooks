package pt.c03ensaios.naxxramas;

import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import anima.annotation.ComponentInterface;
import anima.component.ISupports;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.naxxramas.IEnquirerComponent>")
public interface IEnquirerComponent extends ISupports {
    public void connect(IResponder responder) throws ComponentNotSetException, ComponentNotStartedException;
    
    public String getContentName() throws ComponentNotSetException;
    
    public void start() throws InstantiationException, IllegalAccessException;
}

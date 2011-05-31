package pt.c03ensaios.naxxramas;

import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import anima.annotation.Component;
import anima.component.base.ComponentBase;

@Component(id="<http://purl.org/dcc/pt.c03ensaios.naxxramas.EnquirerWrapper>",
        provides="<http://purl.org/dcc/pt.c03ensaios.naxxramas.IEnquirerWrapperComponent>")
public class EnquirerWrapper extends ComponentBase implements IEnquirerComponent, IEnquirerWrapperComponent {
    private Class<IEnquirer> enquirer = null;
    private IEnquirer object = null;

    @Override
    public void connect(IResponder responder) throws ComponentNotSetException, 
    ComponentNotStartedException {
        if (this.enquirer == null) {
            throw new ComponentNotSetException();
        } else if (this.object == null) {
            throw new ComponentNotStartedException();
        }
        
        this.object.connect(responder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void wrap(String enquirer) throws ClassNotFoundException {
        this.enquirer = (Class<IEnquirer>)Class.forName(enquirer);
    }

    @Override
    public void start() throws InstantiationException, IllegalAccessException {
        this.object = this.enquirer.newInstance();
    }

    @Override
    public String getContentName() throws ComponentNotSetException {
        if (this.enquirer == null) {
            throw new ComponentNotSetException();
        }
        
        return this.enquirer.getName();
    }
}

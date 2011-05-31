package pt.c03ensaios.naxxramas;

import anima.annotation.ComponentInterface;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.naxxramas.IEnquirerWrapperComponent>")
public interface IEnquirerWrapperComponent extends IEnquirerComponent {
    public void wrap(String enquirer) throws ClassNotFoundException;
}

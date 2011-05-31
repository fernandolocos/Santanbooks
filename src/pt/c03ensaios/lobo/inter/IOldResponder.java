package pt.c03ensaios.lobo.inter;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;
import pt.c01interfaces.s01chaveid.s01base.inter.*;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.lobo.inter.IOldResponder>")
public interface IOldResponder extends IResponder, ISupports {
    
    @Override
	public String ask(String question);
    
    @Override
	public boolean finalAnswer(String answer);
    
}


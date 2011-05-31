package pt.c02foundations.led;

import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.base.ComponentBase;

@Component(id="<http://purl.org/dcc/pt.c02foundations.led.Switch>",
           provides="<http://purl.org/dcc/pt.c02foundations.led.ISwitch>",
           requires="<http://purl.org/dcc/pt.c02foundations.led.ILed>")
public class Switch extends ComponentBase implements IRequires<ILed>,
                                                     ISwitch
{
    private ILed aLed;
    
    @Override
    public void connect(ILed aLed)
    {
        this.aLed = aLed;
    }

    public void invert()
    {
        if (aLed.isStateOn())
            aLed.off();
        else
            aLed.on();
    }
}

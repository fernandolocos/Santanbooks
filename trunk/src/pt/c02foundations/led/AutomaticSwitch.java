package pt.c02foundations.led;

import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.base.ComponentBase;

@Component(id="<http://purl.org/dcc/pt.c02foundations.led.AutomaticSwitch>",
           requires="<http://purl.org/dcc/pt.c02foundations.led.ILed>")
public class AutomaticSwitch extends ComponentBase implements IRequires<ILed>
{
    private ILed aLed;
    
    @Override
    public void connect(ILed aLed)
    {
        this.aLed = aLed;
        automaticSwitch();
    }

    public void automaticSwitch()
    {
        for (int s=1; s <= 5; s++ ) {
            aLed.on();
            aLed.off();
        }
    }
}

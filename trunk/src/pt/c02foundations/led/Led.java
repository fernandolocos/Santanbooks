package pt.c02foundations.led;

import anima.annotation.Component;
import anima.component.base.ComponentBase;

/**
 * Simulates a LED behavior in the console.
 * 
 * @author André Santanchè
 *
 */
@Component(id="<http://purl.org/dcc/pt.c02foundations.led.Led>",
           provides={"<http://purl.org/dcc/pt.c02foundations.led.ILed>"})
public class Led extends ComponentBase implements ILed
{
    private boolean stateOn = true;
    
    /**
     * shows the LED
     */
    @Override
    public void show()
    {
        if (stateOn)
            System.out.println("*");
        else
            System.out.println("O");
    }
    /**
     * turns the LED on
     */
    @Override
    public void on()
    {
        stateOn = true;
        show();
    }

    /**
     * turns the LED off
     */
    @Override
    public void off()
    {
        stateOn = false;
        show();
    }
    
    public boolean isStateOn()
    {
        return stateOn;
    }
}

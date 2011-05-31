package pt.c02foundations.led;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Interface for a component that simulates a LED behavior in the console.
 * 
 * @author Andre Santanche
 *
 */
@ComponentInterface("<http://purl.org/dcc/pt.c02foundations.led.ILed>")
public interface ILed extends ISupports
{
    /**
     * shows the LED
     */
    public void show();
    
    /**
     * turns the LED on
     */
    public void on();
    
    /**
     * turns the LED off
     */
    public void off();
    
    public boolean isStateOn();
}

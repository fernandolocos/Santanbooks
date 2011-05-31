package pt.c02foundations.led;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

@ComponentInterface("<http://purl.org/dcc/pt.c02foundations.led.ISwitch>")
public interface ISwitch extends ISupports
{
    public void invert();
}

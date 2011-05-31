package pt.c02foundations.led;

import anima.component.IRequires;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public class AppLed03
{

    public static void main(String[] args)
    {
        try {
            IGlobalFactory factory = 
                ComponentContextFactory.createGlobalFactory();
            
            ILed myLed = factory.createInstance(
                    "<http://purl.org/dcc/pt.c02foundations.led.Led>");
            ISwitch mySwitch = factory.createInstance(
                    "<http://purl.org/dcc/pt.c02foundations.led.Switch>",
                    "<http://purl.org/dcc/pt.c02foundations.led.ISwitch>");
            
            IRequires<ILed> mySwitchRq = mySwitch.queryReceptacle(
                    "<http://purl.org/dcc/pt.c02foundations.led.ILed>");

            mySwitchRq.connect(myLed);
            
            for (int s = 1; s <= 5; s++)
                mySwitch.invert();
           
        } catch (ContextException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FactoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

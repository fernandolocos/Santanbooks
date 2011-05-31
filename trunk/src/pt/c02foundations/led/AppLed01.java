package pt.c02foundations.led;

import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public class AppLed01
{

    public static void main(String[] args)
    {
        try {
            IGlobalFactory factory = 
                ComponentContextFactory.createGlobalFactory();
            
            ILed myLed = factory.createInstance(
                    "<http://purl.org/dcc/pt.c02foundations.led.Led>");
            
            myLed.show();
            myLed.off();
            myLed.on();
            
        } catch (ContextException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FactoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

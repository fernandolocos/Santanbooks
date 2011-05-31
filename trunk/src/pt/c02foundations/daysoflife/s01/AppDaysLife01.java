package pt.c02foundations.daysoflife.s01;

import java.util.Scanner;

import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppDaysLife01
{

    public static void main(String[] args)
    {
        try {
            IGlobalFactory factory = 
                ComponentContextFactory.createGlobalFactory();

            IDaysLife component = factory.createInstance(
                    "<http://purl.org/dcc/pt.c02foundations.daysoflife.s01.DaysLife>");
            
            Scanner keyboard = new Scanner(System.in);
            System.out.print("Insert your age: ");
            int age = Integer.parseInt(keyboard.nextLine());
            
            int days = component.computeDays(age);
            
            System.out.println("You have approximately " + days + " days of life");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

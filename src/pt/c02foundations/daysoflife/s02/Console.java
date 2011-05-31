package pt.c02foundations.daysoflife.s02;

import java.util.Scanner;

import pt.c02foundations.daysoflife.s01.IDaysLife;
import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.base.ComponentBase;

@Component(id="<http://purl.org/dcc/pt.c02foundations.daysoflife.s02.Console>",
           requires="<http://purl.org/dcc/pt.c02foundations.daysoflife.s01.IDaysLife>")
public class Console extends ComponentBase implements IRequires<IDaysLife>
{
    private IDaysLife theDaysLife;
    
    public void connect(IDaysLife theDaysLife)
    {
        this.theDaysLife = theDaysLife;

        activateConsole();
    }
    
    public void activateConsole()
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Insert your age: ");
        int age = Integer.parseInt(keyboard.nextLine());

        int days = theDaysLife.computeDays(age);
        System.out.println("You have approximately " + days + " days of life");
    }
}

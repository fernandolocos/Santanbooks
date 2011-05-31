package pt.c02foundations.fish.s07;

import pt.c02foundations.fish.s06.IPlant;
import anima.component.IReceptacle;

public interface IPlantReceptacle extends IReceptacle
{
    public void connect(IPlant plant);
}

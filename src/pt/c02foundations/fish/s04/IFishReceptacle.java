package pt.c02foundations.fish.s04;

import pt.c02foundations.fish.s01.IFish;
import anima.component.IReceptacle;

public interface IFishReceptacle extends IReceptacle
{
    public void connect(IFish theFish);
}

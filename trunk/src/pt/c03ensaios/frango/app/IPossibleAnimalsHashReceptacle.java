package pt.c03ensaios.frango.app;

import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import anima.component.IReceptacle;

/**
 * Interface feita pra prover a conex�o com a IPossibleAnimalsHash
 * 
 * @author Maur�cio Bertanha
 * @author Rodrigo Elizeu Gon�alves
 * 
 */
public interface IPossibleAnimalsHashReceptacle extends IReceptacle {
	public void connect(IPossibleAnimalsHash possibleAnimalsHash);
}

package pt.c03ensaios.frango.app;

import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import anima.component.IReceptacle;

/**
 * Interface feita pra prover a conexão com a IPossibleAnimalsHash
 * 
 * @author Maurício Bertanha
 * @author Rodrigo Elizeu Gonçalves
 * 
 */
public interface IPossibleAnimalsHashReceptacle extends IReceptacle {
	public void connect(IPossibleAnimalsHash possibleAnimalsHash);
}

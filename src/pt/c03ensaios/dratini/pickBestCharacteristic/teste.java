package pt.c03ensaios.dratini.pickBestCharacteristic;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;



public class teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BaseConhecimento baseC = new BaseConhecimento();
		IQuestionChooser q = new PickBestCharacteristic();
		System.out.println(q.selectBestQuestion(baseC, baseC.listaNomes()));

	}

}

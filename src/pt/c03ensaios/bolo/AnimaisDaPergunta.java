package pt.c03ensaios.bolo;


class AnimaisDaPergunta
{
	private int animais[];
	private int ultimo;
	
	public AnimaisDaPergunta(int IDanimal) {
		animais = new int[1];
		animais[0] = IDanimal;
		ultimo = 0;
	}
	
	public void addAnimal(int IDanimal) {
		int[] aux = new int[animais.length+1];
		for (int i = 0; i < animais.length; i++) {
			aux[i] = animais[i];
		}
		animais = aux;
		animais[animais.length-1] = IDanimal;
	}
	
	public int getAnimal() {
		return animais[ultimo++];
	}
	
	public void primeiro() {
		ultimo = 0;
	}
	
	public int getSize() {
		return animais.length;
	}
}
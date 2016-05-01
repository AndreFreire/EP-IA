import java.util.Random;

public class Mutation {

	 String selectedChrmosome[]; //Armazena o código binário do invididuo selecionado
	 int mutationPoint; //Ponto de mutação
	 String mutationChrmosome[]; //Cromossomo após mutação

	public Mutation(String[] selectedChrmosome) {
		setSelectedChrmosome(selectedChrmosome);
	}
	
	//Realiza a mutação simples
	public  void makeSimpleMutation(){
		String[] pop = new String[1];
		mutationPoint();
		for(int i = 0; i < getSelectedChrmosome()[0].length(); i++){
			String var = getSelectedChrmosome()[0].substring(i, i+1);
			if(i == getMutationPoint() - 1){
				if(i == 0){
					if(var == "1"){
						pop[0] = "0";
					} else {
						pop[0] = "1";
					}
				} else {
					if(var == "1"){
						pop[0] = pop[0] + "0";
					} else {
						pop[0] = pop[0] + "1";
					}
				}
			} else {
				if(i == 0){
					pop[0] = var;
				} else {
					pop[0] = pop[0] + var;
				}
			}
		}
		setMutationChrmosome(pop);
	}
	
	//Define o ponto de mutação.
	public void mutationPoint(){
		int point = randInt(1, getSelectedChrmosome()[0].length());
		setMutationPoint(point);
	}
	
	/**
	 * Função que gera números aleatórios.
	 * @param min -> Valor minimo do range
	 * @param max -> Valor maximo do range
	 * @return 
	 */
	public  int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public  String[] getSelectedChrmosome() {
		return selectedChrmosome;
	}

	public  void setSelectedChrmosome(String[] selectedChrmosome) {
		this.selectedChrmosome = selectedChrmosome;
	}
	
	public  int getMutationPoint() {
		return mutationPoint;
	}

	public  void setMutationPoint(int mutationPoint) {
		this.mutationPoint = mutationPoint;
	}
	
	public  String[] getMutationChrmosome() {
		return mutationChrmosome;
	}

	public  void setMutationChrmosome(String[] mutationChrmosome) {
		this.mutationChrmosome = mutationChrmosome;
	}
}

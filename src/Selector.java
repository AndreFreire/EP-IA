import java.util.Random;

public class Selector {
	
	int fitnessVector[]; //Armazena o fitness de cada individuo
	int totalFitness; //Armazena a soma de todos os fitness
	double porcentFitnessVector[]; //Armazena o porcentual dos fitness de cada individuo
	double rollerVector[]; //Armazena a roleta
	int selectedIndex[]; //Armazena o indice do individuo selecionados
	String selectedChrmosome[]; //Armazena o código binário do invididuo selecionado
	int maxFitness;
	
	public int getMaxFitness() {
		return maxFitness;
	}


	public void setMaxFitness(int maxFitness) {
		this.maxFitness = maxFitness;
	}


	public int getMinFitness() {
		return minFitness;
	}


	public void setMinFitness(int minFitness) {
		this.minFitness = minFitness;
	}


	public int getMedFitness() {
		return medFitness;
	}


	public void setMedFitness(int medFitness) {
		this.medFitness = medFitness;
	}

	int minFitness;
	int medFitness;

	/**
	 * Calcula o fitness de cada cromossomo
	 * @param population população que seŕa calculada o fitness
	 * @return
	 */
	public int calcFitness(Population population){
		String pop[] = population.getPop();
		int fitness[] = new int[population.getPopulationSize()];
	    for(int nChrmosome = 0; nChrmosome < population.getPopulationSize(); nChrmosome++){
	    	fitness[nChrmosome] = Integer.parseInt(pop[nChrmosome], 2);
	    }
	    setFitnessVector(fitness);
		return 0;
	}


	/**
	 * Calcula o maior fitness da população.
	 */
	public void calcMaxFitness(){
		int maxFitness = getFitnessVector()[0];		
		for (int i = 0; i < getFitnessVector().length; i++) {
			if(getFitnessVector()[i] > maxFitness){
				maxFitness = getFitnessVector()[i];
			}
		}
		setMaxFitness(maxFitness);
	}
	
	/**
	 * Calcula o menor fitness da população.
	 */
	public void calcMinFitness(){
		int minFitness = getFitnessVector()[0]; 
		for (int i = 0; i < getFitnessVector().length; i++) {
			if(getFitnessVector()[i] < maxFitness){
				maxFitness = getFitnessVector()[i];
			}
		}
		setMinFitness(minFitness);
	}
	
	/*
	 * Calcula a media de todos os fitness da população
	 */
	public void calcMedFitness(){
		int medFitness = getTotalFitness()/getFitnessVector().length;
		setMedFitness(medFitness);
	}
	
	/**
	 * Calcula o porcentual do fitness, (fitness individual/fitness total) e armazena em um vetor
	 */
	public void calcPorcentFitness(){
		double porcentFitness[] = new double[getFitnessVector().length];
		for(int nChrmosome = 0; nChrmosome < getFitnessVector().length; nChrmosome++){
			porcentFitness[nChrmosome] = ((getFitnessVector()[nChrmosome] * 100) / (getTotalFitness()));
		}
		setPorcentFitnessVector(porcentFitness);
	}
	
	/**
	 * Calcula o fitness total da população.
	 */
	public void calcTotalFitness(){
		int totalFitness = 0;
		for (int i = 0; i < getFitnessVector().length; i++) {
			totalFitness += getFitnessVector()[i];
		}
		setTotalFitness(totalFitness);
	}
	
	//Simula a criação de uma roleta com as porcentagens dos fitness
	public void makeRoller(){
		double roller[] = new double[getPorcentFitnessVector().length];
		for(int i = 0; i < getPorcentFitnessVector().length; i++){
			if(i > 0){
				roller[i] = getPorcentFitnessVector()[i] + roller[i-1];
			} else if(i == 0){
				roller[i] = getPorcentFitnessVector()[i];
			}
		}
		setRollerVector(roller);
	}
	
	//Seleciona o individuo através da roleta
	public void selectChrmosomeIndex(int qttChrmosome, Population pop){
		int selectedIndex[] = new int[qttChrmosome];
		String selectedChrmosome[] = new String[qttChrmosome]; 
		for(int nChrmosome = 0; nChrmosome < qttChrmosome; nChrmosome++){
			int random = randInt(1, 100); //Sorteia um número de 1 a 100
			boolean get = false;
			for(int i = 0; i < getRollerVector().length; i++){
				if(!get){
					if(random < getRollerVector()[i]){
						selectedIndex[nChrmosome] = i; //Insere o indice do individuo no vetor
						selectedChrmosome[nChrmosome] = pop.getPop()[i]; //Insere o código binário do individuo
						get = true;
					}
				}
			}
		}
		
		setSelectedIndex(selectedIndex);
		setSelectedChrmosome(selectedChrmosome);
	}
	
	public void updateGeneration(String[] updateChrmossome, Population pop){
		for(int i = 0; i < getRollerVector().length; i++){
			for (int j = 0; j < getSelectedIndex().length; j++){
				if(i == getSelectedIndex()[j]){
					//System.out.println("individuo "+j+" : "+updateChrmossome[j]);
					pop.getPop()[i] = updateChrmossome[j];
				}
			}
		}
	}

	/**
	 * Imprime o fitness de cada cromossomo.
	 */
	public void printFitness(){
		for (int i = 0; i < getFitnessVector().length; i++) {
		   System.out.println("Cromossomo: "+i+" - fitness: "+ fitnessVector[i]);
		}
	}
	
	/**
	 * Imprime a roleta.
	 */
	public void printRoller(){
		for (int i = 0; i < getRollerVector().length; i++) {
		   System.out.println("Cromossomo: "+i+" - fitness %: "+ rollerVector[i]);
		}
	}
	
	/**
	 * Imprime a porcentagem do fitness de cada cromossomo.
	 */
	public void printFitnessPorcent(){
		for (int i = 0; i < getPorcentFitnessVector().length; i++) {
		   System.out.println("Cromossomo: "+i+" - fitness porcentual: "+ porcentFitnessVector[i]);
		}
	}
	
	/**
	 * Função que gera números aleatórios.
	 * @param min -> Valor minimo do range
	 * @param max -> Valor maximo do range
	 * @return 
	 */
	public int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public int[] getFitnessVector() {
		return fitnessVector;
	}

	public void setFitnessVector(int[] fitness) {
		this.fitnessVector = fitness;
	}
	
	public int getTotalFitness() {
		return totalFitness;
	}

	public void setTotalFitness(int totalFitness) {
		this.totalFitness = totalFitness;
	}
	
	public double[] getPorcentFitnessVector() {
		return porcentFitnessVector;
	}

	public void setPorcentFitnessVector(double[] porcentFitnessVector) {
		this.porcentFitnessVector = porcentFitnessVector;
	}
	
	public double[] getRollerVector() {
		return rollerVector;
	}

	public void setRollerVector(double[] rollerVector) {
		this.rollerVector = rollerVector;
	}
	
	public int[] getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int[] selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public String[] getSelectedChrmosome() {
		return selectedChrmosome;
	}

	public void setSelectedChrmosome(String[] selectedChrmosome) {
		this.selectedChrmosome = selectedChrmosome;
	}
}

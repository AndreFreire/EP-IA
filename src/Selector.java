import java.util.Random;

public class Selector implements Cloneable{
	
	float fitnessVector[]; //Armazena o fitness de cada individuo
	float totalFitness; //Armazena a soma de todos os fitness
	float porcentFitnessVector[]; //Armazena o porcentual dos fitness de cada individuo
	float rollerVector[]; //Armazena a roleta
	int selectedIndex[]; //Armazena o indice do individuo selecionados
	String selectedChrmosome[]; //Armazena o código binário do invididuo selecionado
	float maxFitness;
	
	float minFitness;
	float medFitness;
	
	public float fBump(float x, float y){
		float temp0 = (float) ((Math.pow(Math.cos(x), 4)) + (Math.pow(Math.cos(y), 4)));
		float temp1 = (float) (2 * ((Math.pow(Math.cos(x), 2)) * (Math.pow(Math.cos(y), 2))));
		float temp2 = (float) (Math.sqrt((Math.pow(x, 2)) + (Math.pow(2 * y, 2))));
		float z = Math.abs((temp0-temp1)/temp2);
		z = -z;
		return z;
	}
	
	public float fGold(float x, float y){
		float a = (float) (Math.pow(1*(x+y+1), 2)*(19-14*x+3*(Math.pow(x, 2))-14*y+6*x*y+3*(Math.pow(y, 2))));
		float b = (float) (30+(Math.pow((2*x-3*y), 2))*(18-32*x+12*(Math.pow(x, 2))+48*y-36*x*y+27*Math.pow(y, 2)));
		float z = a*b;
		z = -z;
		return z;
	}
	
	public float fRastrigin(float x, float y){
		float zx = (float) (Math.pow(x, 2)-10*Math.cos(2*Math.PI*x)+10);
		float zy = (float) (Math.pow(y, 2)-10*Math.cos(2*Math.PI*y)+10);
		float z = zx+zy;
		z = -z;
		return z;
	}
	
	public int binaryToInt(String binary){
		int value = Integer.parseInt(binary, 2);
		return value;
	}
	
	/**
	 * Calcula o fitness de cada cromossomo
	 * @param population população que seŕa calculada o fitness
	 * @return
	 */
	public int calcFitness(Population population){
		String pop[] = population.getPop();
		float fitness[] = new float[population.getPopulationSize()];
		String stringFirstPart, stringSecondPart;
		float intFirstPart, intSecondPart;
		int size = population.getSizeChrmosome();
		int half = size/2;
	    for(int nChrmosome = 0; nChrmosome < population.getPopulationSize(); nChrmosome++){
	    	//Divisão do cromossomo em 2 partes.
	    	stringFirstPart = pop[nChrmosome].substring(0, half);
	    	stringSecondPart = pop[nChrmosome].substring(half, size);
	    	//Conversão binario inteiro das duas partes
	    	intFirstPart = binaryToInt(stringFirstPart);
	    	intSecondPart = binaryToInt(stringSecondPart);
	    	//preencher o vetor de fitness com os valores
	    	//fitness[nChrmosome] = fGold(intFirstPart, intSecondPart);
	    	fitness[nChrmosome] = fRastrigin(intFirstPart, intSecondPart);
	    	//fitness[nChrmosome] = fBump(intFirstPart, intSecondPart);
	    }
	    setFitnessVector(fitness);
		return 0;
	}

	/**
	 * Calcula o maior fitness da população.
	 */
	public void calcMaxFitness(){
		float maxFitness = getFitnessVector()[0];		
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
		float minFitness = getFitnessVector()[0]; 
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
		float medFitness = getTotalFitness()/getFitnessVector().length;
		setMedFitness(medFitness);
	}
	
	/**
	 * Calcula o porcentual do fitness, (fitness individual/fitness total) e armazena em um vetor
	 */
	public void calcPorcentFitness(){
		float porcentFitness[] = new float[getFitnessVector().length];
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
		float roller[] = new float[getPorcentFitnessVector().length];
		for(int i = 0; i < getPorcentFitnessVector().length; i++){
			if(i > 0){
				roller[i] = getPorcentFitnessVector()[i] + roller[i-1];
			} else if(i == 0){
				roller[i] = getPorcentFitnessVector()[i];
			}
			//System.out.println(roller[i]);
		}
		setRollerVector(roller);
	}
	
	//Seleciona o individuo através da roleta
	public void selectChrmosomeIndex(int qttChrmosome, Population pop){
		int selectedIndex[] = new int[qttChrmosome];
		String selectedChrmosome[] = new String[qttChrmosome]; 
		for(int nChrmosome = 0; nChrmosome < qttChrmosome; nChrmosome++){
			int random = randInt(0, 99); //Sorteia um número de 0 a 99
			float getted = 0;
			boolean get = false;
			for(int i = 0; i < getRollerVector().length; i++){
				if(!get){
					if(random < getRollerVector()[i] && getRollerVector()[i] != getted){
						selectedIndex[nChrmosome] = i; //Insere o indice do individuo no vetor
						selectedChrmosome[nChrmosome] = pop.getPop()[i]; //Insere o código binário do individuo
						get = true;
						getted = getRollerVector()[i];
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
					System.out.println("individuo atualizado: "+j+" : "+updateChrmossome[j]);
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
	
	public float[] getFitnessVector() {
		return fitnessVector;
	}

	public void setFitnessVector(float[] fitness) {
		this.fitnessVector = fitness;
	}
	
	public float getTotalFitness() {
		return totalFitness;
	}

	public void setTotalFitness(float totalFitness) {
		this.totalFitness = totalFitness;
	}
	
	public float[] getPorcentFitnessVector() {
		return porcentFitnessVector;
	}

	public void setPorcentFitnessVector(float[] porcentFitnessVector) {
		this.porcentFitnessVector = porcentFitnessVector;
	}
	
	public float[] getRollerVector() {
		return rollerVector;
	}

	public void setRollerVector(float[] rollerVector) {
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
	
	public float getMaxFitness() {
		return maxFitness;
	}


	public void setMaxFitness(float maxFitness) {
		this.maxFitness = maxFitness;
	}


	public float getMinFitness() {
		return minFitness;
	}


	public void setMinFitness(float minFitness) {
		this.minFitness = minFitness;
	}


	public float getMedFitness() {
		return medFitness;
	}


	public void setMedFitness(float medFitness) {
		this.medFitness = medFitness;
	}

	public Selector clone() {
		try {
			return (Selector) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}

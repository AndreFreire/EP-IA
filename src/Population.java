import java.util.Random;

public class Population implements Cloneable{
	
	//Vetor de população.
	String pop[];
	int generation;
	int generationLimit;
	int timeLimit;
	int populationSize;
	int sizeChrmosome;
	String nextGeneration[];

	public String[] getNextGeneration() {
		return nextGeneration;
	}

	public void setNextGeneration(String[] nextGeneration) {
		this.nextGeneration = nextGeneration;
	}

	//Inicializa a população, definindo os critérios de paradas.
	public Population(int timeLimit, int generationLimit){
		setGenerationLimit(generationLimit);
		setTimeLimit(timeLimit);
	}
	
	//Verifica o tempo de execução da população.
	public boolean verifyTime(int time){
		if(time > getTimeLimit()){
			return true;
		} else {
			return false;
		}
	}

	//Verifica a geração da população
	public boolean verifyGeneration(int generation){
		if(generation > getGenerationLimit()){
			return true;
		} else {
			return false;
		}
	}
	
	//Função que verifica os critérios de paradas.
	public boolean verifyStop(int time, int generation){
		if(verifyGeneration(generation) || verifyTime(time)){
			return true;
		} else {
			return false;
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
	
	/**Função para gerar o tamanho da população.
	 * @param largeness grandeza da população, preferencial pontencias de 10. 
	 * @return retorna o tamanho da população.
	 */
	public int popSize(int largeness){		
		System.out.println("Size: "+largeness);
		setPopulationSize(largeness);
		return largeness;
	}

	/**Função que preenche o vetor população após a segunad geração.
	 * @param pop vetor de população.
	 * @return
	 */
	public String[] immediateReplacement(String[] newPopulation){
		for(int i = 0; i < newPopulation.length; i++){
			this.pop[i] = newPopulation[i];
		}
		return pop;
	}
	
	/**Função que preenche o vetor população.
	 * @param pop vetor de população.
	 * @return
	 */
	public  String[] fillPop(String[] pop){
		for(int x = 0; x < getPopulationSize(); x++){
			for(int y = 0; y < getSizeChrmosome(); y++){
				if(randInt(0, 1) < 0.5){
					if(y == 0){
						pop[x] = "0";
					} else {
						pop[x] = pop[x] + "0";
					}
				} else {
					if(y == 0){
						pop[x] = "1";
					} else {
						pop[x] = pop[x] + "1";
					}
				}
			}
		}
		return pop;
	}
	
	/** Função que inicia a população.
	 * @param largeness -> Grandeza da população.
	 * @param sizeChrmosome -> Tamanho do cromossomo
	 * @return
	 */
	public  String[] startPop(int largeness, int sizeChrmosome){
		setSizeChrmosome(sizeChrmosome);	
		setPopulationSize(largeness);
		 
		pop = new String[largeness];
		
		setPop(fillPop(this.pop));
		
		return pop;
	}
	
	public  String[] newPop(int largeness, int sizeChrmosome){;
		setSizeChrmosome(sizeChrmosome);	
		
		int populationSize = popSize(largeness); 
		String[] pop = new String[populationSize];
			
		return pop;
	}
	
	public void updatePop(int index, String chrmosome){
		this.pop[index] = chrmosome;
	}
	
	public String[] getPop() {
		return pop;
	}

	public void setPop(String[] pop) {
		this.pop = pop;
	}

	public  int getTimeLimit() {
		return timeLimit;
	}

	public  void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public  int getGenerationLimit() {
		return generationLimit;
	}

	public  void setGenerationLimit(int generationLimit) {
		this.generationLimit = generationLimit;
	}
	
	public  int getPopulationSize() {
		return populationSize;
	}

	public  void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public  int getSizeChrmosome() {
		return sizeChrmosome;
	}

	public  void setSizeChrmosome(int sizeChrmosome) {
		this.sizeChrmosome = sizeChrmosome;
	}
	
	public  int getGeneration() {
		return generation;
	}

	public  void setGeneration(int generation) {
		this.generation = generation;
	}

	public  void printChrmosome(int index){
		String vector[] = getPop();
		System.out.printf(index+"º cromossomo: "+ vector[index]);
		System.out.printf("\n");
	}
	
	public  void printPopulation(){
		String vector[] = getPop();

		int i;
		for (i=0; i<vector.length; i++) {
		   System.out.printf(i+"º cromossomo: "+ vector[i]);
		   System.out.printf("\n");
		}
	}
	public Population clone(){
		try {
			return (Population) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
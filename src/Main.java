import java.util.Random;

public class Main {

	public static void main(String[] args) {
		int time = 0;
		int CHRMO_SIZE = 5; //Tamanho de cada cromossomo
		int CROSSOVER_POINT = 1; //Quantidade de pontos que serão feitos o crossover.
		int GENERATION_LIMIT = 10;
		int POP_SIZE = 100;
		double PROB_CROSSOVER = 1; //Probabilidade de crossover
		double PROB_MUTATION = 0.3; //Probabilidade de mutação.
		Random random = new Random();
		
		Population popA = new Population(3, GENERATION_LIMIT);  //Population(timeLimit, GENERATION_LIMIT)
		Selector bi = new Selector();
		
		popA.startPop(POP_SIZE, CHRMO_SIZE);  //Inicializando população..
		
		for(int atualGeneration = 0; atualGeneration < popA.getGenerationLimit(); atualGeneration++){
			System.out.println(atualGeneration+"ª Geração");
			if(!popA.verifyStop(time, atualGeneration)){
				popA.setGeneration(atualGeneration);
				popA.setSizeChrmosome(CHRMO_SIZE);
						
				popA.printPopulation();
								
				bi.calcFitness(popA);  //Calculando fitness de cada individuo
				bi.calcTotalFitness();  //Calculando fitness total da população
				
				//bi.printFitness();
				bi.calcPorcentFitness();  //Calculando fitness porcentual de cada individuo da população
				//bi.printFitnessPorcent();
				
				//Quanto mais próximo do fim das gerações, menor a probabilidade de crossover.
				if(atualGeneration/popA.getGenerationLimit() > popA.getGenerationLimit() * 0.7){// revisar isso 
					PROB_CROSSOVER = PROB_CROSSOVER * 0.5;
				}
				if(random.nextDouble() < PROB_CROSSOVER){
					bi.makeRoller(); //Criando a seleção por roleta
					//bi.printRoller();
					bi.selectChrmosomeIndex(2, popA);  //Selecionando os individuos através da roleta 
					
					//Inicio da operação de Crossover
					Crossover cros = new Crossover(bi.getSelectedChrmosome());
					cros.divCrossover(CROSSOVER_POINT, CHRMO_SIZE); //Crossover de x pontos
					//cros.printCrossover();
					bi.updateGeneration(cros.getFinalCrossoverChrmosome(), popA); //Atualiza a geração atual com os novos cromossomos
				}
				
				//Quanto mais próximo do fim das gerações, menor a probabilidade de mutação.
				if(atualGeneration/popA.getGenerationLimit() > popA.getGenerationLimit() * 0.7){ // revisar isso 
					PROB_MUTATION = PROB_MUTATION * 0.5;
				}		
				if(random.nextDouble() < PROB_MUTATION){  //Probabilidade de mutação.
					//Inicio da operação de Mutação
					bi.makeRoller(); //Criando a seleção por roleta
					//bi.printRoller();
					bi.selectChrmosomeIndex(1, popA);  //Selecionando os individuos através da roleta 
				
					Mutation mut = new Mutation(bi.getSelectedChrmosome());
					mut.makeSimpleMutation();
					bi.updateGeneration(mut.getMutationChrmosome(), popA);
				}
				
				bi.calcMedFitness(); //Cálculo da média do fitness no fim da geração.
				//lastMedFitness = bi.getMedFitness();
				
				System.out.println("Fitness Total: "+bi.getTotalFitness());
				System.out.println("Fitness Médio: "+bi.getMedFitness());
				//bi.printFitnessPorcent();
				
				/*popA.setToNextGeneration(cros.getFinalCrossoverChrmosome()); //Define os cromossomos que serão passados para a próxima geração.
				
				for(int i = 0; i < popA.getToNextGeneration().length; i++){
					System.out.println(popA.getToNextGeneration()[i]);
				}*/
			}
			System.out.println("\n-- x --\n");
		}
	}
}
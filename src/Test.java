import java.util.Random;

public class Test {
	Random random = new Random();
	
	
	public void makeTest(int START_WITH,int CHRMO_SIZE,int CROSSOVER_POINT,int GENERATION_LIMIT,
			int POP_SIZE,double PROB_CROSSOVER, double PROB_MUTATION,
			int rowMedFitnessLenght, float differenceToStop){
		
		Population popA = new Population(GENERATION_LIMIT);  //Population(timeLimit, GENERATION_LIMIT)
		Selector bi = new Selector(rowMedFitnessLenght, differenceToStop);
		
		popA.startPop(POP_SIZE, CHRMO_SIZE);  //Inicializando população..
		
		for(int atualGeneration = 0; atualGeneration < popA.getGenerationLimit(); atualGeneration++){
			if(bi.isStop()) break;
			System.out.println(atualGeneration+"ª Geração");
				popA.setGeneration(atualGeneration);
				popA.setSizeChrmosome(CHRMO_SIZE);
				
				Population pop_aux = popA.clone();
				Selector bi_aux = bi.clone();		
			
				bi.calcFitness(popA);  //Calculando fitness de cada individuo
				bi.calcTotalFitness();  //Calculando fitness total da população
	
				bi.calcPorcentFitness();  //Calculando fitness porcentual de cada individuo da população
				
				bi.orderPopulation(popA); //Ordena a população para realizar o elitismo
				
				for(int count = 0; count < 50; count ++){
				//Quanto mais próximo do fim das gerações, menor a probabilidade de crossover.
				if((double) atualGeneration/popA.getGenerationLimit() > 0.7){
					PROB_CROSSOVER = PROB_CROSSOVER * 0.5;
				}
				
				if(random.nextDouble() < PROB_CROSSOVER){
					//System.out.println("fez cross");
					bi.makeRoller(); //Criando a seleção por roleta
					//bi.printRoller();
					bi.selectChrmosomeIndex(2, popA, START_WITH);  //Selecionando os individuos através da roleta 
					
					//Inicio da operação de Crossover
					Crossover cros = new Crossover(bi.getSelectedChrmosome());
					cros.divCrossover(CROSSOVER_POINT, CHRMO_SIZE); //Crossover de x pontos
					//cros.printCrossover();
					bi.updateGeneration(cros.getFinalCrossoverChrmosome(), popA); //Atualiza a geração atual com os novos cromossomos
				}
				
				//Quanto mais próximo do fim das gerações, menor a probabilidade de mutação.
				if((double) atualGeneration/popA.getGenerationLimit() > 0.7){ 
					PROB_MUTATION = PROB_MUTATION * 0.5;
				}		
				if(random.nextDouble() < PROB_MUTATION){  //Probabilidade de mutação.
					//System.out.println("fez mut");
					//Inicio da operação de Mutação
					bi.makeRoller(); //Criando a seleção por roleta
					//bi.printRoller();
					bi.selectChrmosomeIndex(1, popA, START_WITH);  //Selecionando os individuos através da roleta 
				
					Mutation mut = new Mutation(bi.getSelectedChrmosome());
					mut.makeSimpleMutation();
					bi.updateGeneration(mut.getMutationChrmosome(), popA);
				}
				}
				try{
					for(int i = 0; i < popA.getPopulationSize(); i++){
						if(bi_aux.getFitnessVector()[i]> bi.getFitnessVector()[i]){
							popA.updatePop(i, pop_aux.getPop()[i]);
						}
					}
					
					bi.calcFitness(popA);  //Calculando fitness de cada individuo
					bi.calcTotalFitness();  //Calculando fitness total da população
					
					//bi.printFitness();
					bi.calcPorcentFitness();  //Calculando fitness porcentual de cada individuo da população
					//bi.printFitnessPorcent();
				}catch(Exception e){}
				
				//bi.printFitness();
				bi.calcTotalFitness();	
				bi.calcMedFitness();
				bi.calcMaxFitness();
				bi.calcMinFitness();
				System.out.println("Fitness Total: "+bi.getTotalFitness());
				System.out.println("Fitness Médio: "+bi.getMedFitness());
				System.out.println("Fitness Máximo: "+bi.getMaxFitness());
				System.out.println("Fitness Minimo: "+bi.getMinFitness());
				//bi.printFitnessPorcent();
				
				popA.setNextGeneration(popA.getPop()); //Define os cromossomos que serão passados para a próxima geração.
				popA.immediateReplacement(popA.getNextGeneration());
			System.out.println("\n-- x --\n");
		}
	}
}

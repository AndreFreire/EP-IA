import java.util.Random;

public class Main {

	public static void main(String[] args) {
		int time = 0;
		int ChrmoSize = 4; //Tamanho de cada cromossomo
		int qttCrossoverPoint = 1; //Quantidade de pontos que serão feitos o crossover.
		//int lastMedFitness = 0; //Fitness médio da ultima geração.
		int generationLimit = 10;
		double probCrossover = 0.3; //Probabilidade de crossover
		double probMutation = 0.3; //Probabilidade de mutação.
		Random random = new Random();
		
		Population popA = new Population(3, generationLimit);  //Population(timeLimit, generationLimit)
		Selector bi = new Selector();
		
		popA.startPop(5, ChrmoSize);  //Inicializando população..
		
		for(int atualGeneration = 0; atualGeneration < popA.getGenerationLimit(); atualGeneration++){
			System.out.println(atualGeneration+"ª Geração");
			if(!popA.verifyStop(time, atualGeneration)){
				popA.setGeneration(atualGeneration);
						
				popA.printPopulation();
								
				bi.calcFitness(popA);  //Calculando fitness de cada individuo
				bi.calcTotalFitness();  //Calculando fitness total da população
				
				//bi.printFitness();			
				bi.calcPorcentFitness();  //Calculando fitness porcentual de cada individuo da população
				//bi.printFitnessPorcent();
				
				//Quanto mais próximo do fim das gerações, menor a probabilidade de crossover.
				if(atualGeneration/popA.getGenerationLimit() > popA.getGenerationLimit() * 0.7){// revisar isso 
					probCrossover = probCrossover * 0.5;
				}
				if(random.nextDouble() < probCrossover){
					bi.makeRoller(); //Criando a seleção por roleta
					bi.printRoller();
					bi.selectChrmosomeIndex(2, popA);  //Selecionando os individuos através da roleta 
					
					//Inicio da operação de Crossover
					Crossover cros = new Crossover(bi.getSelectedChrmosome());
					cros.divCrossover(qttCrossoverPoint); //Crossover de x pontos
					cros.printCrossover();
					bi.updateGeneration(cros.getFinalCrossoverChrmosome(), popA); //Atualiza a geração atual com os novos cromossomos
				}
				
				//Quanto mais próximo do fim das gerações, menor a probabilidade de mutação.
				if(atualGeneration/popA.getGenerationLimit() > popA.getGenerationLimit() * 0.7){ // revisar isso 
					probMutation = probMutation * 0.5;
				}		
				if(random.nextDouble() < probMutation){  //Probabilidade de mutação.
					//Inicio da operação de Mutação
					bi.makeRoller(); //Criando a seleção por roleta
					bi.printRoller();
					bi.selectChrmosomeIndex(1, popA);  //Selecionando os individuos através da roleta 
				
					Mutation mut = new Mutation(bi.getSelectedChrmosome());
					mut.makeSimpleMutation();
					bi.updateGeneration(mut.getMutationChrmosome(), popA);
				}

		
				bi.calcMedFitness(); //Cálculo da média do fitness no fim da geração.
				//lastMedFitness = bi.getMedFitness();
				
				System.out.println("Fitness Total: "+bi.getTotalFitness());
				System.out.println("Fitness Médio: "+bi.getMedFitness());
				bi.printFitnessPorcent();
				
				/*popA.setToNextGeneration(cros.getFinalCrossoverChrmosome()); //Define os cromossomos que serão passados para a próxima geração.
				
				for(int i = 0; i < popA.getToNextGeneration().length; i++){
					System.out.println(popA.getToNextGeneration()[i]);
				}*/
			}
			System.out.println("\n-- x --\n");
		}
	}
}
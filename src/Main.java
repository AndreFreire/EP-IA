public class Main {

	public static void main(String[] args) {
		int time = 0;
		int ChrmoSize = 4; //Tamanho de cada cromossomo
		int qttChrmoRoller = 2; //Quantidade de cromossomo que serão selecionados na roleta
		int qttCrossoverPoint = 1; //Quantidade de pontos que serão feitos o crossover.
		int lastMedFitness = 0; //Fitness médio da ultima geração.
		double probMutation = 0.5; //Probabilidade de mutação.
		int generationLimit = 10;
		
		Population popA = new Population(3, generationLimit);  //Population(timeLimit, generationLimit)
		Selector bi = new Selector();
		
		for(int atualGeneration = 0; atualGeneration < popA.getGenerationLimit(); atualGeneration++){
			System.out.println(atualGeneration+"ª Geração");
			if(!popA.verifyStop(time, atualGeneration)){
				popA.setGeneration(atualGeneration);
				popA.startPop(2, ChrmoSize, atualGeneration);  //Inicializando população..		
				popA.printPopulation();
								
				bi.calcFitness(popA);  //Calculando fitness de cada individuo
				bi.calcTotalFitness();  //Calculando fitness total da população
				
				if(lastMedFitness < bi.getTotalFitness()){ //Critério de troca de geração. (Media do fitness anterior tem que 
															//ser menos que o fitness total da geração atual)
					//bi.printFitness();			
					bi.calcPorcentFitness();  //Calculando fitness porcentual de cada individuo da população
					//bi.printFitnessPorcent();
					bi.makeRoller(); //Criando a seleção por roleta
					//bi.printRoller();
					bi.selectChrmosomeIndex(qttChrmoRoller, popA);  //Selecionando os individuos através da roleta 
					
					//Inicio da operação de Crossover
					Crossover cros = new Crossover(bi.getSelectedChrmosome());
					cros.divCrossover(qttCrossoverPoint); //Crossover de x pontos
					//cros.printCrossover();
					bi.updateGeneration(cros.getFinalCrossoverChrmosome(), popA); //Atualiza a geração atual com os novos cromossomos
						
					/*if(rand.randomNum(0, 1) > probMutation){  //Probabilidade de mutação.
						//Inicio da operação de Mutação
						Mutation mut = new Mutation(bi.getSelectedChrmosome());
						mut.makeSimpleMutation();
						bi.updateGeneration(mut.getMutationChrmosome(), popA);
					}*/

					//Quanto mais próximo do fim das gerações, menor a probabilidade de mutação.
					if(atualGeneration/popA.getGenerationLimit() > popA.getGenerationLimit() * 0.7){ 
						probMutation = probMutation * 0.5;
					}
					
					bi.calcMedFitness(); //Cálculo da média do fitness no fim da geração.
					lastMedFitness = bi.getMedFitness();
					
					System.out.println("Fitness Total: "+bi.getTotalFitness());
					System.out.println("Fitness Médio: "+bi.getMedFitness());
					bi.printFitnessPorcent();
					
					popA.setToNextGeneration(cros.getFinalCrossoverChrmosome()); //Define os cromossomos que serão passados para a próxima geração.
					
					for(int i = 0; i < popA.getToNextGeneration().length; i++){
						System.out.println(popA.getToNextGeneration()[i]);
					}
				}
			}
			System.out.println("\n-- x --\n");
		}
	}
}
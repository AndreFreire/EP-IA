import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;

public class Main {

	public static void main(String[] args) throws IOException {
		int START_WITH = 0; //Usado para controlar o elitismo.
		int CHRMO_SIZE = 8; //Tamanho de cada cromossomo
		int CROSSOVER_POINT = 1; //Quantidade de pontos que serão feitos o crossover.
		int GENERATION_LIMIT = 100;
		int POP_SIZE = 100;
		double PROB_CROSSOVER = 0.05; //Probabilidade de crossover
		double PROB_MUTATION = 0.5; //Probabilidade de mutação.
		Random random = new Random();
		
		Population popA = new Population(GENERATION_LIMIT);  //Population(timeLimit, GENERATION_LIMIT)
		Selector bi = new Selector();
		
		popA.startPop(POP_SIZE, CHRMO_SIZE);  //Inicializando população..
		FileWriter arq = new FileWriter("teste.txt");
		Writer write = new Writer(arq);
		
		for(int atualGeneration = 0; atualGeneration < popA.getGenerationLimit(); atualGeneration++){
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
				/*Quanto mais próximo do fim das gerações, menor a probabilidade de crossover.
				if((double) atualGeneration/popA.getGenerationLimit() > 0.7){
					PROB_CROSSOVER = PROB_CROSSOVER * 0.5;
				}*/
				
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
				
				/*Quanto mais próximo do fim das gerações, menor a probabilidade de mutação.
				if((double) atualGeneration/popA.getGenerationLimit() > 0.7){ 
					PROB_MUTATION = PROB_MUTATION * 0.5;
				}*/		
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
				
				write.header(PROB_CROSSOVER, PROB_MUTATION, GENERATION_LIMIT, POP_SIZE, CHRMO_SIZE, START_WITH);
				//write.aboutFitness(bi.getTotalFitness(), bi.getMedFitness(), bi.getMaxFitness(), bi.getMinFitness(), atualGeneration);
				write.excel(bi.getTotalFitness(), bi.getMedFitness(), bi.getMaxFitness(), bi.getMinFitness(), atualGeneration);
				
				arq.close();
				
				popA.setNextGeneration(popA.getPop()); //Define os cromossomos que serão passados para a próxima geração.
				//popA.immediateReplacement(popA.getNextGeneration());
			System.out.println("\n-- x --\n");
		}
	}
}
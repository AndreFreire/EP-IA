public class Main {

	public static void main(String[] args) {

		int START_WITH = 5; //Usado para controlar o elitismo.
		int CHRMO_SIZE = 8; //Tamanho de cada cromossomo
		int CROSSOVER_POINT = 1; //Quantidade de pontos que serão feitos o crossover.
		int GENERATION_LIMIT = 100;
		int POP_SIZE = 100;
		double PROB_CROSSOVER = 1; //Probabilidade de crossover
		double PROB_MUTATION = 1; //Probabilidade de mutação.
		int rowMedFitnessLenght = 10;
		float differenceToStop = (float) 0.01;
		Test test = new Test();
		test.makeTest(START_WITH, CHRMO_SIZE, CROSSOVER_POINT, GENERATION_LIMIT, 
				POP_SIZE, PROB_CROSSOVER, PROB_MUTATION, rowMedFitnessLenght, differenceToStop);
	}
}
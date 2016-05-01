import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

	PrintWriter gravarArq;
	
	public Writer(FileWriter arq){
		this.gravarArq = new PrintWriter(arq);
	}
	
	public void header(double probCross, double probMutation, int generationLimit, int popSize, int chrmoSize, int eletism){
		this.gravarArq.printf("+---------------------------------------------+%n");
		gravarArq.printf("PARAMETROS UTILIZADOS: %n");
		gravarArq.printf("Função de otimização: %n");
		gravarArq.printf("Taxa de Cromossomo: "+probCross+"%n");
		gravarArq.printf("Taxa de Mutação: "+probMutation+"%n");
		gravarArq.printf("Quantidade de Gerações: "+generationLimit+"%n");
		gravarArq.printf("Tamanho da População: "+popSize+"%n");
		gravarArq.printf("Tamanho do Cromossomo: "+chrmoSize+"%n");
		gravarArq.printf("Taxa de elitismo: "+eletism+"%n");
		gravarArq.printf("+---------------------------------------------+%n");
	}
	
	public void aboutFitness(float totalFitness, float medFitness, float maxFitness, float minFitness, float atualGeneration){
		gravarArq.printf("- "+atualGeneration+"ª Geração%n");
		gravarArq.printf("Fitness Total: "+totalFitness+"%n");
		gravarArq.printf("Fitness Médio: "+medFitness+"%n");
		gravarArq.printf("Fitness Máximo: "+maxFitness+"%n");
		gravarArq.printf("Fitness Minimo: "+minFitness+"%n");
		gravarArq.printf("%n-------- x ----------%n");
	}
	
	public void excel(float totalFitness, float medFitness, float maxFitness, float minFitness, float atualGeneration){
		gravarArq.printf("Geração, Fitness Total, Fitness Médio, Fitness Máximo, Fitness Minimo%n");
		gravarArq.printf(atualGeneration+","+totalFitness+","+medFitness+","+maxFitness+","+minFitness+"%n");
	}
}

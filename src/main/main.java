package main;

public class main {
	
	// Probability variables.
	public static double probMut = 0.25;
	public static double probBreed = 0.8;
	
	// Size of the population and genes.
	public final static int popSize = 50;
	public final static int genes = 100;
	
	/* 2-D Array of the individuals. Each individual has an array of the genes.
	 * E.g.: ind: { {1,0,1,0}, {0,1,0,1}, {0,0,1,1} }
	 *       ind[0]: {1,0,1,0}
	 *       ind[0][1]: 0
	 */ 
	public static int ind[][] = new int[popSize][genes];
	
	public static int genesMutate = 1; // 1 by default. This tells the mutate function how many genes it will change.
	
	public static int genMax = 1000; // Maximum generations. 

	public static void main(String[] args) {
		initPop(); // Initialize population
		
		for(int gen = 0; gen <= genMax; gen++) {
			int id[] = parents(); // Those 2 will breed the entire new population. The function returns an 2 position array with the best 2 individuals.

			// Let's loop through all generations.
			for(int i = 0; i < popSize; i++) {
				if(i == id[0] || i == id[1]) continue; // Makes sure we won't replace the best 2 individuals.
				if(Math.random() < probBreed) { // Probability of breeding.
					int baby[] = breed(ind[id[0]], ind[id[1]]); // Creates a new baby (array of genes).
					if(Math.random() < probMut) mutate(baby); // Probability of mutating the baby.					
					if(score(ind[i]) < score(baby)) ind[i] = baby; // If the baby performs better than the individual, let's replace him.
				}
			}
			int best = best();
			int bs = score(ind[best]);
			System.out.println("> Gen: " + gen);
			System.out.println("> Best score: " + bs + "\n");
			if(bs == genes) { // If the one max individual was born.
				System.out.println("");
				for(int i = 0; i < genes; i++) System.out.print(ind[best][i] + " "); // Print all the genes.
				break; // Stop the generation loop.
			}
		}
	}
	
	// Initialize the population with random genes.
	public static void initPop() {
		for(int i = 0; i < popSize; i++) {
			for(int j = 0; j < genes; j++) {
				ind[i][j] = (Math.random() < 0.5 ? 1 : 0);
			}
		}
	}
	
	// Simply mutates the genes array.
	public static void mutate(int gen[]) {
		for(int i = 0; i < genesMutate; i++) {
			int g = Rand(0,genes-1);
			gen[g] = (gen[g] == 0 ? 1 : 0);
		}
	}
	
	// Get the score of the genes, this is also known as fitness function.
	// In this case, the fitness can be defined as the sum of all the genes.
	// E.g.: the score of {1, 0, 1, 0, 1, 1} will be 4.
	public static int score(int gen[]) {
		int scr = 0;
		for(int i = 0; i < genes; i++) {
			scr += gen[i];
		}
		return scr;
	}
	
	/* Creates a new array of genes with 2 individuals.
	 * 
	 * E.g.: ind.1: {1, 1, 1, 1, 0, 0}
	 *       ind.2: {0, 0, 0, 0, 1, 1}
	 *       cut = 4
	 *       ind.3 = {1, 1, 1, 1, 1, 1}
	 *  The cut variable tells the function where to cut the genes and make the crossover.
	 */ 
	public static int[] breed(int daddy[], int mommy[]) {
		int littleboy[] = new int[genes];
		int cut = Rand(1,genes);
		for(int i = 0; i < cut; i++) littleboy[i] = daddy[i];
		for(int i = cut; i < genes; i++) littleboy[i] = mommy[i];
		return littleboy;
	}
	
	// Returns the index of the best individual on the population.
	public static int best() {
		int id = 0;
		for(int i = 0; i < popSize; i++) id = (score(ind[i]) > score(ind[id]) ? i : id);
		return id;
	}
    
	// Returns the best 2 indivuals index on the population on an array of 2 positions.
	public static int[] parents() {
		int id[] = new int[2];
		id[0] = 0;
		id[1] = 0;
		for(int i = 0; i < popSize; i++) {
			if(score(ind[i]) > score(ind[id[0]])) id[0] = i;
		}
		for(int i = 0; i < popSize; i++) {
			if(i == id[0]) continue;
			if(score(ind[i]) > score(ind[id[1]])) id[1] = i;
		}
		return id;
	}
	
	// Function that generates an Integer random number based on minimum and maximum.
	public static int Rand(int min, int max) {
		return (min + (int)(Math.random() * ((max - min) + 1)));
	}

}

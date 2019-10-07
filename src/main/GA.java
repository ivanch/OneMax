package main;

public class GA {
	
	// Probability variables.
	public double probMut = 0.25;
	public double probBreed = 0.8;
	
	// Size of the population and genes.
	public final Integer popSize = 50;
	public final Integer genes = 100;
	
	/* 2D Array of the individuals. Each individual has an array of genes.
	 * E.g.: ind: { {1,0,1,0}, {0,1,0,1}, {0,0,1,1} }
	 *       ind[0]: {1,0,1,0}
	 *       ind[0][1]: 0
	 */ 
	public Integer ind[][] = new Integer[popSize][genes];
	
	public Integer genesMutate = 1; // 1 by default. How many genes to mutate.
	
    public Integer genMax = 1000; // Maximum generations. 
    
    public GA(){ }

    public void run(){
        initPop(); // Initialize population
        
		for(Integer gen = 0; gen < genMax; gen++) {
			Integer id[] = parents(); // Those 2 will breed the entire new population. The function returns an 2 position array with the best 2 individuals.

			// Loop through all generations.
			for(Integer i = 0; i < popSize; i++) {
				if(i == id[0] || i == id[1]) continue; // Makes sure we won't replace the best 2 individuals.
				if(Math.random() < probBreed) { // Probability of breeding.
					Integer baby[] = breed(ind[id[0]], ind[id[1]]); // Creates a new baby (array of genes).
					if(Math.random() < probMut) mutate(baby); // Probability of mutating the baby.					
					if(score(ind[i]) < score(baby)) ind[i] = baby; // If the baby performs better than the individual, let's replace him.
				}
			}
			Integer best = best();
			Integer bs = score(ind[best]);
			System.out.println("> Gen: " + gen);
			System.out.print("> Best score: " + bs + "\n");
			if(bs == genes) { // If the one max individual was born.
				System.out.print("");
				for(Integer i = 0; i < genes; i++) System.out.print(ind[best][i] + " "); // PrInteger all the genes.
				break; // Stop the generation loop.
			}
		}
    }

	// Initialize the population with random genes.
	public void initPop() {
		for(Integer i = 0; i < popSize; i++) {
			for(Integer j = 0; j < genes; j++) {
				ind[i][j] = (Math.random() < 0.5 ? 1 : 0);
			}
		}
	}
	
	// Simply mutates a gene array.
	public void mutate(Integer gen[]) {
		for(Integer i = 0; i < genesMutate; i++) {
			Integer g = rand(0,genes-1);
			gen[g] = (gen[g] == 0 ? 1 : 0);
		}
	}
	
	// Get the score of the genes, this is also known as fitness function.
	// In this case, the fitness can be defined as the sum of all the genes.
	// E.g.: the score of {1, 0, 1, 0, 1, 1} will be 4.
	public Integer score(Integer gen[]) {
		Integer scr = 0;
		for(Integer i = 0; i < genes; i++) {
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
	public Integer[] breed(Integer daddy[], Integer mommy[]) {
		Integer littleboy[] = new Integer[genes];
		Integer cut = rand(1,genes);
		for(Integer i = 0; i < cut; i++) littleboy[i] = daddy[i];
		for(Integer i = cut; i < genes; i++) littleboy[i] = mommy[i];
		return littleboy;
	}
	
	// Returns the index of the best individual on the population.
	public Integer best() {
		Integer id = 0;
		for(Integer i = 0; i < popSize; i++) id = (score(ind[i]) > score(ind[id]) ? i : id);
		return id;
	}
    
	// Returns the best 2 indivuals index on the population on an array of 2 positions.
	public Integer[] parents() {
		Integer id[] = new Integer[2];
		id[0] = 0;
		id[1] = 0;
		for(Integer i = 0; i < popSize; i++) {
			if(score(ind[i]) > score(ind[id[0]])) id[0] = i;
		}
		for(Integer i = 0; i < popSize; i++) {
			if(i == id[0]) continue;
			if(score(ind[i]) > score(ind[id[1]])) id[1] = i;
		}
		return id;
	}
	
	// Function that generates an Integereger random number based on minimum and maximum.
	public Integer rand(Integer min, Integer max) {
		return (min + (int) (Math.random() * ((max - min) + 1)));
    }
    
}
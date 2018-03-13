package main;

public class main {
	
	public static double probMut = 0.25;
	public static double probBreed = 0.8;
	
	public final static int popSize = 50;
	public final static int genes = 100;
	
	public static int ind[][] = new int[popSize][genes];
	
	public static int genesMutate = 1; // 1 by default
	public static int breedTimes = 50;
	
	// debug
	public static int genMax = 1000;

	public static void main(String[] args) {
		initPop();
		for(int gen = 0; gen <= genMax; gen++) {
			int id[] = better();
			for(int i = 0; i < popSize; i++) {
				if(i == id[0] || i == id[1]) continue;
				if(Math.random() < probBreed) {
					int baby[] = breed(ind[id[0]], ind[id[1]]);
					if(Math.random() < probMut) mutate(baby);
					if(score(ind[i]) < score(baby)) ind[i] = baby;
				}
			}
			int b = best();
			int bs = score(ind[b]);
			System.out.println("> Gen: " + gen);
			System.out.println("> Best score: " + bs + "\n");
			if(bs == genes) {
				System.out.println("");
				for(int i = 0; i < genes; i++) System.out.print(ind[b][i] + " ");
				break;
			}
		}
	}
	
	public static void initPop() {
		for(int i = 0; i < popSize; i++) {
			for(int j = 0; j < genes; j++) {
				ind[i][j] = (Math.random() < 0.5 ? 1 : 0);
			}
		}
	}
	
	public static void mutate(int gen[]) {
		for(int i = 0; i < genesMutate; i++) {
			int g = Rand(0,genes-1);
			gen[g] = (gen[g] == 0 ? 1 : 0);
		}
	}
	
	public static int score(int gen[]) {
		int scr = 0;
		for(int i = 0; i < genes; i++) {
			if(gen[i] == 1) scr++;
		}
		return scr;
	}
	
	public static int[] breed(int daddy[], int mommy[]) {
		int littleboy[] = new int[genes];
		int cut = Rand(1,genes);
		for(int i = 0; i < cut; i++) littleboy[i] = daddy[i];
		for(int i = cut; i < genes; i++) littleboy[i] = mommy[i];
		return littleboy;
	}
	
	public static int best() {
		int id = 0;
		for(int i = 0; i < popSize; i++) id = (score(ind[i]) > score(ind[id]) ? i : id);
		return id;
	}

	public static int[] better() {
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
	
	
	public static int Rand(int min, int max) {
		return (min + (int)(Math.random() * ((max - min) + 1)));
	}

}

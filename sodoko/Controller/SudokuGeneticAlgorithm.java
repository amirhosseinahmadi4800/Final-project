public class SudokuGeneticAlgorithm{

 private ArrayList<SudokuBoard> population;
    private ArrayList<Integer> populationScore;
    private ArrayList<SudokuBoard> matingSelections;
    private Random rand;
    private int bestGenetics;
    private SudokuBoard original;
    private SudokuBoard firstAttempt;
    private SudokuBoard best;
    private int bestScore;

 public SudokuGeneticAlgorithm( int basePopulation){
        original = sb;
        population = new ArrayList<>(basePopulation+1);
        populationScore = new ArrayList<>(basePopulation+1);
        matingSelections = new ArrayList<>(basePopulation+1);
        rand = new Random();
        generatePopulation(sb, basePopulation);
    }

 public void run(int generations){
        int count = 0;

        scoreFirstGeneration();

        while(count < generations && bestScore < bestGenetics){
            
            selection();


            for(int i = 0; i < matingSelections.size(); i+=2){
                crossOver(matingSelections.get(i), matingSelections.get(i+1));
            }
            ++count;
        }
    }
 public void selection(){
        matingSelections.clear(); 
        sortPopulation();

        int limit = (population.size() > 100) ? 100 : population.size();

        for(int i = 0; i < limit; i++){
            matingSelections.add(population.get(i));
        }

        
        if(matingSelections.size() % 2 != 0){
            matingSelections.add(matingSelections.get(rand.nextInt(matingSelections.size()-1)));
        }

        
        while(population.size() > 100){
            population.remove(population.size()-1);
            populationScore.remove(population.size()-1);
        }
    }

}
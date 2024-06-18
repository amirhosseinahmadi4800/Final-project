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




}
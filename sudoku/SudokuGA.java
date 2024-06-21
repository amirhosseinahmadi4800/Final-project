
import java.util.*;

public class SudokuGA {
    public static List<SudokuGenome> initializePopulation(int populationSize, int[] unsolvedGrid){
        List<SudokuGenome> population = new ArrayList<>();

        for(int i=0;i<populationSize;i++)
            population.add(new SudokuGenome(unsolvedGrid));

        for(int i=0;i<populationSize;i++)
            population.get(i).setFitness(fitnessValue(population.get(i).getGrid()));

        return population;
    }

    public static int fitnessValue(int[] g){
        int fitness = 0;
        int[][] grid = convertTo2DGrid(g);

        //rows
        for(int i=0;i<9;i++){
            Set<Integer> numbers = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                numbers.add(grid[i][j]);
            }
            numbers.remove(0);
            fitness += (9-numbers.size());
        }

        //columns
        for(int j=0;j<9;j++){
            Set<Integer> numbers = new HashSet<>();
            for(int i=0;i<9;i++){
                numbers.add(grid[i][j]);
            }
            numbers.remove(0);
            fitness += (9-numbers.size());
        }
        return fitness;
    }

    public static SudokuGenome[] elitismSelection(List<SudokuGenome> population){
        SudokuGenome[] winners = new SudokuGenome[2];
        List<SudokuGenome> sortedPopulationByFitness = new ArrayList<>(population);

        sortedPopulationByFitness.sort(Comparator.comparingInt(SudokuGenome::getFitness));

        winners[0] = sortedPopulationByFitness.get(0);
        winners[1] = sortedPopulationByFitness.get(1);

        return winners;
    }

    public static SudokuGenome[] randomSelection(List<SudokuGenome> population){
        Random random = new Random();
        SudokuGenome[] winners = new SudokuGenome[2];
        SudokuGenome[] chosen = new SudokuGenome[4];
        List<Integer> indexs = new ArrayList<>();

        for(int i=0;i<4;i++){
            boolean alreadyUsed = true;
            do{
                int randomIndex = random.nextInt(population.size());
                if(!indexs.contains(randomIndex)){
                    alreadyUsed = false;
                    chosen[i] = population.get(randomIndex);
                    indexs.add(randomIndex);
                }
            }while(alreadyUsed);
        }

        winners[0] = chosen[0].getFitness() < chosen[1].getFitness() ? chosen[0] : chosen[1];
        winners[1] = chosen[2].getFitness() < chosen[3].getFitness() ? chosen[2] : chosen[3];

        return winners;
    }

    public static List<SudokuGenome> onePointCrossover(SudokuGenome[] winners, int populationSize){
        Random random = new Random();
        List<SudokuGenome> population = new ArrayList<>();
        List<Integer> firstPart = new ArrayList<>();
        List<Integer> secondPart = new ArrayList<>();

        population.add(winners[0]);
        population.add(winners[1]);

        for(int k=2;k<populationSize;k++){
            SudokuGenome sudokuGenome = new SudokuGenome();
            int randomPointIndex = random.nextInt(7)+1;

            for(int i=0;i<randomPointIndex;i++){
                int[] indexs = getSubGridIndexes(i,winners[0].getGrid());
                for(int num : indexs)
                    firstPart.add(num);
            }

            for(int i=randomPointIndex;i<=8;i++){
                int[] indexs = getSubGridIndexes(i,winners[1].getGrid());
                for(int num : indexs)
                    secondPart.add(num);
            }

            int[] child = new int[81];
            for(Integer index : firstPart)
                child[index] = winners[0].getGrid()[index];

            for(Integer index : secondPart)
                child[index] = winners[1].getGrid()[index];

            sudokuGenome.setGrid(child);
            sudokuGenome.setFitness(fitnessValue(child));
            population.add(sudokuGenome);
        }

        return population;
    }

    public static List<SudokuGenome> morePointCrossover(SudokuGenome[] winners, int populationSize){
        Random random = new Random();
        List<SudokuGenome> population = new ArrayList<>();
        List<Integer> subGridIndexs = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        population.add(winners[0]);
        population.add(winners[1]);

        for(int k=2;k<populationSize;k++){
            SudokuGenome sudokuGenome = new SudokuGenome();
            int n = random.nextInt(6)+2;

            int counter = 0;
            do{
                int randomIndex = random.nextInt(9);
                if(!subGridIndexs.contains(randomIndex)){
                    subGridIndexs.add(randomIndex);
                    counter++;
                }
            }while (counter < n);

            subGridIndexs.sort(Comparator.comparing(Integer::intValue));

            for(int i=0;i<9;i++){

                if(subGridIndexs.contains(i)){
                    int[] numbers = getSubGridIndexes(i, winners[1].getGrid());
                    for(int number : numbers)
                        list.add(number);
                }
            }

            int[] child = Arrays.copyOf(winners[0].getGrid(), 81);

            for(int index : list)
                child[index] = winners[1].getGrid()[index];

            sudokuGenome.setGrid(child);
            sudokuGenome.setFitness(fitnessValue(child));

            subGridIndexs.clear();
            list.clear();

            population.add(sudokuGenome);
        }
        return population;
    }

    public static int[] mutation(int[] grid, int[] unsolvedGrid){
        Random random = new Random();
        int[] changedGrid = Arrays.copyOf(grid, grid.length);
        boolean changeAllowed;

        do{
            changeAllowed = true;

            int[] arrayIndex = getSubGridIndexes(random.nextInt(9), grid);
            int randomFirstIndex = arrayIndex[random.nextInt(arrayIndex.length)];
            int randomSecondIndex = arrayIndex[random.nextInt(arrayIndex.length)];

            if(randomFirstIndex==randomSecondIndex || unsolvedGrid[randomFirstIndex] != 0
                        || unsolvedGrid[randomSecondIndex] != 0){
                changeAllowed = false;
            }

            if(changeAllowed){
                int temp = changedGrid[randomFirstIndex];
                changedGrid[randomFirstIndex] = changedGrid[randomSecondIndex];
                changedGrid[randomSecondIndex] = temp;
            }
        }while (!changeAllowed);

        return changedGrid;
    }

    public static Optional<SudokuGenome> checkIfFoundSolution(List<SudokuGenome> population){
        return population.stream()
                .filter(sg -> sg.getFitness() == 0)
                .findFirst();
    }
}

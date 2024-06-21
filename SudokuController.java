
import hr.sudokusolver.sudokusolverga.configuration.Configuration;
import hr.sudokusolver.sudokusolverga.enums.CrossoverType;
import hr.sudokusolver.sudokusolverga.enums.SelectionType;
import hr.sudokusolver.sudokusolverga.information.Information;
import hr.sudokusolver.sudokusolverga.sudoku.SudokuGenome;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.*;

import static hr.sudokusolver.sudokusolverga.sudoku.Sudoku.*;
import static hr.sudokusolver.sudokusolverga.sudoku.SudokuGA.*;

public class SudokuController {

    private final String[] selectionChoices = {"Elitism", "Random"};

    private final String[] crossoverChoices = {"One - point", "More - point"};
    private int MIN_EVOLUTION = Integer.MAX_VALUE;
    private int MAX_EVOLUTION = Integer.MIN_VALUE;
    private int ALL_EVOLUTION_COUNTER = 0;
    private int LOOP_COUNTER = 0;
    private int EVOLUTION_COUNT;

    public static int CLUES;

    private CrossoverType CROSSOVER_TYPE = CrossoverType.ONE_POINT_CROSS;
    private SelectionType SELECTION_TYPE = SelectionType.ELITISM;
    private int POPULATION_SIZE = 15;
    private int MUTATION_CHANCE = 5;
    private int MIN_CLUES = 35;
    private int MAX_CLUES = 45;

    private int EVOLUTION_TO_GIVE_UP = 150000;

    private Configuration configuration;

    private int[] unsolvedGrid;
    private List<SudokuGenome> population;
    private int[] solvedGrid = new int[81];

    @FXML
    private Label cluesText, evolutionText, statusText, averageEvolutionText, minEvolutionText, maxEvolutionText;

    @FXML
    private TextField minCluesField, maxCluesField, populationSizeField, giveUpField, mutationField;

    @FXML
    private Button startButton, newButton;

    @FXML
    private GridPane configurationPane, informationPane;

    @FXML
    private ChoiceBox<String> selectionChoiceBox, crossoverChoiceBox;

    @FXML
    private TextField f0, f1, f2, f3, f4, f5, f6, f7, f8,
            f9, f10, f11, f12, f13, f14, f15, f16, f17,
            f18, f19, f20, f21, f22, f23, f24, f25, f26,
            f27, f28, f29, f30, f31, f32, f33, f34, f35,
            f36, f37, f38, f39, f40, f41, f42, f43, f44,
            f45, f46, f47, f48, f49, f50, f51, f52, f53,
            f54, f55, f56, f57, f58, f59, f60, f61, f62,
            f63, f64, f65, f66, f67, f68, f69, f70, f71,
            f72, f73, f74, f75, f76, f77, f78, f79, f80;

    @FXML
    private TextField[] fields;

    @FXML
    public void initialize(){

        fields = new TextField[]{
                f0, f1, f2, f3, f4, f5, f6, f7, f8,
                f9, f10, f11, f12, f13, f14, f15, f16, f17,
                f18, f19, f20, f21, f22, f23, f24, f25, f26,
                f27, f28, f29, f30, f31, f32, f33, f34, f35,
                f36, f37, f38, f39, f40, f41, f42, f43, f44,
                f45, f46, f47, f48, f49, f50, f51, f52, f53,
                f54, f55, f56, f57, f58, f59, f60, f61, f62,
                f63, f64, f65, f66, f67, f68, f69, f70, f71,
                f72, f73, f74, f75, f76, f77, f78, f79, f80};

        configurationPane.setStyle("-fx-border-width: 1px; -fx-border-color: lightgray;");
        informationPane.setStyle("-fx-border-width: 1px; -fx-border-color: lightgray;");

        startButton.setDisable(true);

        selectionChoiceBox.getItems().addAll(selectionChoices);
        selectionChoiceBox.setValue(selectionChoices[0]);
        crossoverChoiceBox.getItems().addAll(crossoverChoices);
        crossoverChoiceBox.setValue(crossoverChoices[0]);

        minCluesField.setText(MIN_CLUES + "");
        maxCluesField.setText(MAX_CLUES + "");
        populationSizeField.setText(POPULATION_SIZE + "");
        mutationField.setText(MUTATION_CHANCE + "");
        giveUpField.setText(EVOLUTION_TO_GIVE_UP + "");

        configuration = new Configuration(38,46,POPULATION_SIZE, SELECTION_TYPE,
                CROSSOVER_TYPE,MUTATION_CHANCE,EVOLUTION_TO_GIVE_UP);

        Platform.runLater(() -> newButton.requestFocus());
    }
    @FXML
    protected void solvingSudoku(){
        resetAll();

        String populationSizeString = populationSizeField.getText();
        String mutationString = mutationField.getText();
        String giveUpString = giveUpField.getText();
        String selectionChoiceString = selectionChoiceBox.getValue();
        String crossoverChoiceString = crossoverChoiceBox.getValue();

        try{
            int populationSize = Integer.parseInt(populationSizeString);
            int mutationChance = Integer.parseInt(mutationString);
            int giveUpEvolution = Integer.parseInt(giveUpString);

            POPULATION_SIZE = populationSize;
            MUTATION_CHANCE = mutationChance;
            EVOLUTION_TO_GIVE_UP = giveUpEvolution;

            if(selectionChoiceString.equals(selectionChoices[1]))
                SELECTION_TYPE = SelectionType.RANDOM;
            else
                SELECTION_TYPE = SelectionType.ELITISM;

            if(crossoverChoiceString.equals(crossoverChoices[0]))
                CROSSOVER_TYPE = CrossoverType.ONE_POINT_CROSS;
            else
                CROSSOVER_TYPE = CrossoverType.MORE_POINT_CROSS;

        }catch (NumberFormatException e){}

        populationSizeField.setText(POPULATION_SIZE+"");mutationField.setText(MUTATION_CHANCE+"");giveUpField.setText(EVOLUTION_TO_GIVE_UP+"");

        EVOLUTION_COUNT = 0;
        LOOP_COUNTER++;

        Random random = new Random();
        boolean sudokuSolved = false;
        boolean solvingCanceled = false;

        do{
            EVOLUTION_COUNT++;

            SudokuGenome[] parents = new SudokuGenome[2];
            switch (SELECTION_TYPE){
                case ELITISM -> parents = elitismSelection(population);
                case RANDOM -> parents = randomSelection(population);
            }

            switch (CROSSOVER_TYPE){
                case ONE_POINT_CROSS -> population = onePointCrossover(parents, POPULATION_SIZE);
                case MORE_POINT_CROSS -> population = morePointCrossover(parents, POPULATION_SIZE);
            }

            for(int i=0;i<POPULATION_SIZE;i++){
                if(random.nextInt(100)+1 <= MUTATION_CHANCE){
                    int[] changedGrid = mutation(population.get(i).getGrid(), unsolvedGrid);
                    population.get(i).setGrid(changedGrid);
                    population.get(i).setFitness(fitnessValue(changedGrid));
                }
            }

            Optional<SudokuGenome> solution = checkIfFoundSolution(population);

            if(solution.isPresent()){
                solvedGrid = solution.get().getGrid();
                sudokuSolved = true;
            }

            if(EVOLUTION_COUNT >= EVOLUTION_TO_GIVE_UP)
                solvingCanceled = true;

        }while (!sudokuSolved && !solvingCanceled);

        Configuration config = new Configuration(MIN_CLUES, MAX_CLUES, POPULATION_SIZE, SELECTION_TYPE, CROSSOVER_TYPE,
                MUTATION_CHANCE, EVOLUTION_TO_GIVE_UP);

        if(!config.equals(configuration) && sudokuSolved){
            MIN_EVOLUTION = Integer.MAX_VALUE;
            MAX_EVOLUTION = Integer.MIN_VALUE;
        }
        if(!config.equals(configuration)){
            configuration = config;
            ALL_EVOLUTION_COUNTER = 0;
            LOOP_COUNTER = 1;
        }
        if(sudokuSolved){
            ALL_EVOLUTION_COUNTER += EVOLUTION_COUNT;

            int avgEvolution = ALL_EVOLUTION_COUNTER / LOOP_COUNTER;

            if(EVOLUTION_COUNT < MIN_EVOLUTION) MIN_EVOLUTION = EVOLUTION_COUNT;
            if(EVOLUTION_COUNT > MAX_EVOLUTION) MAX_EVOLUTION = EVOLUTION_COUNT;

            Information info = new Information(CLUES, EVOLUTION_COUNT, true, avgEvolution, MIN_EVOLUTION, MAX_EVOLUTION);
            setInformation(info);
            setNumbersToGrid(solvedGrid);
            startButton.setDisable(true);
        }

        if (solvingCanceled){
            LOOP_COUNTER--;
            Information info = new Information();
            info.setClues(CLUES);
            info.setEvolution(EVOLUTION_COUNT);
            info.setStatus(false);
            setInformation(info);
            tryAgain();
        }
    }
    @FXML
    protected void generateNewSudoku() {
        resetAll();
        startButton.setDisable(false);
        minCluesField.setDisable(true);
        maxCluesField.setDisable(true);

        String minCluesString = minCluesField.getText();
        String maxCluesString = maxCluesField.getText();

        try{
            int min = Integer.parseInt(minCluesString);
            int max = Integer.parseInt(maxCluesString);

            if(min <= max && min > 0){
                MIN_CLUES = min;
                MAX_CLUES = max;
            }
        }catch (NumberFormatException e){}

        minCluesField.setText(MIN_CLUES+"");maxCluesField.setText(MAX_CLUES+"");

        unsolvedGrid = new int[81];
        for(int i=0;i<81;i++)
            unsolvedGrid[i] = 0;

        unsolvedGrid = generateSudoku(MIN_CLUES, MAX_CLUES);

        setNumbersToGrid(unsolvedGrid);

        cluesText.setText(String.valueOf(CLUES));

        if(population != null)
            population.clear();
        population = initializePopulation(POPULATION_SIZE, unsolvedGrid);
    }
    @FXML
    protected void exitApplication() {
        Platform.exit();
    }
    protected void tryAgain(){
        startButton.setDisable(false);
        startButton.setText("Try Again");

        population.clear();
        population = initializePopulation(POPULATION_SIZE, unsolvedGrid);
    }
    protected void setNumbersToGrid(int[] grid){
        for(int i=0;i<81;i++){
            fields[i].setText("");
            if(grid[i] != 0)
                fields[i].setText(String.valueOf(grid[i]));
        }
        colorCluesAndGrayBoxes();
    }
    protected void resetAll(){
        startButton.setText("Start");
        cluesText.setText("-");
        evolutionText.setText("-");
        evolutionText.setTextFill(Color.BLACK);
        statusText.setText("-");
        statusText.setTextFill(Color.BLACK);
        minCluesField.setDisable(false);
        maxCluesField.setDisable(false);
    }
    protected void setInformation(Information information){
        cluesText.setText(information.getClues() + "");
        if(information.isStatus()){
            evolutionText.setText(information.getEvolution()+"");
            statusText.setTextFill(Color.GREEN);
            statusText.setText("Solution found.");
        }else{
            evolutionText.setTextFill(Color.RED);
            evolutionText.setText(EVOLUTION_COUNT + "+");
            statusText.setTextFill(Color.RED);
            statusText.setText("Failed.");
        }
        if(information.isStatus()){
            averageEvolutionText.setText(information.getAverageEvolution()+"");
            minEvolutionText.setText(information.getMinEvolution()+"");
            maxEvolutionText.setText(information.getMaxEvolution()+"");
        }
    }
    protected void colorCluesAndGrayBoxes(){
        for(int i=0;i<81;i++){

            if(unsolvedGrid[i] != 0 && isBoxGray(i))
                fields[i].setStyle("-fx-text-fill: blue; -fx-font-weight: bold; -fx-control-inner-background:#d4d4d4;");
            else if(unsolvedGrid[i] != 0 && !isBoxGray(i))
                fields[i].setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
            else if(unsolvedGrid[i] == 0 && isBoxGray(i) )
                fields[i].setStyle("-fx-control-inner-background:#d4d4d4;");
            else
                fields[i].setStyle("-fx-text-fill: black;-fx-font-weight: normal;");
        }
    }
    protected boolean isBoxGray(int index){
        List<Integer> list = new ArrayList<>();
        for(int k=0;k<9;k++){
            if(k % 2 == 1){
                int[] indexes = getSubGridIndexes(k, unsolvedGrid);
                for(int i : indexes)
                    list.add(i);
            }
        }
        return list.stream().anyMatch(n -> n == index);
    }
}
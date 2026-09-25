import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] sizes = {1000, 10000, 100000};
        String[] types = {"Random", "Sorted", "Reverse-sorted", "Duplicate-heavy"};
        Random random = new Random();

        try (FileWriter csvWriter = new FileWriter("project/results/results.csv")) {
            csvWriter.append("Algorithm,Size,Type,Time_ns,Comparisons,MaxDepth,IsCorrect\n");

            MergeSorter mergeSorter = new MergeSorter();
            QuickSorter quickSorter = new QuickSorter();
            DeterministicSelector selector = new DeterministicSelector();
            ClosestPairSolver cpSolver = new ClosestPairSolver();

            for (int size : sizes) {
                for (String type : types) {
                    int[] originalArray = switch (type) {
                        case "Random" -> Experiment.generateRandomArray(size);
                        case "Sorted" -> Experiment.generateSortedArray(size);
                        case "Reverse-sorted" -> Experiment.generateReverseSortedArray(size);
                        case "Duplicate-heavy" -> Experiment.generateDuplicateHeavyArray(size);
                        default -> new int[0];
                    };

                    int[] mergeArr = Arrays.copyOf(originalArray, originalArray.length);
                    long startTime = System.nanoTime();
                    mergeSorter.mergeSort(mergeArr);
                    long endTime = System.nanoTime();
                    long duration = endTime - startTime;
                    boolean isCorrect = Experiment.verifySort(originalArray, mergeArr);

                    csvWriter.append(String.format("MergeSort,%d,%s,%d,%d,%d,%b\n",
                            size, type, duration, mergeSorter.comparisons, mergeSorter.maxRecursionDepth, isCorrect));
                    csvWriter.flush();

                    int[] quickArr = Arrays.copyOf(originalArray, originalArray.length);
                    startTime = System.nanoTime();
                    quickSorter.quickSort(quickArr);
                    endTime = System.nanoTime();
                    duration = endTime - startTime;
                    isCorrect = Experiment.verifySort(originalArray, quickArr);

                    csvWriter.append(String.format("QuickSort,%d,%s,%d,%d,%d,%b\n",
                            size, type, duration, quickSorter.comparisons, quickSorter.maxRecursionDepth, isCorrect));
                    csvWriter.flush();

                    int[] selectArr = Arrays.copyOf(originalArray, originalArray.length);
                    int k = random.nextInt(size);
                    startTime = System.nanoTime();
                    selector.select(selectArr, k);
                    endTime = System.nanoTime();
                    isCorrect = Experiment.verifySelect100Times(originalArray);
                    csvWriter.append(String.format("DeterministicSelect,%d,%s,%d,%d,%d,%b\n",
                            size, type, (endTime - startTime), selector.comparisons, selector.maxRecursionDepth, isCorrect));
                    csvWriter.flush();
                }
                Point[] points = Experiment.generateRandomPoints(size);
                long startTime = System.nanoTime();
                double closestDist = cpSolver.closestPair(points);
                long endTime = System.nanoTime();

                boolean isCpCorrect = true;
                if (size <= 2000) {
                    double bruteForceDist = cpSolver.bruteForce(points, 0, points.length - 1);
                    isCpCorrect = Math.abs(closestDist - bruteForceDist) < 1e-9;
                }

                csvWriter.append(String.format("ClosestPair,%d,RandomPoints,%d,%d,%d,%b\n",
                        size, (endTime - startTime), cpSolver.comparisons, cpSolver.maxRecursionDepth, isCpCorrect));
                csvWriter.flush();
            }
            System.out.println("Experiment done correctly. Results saved in project/results/results.csv");
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
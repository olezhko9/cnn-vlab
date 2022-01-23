package vlab.server_java.generate;

import rlcp.generate.GeneratingResult;
import rlcp.server.processor.generate.GenerateProcessor;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Random;

/**
 * Simple GenerateProcessor implementation. Supposed to be changed as needed to
 * provide necessary Generate method support.
 */
public class GenerateProcessorImpl implements GenerateProcessor {
    @Override
    public GeneratingResult generate(String condition) {
        final int inputMatrixSize = 5;
        int[][] inputMatrix = new int[inputMatrixSize][inputMatrixSize];

        for (int i = 0; i < inputMatrixSize; i++) {
            for (int j = 0; j < inputMatrixSize; j++) {
                inputMatrix[i][j] = this.getRandomIntInRange(0, 9);
            }
        }
        System.out.println("Входная матрица: " + Arrays.deepToString(inputMatrix));

        JSONObject variant = new JSONObject();
        variant.put("inputMatrix", inputMatrix);
        variant.put("inputMatrixSize", inputMatrixSize);

        String instructions = "instructions";
        String text = "Вариант VL загружен";
        String code = variant.toString();

        return new GeneratingResult(text, code, instructions);
    }

    private int getRandomIntInRange(int min, int max) {
        return min + new Random().nextInt(max - min + 1);
    }

    private void printMatrix(int[][] matrix, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }
}

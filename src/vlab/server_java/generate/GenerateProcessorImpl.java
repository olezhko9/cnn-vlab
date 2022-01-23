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
        int[][] inputMatrix = getRandomMatrix(inputMatrixSize, 0, 9);

        final int convKernelSize = 3;
        int[][] convKernelMatrix = getRandomMatrix(convKernelSize, -1, 1);
        final int convStride = this.getRandomIntInRange(1, 2);
        final int convPadding = this.getRandomIntInRange(1, 2);

        final int poolSize = 2;
        final int poolType = this.getRandomIntInRange(0, 1); // 0 - max, 1 - mean

        System.out.println("Входная матрица: " + Arrays.deepToString(inputMatrix));
        System.out.println("Ядро свертки: " + Arrays.deepToString(convKernelMatrix));

        JSONObject inputLayer = new JSONObject();
        inputLayer.put("data", inputMatrix);

        JSONObject convLayer = new JSONObject();
        convLayer.put("kernel", convKernelMatrix);
        convLayer.put("stride", convStride);
        convLayer.put("padding", convPadding);

        JSONObject poolLayer = new JSONObject();
        poolLayer.put("size", poolSize);
        poolLayer.put("type", poolType);

        JSONObject variant = new JSONObject();
        variant.put("input", inputLayer);
        variant.put("conv", convLayer);
        variant.put("pool", poolLayer);

        String instructions = "instructions";
        String text = "Вариант VL загружен";
        String code = variant.toString();

        return new GeneratingResult(text, code, instructions);
    }

    private int getRandomIntInRange(int min, int max) {
        return min + new Random().nextInt(max - min + 1);
    }

    private int[][] getRandomMatrix(int size, int min, int max) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = this.getRandomIntInRange(min, max);
            }
        }

        return matrix;
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

package vlab.server_java.generate;

import org.json.JSONObject;
import rlcp.generate.GeneratingResult;
import rlcp.server.processor.generate.GenerateProcessor;
import vlab.server_java.Matrix;
import vlab.server_java.Utils;

import java.util.Arrays;

/**
 * Simple GenerateProcessor implementation. Supposed to be changed as needed to
 * provide necessary Generate method support.
 */
public class GenerateProcessorImpl implements GenerateProcessor {
    @Override
    public GeneratingResult generate(String condition) {
        final int inputMatrixSize = 5;
        Matrix inputMatrix = Matrix.getRandomMatrix(inputMatrixSize, 0, 9);

        final int convKernelSize = 3;
        Matrix convKernelMatrix = Matrix.getRandomMatrix(convKernelSize, -1, 1);
        final int convStride = Utils.getRandomIntInRange(1, 2);
        final int convPadding = Utils.getRandomIntInRange(1, 2);

        final int poolSize = 2;
        final int poolType = Utils.getRandomIntInRange(0, 1); // 0 - max, 1 - mean

        System.out.println("Входная матрица: " + Arrays.deepToString(inputMatrix.data));
        System.out.println("Ядро свертки: " + Arrays.deepToString(convKernelMatrix.data));

        JSONObject inputLayer = new JSONObject();
        inputLayer.put("data", inputMatrix.data);

        JSONObject convLayer = new JSONObject();
        convLayer.put("kernel", convKernelMatrix.data);
        convLayer.put("stride", convStride);
        convLayer.put("padding", convPadding);

        JSONObject poolLayer = new JSONObject();
        poolLayer.put("size", poolSize);

        JSONObject variant = new JSONObject();
        variant.put("input", inputLayer);
        variant.put("conv", convLayer);
        variant.put("pool", poolLayer);

        String instructions = "instructions";
        String text = "Вариант VL загружен";
        String code = variant.toString();

        return new GeneratingResult(text, code, instructions);
    }
}

package vlab.server_java.generate;

import org.json.JSONObject;
import rlcp.generate.GeneratingResult;
import rlcp.server.processor.generate.GenerateProcessor;
import vlab.server_java.Matrix;
import vlab.server_java.Utils;

/**
 * Simple GenerateProcessor implementation. Supposed to be changed as needed to
 * provide necessary Generate method support.
 */
public class GenerateProcessorImpl implements GenerateProcessor {
    @Override
    public GeneratingResult generate(String condition) {
        // размер входного слоя, размер ядра свертки, conv. padding, conv. stride
        int[][] presets = {
                {6, 3, 0, 1},
                {5, 2, 0, 1},
                {5, 4, 1, 1},
                {5, 3, 2, 2},
                {6, 2, 1, 2},
        };

        final int presetId = Utils.getRandomIntInRange(0, presets.length);
        final int[] variantParams = presets[presetId];

        final int inputMatrixSize = variantParams[0];
        Matrix inputMatrix = Matrix.getRandomMatrix(inputMatrixSize, 0, 9);

        final int convKernelSize = variantParams[1];
        Matrix convKernelMatrix = Matrix.getRandomMatrix(convKernelSize, -1, 1);
        final int convPadding = variantParams[2];
        final int convStride = variantParams[3];

        final int poolSize = 2;

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

        String instructions = "инструкции";
        String text = "В данном задании вам предстоит ощутить себя в роли сверточной нейронной сети, состоящей из двух " +
                "слоев: свертки и пулинга. Параметы каждого из слоев указаны в таблице ниже в первой колонке, входные " +
                "данные слоя указаны во второй колонке. Применив характерные для обозначенных слоев операции, заполните " +
                "матрицы с выходными значениями слоев в третьей колонке.";
        String code = variant.toString();

        return new GeneratingResult(text, code, instructions);
    }
}

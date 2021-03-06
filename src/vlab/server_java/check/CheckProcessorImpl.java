package vlab.server_java.check;

import org.json.JSONArray;
import org.json.JSONObject;
import rlcp.check.ConditionForChecking;
import rlcp.generate.GeneratingResult;
import rlcp.server.processor.check.PreCheckProcessor.PreCheckResult;
import rlcp.server.processor.check.PreCheckResultAwareCheckProcessor;
import vlab.server_java.Matrix;

import java.math.BigDecimal;


/**
 * Simple CheckProcessor implementation. Supposed to be changed as needed to provide
 * necessary Check method support.
 */
public class CheckProcessorImpl implements PreCheckResultAwareCheckProcessor<String> {


    @Override
    public CheckingSingleConditionResult checkSingleCondition(ConditionForChecking condition, String instructions, GeneratingResult generatingResult) throws Exception {
        // разбор задания
        JSONObject variant = new JSONObject(generatingResult.getCode());
        System.out.println("code: " + variant);

        Matrix inputData = Matrix.fromJSON(variant.getJSONObject("input").getJSONArray("data"));
        Matrix convKernel = Matrix.fromJSON(variant.getJSONObject("conv").getJSONArray("kernel"));
        int convPadding = variant.getJSONObject("conv").getInt("padding");
        int convStride = variant.getJSONObject("conv").getInt("stride");
        int poolSize = variant.getJSONObject("pool").getInt("size");

        // разбор ответа
        JSONObject answers = new JSONObject(instructions);
        JSONArray convResultJson = answers.getJSONArray("convResult");
        JSONArray poolResultJson = answers.getJSONArray("poolResult");

        Matrix convResult = Matrix.fromJSON(convResultJson);
        Matrix poolResult = Matrix.fromJSON(poolResultJson);

        // проверка
        String comment = "";

        Matrix convTrueMatrix = inputData.convolve(convKernel, convPadding, convStride);
        System.out.println("Conv true:");
        convTrueMatrix.print();
        int convNotEqualNumber = convTrueMatrix.compare(convResult);
        if (convNotEqualNumber != 0) {
            comment += convNotEqualNumber + " элементов матрицы сверточного слоя рассчитаны неверно. ";
        }

        Matrix poolTrueMatrix = convTrueMatrix.pool(poolSize);
        System.out.println("Pool true:");
        poolTrueMatrix.print();
        int poolNotEqualNumber = poolTrueMatrix.compare(poolResult);
        if (poolNotEqualNumber != 0) {
            comment += poolNotEqualNumber + " элементов матрицы слоя пулинга рассчитаны неверно.";
        }

        if (convNotEqualNumber + poolNotEqualNumber == 0) {
            comment += "Выходные матрицы рассчитаны верно.";
        }

        final int convTotalNum = convTrueMatrix.size * convTrueMatrix.size;
        final int poolTotalNum = poolTrueMatrix.size * poolTrueMatrix.size;
        double convPoints = 80.0 * (convTotalNum - convNotEqualNumber) / convTotalNum;
        double poolPoints = 20.0 * (poolTotalNum - poolNotEqualNumber) / poolTotalNum;
        BigDecimal points = BigDecimal.valueOf((convPoints + poolPoints) / 100.0);

        return new CheckingSingleConditionResult(points, comment);
    }

    @Override
    public void setPreCheckResult(PreCheckResult<String> preCheckResult) {}
}

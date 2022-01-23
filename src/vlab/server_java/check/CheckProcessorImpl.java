package vlab.server_java.check;

import org.json.JSONArray;
import org.json.JSONObject;
import rlcp.check.ConditionForChecking;
import rlcp.generate.GeneratingResult;
import rlcp.server.processor.check.CheckProcessor;
import rlcp.server.processor.check.PreCheckProcessor;
import rlcp.server.processor.check.PreCheckProcessor.PreCheckResult;
import rlcp.server.processor.check.PreCheckResultAwareCheckProcessor;

import java.math.BigDecimal;

/**
 * Simple CheckProcessor implementation. Supposed to be changed as needed to provide
 * necessary Check method support.
 */
public class CheckProcessorImpl implements PreCheckResultAwareCheckProcessor<String> {
    @Override
    public CheckingSingleConditionResult checkSingleCondition(ConditionForChecking condition, String instructions, GeneratingResult generatingResult) throws Exception {
        System.out.println("code: " + generatingResult.getCode());
        System.out.println("instructions: " + generatingResult.getInstructions());
        System.out.println("text: " + generatingResult.getText());

        System.out.println(instructions);
        JSONObject answers = new JSONObject(instructions);
        JSONArray convResult = answers.getJSONArray("convResult");
        JSONArray poolResult = answers.getJSONArray("poolResult");

        BigDecimal points = new BigDecimal("1.0");
        String comment = "Ответ проверен";

        return new CheckingSingleConditionResult(points, comment);
    }

    @Override
    public void setPreCheckResult(PreCheckResult<String> preCheckResult) {}
}

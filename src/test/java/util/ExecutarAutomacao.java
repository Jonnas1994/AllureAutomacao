package util;

import com.google.common.collect.ImmutableMap;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

import static util.AllureFunc.*;
import static util.AllureFunc.allureSummary;
import static util.SystemFunc.*;

public class ExecutarAutomacao {

    @Test
    @Parameters({ "tipo" })
    public void jonnas(String param) throws IOException, InterruptedException {

        /*
            Run ==> mvn -Dtest=ExecutarAutomacao#jonnas -Dtipo=padrao -Dnome=Lucas test

            System.out.println("Tipo: " + System.getProperty("tipo"));
            System.out.println("Nome: " + System.getProperty("nome"));
         */

        atualizaDrivers();

        disableLOGS();
        allureClearHisory();

        allureEnvironment(
                ImmutableMap.<String, String>builder()
                        .put("Browser", "Chrome")
                        .put("Chrome Driver", "80.0.3987.149")
                        .put("User", "maxstring.paulo")
                        .put("Solution", "maxRoteirizador [NV]")
                        .put("URL", "http://10.163.17.219:4400")
                        .put("Key", "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjhMVVQ4WmNFUjhqVVkzNjRDYi9XTHc9PSIsImp0aSI6IjdiNDI5OTM2MmE0NDQzN2FhYTg1MzAyYmM0YTQ3YmEwIiwiQ29ubmVjdGlvbkNsb3VkIjoidFZDUFcrSzQxMUpZSjY4VWZwY2VNMGxIbWF5SlBwcmlZbVozMGo2ZTFMd1R0ZDZhZDR5Mk5hbFJMMExXNE9kS2hxMHMwb2V2TWtCTnJyK0d0Q1h4dEZrQ1ZDUjczR1UyWGMzRVUyOFl1djVvUnpCN3RKeE1PQTVsSldyWlhLd21FKzJWa0FyaDd4WmVQRFA4Sit5Y2NIU2drb3F1YmJocTBrT1U4Zy9jTG9rVnBGWHJmc2VnZ3JXbnAzY0EzSGZDdldrTWMwZDBGQUhYS3RZcVRRMGhiajNGemZGUlBJaHNrbGY4MzBPWVkxVS9BYmhxMnIrYThRdUdEajhSRmJuRjhHZnEyTkREdkFhQWMyNG1IT0xGN00zeUpISVVDYkVQUU9yOTlpWCtPcjA9IiwiQ29ubmVjdGlvbkNsaWVudCI6InpSWXM1NTJmN2VHKzNLTkZoYWRUM2Z0Q00rWVN6NVI0SmtseXpsc3NnY2s9IiwiQ2xpZW50SWQiOiJPY3c0U3d6SCtXSHpiV2tkRkxmZzhnPT0iLCJVc2VyIjoiOExVVDhaY0VSOGpVWTM2NENiL1dMdz09IiwiVXNlcklkIjoiNHNGTkpKLzY3NDJnRlN2dnE3NjA2Zz09IiwiRW52aXJvbm1lbnQiOiJTSlpGWE45OXovMUlGTU9uV2NlbzNLdVVmcFlRaWZ6dXRyRHdMVktleWNnPSIsIkVudmlyb25tZW50SWQiOiI2cWFqdm1VeHVDc2hQVE1zajVqMzF3PT0iLCJVbmlxdWVOYW1lIjoiaHFFYVFYb2crTkxCMlEycEpVVURkWWNKMS9pNkcvaXlUZERJVHBRNVR3bkxGeXQ5TXJiUE1GeExlcC9WektWQyIsIlZlcnNpb24iOiJldlNPYXVUWC80c3ZZOS9hdkJaYnNnPT0iLCJuYmYiOjE1NTQ0ODU4ODEsImV4cCI6MTU1NDU3MjI4MSwiaWF0IjoxNTU0NDg1ODgxLCJpc3MiOiJNYXhpbWEiLCJhdWQiOiJNYXhpbWEifQ.cPvTy73umWK4wnL_4WFxCsvcqD7_rHbQzm5dSr40F8n9TOXPgb9QpDgPWonfLpPCOJCAgLO1gyRcJJJaODgbMA")
                        .build(), System.getProperty("user.dir") + "/allure-results/");

        allureExecutors("Mavem",
                        "Mavem",
                        "TestNG + Selenium + Allure Framework",
                        "https://docs.qameta.io/allure/");

        runTestSuites();
        checkFolderAllure();
        generateHistory();
        generateReport();
        allureSummary();
        modifyReport();
        openReport();

    }
}
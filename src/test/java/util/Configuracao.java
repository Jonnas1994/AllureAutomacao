package util;

import DTO.Clientes;
import DTO.TestCase;
import com.google.gson.Gson;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Configuracao {

    public static ChromeOptions chromeOptions;

    public static String resolucao;
    public static boolean modoGrafico;
    public static String avdName;
    public static String deviceName;
    public static String packageName;
    public static String activity;
    public static String apkPath;
    public static String chromeVersion;
    public static String urlBaseApi;

    public Configuracao(){
        try {

            this.resolucao = "1366,768";
            this.modoGrafico =  true;
            this.avdName =  "Emulador-1";
            this.deviceName =  "5c598d9b32";
            this.packageName =  "br.com.maximasistemas.portalexecutivodelivery";
            this.activity =  "br.com.maximasistemas.portalexecutivodelivery.activity.ActivitySplashscreen";
            this.apkPath =  "apps/android/maxMotorista-2.19.10.28.1.apk";
            this.chromeVersion = "86";
            this.urlBaseApi = "http://10.163.17.219:9009/maxRoteirizadorService";

            chromeOptions = new ChromeOptions();

        }catch (Exception ex){
            new Exception("Exception message");
        }
    }

    public enum Environment
    {
        RoterizadorNV("http://10.163.17.219:4400/#/?PWE=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjhMVVQ4WmNFUjhqVVkzNjRDYi9XTHc9PSIsImp0aSI6IjdiNDI5OTM2MmE0NDQzN2FhYTg1MzAyYmM0YTQ3YmEwIiwiQ29ubmVjdGlvbkNsb3VkIjoidFZDUFcrSzQxMUpZSjY4VWZwY2VNMGxIbWF5SlBwcmlZbVozMGo2ZTFMd1R0ZDZhZDR5Mk5hbFJMMExXNE9kS2hxMHMwb2V2TWtCTnJyK0d0Q1h4dEZrQ1ZDUjczR1UyWGMzRVUyOFl1djVvUnpCN3RKeE1PQTVsSldyWlhLd21FKzJWa0FyaDd4WmVQRFA4Sit5Y2NIU2drb3F1YmJocTBrT1U4Zy9jTG9rVnBGWHJmc2VnZ3JXbnAzY0EzSGZDdldrTWMwZDBGQUhYS3RZcVRRMGhiajNGemZGUlBJaHNrbGY4MzBPWVkxVS9BYmhxMnIrYThRdUdEajhSRmJuRjhHZnEyTkREdkFhQWMyNG1IT0xGN00zeUpISVVDYkVQUU9yOTlpWCtPcjA9IiwiQ29ubmVjdGlvbkNsaWVudCI6InpSWXM1NTJmN2VHKzNLTkZoYWRUM2Z0Q00rWVN6NVI0SmtseXpsc3NnY2s9IiwiQ2xpZW50SWQiOiJPY3c0U3d6SCtXSHpiV2tkRkxmZzhnPT0iLCJVc2VyIjoiOExVVDhaY0VSOGpVWTM2NENiL1dMdz09IiwiVXNlcklkIjoiNHNGTkpKLzY3NDJnRlN2dnE3NjA2Zz09IiwiRW52aXJvbm1lbnQiOiJTSlpGWE45OXovMUlGTU9uV2NlbzNLdVVmcFlRaWZ6dXRyRHdMVktleWNnPSIsIkVudmlyb25tZW50SWQiOiI2cWFqdm1VeHVDc2hQVE1zajVqMzF3PT0iLCJVbmlxdWVOYW1lIjoiaHFFYVFYb2crTkxCMlEycEpVVURkWWNKMS9pNkcvaXlUZERJVHBRNVR3bkxGeXQ5TXJiUE1GeExlcC9WektWQyIsIlZlcnNpb24iOiJldlNPYXVUWC80c3ZZOS9hdkJaYnNnPT0iLCJuYmYiOjE1NTQ0ODU4ODEsImV4cCI6MTU1NDU3MjI4MSwiaWF0IjoxNTU0NDg1ODgxLCJpc3MiOiJNYXhpbWEiLCJhdWQiOiJNYXhpbWEifQ.cPvTy73umWK4wnL_4WFxCsvcqD7_rHbQzm5dSr40F8n9TOXPgb9QpDgPWonfLpPCOJCAgLO1gyRcJJJaODgbMA"),
        RoterizadorPrime(""),
        MotoristaNV("http://10.163.17.219:4200/#/?PWE=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjhMVVQ4WmNFUjhqVVkzNjRDYi9XTHc9PSIsImp0aSI6IjdiNDI5OTM2MmE0NDQzN2FhYTg1MzAyYmM0YTQ3YmEwIiwiQ29ubmVjdGlvbkNsb3VkIjoidFZDUFcrSzQxMUpZSjY4VWZwY2VNMGxIbWF5SlBwcmlZbVozMGo2ZTFMd1R0ZDZhZDR5Mk5hbFJMMExXNE9kS2hxMHMwb2V2TWtCTnJyK0d0Q1h4dEZrQ1ZDUjczR1UyWGMzRVUyOFl1djVvUnpCN3RKeE1PQTVsSldyWlhLd21FKzJWa0FyaDd4WmVQRFA4Sit5Y2NIU2drb3F1YmJocTBrT1U4Zy9jTG9rVnBGWHJmc2VnZ3JXbnAzY0EzSGZDdldrTWMwZDBGQUhYS3RZcVRRMGhiajNGemZGUlBJaHNrbGY4MzBPWVkxVS9BYmhxMnIrYThRdUdEajhSRmJuRjhHZnEyTkREdkFhQWMyNG1IT0xGN00zeUpISVVDYkVQUU9yOTlpWCtPcjA9IiwiQ29ubmVjdGlvbkNsaWVudCI6InpSWXM1NTJmN2VHKzNLTkZoYWRUM2Z0Q00rWVN6NVI0SmtseXpsc3NnY2s9IiwiQ2xpZW50SWQiOiJPY3c0U3d6SCtXSHpiV2tkRkxmZzhnPT0iLCJVc2VyIjoiOExVVDhaY0VSOGpVWTM2NENiL1dMdz09IiwiVXNlcklkIjoiNHNGTkpKLzY3NDJnRlN2dnE3NjA2Zz09IiwiRW52aXJvbm1lbnQiOiJTSlpGWE45OXovMUlGTU9uV2NlbzNLdVVmcFlRaWZ6dXRyRHdMVktleWNnPSIsIkVudmlyb25tZW50SWQiOiI2cWFqdm1VeHVDc2hQVE1zajVqMzF3PT0iLCJVbmlxdWVOYW1lIjoiaHFFYVFYb2crTkxCMlEycEpVVURkWWNKMS9pNkcvaXlUZERJVHBRNVR3bkxGeXQ5TXJiUE1GeExlcC9WektWQyIsIlZlcnNpb24iOiJldlNPYXVUWC80c3ZZOS9hdkJaYnNnPT0iLCJuYmYiOjE1NTQ0ODU4ODEsImV4cCI6MTU1NDU3MjI4MSwiaWF0IjoxNTU0NDg1ODgxLCJpc3MiOiJNYXhpbWEiLCJhdWQiOiJNYXhpbWEifQ.cPvTy73umWK4wnL_4WFxCsvcqD7_rHbQzm5dSr40F8n9TOXPgb9QpDgPWonfLpPCOJCAgLO1gyRcJJJaODgbMA"),
        MotoristaPrime("");

        private String url;

        Environment(String envUrl) {
            this.url = envUrl;
        }

        public String getUrl() {
            return url;
        }
    }

    public enum mxWebDriver
    {
        Chrome("webdriver.chrome.driver", "./drivers/chromedriver.exe"),
        Firefox("webdriver.gecko.driver", "./drivers/geckodriver.exe");

        private String dir, driver;

        mxWebDriver(String driver, String pathDriver) {
            this.dir = pathDriver;
            this.driver = driver;
        }

        public String getDir() {
            return dir;
        }

        public String getDriver() {
            return driver;
        }
    }

    public static WebDriver getDriver(){

        if(!modoGrafico){

            chromeOptions.addArguments("--window-size=" + resolucao);
            chromeOptions.setExperimentalOption("useAutomationExtension", false);
            chromeOptions.addArguments("--headless");
        }

        return new ChromeDriver(chromeOptions);
    }

    public static Object readRta(String className) throws FileNotFoundException {

        BufferedReader br = new BufferedReader(new FileReader("src/test/java/RTA/"+ className.replace(".","/") +".json"));
        return new Gson().fromJson(br, TestCase.class);
    }

    public static Object readToken() throws FileNotFoundException {

        BufferedReader br = new BufferedReader(new FileReader("src/test/java/util/TokenClientes.json"));
        return new Gson().fromJson(br, Clientes.class);
    }

    public static Object readConf() throws FileNotFoundException {

        BufferedReader br = new BufferedReader(new FileReader("src/test/java/RTA/config.json"));
        return new Gson().fromJson(br, TestCase.class);
    }
}

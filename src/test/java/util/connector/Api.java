package util.connector;

import DTO.Clientes;
import DTO.TestCase;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import okhttp3.*;
import one.util.streamex.StreamEx;
import org.openqa.selenium.By;
import org.testng.Assert;
import util.AllureFunc;
import java.io.IOException;
import util.Configuracao;

public class Api {

    public static Configuracao config = new Configuracao();

    public static void executaTestCase(String testCaseName, TestCase objTestCase) throws IOException {

        String idStep = new String();
        String idSubStep = new String();
        By elemento;
        AllureFunc objAllureFunc = new AllureFunc();
        Clientes listaToken = (Clientes) config.readToken();

        //Busca o caso de teste dentro da suite
        long posicaoDoTeste = StreamEx.of(objTestCase.cases)
                .indexOf(caseTest -> caseTest.nameClass.equals(testCaseName))
                .getAsLong();
        TestCase.Case objCase = objTestCase.cases.get((int) posicaoDoTeste);

        //Atualiza a descrição do Test
        Allure.getLifecycle().updateTestCase(testResult  -> testResult.setName(objCase.name));
        Allure.getLifecycle().updateTestCase(testResult  -> testResult.setDescription(objCase.description));
        Allure.getLifecycle().updateTestCase(testResult  -> testResult.setFullName(objCase.name));

        //Percorre a lista de clientes
        for (Clientes.Cliente cli : listaToken.clientes)
        {
            //Percorre as lista de steps definidas na RTA
            for (TestCase.Step step : objCase.steps)
            {
                try {
                    idStep = objAllureFunc.startStep(step.description + "(" + cli.nome + ")");

                    //Percorre a slista de comandos para a Step atual
                    for (TestCase.Command objcmd : step.commands){

                        execComand(objcmd.comand, objcmd.target, objcmd.value, cli.token);

                    }
                    objAllureFunc.stopStep();

                }catch (AssertionError ex){

                    objAllureFunc.stopStep(Status.FAILED, idStep);
                    throw ex;
                }
            }
        }
    }

    public static void execComand(String method, String target, String value, String token) throws IOException {
        try {
            OkHttpClient client = new OkHttpClient();
            Request.Builder urlReq = new Request.Builder().url(config.urlBaseApi);
            RequestBody body;
            Request request;

            switch (method)
            {
                case "GET":
                    urlReq.get();
                    break;

                case "POST":
                    body = RequestBody.create(MediaType.parse("application/json"), value);
                    urlReq.post(body);
                    break;
            }

            urlReq
                .addHeader("accept", "application/json, text/plain, */*")
                .addHeader("authorization", "Bearer " + token)
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36")
                .addHeader("cache-control", "no-cache");

            request = urlReq.build();
            Response response = client.newCall(request).execute();

            Assert.assertEquals(response.code(), 200);

        } catch (IOException ex) {
            throw ex;
        }
    }

}

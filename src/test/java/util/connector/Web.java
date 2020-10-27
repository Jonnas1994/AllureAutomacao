package util.connector;

import DTO.TestCase;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import one.util.streamex.StreamEx;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.AllureFunc;
import util.Configuracao;
import util.operator.Selenium;

import java.io.FileNotFoundException;

public class Web {

    public static WebDriver webDriver;
    protected static Selenium seleniumOperator;
    protected static WebDriverWait wait;
    public static Configuracao config = new Configuracao();

    public static void opemNavegador(String url) {
        System.setProperty(Configuracao.mxWebDriver.Chrome.getDriver(), Configuracao.mxWebDriver.Chrome.getDir());
        modoExecucao(url);
    }

    private static void modoExecucao(String urlAplicacao){

        webDriver = config.getDriver();
        wait = new WebDriverWait(webDriver, 30);

        webDriver.get(urlAplicacao);
        seleniumOperator = new Selenium(webDriver, wait);
    }

    public static void executaTestCase(String testCaseName, TestCase objTestCase) throws FileNotFoundException {

        String idStep = new String();
        String idSubStep = new String();
        By elemento;
        AllureFunc objAllureFunc = new AllureFunc();

        //Busca o caso de teste dentro da suite
        long posicaoDoTeste = StreamEx.of(objTestCase.cases)
                                .indexOf(caseTest -> caseTest.nameClass.equals(testCaseName))
                                .getAsLong();
        TestCase.Case objCase = objTestCase.cases.get((int) posicaoDoTeste);

        //Atualiza a descrição do Test
        Allure.getLifecycle().updateTestCase(testResult  -> testResult.setName(objCase.name));
        Allure.getLifecycle().updateTestCase(testResult  -> testResult.setDescription(objCase.description));
        Allure.getLifecycle().updateTestCase(testResult  -> testResult.setFullName(objCase.name));

        //Percorre as lista de steps definidas na RTA
        for (TestCase.Step step : objCase.steps)
        {
            try {
                idStep = objAllureFunc.startStep(step.description);

                //Percorre a slista de comandos para a Step atual
                for (TestCase.Command objcmd : step.commands){

                    seleniumOperator = seleniumOperator.findElement(objcmd.findBy, objcmd.target);
                    execComand(objcmd.comand, objcmd.findBy, objcmd.target, objcmd.value);

                    //Verifica se existe subComandos / SubStep
                    if(objcmd.subComand != null && objcmd.subComand.size() > 0){

                        //Percorre a lista de subComandos / SubStep
                        for (TestCase.SubComand objSubCmd : objcmd.subComand){
                            try {

                                seleniumOperator = seleniumOperator.findElement(objSubCmd.findBy, objSubCmd.target);

                                idSubStep = objAllureFunc.startStep(objSubCmd.description);
                                execComand(objSubCmd.comand, objSubCmd.findBy, objSubCmd.target, objSubCmd.value);
                                objAllureFunc.stopStep();

                            }catch (Exception ex){

                                objAllureFunc.stopStep(Status.FAILED, idSubStep);
                                throw ex;
                            }

                        }
                        objAllureFunc.stopStep(Status.PASSED, idStep);
                    }
                }
                objAllureFunc.stopStep();

            }catch (AssertionError ex){

                Allure.getLifecycle().addAttachment("capturaDeTela","image/png","json", objAllureFunc.screenshot(webDriver));
                objAllureFunc.stopStep(Status.FAILED, idStep);
                throw ex;
            }
        }
    }

    public static void execComand(String cmd, String findBy, String target, String value){

        switch (cmd)
        {
            case "wait_ElementToBeClickable":
                seleniumOperator.wait_ElementToBeClickable();
                break;

            case "wait_VisibilityOfElementLocated":
                seleniumOperator.wait_VisibilityOfElementLocated();
                break;

            case "click":
                seleniumOperator.click();
                break;

            case "wait_time":
                seleniumOperator.wait_time(Long.parseLong(value));
                break;

            case "navigate":
                seleniumOperator.navigate(value);
                break;

            case "sendKeys":
                seleniumOperator.sendKeys(value);
                break;

            case "comboPesquisa":
                seleniumOperator = seleniumOperator.findElement(findBy, target);
                break;

            case "mouseMove":
                seleniumOperator.mouseMove();
                break;

            case "findElement":
                seleniumOperator = seleniumOperator.findElement(findBy, target);
                break;

            case "assertEquals_text":
                seleniumOperator.assertEquals_text(value);
                break;

            case "assertEquals_checkbox":
                seleniumOperator.assertEquals_checkbox(value);
                break;
        }
    }

    public static void closeBrowser() {
        webDriver.quit();
    }

}
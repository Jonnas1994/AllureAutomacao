package testCases;

import DTO.TestCase;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.Configuracao;

import java.io.FileNotFoundException;
import java.io.IOException;

import static util.connector.Api.executaTestCase;

public class Rotas {

    private TestCase objTestCase;

    @BeforeMethod
    public void InicializaTeste() throws FileNotFoundException {

        objTestCase = (TestCase) Configuracao.readRta(this.getClass().getCanonicalName());
    }


    @Severity(SeverityLevel.NORMAL)
    @Test(description="", priority=1)
    @Description("")
    public void ConsultaCarregamento() throws IOException {

        executaTestCase("TC_ConsultaCarregamento", objTestCase);
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description="", priority=2)
    @Description("")
    public void ConsultaPainelBaixaDeFrete() throws IOException {

        executaTestCase("TC_ConsultaPainelBaixaDeFrete", objTestCase);
    }

    @AfterMethod
    public void tearDown() {

    }
}

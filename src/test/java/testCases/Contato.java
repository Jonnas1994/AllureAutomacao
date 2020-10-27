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

import static util.connector.Web.*;

public class Contato {

    private TestCase objTestCase;

    @BeforeMethod
    public void InicializaTeste() throws FileNotFoundException {

        opemNavegador(Configuracao.Environment.MotoristaNV.getUrl());
        objTestCase = (TestCase) Configuracao.readRta(this.getClass().getCanonicalName());
    }


    @Severity(SeverityLevel.NORMAL)
    @Test(description="", priority=1)
    @Description("")
    public void cadastrar() throws IOException {

        executaTestCase("TC_Cadastrar", objTestCase);
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description="", priority=2)
    @Description("")
    public void editar() throws IOException {

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description="", priority=3)
    @Description("")
    public void filtrar() throws IOException {

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description="", priority=4)
    @Description("")
    public void deletar() throws IOException {

    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description="", priority=5)
    @Description("")
    public void camposObrigatorios() throws IOException {

    }

    @AfterMethod
    public void tearDown() {
        closeBrowser();
    }
}

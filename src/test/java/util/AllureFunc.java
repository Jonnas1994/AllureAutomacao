package util;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class AllureFunc {

    StepResult sr;
    String uuid;

    public String startStep(String name){
        sr = new StepResult();
        sr.setName(name);
        sr.setStatus(Status.PASSED);
        uuid = UUID.randomUUID().toString();

        Allure.getLifecycle().startStep(uuid, sr);
        return uuid;
    }
    public void stopStep(Status status, String uuid){
        sr.setStatus(status);
        Allure.getLifecycle().updateStep(uuid, stepResult -> stepResult.setStatus(status));
        Allure.getLifecycle().stopStep(uuid);
    }

    public void stopStep(){
        Allure.getLifecycle().stopStep(uuid);
    }

    public static byte[] screenshot(WebDriver driver) /* throws IOException */ {
        try {

            File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            return Files.toByteArray(screen);

        } catch (IOException e) {
            return null;
        }
    }
    public static byte[] screenshotFile(String fileJson)  {
        ;
        byte[] byteArray = fileJson.getBytes();
        return byteArray;

    }

    public static void allureEnvironment(ImmutableMap<String, String> environmentValuesSet, String customResultsPath)  {
        try {

            System.out.println("Definindo variaveis de ambiente...");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element environment = doc.createElement("environment");
            doc.appendChild(environment);
            environmentValuesSet.forEach((k, v) -> {
                Element parameter = doc.createElement("parameter");
                Element key = doc.createElement("key");
                Element value = doc.createElement("value");
                key.appendChild(doc.createTextNode(k));
                value.appendChild(doc.createTextNode(v));
                parameter.appendChild(key);
                parameter.appendChild(value);
                environment.appendChild(parameter);
            });

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            File allureResultsDir = new File(customResultsPath);
            if (!allureResultsDir.exists()) allureResultsDir.mkdirs();
            StreamResult result = new StreamResult(
                    new File( customResultsPath + "environment.xml"));
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public static void allureClearHisory() throws IOException {

        System.out.println("Deletando pastas do Allure...");
        FileUtils.deleteDirectory(new File("allure-results"));
        FileUtils.deleteDirectory(new File("allure-report"));
    }

    public static void checkFolderAllure() throws IOException {

        System.out.println("Verificando geracao dos resultados...");
        LocalDateTime then = LocalDateTime.now();
        while(new File(new File(".").getCanonicalPath() + "\\allure-results").exists() != true){
            if (ChronoUnit.SECONDS.between(then, LocalDateTime.now()) >= 20){
                throw new FileNotFoundException("Ops! Não foi possivel gerar o Diretorio [allure-report] :(");
            }
        }
    }

    public static void generateHistory() throws IOException {

        System.out.println("Salvando Historico dos testes anteriores...");
        File srcDir = new File("allure-report/history");
        File destDir = new File("allure-results/history");

        if(srcDir.exists()){
            FileUtils.copyDirectory(srcDir, destDir);
        }
    }

    public static void modifyReport() throws IOException {

        System.out.println("Movendo arquivos personaliados...");
        File titlePage = new File("src/test/java/util/sourceReport/index.html");
        File pathTitlePage = new File("allure-report/index.html");
        FileUtils.copyFile(titlePage, pathTitlePage);

        File logo = new File("src/test/java/util/sourceReport/roteirizador.png");
        File pathLogo = new File("allure-report/plugins/screen-diff/roteirizador.png");
        FileUtils.copyFile(logo, pathLogo);

        File nameMenu = new File("src/test/java/util/sourceReport/styles.css");
        File pathNameMenu = new File("allure-report/plugins/screen-diff/styles.css");
        FileUtils.copyFile(nameMenu, pathNameMenu);
    }

    public static  void allureSummary() throws IOException, InterruptedException {

        File file = new File("allure-report/widgets/summary.json");
        List<String> lines = Files.readLines(file, Charset.defaultCharset());
        lines.set(1, "\"reportName\" : \"Testes Automáticos\",");

        PrintWriter writer = new PrintWriter(file.getAbsolutePath());
        for (String line : lines) {
            writer.println(line);
        }
        writer.close();
        Thread.sleep(5000);
    }

    public static void generateReport() throws IOException, InterruptedException {

        System.out.println("Gerando relatorio...");

        LocalDateTime then = LocalDateTime.now();
        String dirLocal = new File(".").getCanonicalPath();

        if (new File(dirLocal + "\\allure-results").exists()) {

            System.out.println("Gerando Report do Allure...");

            Runtime.getRuntime().exec("cmd.exe /K " +
                    "cd "+ dirLocal +" && allure generate  "+ dirLocal +"\\allure-results --clean -o "+ dirLocal +"\\allure-report");

            then = LocalDateTime.now();
            File fileReport = new File(dirLocal + "\\allure-report");
            while(fileReport.exists() != true){
                if (ChronoUnit.SECONDS.between(then, LocalDateTime.now()) >= 15){
                    throw new FileNotFoundException("Ops! Não foi possivel gerar o Diretorio [allure-report] :(");
                }
            }
            System.out.println("Report gerado com sucesso!");
            Thread.sleep(5000);

        }else{
            throw new FileNotFoundException("Ops! Diretorio [allure-results] não encontrado :(");
        }
    }

    public static void allureExecutors(String name, String type, String buildName, String buildUrl) throws IOException {

        JSONObject jsonExecutors = new JSONObject();
        jsonExecutors.put("name", name);
        jsonExecutors.put("type", type);
        jsonExecutors.put("buildName", buildName);
        jsonExecutors.put("buildUrl", buildUrl);

        System.out.println("Definindo Executores...");
        FileWriter fileExecutors = new FileWriter(System.getProperty("user.dir") + "/allure-results/executor.json");
        fileExecutors.write(jsonExecutors.toJSONString());
        fileExecutors.close();

    }
}

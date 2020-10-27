package util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.TestNG;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import util.Configuracao;

public class SystemFunc {

    public static  void disableLOGS(){

        System.out.println("Desabilitando sistema de Logs [SLF4J]...");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
    }

    public static void runTestSuites() throws IOException {

        List<File> files = listSuites();
        
        System.out.println("Executando Suites de teste...");

        for (File file : files) {

            TestNG runner = new TestNG();
            List<String> suitefiles = new ArrayList<String>();
            suitefiles.add(file.getPath());
            runner.setTestSuites(suitefiles);
            runner.run();
        }
    }

    public static void openReport() throws IOException {

        String output = "";
        String portServer = "";
        String dirLocal = new File(".").getCanonicalPath();
        String cmdPath = "CD\\Windows\\System32";

        output = execComand(Runtime.getRuntime().exec("cmd.exe /K cd "+ dirLocal +" && allure open"),"/>");
        System.out.println(output);

        List<String> items = Arrays.asList(output.split(":"));
        portServer = items.get(items.size()-1).replace("/>","");

        output = execComand(Runtime.getRuntime().exec("cmd.exe /K "+ cmdPath +" && netstat -ano | find /i \"listening\" | find \""+ portServer +"\" | find \"0.0.0.0\""),"\r");
        items = Arrays.asList(output.split(" "));

        System.out.println("[!] Para encerrar o servidor digite no cmd: $ taskkill /F /PID " + items.get(items.size()-1).replace("\r",""));
        System.out.println("Abrindo relatorio...\n");
    }

    private static String execComand(Process cmd, String separator) throws IOException {

        InputStream inputStream = cmd.getInputStream();
        String output = "";
        int _char;

        while ((_char = inputStream.read()) != -1) {
            output += (char) _char;
            if(output.contains(separator)){
                break;
            }
        }

        inputStream.close();
        return output;
    }

    private static List<File> listSuites() throws IOException {

        System.out.println("Mapeando Solution...");
        File dir = new File("src/test/java/suites");
        String[] extensions = new String[] { "xml" };
        return (List<File>) FileUtils.listFiles(dir, extensions, true);
    }

    public static void atualizaDrivers(){



    }
}

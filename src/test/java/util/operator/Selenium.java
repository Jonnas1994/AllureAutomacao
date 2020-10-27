package util.operator;

import DTO.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Selenium {

    protected static WebDriver driver;
    protected static WebDriverWait wait;
    private By elemento;
    private WebElement elementToClick;
    private Actions builder;

    public Selenium(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public Selenium findElement(String findBy, String target){

        switch (findBy)
        {
            case "id":
                elemento = By.id(target);
                break;

            case "xpath":
                elemento = By.xpath(target);
                break;

            case "css":
                elemento = By.cssSelector(target);
                break;
        }
        return this;
    }

    public void mouseMove(){

        elementToClick = driver.findElement(elemento);
        builder = new Actions(driver);

        //mouseDownAt
        builder.moveToElement(elementToClick).clickAndHold().perform();
        //mouseMoveAt
        builder.moveToElement(elementToClick).perform();
        //mouseUpAt
        builder.moveToElement(elementToClick).release().perform();
    }

    public void navigate(String url){
        String novaUrl = driver.getCurrentUrl().replace("paineis", url);
        driver.get(novaUrl);
    }

    public void click(){

        try {

            wait.until(ExpectedConditions.elementToBeClickable(elemento));
            driver.findElement(elemento).click();

        }catch (org.openqa.selenium.ElementClickInterceptedException exIntercepted){
            try{

                elementToClick = driver.findElement(elemento);
                ((JavascriptExecutor)driver).executeScript("window.scrollTo(0," + elementToClick.getLocation().y +")");
                elementToClick.click();

            }catch (Exception exElementClickIntercepted){

                elementToClick = driver.findElement(elemento);
                ((JavascriptExecutor)driver).executeScript("window.scrollTo(0," + elementToClick.getLocation().x +")");
                elementToClick.click();

            }
        }catch (org.openqa.selenium.ElementNotInteractableException exInteractable){

            wait.until(ExpectedConditions.visibilityOfElementLocated(elemento));
        }
    }

    public void wait_ElementToBeClickable(){

        wait.until(ExpectedConditions.elementToBeClickable(elemento));
    };

    public void wait_VisibilityOfElementLocated(){

        wait.until(ExpectedConditions.visibilityOfElementLocated(elemento));
    }

    public void wait_time(long seconds) {
        try {
            Thread.sleep(seconds*1000);

        }catch (InterruptedException ex){
        }
    }

    public void sendKeys(String value){

        wait.until(ExpectedConditions.visibilityOfElementLocated(elemento));
        driver.findElement(elemento).clear();
        driver.findElement(elemento).sendKeys(value);
    }

    public void assertEquals_text(String value){

        wait.until(ExpectedConditions.visibilityOfElementLocated(elemento));
        Assert.assertEquals(driver.findElement(elemento).getText(), value);
    }

    public void assertEquals_checkbox(String value){

        WebElement checkbox = driver.findElement(elemento);
        wait.until(ExpectedConditions.visibilityOfElementLocated(elemento));
        Assert.assertEquals( Boolean.parseBoolean(value), checkbox.isSelected());
    }
}

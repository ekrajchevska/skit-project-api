import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestDeleteOperation {

    public static WebDriver driver;
    public static String url;

    @BeforeAll
    public static void setUp() throws InterruptedException{
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Eva\\eclipse-workspace\\SeleniumTest\\lib\\geckodriver\\geckodriver.exe");
        driver = new FirefoxDriver();
        url = "http://localhost:3000/";
        driver.get(url);
        Thread.sleep(3000);
    }

    @Test
    public void deleteRandomStudent() throws InterruptedException {
        List<WebElement> studentsList = driver.findElement(By.id("student-items")).findElements(By.xpath("./*"));
        int currentSize = studentsList.size();
        int randomStudent = ThreadLocalRandom.current().nextInt(0, studentsList.size());
        studentsList.get(randomStudent).findElement(By.name("deleteStudent")).click();
        Thread.sleep(1500);

        assertEquals(currentSize-1, driver.findElement(By.id("student-items")).findElements(By.xpath("./*")).size());
    }

    @Test
    public void deleteProgramWithStudentsEnrolled() throws InterruptedException {
        List<WebElement> programs = driver.findElement(By.id("program-items")).findElements(By.xpath("./*"));
        for(WebElement studyProgram : programs) {
            if(studyProgram.getText().contains("KNI")) {
                studyProgram.findElement(By.name("deleteProgram")).click();
                break;
            }
        }
        Thread.sleep(1500);
        Alert alert = driver.switchTo().alert();

        assertEquals(alert.getText(), "Cant delete study program with students enrolled!");
        alert.accept();
    }

    @Test
    public void deleteProgramWithNoStudentsEnrolled() throws InterruptedException {
        List<WebElement> programs = driver.findElement(By.id("program-items")).findElements(By.xpath("./*"));
        int currentSize = programs.size();
        for(WebElement studyProgram : programs) {
            if(studyProgram.getText().contains("PET")) {
                studyProgram.findElement(By.name("deleteProgram")).click();
                break;
            }
        }

        Thread.sleep(1500);
        assertEquals(currentSize-1,driver.findElement(By.id("program-items")).findElements(By.xpath("./*")).size());
    }
}

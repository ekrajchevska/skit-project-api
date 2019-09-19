import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


public class TestAddOperation {

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
    public void addStudentWithEmptyInfo() throws InterruptedException {
        driver.findElement(By.id("addStudent")).click();
        driver.findElement(By.name("studentName")).sendKeys("Evgenija");
        driver.findElement(By.name("studentIndex")).sendKeys("151050");

        driver.findElement(By.id("studentSubmit")).submit();
        Thread.sleep(2000);
        Alert alert = driver.switchTo().alert();

        assertEquals(alert.getText(), "Missing information!");
        alert.accept();
        driver.findElement(By.id("addStudent")).click();
    }

    @Test
    public void addStudyProgramWithEmptyInfo() throws InterruptedException {
        driver.findElement(By.id("addStudyProgram")).click();
        driver.findElement(By.id("programSubmit")).submit();
        Thread.sleep(2000);
        Alert alert = driver.switchTo().alert();

        assertEquals(alert.getText(), "Missing information!");
        alert.accept();
        driver.findElement(By.id("addStudyProgram")).click();
    }

    @Test	//happy path
    public void addStudyPrograms() throws InterruptedException {
        try {
            driver.findElement(By.name("studyProgramName")).sendKeys("IKI");
        }
        catch(NoSuchElementException e) {
            driver.findElement(By.id("addStudyProgram")).click();
            driver.findElement(By.name("studyProgramName")).sendKeys("IKI");
        }
        driver.findElement(By.id("programSubmit")).submit();

        driver.findElement(By.id("addStudyProgram")).click();
        driver.findElement(By.name("studyProgramName")).sendKeys("MT");
        driver.findElement(By.id("programSubmit")).submit();

        Thread.sleep(2000);
        List<WebElement> programsListItems = driver.findElement(By.id("program-items")).findElements(By.xpath("./*"));
        assertEquals(6, programsListItems.size());
    }

    @Test	//happy path
    public void addStudents() throws InterruptedException {
        try {
            driver.findElement(By.name("studentName")).sendKeys("Ivana");
        }catch(NoSuchElementException e) {
            driver.findElement(By.id("addStudent")).click();
            driver.findElement(By.name("studentName")).sendKeys("Ivana");
        }

        driver.findElement(By.name("studentSurname")).sendKeys("Ivanovska");
        driver.findElement(By.name("studentIndex")).sendKeys("151056");

        Select programsDropDown1 = new Select(driver.findElement(By.id("programsOptions")));
        programsDropDown1.selectByValue("MT");
        driver.findElement(By.id("studentSubmit")).submit();

        driver.findElement(By.id("addStudent")).click();
        driver.findElement(By.name("studentName")).sendKeys("Marko");
        driver.findElement(By.name("studentSurname")).sendKeys("Markovski");
        driver.findElement(By.name("studentIndex")).sendKeys("155027");
        Select programsDropDown2 = new Select(driver.findElement(By.id("programsOptions")));
        programsDropDown2.selectByValue("IKI");

        driver.findElement(By.id("studentSubmit")).submit();
        Thread.sleep(2000);

        List<WebElement> studentsListItems = driver.findElement(By.id("student-items")).findElements(By.xpath("./*"));
        assertEquals(5, studentsListItems.size());
    }

    @Test
    public void addExistingStudent() throws InterruptedException {
        try {
            driver.findElement(By.name("studentName")).sendKeys("Andrej");
        }catch(NoSuchElementException e) {
            driver.findElement(By.id("addStudent")).click();
            driver.findElement(By.name("studentName")).sendKeys("Andrej");
        }
        driver.findElement(By.name("studentSurname")).sendKeys("Andreev");
        driver.findElement(By.name("studentIndex")).sendKeys("151050");
        Select programsDropDown = new Select(driver.findElement(By.id("programsOptions")));
        programsDropDown.selectByValue("KNI");

        driver.findElement(By.id("studentSubmit")).submit();
        Thread.sleep(2000);
        Alert alert = driver.switchTo().alert();

        assertEquals(alert.getText(), "Error!");
        alert.accept();
        driver.findElement(By.id("addStudent")).click();
    }

    @Test
    public void addExistingStudyProgram() throws InterruptedException {

        try {
            driver.findElement(By.name("studyProgramName")).sendKeys("IKI");
        }
        catch(NoSuchElementException e) {
            driver.findElement(By.id("addStudyProgram")).click();
            driver.findElement(By.name("studyProgramName")).sendKeys("IKI");
        }
        driver.findElement(By.id("programSubmit")).submit();
        Thread.sleep(2000);
        Alert alert = driver.switchTo().alert();

        assertEquals(alert.getText(), "Study program with given name exists!");
        alert.accept();
        driver.findElement(By.id("addStudyProgram")).click();
    }

    @Test
    public void addStudentWithIndexAsLetters() throws InterruptedException {
        try{
            driver.findElement(By.name("studentName")).sendKeys("Simona");
        }catch(NoSuchElementException e) {
            driver.findElement(By.id("addStudent")).click();
            driver.findElement(By.name("studentName")).sendKeys("Simona");
        }

        driver.findElement(By.name("studentSurname")).sendKeys("Simevska");
        driver.findElement(By.name("studentIndex")).sendKeys("145abc");
        driver.findElement(By.id("studentSubmit")).submit();
        Thread.sleep(2000);
        Alert alert = driver.switchTo().alert();

        assertEquals(alert.getText(), "Error!");
        alert.accept();
        driver.findElement(By.id("addStudent")).click();
    }

    @Test
    public void addStudentWithIndexWithLessChars() throws InterruptedException {
        driver.findElement(By.id("addStudent")).click();
        driver.findElement(By.name("studentName")).sendKeys("Petar");
        driver.findElement(By.name("studentSurname")).sendKeys("Petrevski");
        driver.findElement(By.name("studentIndex")).sendKeys("17125");
        Select programsDropDown = new Select(driver.findElement(By.id("programsOptions")));
        programsDropDown.selectByValue("KNI");
        driver.findElement(By.id("studentSubmit")).submit();
        Thread.sleep(2000);
        Alert alert = driver.switchTo().alert();

        assertEquals(alert.getText(), "Error!");
        alert.accept();
        driver.findElement(By.id("addStudent")).click();
    }
}

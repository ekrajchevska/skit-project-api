//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.Alert;
//import org.openqa.selenium.By;
//import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.support.ui.Select;


public class TestEditOperation {

    //public static WebDriver driver;
    //public static String url;
//
    //@BeforeAll
    //public static void setUp() throws InterruptedException{
    //    System.setProperty("webdriver.gecko.driver", "C:\\Users\\Eva\\eclipse-workspace\\SeleniumTest\\lib\\geckodriver\\geckodriver.exe");
    //    driver = new FirefoxDriver();
    //    url = "http://localhost:3000/";
    //    driver.get(url);
    //    Thread.sleep(3000);
    //}
//
    //@Test	//happy path
    //public void testEditRandomStudentInfo() throws InterruptedException {
    //    List<WebElement> studentsList = driver.findElement(By.id("student-items")).findElements(By.xpath("./*"));
    //    int randomStudent = ThreadLocalRandom.current().nextInt(0, studentsList.size());
    //    //System.out.println(randomStudent);
    //    studentsList.get(randomStudent).findElement(By.name("editStudent")).click();
    //    driver.findElement(By.name("studentSurname")).clear();
    //    driver.findElement(By.name("studentSurname")).sendKeys("Filipov");
    //    driver.findElement(By.name("submitStudent")).submit();
    //    Thread.sleep(2000);
    //    System.out.println(randomStudent);
    //    assertEquals(studentsList.get(studentsList.size()-1).findElements(By.className("col-sm-1")).get(2).getText(), "Filipov");
//
    //}
//
    //@Test	//happy path
    //public void editStudyProgramInfo() throws InterruptedException {
    //    List<WebElement> programsList = driver.findElement(By.id("program-items")).findElements(By.xpath(".//*"));
    //    try {
    //        driver.findElement(By.name("studyProgramName")).clear();
    //    } catch(NoSuchElementException e) {
    //        programsList.get(0).findElement(By.name("editProgram")).click();
    //        driver.findElement(By.name("studyProgramName")).clear();
    //    }
//
    //    driver.findElement(By.name("studyProgramName")).sendKeys("ASI");
    //    driver.findElement(By.name("submitProgram")).submit();
    //    Thread.sleep(2000);
//
    //    assertEquals("ASI", programsList.get(0).findElements(By.className("col-sm-1")).get(0).getText());
    //}
//
    //@Test
    //public void editRandomStudyProgramToExistingStudyProgram() throws InterruptedException {
    //    List<WebElement> programsList = driver.findElement(By.id("program-items")).findElements(By.xpath("./*"));
    //    int randomProgram = ThreadLocalRandom.current().nextInt(0, programsList.size());
//
    //    programsList.get(randomProgram).findElement(By.name("editProgram")).click();
    //    driver.findElement(By.name("studyProgramName")).clear();
    //    driver.findElement(By.name("studyProgramName")).sendKeys("KNI");
    //    driver.findElement(By.name("submitProgram")).submit();
    //    Thread.sleep(2000);
    //    Alert alert = driver.switchTo().alert();
//
    //    assertEquals(alert.getText(), "Error!");
    //    alert.accept();
    //    programsList.get(randomProgram).findElement(By.name("editProgram")).click();
    //}
//
    //@Test
    //public void editStudentWithSameCredentialsAsAnother() throws InterruptedException {
    //    List<WebElement> studentsList = driver.findElement(By.id("student-items")).findElements(By.xpath("./*"));
    //    String studentName = studentsList.get(0).findElements(By.className("col-sm-1")).get(1).getText();
    //    String studentSurname = studentsList.get(0).findElements(By.className("col-sm-1")).get(2).getText();
    //    String studentProgram = studentsList.get(0).findElements(By.className("col-sm-1")).get(3).getText();
//
    //    int randomStudent = ThreadLocalRandom.current().nextInt(1, studentsList.size());
    //    try {
    //        driver.findElement(By.name("studentName")).clear();
    //    } catch(NoSuchElementException e) {
    //        studentsList.get(randomStudent).findElement(By.name("editStudent")).click();
    //        driver.findElement(By.name("studentName")).clear();
    //    }
//
    //    driver.findElement(By.name("studentName")).sendKeys(studentName);
    //    driver.findElement(By.name("studentSurname")).clear();
    //    driver.findElement(By.name("studentSurname")).sendKeys(studentSurname);
    //    Select programsDropDown = new Select(driver.findElement(By.id("programsOptions")));
    //    programsDropDown.selectByValue(studentProgram);
    //    driver.findElement(By.name("submitStudent")).submit();
    //    Thread.sleep(2000);
//
    //    assertEquals(studentName, studentsList.get(studentsList.size()-1).findElements(By.className("col-sm-1")).get(1).getText());
    //    assertEquals(studentSurname, studentsList.get(studentsList.size()-1).findElements(By.className("col-sm-1")).get(2).getText());
    //    assertEquals(studentProgram, studentsList.get(studentsList.size()-1).findElements(By.className("col-sm-1")).get(3).getText());
//
    //}
//
    //@Test
    //public void editStudentWithEmptyInfo() throws InterruptedException {
    //    List<WebElement> studentsList = driver.findElement(By.id("student-items")).findElements(By.xpath("./*"));
    //    int randomStudent = ThreadLocalRandom.current().nextInt(0, studentsList.size());
//
    //    studentsList.get(randomStudent).findElement(By.name("editStudent")).click();
    //    driver.findElement(By.name("studentName")).clear();
    //    driver.findElement(By.name("studentName")).sendKeys("");
    //    driver.findElement(By.name("submitStudent")).submit();
    //    Thread.sleep(2000);
    //    Alert alert = driver.switchTo().alert();
//
    //    assertEquals(alert.getText(), "Missing information!");
    //    alert.accept();
    //    studentsList.get(randomStudent).findElement(By.name("editStudent")).click();
    //}
//
    //@Test
    //public void editStudyProgramWithEmptyInfo() throws InterruptedException {
    //    List<WebElement> programsList = driver.findElement(By.id("program-items")).findElements(By.xpath("./*"));
    //    int randomProgram = ThreadLocalRandom.current().nextInt(0, programsList.size());
    //    try {
    //        driver.findElement(By.name("studyProgramName")).clear();
    //    } catch(NoSuchElementException e) {
    //        programsList.get(randomProgram).findElement(By.name("editProgram")).click();
    //        driver.findElement(By.name("studyProgramName")).clear();
    //    }
//
    //    driver.findElement(By.name("studyProgramName")).sendKeys("");
    //    driver.findElement(By.name("submitProgram")).submit();
    //    Thread.sleep(2000);
    //    Alert alert = driver.switchTo().alert();
//
    //    assertEquals(alert.getText(), "Missing information!");
    //    alert.accept();
    //    programsList.get(randomProgram).findElement(By.name("editProgram")).click();
    //}
//
}

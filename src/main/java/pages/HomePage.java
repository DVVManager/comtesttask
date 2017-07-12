package pages;

import com.codeborne.selenide.Condition;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertTrue;
import static pages.locators.HomePageLocators.*;

/**
 * Created by Administrator on 7/11/2017.
 */
public class HomePage extends BasePage<HomePage> {

    private String URL = System.getProperty("env");

    @Override
    protected void load() {
        open(URL);
    }

    @Override
    protected void isLoaded() throws Error {
       String homePageHeader= $(By.xpath(HOME_PAGE_TITLE)).shouldBe(Condition.visible).getText();
       assertTrue(homePageHeader.equalsIgnoreCase("todos"));
       $(NEW_TASK_INPUT).shouldBe(Condition.enabled);
    }

    @Step("Creating new Task with name : {0}")
    public void createNewTask(String taskName){
        $(NEW_TASK_INPUT).shouldBe(Condition.enabled).setValue(taskName).pressEnter();
        int tasksAmount=Integer.valueOf($(TASKS_COUNT).shouldBe(Condition.visible).getText());
        assertTrue(tasksAmount>0);
    }

    public static String getRandomTaskName() {
        return "Task_" + RandomStringUtils.randomAlphanumeric(7);
    }

    @Step("Amending Task with name : {0}")
    public String amendTask(String taskName){
        String newTaskName=getRandomTaskName();
        $(By.xpath(String.format(SPECIFIED_TASK,taskName))).doubleClick();
        $(By.xpath(String.format(EDIT_TASKFIELD,taskName))).setValue(newTaskName).pressEnter();
        assertTrue($(By.xpath(String.format(SPECIFIED_TASK,newTaskName))).isDisplayed());
        return newTaskName;
    }

    @Step("Deleting Task with name : {0}")
    public void deleteTask(String taskName){
        String xpath=String.format(SPECIFIED_TASK,taskName)+DELETE;
        $(By.xpath(xpath)).shouldBe(Condition.enabled).click();
    }


}

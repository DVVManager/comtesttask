import org.testng.annotations.Test;
import pages.HomePage;
import ru.yandex.qatools.allure.annotations.*;
import ru.yandex.qatools.allure.model.SeverityLevel;

/**
 * Created by Administrator on 7/11/2017.
 */
public class CRUDTest extends TestBase {

    HomePage homePage;
    String taskName;
    String newTaskName;

    //TODO: disregarding fact that dependency is not a good practice we can replace priority by dependsOnMethods
    // as how we dont have data source

    @Title("Creation title")
    @Description("This is creation of task")
    @Features("Feature - CREATE")
    @Stories({"CREATE"})
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 0)
     public void createNewTaskTest(){
     taskName=HomePage.getRandomTaskName();
     homePage=loadHomePage();
     homePage.createNewTask(taskName);
    }

    @Title("Amendment title")
    @Description("This is amendment of task")
    @Features("Feature - AMEND")
    @Stories({"AMEND"})
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 1)
    public void amendExistingTaskTest(){
      newTaskName= homePage.amendTask(taskName);
    }

    @Title("Deletion title")
    @Description("This is deletion of task")
    @Features("Feature - DELETE")
    @Stories({"DELETE"})
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 2)
    public void deleteTask(){
        homePage.deleteTask(newTaskName);
    }

}

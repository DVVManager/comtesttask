package pages.locators;

/**
 * Created by Administrator on 7/12/2017.
 */
public class HomePageLocators {
    public static final String HOME_PAGE_TITLE="//header/h1";
    public static final String NEW_TASK_INPUT="input.new-todo";
    public static final String TASKS_COUNT="span.todo-count strong";
    public static final String ALL_TASKS="a.selected";
    public static  String SPECIFIED_TASK=" //div[@class='view']/label[contains(.,'%s')]";
    public static  String EDIT_TASKFIELD="//input[@class='edit' and @value='%s']";
    public static final String DELETE="/following-sibling::button";
}

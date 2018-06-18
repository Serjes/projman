package github.serjes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ManagSystemTest {
    ManagSystem mSystem;
    ArrayList<String> allProjects;
    ArrayList<String> allPersons;
    long time;

    @Before
    public void setUp() throws Exception {
        mSystem = new ManagSystem(true);
        allProjects = new ArrayList<>(Arrays.asList("ATM", "Project management system", "JabaChat"));
        allPersons = new ArrayList<>(Arrays.asList("Валуев", "Иванов", "Сидоров"));
        time = System.currentTimeMillis();
    }

    @Test
    public void getProjectsInWork() throws Exception {
        HashSet<String> correctAnswer = new HashSet<>();
//        Collections.addAll(correctAnswer, "ATM JabaChat".split(" "));
        Collections.addAll(correctAnswer, "ATM", "JabaChat");
//        System.out.println(correctAnswer);
        ArrayList<String> projsInWork = mSystem.getProjectsInWork();
//        correctAnswer.containsAll(projsInWork);
        assertTrue("Not containsAll", correctAnswer.containsAll(projsInWork));
        assertEquals(correctAnswer.size(), projsInWork.size());
    }

    @Test
    public void getProjects() throws Exception {
        ArrayList<String> projs = mSystem.getProjects();
        Assert.assertTrue("Not containsAll", allProjects.containsAll(projs));
        Assert.assertEquals(allProjects.size(), projs.size());
    }

    @Test
    public void getAmountUnfinishedTask() throws Exception {
        int[] correctNum = {1, 0, 3};
        for (int i = 0; i < allProjects.size(); i++) {
            int num = mSystem.getAmountUnfinishedTask(allProjects.get(i));
            assertEquals(correctNum[i], num);
        }
    }

    @Test
    public void getPersons() throws Exception {
        ArrayList<String> persons = mSystem.getPersons();
        Assert.assertTrue("Not containsAll", allPersons.containsAll(persons));
        Assert.assertEquals(allPersons.size(), persons.size());
    }

    @Test
    public void getUnfinishedTasksOfPerson() throws Exception {
        HashMap<String, ArrayList<String>> correctAnswers = new HashMap<>();
        for (String person : allPersons) {
            correctAnswers.put(person, new ArrayList<String>());
            switch (person) {
                case "Валуев":
                    correctAnswers.get(person).add("Наполнение вкладки Операционист");
                    break;
                case "Иванов":
                    correctAnswers.get(person).add("Маркировать текст для разных абонентов");
                    break;
                case "Сидоров":
                    correctAnswers.get(person).add("Дополнительные кнопки");
                    correctAnswers.get(person).add("Покрыть тестами");
                    break;
            }
//            System.out.println(person + correctAnswers.get(person).toString());
        }
        for (int i = 0; i < allPersons.size(); i++) {
            ArrayList<String> tasks = mSystem.getUnfinishedTasksOfPerson(allPersons.get(i));
            assertTrue("Not containsAll", correctAnswers.get(allPersons.get(i)).containsAll(tasks));
            assertEquals(correctAnswers.get(allPersons.get(i)).size(), tasks.size());
        }
    }

    @Test
    public void getTasksToday() throws Exception {
        HashMap<String, String> correctAnswer = new HashMap<>();
        correctAnswer.put("Наполнение вкладки Операционист", "Валуев");
//        correctAnswer.put("Наполнение вкладки Операционист", " ");
        correctAnswer.put("Дополнительные кнопки", "Сидоров");
        HashMap<String, String> tasksTodayForPerson = mSystem.getTasksToday(time);
        assertTrue("Maps are not equals", correctAnswer.equals(tasksTodayForPerson));
    }

    @Test
    public void getPersonsWithExpiredTasks() throws Exception {
        HashMap<String,ArrayList<String>> correctAnswer = new HashMap<>();
        correctAnswer.put("Сидоров", new ArrayList<>(Arrays.asList("+7(903)1234567","sidorov@mail.ru","Покрыть тестами")));
        HashMap<String,ArrayList<String>> personsWithExpiredTasks = mSystem.getPersonsWithExpiredTasks(time);
        assertTrue("Maps are not equals", correctAnswer.equals(personsWithExpiredTasks));
    }

}
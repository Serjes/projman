package github.serjes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

public class ManagSystemTest {
    ManagSystem mSystem;

    @Before
    public void setUp() throws Exception {
        mSystem = new ManagSystem();
    }

    @Test
    public void getProjectsInWork() throws Exception {
        HashSet<String> correctAnswer = new HashSet<>();
//        Collections.addAll(correctAnswer, "ATM JabaChat".split(" "));
        Collections.addAll(correctAnswer, "ATM", "JabaChat");
//        System.out.println(correctAnswer);
        ArrayList<String> projsInWork = mSystem.getProjectsInWork();
//        correctAnswer.containsAll(projsInWork);
        Assert.assertTrue("containsAll", correctAnswer.containsAll(projsInWork));
        Assert.assertEquals(correctAnswer.size(),projsInWork.size());

    }

    @Test
    public void getProjects() throws Exception {
    }

    @Test
    public void getAmountFinishedTask() throws Exception {
    }

    @Test
    public void getPersons() throws Exception {
    }

    @Test
    public void getUnfinishedTasksOfPerson() throws Exception {
    }

    @Test
    public void getTasksToday() throws Exception {
    }

}
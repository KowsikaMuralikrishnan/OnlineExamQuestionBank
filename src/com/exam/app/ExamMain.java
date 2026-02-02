package com.exam.app;

import com.exam.service.ExamService;
import com.exam.bean.Question;
import com.exam.util.QuestionPoolInsufficientException;
import com.exam.util.QuestionInPublishedPaperException;

public class ExamMain {

    public static void main(String[] args) {
        ExamService examService = new ExamService();
        System.out.println("--- Online Exam Question Bank Console ---");

        // TEST 1: ADD QUESTION (Unique ID, Max 12 chars)
        try {
            Question q = new Question();
            // Using last 9 digits of timestamp + "Q" to stay under 12 chars
            String shortId = "Q" + (System.currentTimeMillis() % 1000000000L);
            q.setQuestionID(shortId);
            q.setSubject("JAVA");
            q.setTopic("Collections");
            q.setDifficulty("EASY");
            q.setMarks(2.0);
            
            System.out.println(examService.addNewQuestion(q) ? "QUESTION ADDED" : "ADD FAILED");
        } catch (Exception e) {
            System.out.println("Add Error: " + e.getMessage());
        }

        // TEST 2: CREATE PAPER (Using JAVA pool)
        try {
            // JAVA has plenty of marks, so this will satisfy the pool requirement
            boolean r = examService.createTestPaper(
                    "JAVA Quick Quiz", "JAVA", 5.0, "EASY=2,MEDIUM=3");
            System.out.println(r ? "TEST PAPER CREATED" : "CREATION FAILED");
        } catch (QuestionPoolInsufficientException e) {
            System.out.println("Pool Error: Insufficient questions.");
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }

        // TEST 3: REMOVE QUESTION (Targeting QST1001 - DRAFT status)
        try {
            boolean r = examService.removeQuestion("QST1001");
            System.out.println(r ? "QUESTION REMOVED" : "REMOVAL FAILED");
        } catch (QuestionInPublishedPaperException e) {
            System.out.println("Removal Blocked: Published paper constraint.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
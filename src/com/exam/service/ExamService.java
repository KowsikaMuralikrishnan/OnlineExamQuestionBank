package com.exam.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exam.bean.Question;
import com.exam.bean.TestPaper;
import com.exam.dao.QuestionDAO;
import com.exam.dao.TestPaperDAO;
import com.exam.util.DBUtil;
import com.exam.util.QuestionInPublishedPaperException;
import com.exam.util.QuestionPoolInsufficientException;
import com.exam.util.ValidateException;

public class ExamService {

    private QuestionDAO questionDAO = new QuestionDAO();
    private TestPaperDAO testPaperDAO = new TestPaperDAO();

    public Question viewQuestionDetails(String questionID) {
        if (questionID == null || questionID.trim().isEmpty()) return null;
        return questionDAO.findQuestion(questionID.trim());
    }

    public boolean addNewQuestion(Question question) {
        if (question == null || question.getSubject() == null || question.getDifficulty() == null) return false;
        
        // Normalize difficulty
        String difficulty = question.getDifficulty().trim().toUpperCase();
        question.setDifficulty(difficulty);

        if (questionDAO.findQuestion(question.getQuestionID()) != null) return false;

        question.setStatus("ACTIVE");
        return questionDAO.insertQuestion(question);
    }

    public boolean removeQuestion(String questionID) throws QuestionInPublishedPaperException {
        if (questionID == null) return false;
        
        // Safety check: is it in a PUBLISHED paper?
        List<TestPaper> papers = testPaperDAO.findPublishedPapersContainingQuestion(questionID.trim());
        if (papers != null && !papers.isEmpty()) {
            throw new QuestionInPublishedPaperException();
        }

        return questionDAO.updateQuestionStatus(questionID.trim(), "INACTIVE");
    }

    public boolean createTestPaper(String paperTitle, String subject, double requiredTotalMarks, String difficultyMixSpec)
            throws ValidateException, QuestionPoolInsufficientException {

        if (paperTitle == null || subject == null || difficultyMixSpec == null) throw new ValidateException();

        Map<String, Integer> difficultyMap = new HashMap<>();
        String[] parts = difficultyMixSpec.split(",");
        int totalFromSpec = 0;

        for (String part : parts) {
            String[] kv = part.split("=");
            int marks = Integer.parseInt(kv[1].trim());
            difficultyMap.put(kv[0].trim().toUpperCase(), marks);
            totalFromSpec += marks;
        }

        if (totalFromSpec != (int)requiredTotalMarks) throw new ValidateException();

        List<String> selectedQuestionIds = new ArrayList<>();

        for (String difficulty : difficultyMap.keySet()) {
            int requiredMarks = difficultyMap.get(difficulty);
            if (requiredMarks <= 0) continue;

            List<Question> questions = questionDAO.findQuestionsBySubjectAndDifficulty(subject.trim(), difficulty);
            List<Question> activeQuestions = new ArrayList<>();
            double totalAvailableMarks = 0;

            for (Question q : questions) {
                // Ignore case and trim status to avoid ORA-style padding issues
                if (q.getStatus() != null && q.getStatus().trim().equalsIgnoreCase("ACTIVE")) {
                    activeQuestions.add(q);
                    totalAvailableMarks += q.getMarks();
                }
            }

            if (totalAvailableMarks < requiredMarks) throw new QuestionPoolInsufficientException();

            double collectedMarks = 0;
            for (Question q : activeQuestions) {
                if (collectedMarks >= requiredMarks) break;
                selectedQuestionIds.add(q.getQuestionID());
                collectedMarks += q.getMarks();
            }
        }

        Connection con = DBUtil.getDBConnection();
        try {
            con.setAutoCommit(false);
            int paperID = testPaperDAO.generatePaperID();
            TestPaper paper = new TestPaper();
            paper.setPaperID(paperID);
            paper.setPaperTitle(paperTitle);
            paper.setSubject(subject);
            paper.setTotalMarks(requiredTotalMarks);
            paper.setQuestionIdList(String.join(",", selectedQuestionIds));
            paper.setStatus("DRAFT");

            testPaperDAO.recordTestPaper(paper);
            con.commit();
            return true;
        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            return false;
        }
    }
}
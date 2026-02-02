package com.exam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.exam.bean.Question;
import com.exam.util.DBUtil;

public class QuestionDAO {
	
	public Question findQuestion(String questionID) {
		Connection connection=DBUtil.getDBConnection();
		String query="SELECT * FROM QUESTION_TBL WHERE Question_ID=?";
		
		try {
			PreparedStatement ps= connection.prepareStatement(query);
			ps.setString(1, questionID);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				Question q= new Question();
				q.setQuestionID(rs.getString(1));
				q.setSubject(rs.getString(2));
				q.setTopic(rs.getString(3));
				q.setDifficulty(rs.getString(4));
				q.setMarks(rs.getInt(5));
				q.setStatus(rs.getString(6));
				return q;
			}
			else
				return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Question> viewAllQuestions(){
		Connection connection = DBUtil.getDBConnection();
		String query="SELECT * FROM QUESTION_TBL";
		List<Question> list = new ArrayList<>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				Question q = new Question();
				q.setQuestionID(rs.getString(1));
				q.setSubject(rs.getString(2));
				q.setTopic(rs.getString(3));
				q.setDifficulty(rs.getString(4));
				q.setMarks(rs.getInt(5));
				q.setStatus(rs.getString(6));
				list.add(q);			
			}
			return list;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean insertQuestion(Question q) {
		Connection connection = DBUtil.getDBConnection();
		String query="INSERT INTO QUESTION_TBL VALUES(?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, q.getQuestionID());
			ps.setString(2,q.getSubject());
			ps.setString(3, q.getTopic());
			ps.setString(4, q.getDifficulty());
			ps.setDouble(5,q.getMarks());
			ps.setString(6,q.getStatus());
			int rows = ps.executeUpdate();
			if(rows>0) {
				return true;
			}
			return false;
			
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Question> findQuestionsBySubjectAndDifficulty(String subject,String difficulty){
	    Connection connection = DBUtil.getDBConnection();
	    String query="SELECT * FROM QUESTION_TBL WHERE Subject=? AND Difficulty=? AND Status='ACTIVE'";
	    List<Question> list = new ArrayList<>();
	    
	    try {
	        PreparedStatement ps= connection.prepareStatement(query);
	        ps.setString(1, subject);
	        ps.setString(2, difficulty);
	        ResultSet rs=ps.executeQuery();
	        
	        while(rs.next()) {  // <-- use while, not if
	            Question q=new Question();
	            q.setQuestionID(rs.getString("Question_ID"));
	            q.setSubject(rs.getString("Subject"));
	            q.setTopic(rs.getString("Topic"));
	            q.setDifficulty(rs.getString("Difficulty"));
	            q.setMarks(rs.getInt("Marks"));
	            q.setStatus(rs.getString("Status"));
	            list.add(q);
	        }
	        
	        return list;
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	
	public boolean updateQuestionStatus(String questionID,String status) {
		Connection connection= DBUtil.getDBConnection();
		String query = "UPDATE QUESTION_TBL SET Status=? WHERE Question_ID=?";
		
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, status);
			ps.setString(2, questionID);
			int rows= ps.executeUpdate();
			if(rows>0) {
				return true;
			}
			return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteQuestion(String questionID) {
		Connection connection = DBUtil.getDBConnection();
		String query="DELETE * FROM QUESTION_TBL WHERE QuestionID=?";
		
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, questionID);
			int rows=ps.executeUpdate();
			if(rows>0) {
				return true;
			}
			return false;
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

package com.exam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.exam.bean.TestPaper;
import com.exam.util.DBUtil;

public class TestPaperDAO {
	
	public int generatePaperID() {
		Connection connection = DBUtil.getDBConnection();
		String query = "SELECT test_paper_seq.NEXTVAL from dual";
		
		try {
			PreparedStatement ps= connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public boolean recordTestPaper(TestPaper paper) {
		Connection connection= DBUtil.getDBConnection();
		String query="INSERT INTO TEST_PAPER_TBL VALUES(?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setInt(1,paper.getPaperID());
			ps.setString(2, paper.getPaperTitle());
			ps.setString(3, paper.getSubject());
			ps.setDouble(4, paper.getTotalMarks());
			ps.setString(5, paper.getQuestionIdList());
			ps.setString(6, "DRAFT");
			int rows= ps.executeUpdate();
			if(rows>0)
				return true;
			return false;
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updatePaperStatus(int paperID,String status)
	{
		Connection connection= DBUtil.getDBConnection();
		String query="UPDATE TEST_PAPER_TBL SET STATUS=? WHERE PAPER_ID=? ";
		
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, status);
			ps.setInt(2,paperID);
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
	
	public TestPaper findPaperByID(int paperID) {
		
		Connection connection = DBUtil.getDBConnection();
		String query="SELECT * FROM TEST_PAPER_TBL WHERE PAPER_ID=?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1,paperID);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				TestPaper t= new TestPaper();
				t.setPaperID(rs.getInt(1));
				t.setPaperTitle(rs.getString(2));
				t.setSubject(rs.getString(3));
				t.setTotalMarks(rs.getDouble(4));
				t.setQuestionIdList(rs.getString(5));
				t.setStatus(rs.getString(6));
				return t;
			}
			return null;
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<TestPaper> findPublishedPapersContainingQuestion(String questionID) {

	    Connection connection = DBUtil.getDBConnection();
	    String query ="SELECT * FROM TEST_PAPER_TBL WHERE STATUS = 'PUBLISHED' AND QUESTION_ID_LIST LIKE ?";
	    List<TestPaper> list = new ArrayList<>();
	    
	    try {
	        PreparedStatement ps = connection.prepareStatement(query);
	        ps.setString(1, "%" + questionID + "%");

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            TestPaper paper = new TestPaper();
	            paper.setPaperID(rs.getInt("PAPER_ID"));
	            paper.setPaperTitle(rs.getString("PAPER_TITLE"));
	            paper.setSubject(rs.getString("SUBJECT"));
	            paper.setTotalMarks(rs.getDouble("TOTAL_MARKS"));
	            paper.setQuestionIdList(rs.getString("QUESTION_ID_LIST"));
	            paper.setStatus(rs.getString("STATUS"));

	            list.add(paper);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return list;

	    
	}

}

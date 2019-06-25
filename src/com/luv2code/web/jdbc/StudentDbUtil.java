package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

public class StudentDbUtil{

	 private DataSource dataSource;
	 public StudentDbUtil(DataSource theDataSource){
		 dataSource = theDataSource;
	 }
	  
	 public ArrayList<Student> getStudents() throws Exception
	 { 
		ArrayList<Student> al = new ArrayList<>();
		
		// get a connection
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try{
				conn = dataSource.getConnection();
				
				// get a statement
				
				String sql = "select * from student order by last_name";
		        stat = conn.createStatement();
				
				// process query
				rs = stat.executeQuery(sql);
		        
				// process resultSet 
				while(rs.next()) {
					
					int id = rs.getInt("id");
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String email = rs.getString("email");
					
					// create a new Student object 
					
					Student obj = new Student(id , firstName,lastName,email);
					
					al.add(obj);
				}
				return al;
		   }
		   finally {
			   // close jdbc connection
			   close(stat,rs,conn);
		   }
		
	 }

	private void close(Statement stat, ResultSet rs, Connection conn) {
		
		try {
			
			if(rs!=null) {
				rs.close();
			}
			
			if(stat!=null) {
				stat.close();
			}
			
			if(conn!=null) {
				conn.close();
			}
			  
		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}

	public void addStudent(Student thestudent) throws Exception {
		
		
		// create sql for insert
		Connection conn=null ;
		PreparedStatement prepStat=null;
		
		try {
			
			// get connection
			conn = dataSource.getConnection();
			
		   // create sql for insert
			
			String sql = "insert into student"
			              +"(first_name,last_name,email)"
					      +"values(?,?,?)";
 			
			prepStat = conn.prepareStatement(sql);
			// get param values
			prepStat.setString(1,thestudent.getFirstName());
			prepStat.setString(2, thestudent.getLastName());
			prepStat.setString(3, thestudent.getEmail());
			 
			//execute query
			
			prepStat.execute();
			
		}finally {
			// close connection
			
			close(prepStat,null,conn);
			
		}
		
		
	}

	public Student getStudent(String theStudentId)throws Exception {
	
		Connection conn =null;
		Student theStudent=null;
		PreparedStatement prestat = null;
		ResultSet rs=null;
		int studentId;
		
		try {
			// convert student id to int 
			studentId = Integer.parseInt(theStudentId);
			
			// get connection to database
			conn = dataSource.getConnection();
			
	
			
			// create sql to get selected student
			String sql = "select * from student where id=?";
			
			
			// create prepared statement
			prestat = conn.prepareStatement(sql);
			
			
			
			//set params
			prestat.setInt(1,studentId);
			
			
			//execute statements
			rs = prestat.executeQuery();
			
			
			// retrieve data from resultset 
			if(rs.next()) {
				String firstName = rs.getString("first_name");
				String secondName = rs.getString("last_Name");
				String email = rs.getString("email");
				
				theStudent = new Student(studentId ,firstName,secondName,email);
				
			}
			else {
			  throw new Exception("could not find student id :"+ studentId);	
			}
			
			
			return theStudent;
			
		}finally {
			close(prestat,rs,conn);
		}
	
	}

	public void updateStudent(Student theStudent) throws SQLException {
	   
		Connection conn=null;
		PreparedStatement prepStat= null;
		
	    try {
			 // get dbconnection
			conn = dataSource.getConnection();
			
			
			//create sql statement
			
		    String sql = " update student "
		    		     +"set first_name=?, last_name=?, email=? "
		    		     +" where id=?";
			
			// prepare Statment
		    prepStat = conn.prepareStatement(sql);
			
		    
		    // set params
		    prepStat.setString(1, theStudent.getFirstName());
		    prepStat.setString(2, theStudent.getLastName());
		    prepStat.setString(3, theStudent.getEmail());
		    prepStat.setInt(4, theStudent.getId());
		    
		    
			// execute query
		    prepStat.execute();
	    }finally {
	    	close(prepStat,null,conn);
	    }
	}

	public void deleteStudents(String theStudentid) throws Exception {
		
	    // get connection
		Connection conn=null;
		PreparedStatement prepstat=null;
		
		try {
			// convert student id to int 
		       int studentId = Integer.parseInt(theStudentid);
		
		    // get connection to database 
			   conn = dataSource.getConnection();
			
			//  create sql to database
			   
			   String sql = "delete from student where id=?";
			   
			// prepare statement
			   
			   prepstat = conn.prepareStatement(sql);
			
			// set params
			   
			    prepstat.setInt(1, studentId);
			   
			// execute query
			   
			    prepstat.execute();
			    
			
		}finally{
			
			close(prepstat,null,conn);
		}
		
		
	}
}

package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDbUtil;
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
	
		
		// create our student db util ... and pass in the conn pool/datasource
		try {
			 studentDbUtil = new StudentDbUtil(dataSource);
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    // list the student in MVC fashion
		try {
			
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing then default to listing students
			
			if(theCommand==null) {
				theCommand = "LIST";
			}
			
			// route to appropriate method 
			
			switch(theCommand) {
			
			  case "LIST":
				           listStudents(request,response);
				           break;
			
			
			  case "ADD" :
				           addStudents(request,response);
				           break;
				           
			
			  case "LOAD" : 
				             loadStudents(request,response);
				             break;
				           
				             
			  case "UPDATE":
				            updateStudents(request,response); 
				default:
					    listStudents(request,response);
				
					    
			  case "DELETE":
				            deleteStudents(request,response);
				            break;
			}
		
			
			listStudents(request , response);
		} catch (Exception e) {
			
			throw new ServletException(e);
		}
		
	}

	private void deleteStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// read student id from data
		   String theStudentid = request.getParameter("studentId");		  
		
		//delete from database
		   studentDbUtil.deleteStudents(theStudentid);
		   
		
		// send back to jsp
		   listStudents(request,response);
		  
		
	}

	private void updateStudents(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
	   // read student info from data
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
	   // create a new Student object
		
		Student theStudent = new Student(id,firstName,lastName,email);
		
		
	   // perform update on database
		studentDbUtil.updateStudent(theStudent);
		
	   // send them back to list students page
		
		listStudents(request,response);
	
	}

	private void loadStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// read student id from data
		String theStudentId = request.getParameter("studentId");
		
		// get student form database
		Student thestudent = studentDbUtil.getStudent(theStudentId);
		
		// place student in the request attribute
		request.setAttribute("THE_STUDENT", thestudent);
		
		// send to list_student.jsp
		
		RequestDispatcher requestdispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		requestdispatcher.forward(request, response);
	
	}

	private void addStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		  String firstName = request.getParameter("firstName");
		  String lastName = request.getParameter("lastName");
		  String email = request.getParameter("email");
		
		  // create a new object
		  Student thestudent = new Student(firstName,lastName,email);
		  
		  // add student data to database

		  studentDbUtil.addStudent(thestudent);		   
		  
		  // send back to main page
		  
		  listStudents(request,response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// get students from db util
		ArrayList<Student> students = studentDbUtil.getStudents();
		
		// add students to the request 
		request.setAttribute("STUDENTS_LIST",students);
		
		// send to JSP view page 
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
        dispatcher.forward(request, response);
		
	}

}

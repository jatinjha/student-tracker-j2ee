<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!Doctype html>
<html>
<head>
      <title>student tracker app</title>
      <link type = "text/css" rel = "stylesheet" href="css/style.css">
</head>

<body>
      <div id = "wrapper">
      <div id = "header">
                        <h2>FOO BAR</h2>
      </div>
      </div>
      <div id = "container">
      <div id = "content">
      					 <!-- add new button -->
      					 <input type= "button" value = "Add Student"
      					  onclick="window.location.href='add-student-form.jsp';return false;"
      					  class="add-student-button"
      					 />
      				
                         <table>
                               <tr>
                               <th>First Name</th>
                               <th>Last Name</th>
                               <th>Email</th>
                               <th>Action</th>
     
                               </tr>
                               
                               <c:forEach var = "tempStudent" items = "${STUDENTS_LIST}">
                                    <!-- create a unique student for each link -->
                                     <c:url var = "tempLink" value="StudentControllerServlet" >
                                         <c:param name = "command" value="LOAD" />
                                         <c:param name = "studentId" value="${tempStudent.id}" />
                                     </c:url>
                                     <!-- create a link for delet -->
                                     <c:url var = "deleteLink" value="StudentControllerServlet" >
                                         <c:param name = "command" value="DELETE" />
                                         <c:param name = "studentId" value="${tempStudent.id}" />
                                     </c:url>
                                    
                                	 <tr>
                                	 <td>${tempStudent.firstName}</td>
                                	 <td>${tempStudent.lastName}</td>
                                	 <td>${tempStudent.email}</td>
                                     <td><a href="${tempLink}">Update</a>
                                     |
                                     <a href="${deleteLink}"
                                     onclick="if(!(confirm('Are you sure you want to delete this Student ? ')))return false">
                                     Delete</a>
                                     </td>
                                     </tr>
                               </c:forEach>      
                         </table> 
                 
</body>
</html> 
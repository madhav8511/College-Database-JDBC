//STEP 1. Import required packages
import java.sql.*;
import java.util.*;

public class JdbcDemo {

   // Set JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost:3306/college?useSSL=false";

   // Database credentials
   static final String USER = "root";// add your user
   static final String PASSWORD = "password";// add password

   public static void main(String[] args) {
      Connection conn = null;
      Statement stmt = null;
      Scanner sc = new Scanner(System.in);

      // STEP 2. Connecting to the Database
      try {
         // STEP 2a: Register JDBC driver
         Class.forName(JDBC_DRIVER);
         // STEP 2b: Open a connection
         conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         // STEP 2c: Execute a query
         stmt = conn.createStatement();
         conn.setAutoCommit(false);
         while (true) 
         {
            System.out.println();
            System.out.println("---------- MENU ----------");
            System.out.println("0.  For exiting Program");
            System.out.println("1.  Show All Departments");
            System.out.println("2.  Show All Courses offered");
            System.out.println("3.  Show All Teachers in college");
            System.out.println("4.  Show All Students in college");
            System.out.println("5.  No. of Student in particular course");
            System.out.println("6.  Add a Student");
            System.out.println("7.  Enroll a Student in a Course");
            System.out.println("8.  No. of Course offered in particular Department");
            System.out.println("9.  Topper of College");
            System.out.println("10. List of Student enrolled in Particular Course");
            System.out.println("11. Add a Department");
            System.out.println("12. Add a Course");
            System.out.println("13. Add a teacher");
            System.out.println("14. List of Teacher in particular department");
            System.out.println("15. List of course offered in particular department");
            System.out.println("16. Details of particular student");
            System.out.println("17. Update cgpa of student");
            System.out.println("18. De-enroll a student from course");
            System.out.println("--------------------------");

            int value = sc.nextInt();

            if(value == 0) break;

            else if(value == 1)
            {
               String query = "SELECT department_id, department_name from department";
               ResultSet rs = stmt.executeQuery(query);
               System.out.printf("| %-15s | %-25s |\n", "department_id", "department_name");
               while (rs.next()) {
                  // Retrieve by column name
                  Integer id = rs.getInt("department_id");
                  String name = rs.getString("department_name");

                  // Display values
                  System.out.printf("| %-15d | %-25s |\n", id, name);
               }
               rs.close();
            }

            else if(value == 2)
            {
               String query = "SELECT course_id, course_name, department_id from course";
               ResultSet rs = stmt.executeQuery(query);
               System.out.printf("| %-10s | %-20s | %-10s |\n", "course_id", "course_name", "department_id");
               while (rs.next()) {
                  // Retrieve by column name
                  Integer id = rs.getInt("course_id");
                  String name = rs.getString("course_name");
                  Integer d_id = rs.getInt("department_id");

                  // Display values
                  System.out.printf("| %-10d | %-20s | %-13d |\n", id, name, d_id);
               }
               rs.close();
            }

            else if(value == 3)
            {
               String query = "SELECT teacher_id, teacher_name, course_id from teacher";
               ResultSet rs = stmt.executeQuery(query);
               System.out.printf("| %-10s | %-20s | %-10s |\n", "teacher_id", "teacher_name", "course_id");
               while (rs.next()) {
                  // Retrieve by column name
                  Integer id = rs.getInt("teacher_id");
                  String name = rs.getString("teacher_name");
                  Integer c_id = rs.getInt("course_id");

                  // Display values
                  System.out.printf("| %-10d | %-20s | %-10d |\n", id, name, c_id);
               }
               rs.close();
            }

            else if(value == 4)
            {
               String query = "SELECT student_id, student_name, cgpa from student";
               ResultSet rs = stmt.executeQuery(query);
               System.out.printf("| %-10s | %-20s | %-5s |\n", "student_id", "student_name", "cgpa");
               while (rs.next()) {
                  // Retrieve by column name
                  Integer id = rs.getInt("student_id");
                  String name = rs.getString("student_name");
                  Float cgpa = rs.getFloat("cgpa");

                  // Display values
                  System.out.printf("| %-10d | %-20s | %-5.2f |\n", id, name, cgpa);
               }
               rs.close();
            }

            else if(value == 5)
            {
               System.out.print("Enter course Id : ");
               int course_id = sc.nextInt();
               String query = "SELECT c.course_id, c.course_name, ct.total_enrollment from course_taken ct INNER JOIN course c ON ct.course_id = c.course_id where ct.course_id = "+course_id; 

               // Executing the SQL query and retrieving the result set
               ResultSet rs = stmt.executeQuery(query);
               System.out.printf("| %-10s | %-20s | %-15s |\n", "Course ID", "Course Name", "No. of Students");
               // Iterating over the result set and printing the course_id and No. of Students
               while (rs.next()) {
                  int courseIdResult = rs.getInt("c.course_id");
                  String name = rs.getString("c.course_name");
                  int numOfStudents = rs.getInt("ct.total_enrollment");

                  System.out.printf("| %-10d | %-20s | %-15d |\n", courseIdResult, name, numOfStudents);
               }
            }

            else if(value == 6)
            {
               System.out.print("Enter Student Id : ");
               int id = sc.nextInt();
               String query = "SELECT * from student where student_id = "+id;
               ResultSet rs = stmt.executeQuery(query);
               if(rs.next())
               {
                  System.out.println("Sorry ! A student with Same Id exist");
               }
               else
               {
                  System.out.print("Enter Name : ");
                  String name = sc.next();
                  System.out.print("Enter CGPA : ");
                  Float cgpa = sc.nextFloat();
                  query = "INSERT into student values ( "+id+", '"+name+"' , "+cgpa+" )";
                  if(stmt.executeUpdate(query) >= 0) 
                  {
                     System.out.println("Student Added Successfully");
                     conn.commit();
                  }
                  else 
                  {
                     System.out.println("!!! Student Not added !!!");
                     conn.rollback();
                  }
               }
            }

            else if(value == 7)
            {
               System.out.print("Enter Student Id : ");
               int s_id = sc.nextInt();
               System.out.print("Enter Course ID : ");
               int c_id = sc.nextInt();
               String query = "SELECT * from student where student_id = "+s_id;
               ResultSet rs = stmt.executeQuery(query);
               if(rs.next())
               {
                  query = "SELECT * from course where course_id = "+c_id;
                  rs = stmt.executeQuery(query);
                  if(rs.next())
                  {
                     query = "SELECT * FROM student_course where student_id = "+s_id+" and course_id = "+c_id;
                     rs = stmt.executeQuery(query);
                     if(rs.next())
                     {
                        System.out.println("Student already Enrolled in that Course");
                     }
                     else
                     {
                        query = "INSERT into student_course values ( "+s_id+", "+c_id+" )";
                        if(stmt.executeUpdate(query)>=0)
                        {
                           query = "Select total_enrollment from course_taken where course_id = "+c_id;
                           rs = stmt.executeQuery(query);
                           Integer count = 0;
                           while(rs.next())
                           {
                              count = rs.getInt("total_enrollment");
                              count = count+1;
                           }

                           query = "update course_taken SET total_enrollment = "+count+" where course_id = "+c_id;
                           if(stmt.executeUpdate(query)>=0)
                           {
                              System.out.println("Student Enrolled Successfully");
                              conn.commit();
                           }
                           else conn.rollback();
                        }
                        else conn.rollback();
                     }
                  }
                  else System.out.println("Sorry that Course doesn't exist");
               }
               else System.out.println("Sorry that Student doesn't exist");
            }

            else if(value == 8)
            {
               System.out.print("Enter Department Id : ");
               int id = sc.nextInt();
               String query = "SELECT * from department where department_id = "+id;
               ResultSet rs = stmt.executeQuery(query);
               if(rs.next())
               {
                  query = "SELECT d.department_id, d.department_name, COUNT(c.course_id) AS "+"'No. of Course Offered'"+" FROM course c INNER JOIN department d ON c.department_id = d.department_id WHERE d.department_id = "+id+" GROUP BY d.department_id";
                  //System.out.println(query);
                  rs = stmt.executeQuery(query);
                  System.out.printf("| %-15s | %-30s | %-20s |\n", "Department ID", "Department Name", "No. of Courses Offered");
                  while(rs.next())
                  {
                     Integer d_id = rs.getInt("d.department_id");
                     String name = rs.getString("d.department_name");
                     Integer courses = rs.getInt("No. of Course Offered");

                     System.out.printf("| %-15d | %-30s | %-22d |\n", d_id, name, courses);
                  }
               }
               else System.out.println("Sorry that Department doesn't exist");
               rs.close();
            }

            else if(value == 9)
            {
               String query = "Select student_id,student_name from student where cgpa = (Select MAX(cgpa) from student)";
               ResultSet rs = stmt.executeQuery(query);
               while(rs.next())
               {
                  Integer id = rs.getInt("student_id");
                  String name = rs.getString("student_name");

                  System.out.println("Topper of college : ID: "+id+", Name: "+name);
               }
               rs.close();
            }

            else if(value == 10)
            {
               System.out.print("Enter Course ID: ");
               int id = sc.nextInt();
               String query = "Select * from course where course_id = "+id;
               ResultSet rs = stmt.executeQuery(query);
               if(rs.next()){
                  query = "select s.student_id,s.student_name,c.course_id,c.course_name from student_course sc INNER JOIN student s ON sc.student_id = s.student_id INNER JOIN course c ON sc.course_id = c.course_id where c.course_id = "+id;
                  rs = stmt.executeQuery(query);
                  System.out.printf("| %-12s | %-15s | %-10s | %-15s |\n", "Student ID", "Student Name", "Course ID", "Course Name");
                  while(rs.next())
                  {
                     Integer s_id = rs.getInt("s.student_id");
                     String s_name = rs.getString("s.student_name");
                     Integer c_id = rs.getInt("c.course_id");
                     String c_name = rs.getString("c.course_name");

                     System.out.printf("| %-12d | %-15s | %-10d | %-15s |\n", s_id, s_name, c_id, c_name);
                  }
               }
               else System.out.println("Sorry This course is not offered yet");
               rs.close();
            }

            else if(value == 11)
            {
               System.out.print("Enter Department Id: ");
               int id = sc.nextInt();
               System.out.print("Enter Department Name: ");
               String name = sc.next();

               String query = "SELECT * from department where department_id = "+id;
               ResultSet rs = stmt.executeQuery(query);
               if(rs.next()) System.out.println("Sorry Department with this Id already exist !");
               else
               {
                  query = "insert into department values ("+id+", '"+name+"' )";
                  if(stmt.executeUpdate(query) >= 0) 
                  {
                     System.out.println("Department added Successfully");
                     conn.commit();
                  }
                  else 
                  {
                     System.out.println("!!! Department Not Added !!!");
                     conn.rollback();
                  }
               }
               rs.close();
            }

            else if(value == 12)
            {
               System.out.print("Enter Course Id: ");
               int c_id = sc.nextInt();
               System.out.print("Enter Course Name: ");
               String name = sc.next();
               System.out.print("Enter Department Id of this course: ");
               int d_id = sc.nextInt();

               String query = "SELECT * from course where course_id = "+c_id;
               ResultSet rs = stmt.executeQuery(query);
               if(rs.next()) System.out.println("Sorry a course with this id alredy exist !");
               else
               {
                  query = "SELECT * from department where department_id = "+d_id;
                  rs = stmt.executeQuery(query);
                  if(rs.next())
                  {
                     query = "insert into course values ("+c_id+", '"+name+"' ,"+d_id+" )";
                     if(stmt.executeUpdate(query) >= 0) 
                     {
                        int count = 0;
                        query = "insert into course_taken values ("+c_id+", "+count+" )";
                        if(stmt.executeUpdate(query)>=0)
                        {
                           System.out.println("Course added successfully");
                           conn.commit();
                        }
                        else conn.rollback();
                     }
                     else 
                     {
                        System.out.println("!!! Course Not Added !!!");
                        conn.rollback();
                     }
                  }
                  else System.out.println("Department doesn't exist !");
               }
               rs.close();
            }

            else if(value == 13)
            {
               System.out.print("Enter Teacher Id: ");
               int id = sc.nextInt();
               System.out.print("Enter Teacher Name: ");
               String name = sc.next();
               System.out.print("Enter Course Id for teacher: ");
               int c_id = sc.nextInt();

               String query = "SELECT * FROM teacher where teacher_id = "+id;
               ResultSet rs = stmt.executeQuery(query);
               if(rs.next()) System.out.println("Sorry Teacher with same id already exist !!");
               else
               {
                  query = "SELECT * from course where course_id = "+c_id;
                  rs = stmt.executeQuery(query);
                  if(rs.next())
                  {
                     query = "insert into teacher values ("+id+", '"+name+"', "+c_id+" )";
                     if(stmt.executeUpdate(query) >= 0) 
                     {
                        System.out.println("Teacher added Successfully");
                        conn.commit();
                     }
                     else 
                     {
                        System.out.println("!!! Teacher not addded !!!");
                        conn.rollback();
                     }
                  }
                  else System.out.println("Sorry this course this not yet offered !!");
               }
               rs.close();
            }

            else if(value == 14)
            {
               System.out.print("Enter Department Id: ");
               int id = sc.nextInt();

               String query = "select * from department where department_id = "+id;
               ResultSet rs = stmt.executeQuery(query);
               if(rs.next())
               {
                  query = "select t.teacher_id,t.teacher_name,c.course_name,d.department_name from teacher t INNER JOIN course c ON t.course_id = c.course_id INNER JOIN department d ON c.department_id = d.department_id where d.department_id = "+id;
                  rs = stmt.executeQuery(query);
                  System.out.printf("| %-15s | %-20s | %-20s | %-20s |\n", "Teacher ID", "Teacher Name", "Course Name" ,"Department Name");
                  while(rs.next())
                  {
                     Integer t_id = rs.getInt("t.teacher_id");
                     String name = rs.getString("t.teacher_name");
                     String c_name  =rs.getString("c.course_name");
                     String d_name = rs.getString("d.department_name");

                     System.out.printf("| %-15d | %-20s | %-20s | %-20s |\n", t_id, name, c_name,d_name);
                  }
                  rs.close();
               }
               else System.out.println("Sorry that departement doesn't exist");
               rs.close();
            }

            else if(value == 15)
            {
               System.out.print("Enter department id: ");
               int id = sc.nextInt();

               String query = "select * from department where department_id = "+id;
               ResultSet rs = stmt.executeQuery(query);
               if(rs.next())
               {
                  query = "select c.course_id,c.course_name,d.department_name from course c INNER JOIN department d ON c.department_id = d.department_id where d.department_id = "+id;
                  rs = stmt.executeQuery(query);
                  System.out.printf("| %-15s | %-30s | %-20s |\n", "Course ID", "Course Name", "Department Name");
                  while(rs.next())
                  {
                     Integer c_id = rs.getInt("c.course_id");
                     String c_name = rs.getString("c.course_name");
                     String d_name = rs.getString("d.department_name");

                     System.out.printf("| %-15d | %-30s | %-20s |\n", c_id, c_name, d_name);
                  }
               }
               else System.out.println("Sorry that department doesn't exist !!");
               rs.close();
            }

            else if(value == 16)
            {
               System.out.print("Enter Student Id: ");
               int id = sc.nextInt();

               String query = "select * from student where student_id = "+id;
               ResultSet rs = stmt.executeQuery(query);
               if(rs.next())
               {
                  query = "select s.student_id,s.student_name,s.cgpa,c.course_name,d.department_name from student s INNER JOIN student_course sc ON s.student_id = sc.student_id INNER JOIN course c ON c.course_id = sc.course_id INNER JOIN department d ON c.department_id = d.department_id where s.student_id = "+id;
                  rs = stmt.executeQuery(query);
                  System.out.printf("| %-12s | %-15s | %-10s | %-12s | %-15s |\n", "Student ID", "Student Name","CGPA","Course Name", "Department Name");
                  while(rs.next())
                  {
                     Integer s_id = rs.getInt("s.student_id");
                     String s_name = rs.getString("s.student_name");
                     Float cgpa = rs.getFloat("s.cgpa");
                     String c_name = rs.getString("c.course_name");
                     String d_name = rs.getString("d.department_name");

                     System.out.printf("| %-12d | %-15s | %-10.2f | %-12s | %-15s |\n", s_id, s_name, cgpa ,c_name, d_name);
                  }
               }
               else System.out.println("Sorry that student doesn't exist !!");
               rs.close(); 
            }

            else if(value == 17)
            {
               System.out.print("Enter Student Id: ");
               int id = sc.nextInt();

               String query = "SELECT cgpa from student where student_id = "+id;
               ResultSet rs = stmt.executeQuery(query);
               if(rs.next())
               {
                  System.out.println("Current cgpa : "+rs.getFloat("cgpa"));
                  System.out.print("Enter new Cgpa : ");
                  Float cgpa = sc.nextFloat();
                  query = " update student SET cgpa = "+ cgpa + " where student_id = "+id;
                  if(stmt.executeUpdate(query)>=0) 
                  {
                     System.out.println("CGPA updated successfully");
                     conn.commit();
                  }
                  else 
                  {
                     System.out.println("Not updated!!!");
                     conn.rollback();
                  }
               }
               else System.out.println("Sorry that student doesn't exist !!!");
            }

            else if(value == 18)
            {
               System.out.print("Enter Student id: ");
               int st_id = sc.nextInt();
               System.out.print("Enter Course id: ");
               int c_id = sc.nextInt();
               String query = "SELECT * from student_course where student_id = "+st_id+" and course_id = "+c_id;
               ResultSet rs = stmt.executeQuery(query);
               if(rs.next())
               {
                  query = "DELETE  from student_course where student_id = "+st_id+" AND course_id = "+c_id;
                  
                  if(stmt.executeUpdate(query)>=0)
                  {
                     query = "Select total_enrollment from course_taken where course_id = "+c_id;
                     rs = stmt.executeQuery(query);
                     Integer count = 0;
                     while(rs.next())
                     {
                        count = rs.getInt("total_enrollment");
                        count = count-1;
                     }

                     query = "update course_taken SET total_enrollment = "+count+" where course_id = "+c_id;
                     if(stmt.executeUpdate(query)>=0)
                     {
                        System.out.println("Student De-enrolled Successfully");
                        conn.commit();
                     }
                     else conn.rollback();
                  }
                  else conn.rollback();
               }
               else System.out.println("Already Not enrolled");
            }
         }
         // STEP 5: Clean-up environment
         stmt.close();  
         conn.close();
      } catch (SQLException se) { // Handle errors for JDBC
         se.printStackTrace();
         System.out.println("Rolling back data here....");
         try{
            if(conn!=null)
               conn.rollback();
         }catch(SQLException se2){
            System.out.println("Rollback failed....");
               se2.printStackTrace();
         }
      } catch (Exception e) { // Handle errors for Class.forName
         e.printStackTrace();
      } finally { // finally block used to close resources regardless of whether an exception was
                  // thrown or not
         try {
            if (stmt != null)
               stmt.close();
         } catch (SQLException se2) {
         }
         try {
            if (conn != null)
               conn.close();
         } catch (SQLException se) {
            se.printStackTrace();
         } // end finally try
      } // end try
      System.out.println("...Thank-You...");
   } // end main
} // end class

// Note : By default autocommit is on. you can set to false using
// con.setAutoCommit(false)

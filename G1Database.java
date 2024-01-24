import java.sql.*;
import java.io.*;

public class G1Database {	

	public static void launch_db()
	{
		try {
			System.out.print("userid: ");
			String uid;
			uid = Utility.getString();

			System.out.print("password: ");
			String pword;
			pword = Utility.getString();

			String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
			// Load the Oracle JDBC driver
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// Connect to the database
			Connection conn = DriverManager.getConnection(url, uid, pword);
			uid = null;
			pword = null;

			Statement stmt = conn.createStatement();			
			PreparedStatement update_grade = conn.prepareStatement("UPDATE ENROLLED_IN SET LetterGrade = ? WHERE StudSSN = ? AND SectionNum = ?");
			PreparedStatement update_gpa = conn.prepareStatement("UPDATE ENROLLED_IN SET GradePoint = ? WHERE StudSSN = ? AND SectionNum = ?");
			
			int exit = 1;

			while (exit != 0) {
				System.out.println("Choose an option 1-6 to perform the corresponding SQL statement:\n"
						+ "1) Add a student, department, course, instructor, or course section.\n"
						+ "2) Add students to a given course/section.\n" 
						+ "3) Generate student's grade report.\n"
						+ "4) Find the courses offered by a department.\n"
						+ "5) List all the course sections an instructor has taught.\n"
						+ "6) Add a grade to a given student for a given course/section.\n");

				int choice = Utility.getInt();
				DatabaseOptions opt = new DatabaseOptions(conn);
				switch (choice) {
					case 1:
						opt.add_database_item();
						break;
						
					case 2:
						System.out.println("You chose to add a student to a course section.");
						opt.add_to_course();
						break;
						
					case 3:
						System.out.println("You chose to generate a grade report.");
						opt.calc_grade_report(stmt, conn);
						break;
						
					case 4:
						System.out.println("You chose to find a department's courses.");
						opt.find_course(stmt);
						break;
						
					case 5:
						System.out.println("You chose to list an Instructor's sections.");
						opt.list_courses(stmt);
						break;
						
					case 6:
						System.out.println("You chose to add a grade for a student's class section.");
						opt.grade_student(update_grade, update_gpa);				
						break;
					
					default:
						System.out.println("This is an invalid option!\n");
				}
				
				System.out.println("Press 0 to exit. Otherwise, press any other number to perform another SQL statement.\n");
				exit = Utility.getInt();
			}

			System.out.println("Have a nice day!");
		} catch (SQLException e) {
			System.out.println("[!] Error with connection.");
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		launch_db();
	}
}

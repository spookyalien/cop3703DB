import java.sql.*;
import java.io.*;
import java.util.ArrayList;

public class G1Database {
	
	public static void find_course(Statement stmt)
	{
		String DName = "";
		System.out.println("[+] Enter a department name or code to show courses offered: ");
		String name = getString();
		String q = "SELECT * " +
	               "FROM DEPARTMENT " +
	               "WHERE DName= \'" + name + "\'" + " OR DCode= \'" + name + "\'";
		ResultSet rset;
		try {
			rset = stmt.executeQuery(q);
			if (rset.next()) {
				String DCode = rset.getString("DCode");
				DName = rset.getString("DName");
				q = "SELECT * " +
			        "FROM COURSE " +
			        "WHERE DCode= " + DCode;
			}
			rset = stmt.executeQuery(q);
			System.out.println("Courses offered by " + DName + ": ");
			while (rset.next()) {
				String courseName = rset.getString("courseName");
				System.out.println("[+] " + courseName);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		
	}
	
	public static void calc_grade_report(Statement stmt, Connection conn)
	{
		String SSN, sectNum, courseNum;
		SSN = courseNum = sectNum= "";
		Double finalGPA = 0.0;
		System.out.print("[+] Enter your N. Number: ");
		String Nno = getString();
		ArrayList<String> Grades = new ArrayList<String>();
	     
	     try {
	    	String q = "SELECT * " +
		               "FROM STUDENT " +
		               "WHERE Nnum= " + Nno ;
			ResultSet rset = stmt.executeQuery(q);
			System.out.println("[+] Grade Report for " + Nno);
			if (rset.next ()) {
			       SSN = rset.getString("SSN");
			       String fName = rset.getString("FName");
			       String lName = rset.getString("LName");
			       String midIn = rset.getString("MidIn");
			       String Sex = rset.getString("Sex");
			       String bDate = rset.getString("BDate");
			       String degProg = rset.getString("DegProg");
			       String Seniority = rset.getString("Seniority");
			       String PermPhone = rset.getString("PermPhone");
			       String PermStreetAddress = rset.getString("PermStreetAddress");
			       String PermCity = rset.getString("PermCity");
			       String PermState = rset.getString("PermState");
			       String PermZip = rset.getString("PermZip");
			       String CurrPhone = rset.getString("CurrPhone");
			       String CurrAddress = rset.getString("CurrAddress");

					
					System.out.println("Student");
					System.out.println("---------------");
					System.out.println("Name: " + fName + " " + midIn + " " + lName);
					System.out.println("Sex: " + Sex);
					System.out.println("Date of Birth: " + bDate);
					System.out.println("Degree: " + degProg);
					System.out.println("Seniority: " + Seniority);
					System.out.println("Address (Permanent): " + PermStreetAddress + " " + PermCity + " " + PermState + ", " + PermZip);
					System.out.println("Address (Current): " + CurrAddress);
					System.out.println("Phone No. (Permanent): " + PermPhone);
					System.out.println("Phone No. (Current): " + CurrPhone);
					System.out.println();
			}
			
			q = "SELECT * " +
		        "FROM ENROLLED_IN " +
		        "WHERE StudSSN= " + SSN;
			rset = stmt.executeQuery(q);
			while (rset.next()) {
				sectNum = rset.getString("SectionNum");
				String GPA = rset.getString("GradePoint");
				String Grade = rset.getString("LetterGrade");
				if (Grade == null)
					Grade = "N/A";
				else if (GPA == null)
					GPA = "N/A";
				else 
					Grades.add(GPA);
				String q1 = "SELECT * " +
				    "FROM SECTION " +
				    "WHERE SectionNum= " + sectNum;
				
				ResultSet rsetSect = conn.createStatement().executeQuery(q1);
				if (rsetSect.next()) {
					String year = rsetSect.getString("Year");
					String sem = rsetSect.getString("Sem");
					String issn = rsetSect.getString("ISSN");
					courseNum = rsetSect.getString("CourseNum");
					System.out.println("Section");
					System.out.println("---------------");
					System.out.println("Year: " + year);
					System.out.println("Semester: " + sem);
					System.out.println("issn: " + issn);
					System.out.println("courseNum: " + courseNum);
					System.out.println();
				}
				
				String q3 = "SELECT * " +
					"FROM COURSE " +
					"WHERE CourseNum = " + courseNum;
				ResultSet rsetCourse = conn.createStatement().executeQuery(q3);
				if (rsetCourse.next()) {
					String courseName = rsetCourse.getString("CourseName");
					String desc = rsetCourse.getString("Description");
					String semHours = rsetCourse.getString("SemHours");
					String lvl = rsetCourse.getString("Lvl");
					System.out.println("Course");
					System.out.println("---------------");
					System.out.println("Course Name: " + courseName);
					System.out.println("Description: " + desc);
					System.out.println("Semester Hours: " + semHours);
					System.out.println("Level: " + lvl);
					System.out.println("Grade: " + Grade);
					System.out.println("Grade Point: " + GPA);
					System.out.println();
					
				}
			}
			for (int i = 0; i < Grades.size(); i++) {
				Double gradePoint = Double.parseDouble(Grades.get(i));
				finalGPA += gradePoint;
			}
			finalGPA /= Grades.size();
			System.out.println("\nOverall GPA: " + finalGPA);
			if (finalGPA > 3) {
				if (finalGPA == 4)
					System.out.println("Overall Grade: A");
				else if (finalGPA >= 3.7)
					System.out.println("Overall Grade: A-");
				else if (finalGPA >= 3.3)
					System.out.println("Overall Grade: B+");
				else
					System.out.println("Overall Grade: B");
			}
			else if (finalGPA > 2) {
				if (finalGPA >= 2.7)
					System.out.println("Overall Grade: B-");
				else if (finalGPA >= 2.3)
					System.out.println("Overall Grade: C+");
				else
					System.out.println("Overall Grade: C");
			}
			else if (finalGPA > 1) {
				System.out.println("Overall Grade: D");
			}
			else {
				System.out.println("Overall Grade: F");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws SQLException {

		System.out.print("userid: ");
		String uid;
		uid = getString();

		System.out.print("password: ");
		String pword;
		pword = getString();

		String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		// Load the Oracle JDBC driver
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

		// Connect to the database
		Connection conn = DriverManager.getConnection(url, uid, pword);

		Statement stmt = conn.createStatement();
		PreparedStatement pstmt11 = conn.prepareStatement ("INSERT INTO STUDENT(SSN, FName, MidIn, LName, Sex, Nnum, BDate, DegProg,"
				+ " Seniority, PermPhone, PermStreetAddress, PermCity, PermState, PermZip, CurrPhone, CurrAddress) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		PreparedStatement pstmt111 = conn.prepareStatement ("INSERT INTO MAJOR_IN(StudSSN, DCode) "
				+ "VALUES (?, ?)");
		
		PreparedStatement pstmt112 = conn.prepareStatement ("INSERT INTO MINOR_IN(StudSSN, DCode) "
				+ "VALUES (?, ?)");
		
		PreparedStatement pstmt12 = conn.prepareStatement ("INSERT INTO DEPARTMENT(DCode, DName, College, OfficeNum, OfficePhone) "
				+ "VALUES (?, ?, ?, ?, ?)");
		
		PreparedStatement pstmt13 = conn.prepareStatement ("INSERT INTO COURSE(CourseNum, CourseName, Description, SemHours, Lvl, DCode) "
				+ "VALUES (?, ?, ?, ?, ?, ?)");
		
		PreparedStatement pstmt14 = conn.prepareStatement ("INSERT INTO INSTRUCTOR(SSN, Nnum, City, State, Zip, OfficeNum, Age, Fname,"
				+ " MidIn, LName, DCode) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		PreparedStatement pstmt15 = conn.prepareStatement ("INSERT INTO SECTION(Year, Sem, SectionNum, ISSN, CourseNum) "
				+ "VALUES (?, ?, ?, ?, ?)");
		
		PreparedStatement pstmt2 = conn.prepareStatement ("INSERT INTO ENROLLED_IN(StudSSN, SectionNum, LetterGrade, GradePoint) "
				+ "VALUES (?, ?, NULL, NULL)");
		

		int exit = 1;

		while (exit != 0) {

			System.out.println("Choose an option 1-6 to perform the corresponding SQL statement:\n"
					+ "1) Add a student, department, course, instructor, or course section.\n"
					+ "2) Add students to a given course/section.\n" 
					+ "3) Generate student's grade report.\n"
					+ "4) Find the courses offered by a department.\n"
					+ "5) List all the course sections an instructor has taught.\n"
					+ "6) Add a grade to a given student for a given course/section.\n");

			int choice = getInt();
			
			switch (choice) {
				
			case 1:
				System.out.println("Would you like to add a...\n"
						+ "1) Student\n"
						+ "2) Department\n"
						+ "3) Course\n"
						+ "4) Instructor\n"
						+ "5) Course Section\n");
				
				int choice2 = getInt();
				
				switch (choice2) {
				
				case 1:
					System.out.println("You chose to add a Student\n");
					
					System.out.println("Enter SSN: ");
					String ssn = getString();
					System.out.println("Enter First Name: ");
					String fName = getString();
					System.out.println("Enter Middle Initial: ");
					String midIn = getString();
					System.out.println("Enter Last Name: ");
					String lName = getString();
					System.out.println("Enter Sex: ");
					String sex = getString();
					System.out.println("Enter N Number: ");
					String nNum = getString();
					System.out.println("Enter Birth Date (yyyy-mm-dd): ");
					String tempBDate = getString();
				    Date bDate=Date.valueOf(tempBDate);
					System.out.println("Enter Degree Program: ");
					String degProg = getString();
					System.out.println("Enter Seniority: ");
					String seniority = getString();
					System.out.println("Enter Permanent Phone Number: ");
					String permPhone = getString();
					System.out.println("Enter Permanent Street Address: ");
					String permStrtAdd = getString();
					System.out.println("Enter Permanent City: ");
					String permCity = getString();
					System.out.println("Enter Permanent State: ");
					String permState = getString();
					System.out.println("Enter Permanent Zip: ");
					String permZip = getString();
					System.out.println("Enter Current Phone Number: ");
					String currPhone = getString();
					System.out.println("Enter Current Full Address: ");
					String currAdd = getString();
					System.out.println("Enter student's Major Department Code (4 digits): ");
					String majorDCode = getString();
					
					pstmt11.setString(1, ssn);
					pstmt11.setString(2, fName);
					pstmt11.setString(3, midIn);
					pstmt11.setString(4, lName);
					pstmt11.setString(5, sex);
					pstmt11.setString(6, nNum);
					pstmt11.setDate(7, bDate);
					pstmt11.setString(8, degProg);
					pstmt11.setString(9, seniority);
					pstmt11.setString(10, permPhone);
					pstmt11.setString(11, permStrtAdd);
					pstmt11.setString(12, permCity);
					pstmt11.setString(13, permState);
					pstmt11.setString(14, permZip);
					pstmt11.setString(15, currPhone);
					pstmt11.setString(16, currAdd);
					
					int NumRows11 = pstmt11.executeUpdate();
				    System.out.println("\n" + NumRows11 + " row(s) inserted");
				    
				    pstmt111.setString(1, ssn);
				    pstmt111.setString(2, majorDCode);
				    
				    int NumRows111 = pstmt111.executeUpdate();
				    System.out.println("\n" + NumRows111 + " row(s) inserted");
				    
				    System.out.println("If student has a minor, enter 1. If not, enter any other number.");
				    int minorChoice = getInt();
				    if(minorChoice == 1) {
				    	
				    	System.out.println("Enter student's Minor Department Code (4 digits): ");
						String minorDCode = getString();
						
						pstmt112.setString(1, ssn);
						pstmt112.setString(2, minorDCode);
						
						int NumRows112 = pstmt112.executeUpdate();
					    System.out.println("\n" + NumRows112 + " row(s) inserted");
				    }
					
					break;
				
				case 2:
					System.out.println("You chose to add a Department\n");
					
					System.out.println("Enter Department Code (4 digits): ");
					String dCode = getString();
					System.out.println("Enter Department Name: ");
					String dName = getString();
					System.out.println("Enter College: ");
					String college = getString();
					System.out.println("Enter Department Office Number: ");
					int offNum = getInt();
					System.out.println("Enter Department Office Phone: ");
					String offPhone = getString();
					
					pstmt12.setString(1, dCode);
					pstmt12.setString(2, dName);
					pstmt12.setString(3, college);
					pstmt12.setInt(4, offNum);
					pstmt12.setString(5, offPhone);
					
					int NumRows12 = pstmt12.executeUpdate();
				    System.out.println("\n" + NumRows12 + " row(s) inserted");
					
					break;
					
				case 3:
					System.out.println("You chose to add a Course\n");
					
					System.out.println("Enter Course Number (4 digits): ");
					int corNum = getInt();
					System.out.println("Enter Course Name: ");
					String corName = getString();
					System.out.println("Enter Description: ");
					String desc = getString();
					System.out.println("Enter Semester Hours Amount (1 digit): ");
					int hours = getInt();
					System.out.println("Enter Course Level (4 digits): ");
					int corLvl = getInt();
					System.out.println("Enter existing Department Code to place Course into (4 digits): ");
					String corDCode = getString();
					
					pstmt13.setInt(1, corNum);
					pstmt13.setString(2, corName);
					pstmt13.setString(3, desc);
					pstmt13.setInt(4, hours);
					pstmt13.setInt(5, corLvl);
					pstmt13.setString(6, corDCode);
					
					int NumRows13 = pstmt13.executeUpdate();
				    System.out.println("\n" + NumRows13 + " row(s) inserted");
					
					break;
					
				case 4:
					System.out.println("You chose to add an Instructor\n");
					
					System.out.println("Enter SSN: ");
					String issn = getString();
					System.out.println("Enter N Number: ");
					String iNNum = getString();
					System.out.println("Enter City: ");
					String iCity = getString();
					System.out.println("Enter State: ");
					String iState = getString();
					System.out.println("Enter Zip: ");
					String iZip = getString();
					System.out.println("Enter Office Number: ");
					int iOfficeNum = getInt();
					System.out.println("Enter Age: ");
					int iAge = getInt();
					System.out.println("Enter First Name: ");
					String iFName = getString();
					System.out.println("Enter Middle Initial: ");
					String iMidIn = getString();
					System.out.println("Enter Last Name: ");
					String iLName = getString();
					System.out.println("Enter existing Department Code to place Instructor into (4 digits): ");
					String iDCode = getString();
					
					pstmt14.setString(1, issn);
					pstmt14.setString(2, iNNum);
					pstmt14.setString(3, iCity);
					pstmt14.setString(4, iState);
					pstmt14.setString(5, iZip);
					pstmt14.setInt(6, iOfficeNum);
					pstmt14.setInt(7, iAge);
					pstmt14.setString(8, iFName);
					pstmt14.setString(9, iMidIn);
					pstmt14.setString(10, iLName);
					pstmt14.setString(11, iDCode);
					
					int NumRows14 = pstmt14.executeUpdate();
				    System.out.println("\n" + NumRows14 + " row(s) inserted");
					
					break;
					
				case 5:
					System.out.println("You chose to add a Course Section\n");
					
					System.out.println("Enter Year: ");
					int year = getInt();
					System.out.println("Enter Semester: ");
					String sem = getString();
					System.out.println("Enter Section Number (7 digits): ");
					int secNum = getInt();
					System.out.println("Enter existing Instructor's SSN: ");
					String secISSN = getString();
					System.out.println("Enter existing Course Number (4 digits): ");
					int secCNum = getInt();
					
					pstmt15.setInt(1, year);
					pstmt15.setString(2, sem);
					pstmt15.setInt(3, secNum);
					pstmt15.setString(4, secISSN);
					pstmt15.setInt(5, secCNum);
					
					int NumRows15 = pstmt15.executeUpdate();
				    System.out.println("\n" + NumRows15 + " row(s) inserted");
					
					break;
					
				default:
					System.out.println("This is an invalid option!");
				}
				
				break;
				
			case 2:
				
				System.out.println("You chose to add a student to a course section.");
				
				System.out.println("Enter existing Student's SSN: ");
				String enrollSSN = getString();
				System.out.println("Enter existing Course Section's Section Number (7 digits): ");
				int enrollSecNum = getInt();
				
				pstmt2.setString(1, enrollSSN);
				pstmt2.setInt(2, enrollSecNum);
				
				int NumRows2 = pstmt2.executeUpdate();
			    System.out.println("\n" + NumRows2 + " row(s) inserted");
				
				break;
				
			case 3:
				System.out.println("You chose to generate a grade report.");
				calc_grade_report(stmt, conn);
				break;
				
			case 4:
				System.out.println("You chose to find a department's courses.");
				find_course(stmt);
				break;
				
			case 5:
				System.out.println("You chose to list an Instructor's sections.");
				System.out.print("Please enter the Instructor's N-Number: ");
				String instNnum = getString();
				
				Statement stmnt = conn.createStatement();
				String q5 = "SELECT SectionNum " +
							"FROM SECTION " +
							"WHERE ISSN IN (SELECT SSN " +
						               "FROM INSTRUCTOR " +
						               "WHERE Nnum = " + instNnum + ")";
				ResultSet rset = stmnt.executeQuery(q5);
				
				System.out.println("Section Numbers:");
				System.out.println("----------------");
				
				while (rset.next()) {
					int sectionNum = rset.getInt("SectionNum");
					System.out.println(sectionNum);
				}
				
				break;
				
			case 6:
				System.out.println("You chose to add a grade for a student's class section.");
				
				System.out.print("Enter Student SSN: ");
				String sSSN = getString();
				
				System.out.print("\nEnter Section Number: ");
				int secNum = getInt();
				
				System.out.print("\nEnter Letter Grade: ");
				String lg = getString();
				
				System.out.print("\nEnter Grade Point: ");
				float gp = getFloat();
			
				PreparedStatement stmnt61 = conn.prepareStatement("UPDATE ENROLLED_IN SET LetterGrade = ? WHERE StudSSN = ? AND SectionNum = ?");
				PreparedStatement stmnt62 = conn.prepareStatement("UPDATE ENROLLED_IN SET GradePoint = ? WHERE StudSSN = ? AND SectionNum = ?");
				stmnt61.setString(1, lg);
				stmnt61.setString(2, sSSN);
				stmnt61.setInt(3, secNum);
				stmnt62.setFloat(1, gp);
				stmnt62.setString(2, sSSN);
				stmnt62.setInt(3, secNum);
				
				int numRows61 = stmnt61.executeUpdate();
				int numRows62 = stmnt62.executeUpdate();
				System.out.println("\n" + numRows61 + " row(s) updated");
				System.out.println("\n" + numRows62 + " row(s) updated");
				
				break;
			
			default:
				System.out.println("This is an invalid option!\n");
			}
			
			System.out.println("Press 0 to exit. Otherwise, press any other number to perform another SQL statement.\n");
			exit = getInt();
		}

		System.out.println("Have a nice day!");
	}

	public static String getString() {
		try {
			StringBuffer buffer = new StringBuffer();
			int c = System.in.read();
			while (c != '\n' && c != -1) {
				buffer.append((char) c);
				c = System.in.read();
			}
			return buffer.toString().trim();
		} catch (IOException e) {
			return "";
		}
	}

	public static int getInt()

	{
		String s = getString();
		return Integer.parseInt(s);
	}

	public static float getFloat()

	{
		String s = getString();
		return Float.parseFloat(s);
	}
}

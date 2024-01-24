import java.sql.*;
import java.util.ArrayList;

public class DatabaseOptions {
    private PreparedStatement stud_insert;
    private PreparedStatement major_insert;
    private PreparedStatement minor_insert;
    private PreparedStatement dept_insert;
    private PreparedStatement course_insert;
    private PreparedStatement instruc_insert;
    private PreparedStatement sect_insert;
    private PreparedStatement enroll_insert;

    public DatabaseOptions(Connection conn) throws SQLException
    {
        stud_insert = conn.prepareStatement ("INSERT INTO STUDENT(SSN, FName, MidIn, LName, Sex, Nnum, BDate, DegProg,"
				+ " Seniority, PermPhone, PermStreetAddress, PermCity, PermState, PermZip, CurrPhone, CurrAddress) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		major_insert = conn.prepareStatement ("INSERT INTO MAJOR_IN(StudSSN, DCode) "
				+ "VALUES (?, ?)");
		
		minor_insert = conn.prepareStatement ("INSERT INTO MINOR_IN(StudSSN, DCode) "
				+ "VALUES (?, ?)");
		
		dept_insert = conn.prepareStatement ("INSERT INTO DEPARTMENT(DCode, DName, College, OfficeNum, OfficePhone) "
				+ "VALUES (?, ?, ?, ?, ?)");
		
		course_insert = conn.prepareStatement ("INSERT INTO COURSE(CourseNum, CourseName, Description, SemHours, Lvl, DCode) "
				+ "VALUES (?, ?, ?, ?, ?, ?)");
		
		instruc_insert = conn.prepareStatement ("INSERT INTO INSTRUCTOR(SSN, Nnum, City, State, Zip, OfficeNum, Age, Fname,"
				+ " MidIn, LName, DCode, StreetAddress) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		sect_insert = conn.prepareStatement ("INSERT INTO SECTION(Year, Sem, SectionNum, ISSN, CourseNum) "
				+ "VALUES (?, ?, ?, ?, ?)");

		enroll_insert = conn.prepareStatement ("INSERT INTO ENROLLED_IN(StudSSN, SectionNum, LetterGrade, GradePoint) "
        + "VALUES (?, ?, NULL, NULL)");
    }

    public void add_database_item() throws SQLException
    {
        System.out.println("Would you like to add a...\n"
							+ "1) Student\n"
							+ "2) Department\n"
							+ "3) Course\n"
							+ "4) Instructor\n"
							+ "5) Course Section\n");
					
        int choice = Utility.getInt();
        
        switch (choice) {
            case 1:
                DatabaseAdd.add_student(stud_insert, major_insert, minor_insert);
                break;
            
            case 2:
                DatabaseAdd.add_dept(dept_insert);
                break;
                
            case 3:
                DatabaseAdd.add_course(course_insert);
                break;
                                    
            case 4:
                DatabaseAdd.add_instruc(instruc_insert);		
                break;
                
            case 5:
                DatabaseAdd.add_sect(sect_insert);		
                break;
                
            default:
                System.out.println("This is an invalid option!");
        }
					
    }

    public void add_to_course() throws SQLException
    {        
        System.out.println("Enter existing Student's SSN: ");
        String enrollSSN = Utility.getString();
        System.out.println("Enter existing Course Section's Section Number (7 digits): ");
        int enrollSecNum = Utility.getInt();
        
        enroll_insert.setString(1, enrollSSN);
        enroll_insert.setInt(2, enrollSecNum);
        
        DatabaseAdd.print_rows(enroll_insert);
    }

    public void find_course(Statement stmt)
	{
		String DName = "";
		System.out.println("[+] Enter a department name or code to show courses offered: ");
		String name = Utility.getString();
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
			        "WHERE DCode= " + "\'" + DCode + "\'";
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
	
	public void calc_grade_report(Statement stmt, Connection conn) throws SQLException
	{
		String SSN, sectNum, courseNum;
		SSN = courseNum = sectNum= "";
		System.out.print("[+] Enter your N. Number: ");
		String Nno = Utility.getString();
		ArrayList<String> Grades = new ArrayList<String>();
	    
        /*
         * STUDENT QUERY
         */
        String student_query = "SELECT * " +
                    "FROM STUDENT " +
                    "WHERE Nnum= " + Nno ;
        ResultSet rset = stmt.executeQuery(student_query);
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
        
        student_query = "SELECT * " +
            "FROM ENROLLED_IN " +
            "WHERE StudSSN= " + SSN;

        rset = stmt.executeQuery(student_query);
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

            /*
             * SECTION QUERY
             */
            String sect_query = "SELECT * " +
                "FROM SECTION " +
                "WHERE SectionNum= " + sectNum;
            
            ResultSet rsetSect = conn.createStatement().executeQuery(sect_query);
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
            
            /*
             * COURSE QUERY
             */
            String course_query = "SELECT * " +
                "FROM COURSE " +
                "WHERE CourseNum = " + courseNum;
            ResultSet rsetCourse = conn.createStatement().executeQuery(course_query);
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

        Utility.grade_scale(Grades);
	}

    public void list_courses(Statement stmt) throws SQLException
    {
        System.out.print("Please enter the Instructor's N-Number: ");
        String instNnum = Utility.getString();
        
        String sect_query = "SELECT SectionNum " +
                    "FROM SECTION " +
                    "WHERE ISSN IN (SELECT SSN " +
                            "FROM INSTRUCTOR " +
                            "WHERE Nnum = " + instNnum + ")";
        ResultSet rset = stmt.executeQuery(sect_query);
        
        System.out.println("Section Numbers:");
        System.out.println("----------------");
        
        while (rset.next()) {
            int sectionNum = rset.getInt("SectionNum");
            System.out.println(sectionNum);
        }
					
    }

    public void grade_student(PreparedStatement update_grade, PreparedStatement update_gpa) throws SQLException
    {

        System.out.print("Enter Student SSN: ");
        String sSSN = Utility.getString();
        
        System.out.print("\nEnter Section Number: ");
        int secNum = Utility.getInt();
        
        System.out.print("\nEnter Letter Grade: ");
        String grade = Utility.getString();
        
        System.out.print("\nEnter Grade Point: ");
        float gpa = Utility.getFloat();

        update_grade.setString(1, grade);
        update_grade.setString(2, sSSN);
        update_grade.setInt(3, secNum);
        update_gpa.setFloat(1, gpa);
        update_gpa.setString(2, sSSN);
        update_gpa.setInt(3, secNum);
        
        DatabaseAdd.print_rows(update_grade);
        DatabaseAdd.print_rows(update_gpa);
    }
}

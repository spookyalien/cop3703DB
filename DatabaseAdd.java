import java.sql.*;

public class DatabaseAdd 
{
    public static void add_student(PreparedStatement stud_insert, PreparedStatement major_insert, PreparedStatement minor_insert) throws SQLException
	{
		System.out.println("You chose to add a Student\n");
					
		System.out.println("Enter SSN: ");
		String ssn = Utility.getString();
		System.out.println("Enter First Name: ");
		String fName = Utility.getString();
		System.out.println("Enter Middle Initial: ");
		String midIn = Utility.getString();
		System.out.println("Enter Last Name: ");
		String lName = Utility.getString();
		System.out.println("Enter Sex: ");
		String sex = Utility.getString();
		System.out.println("Enter N Number: ");
		String nNum = Utility.getString();
		System.out.println("Enter Birth Date (yyyy-mm-dd): ");
		String tempBDate = Utility.getString();
		Date bDate=Date.valueOf(tempBDate);
		System.out.println("Enter Degree Program: ");
		String degProg = Utility.getString();
		System.out.println("Enter Seniority: ");
		String seniority = Utility.getString();
		System.out.println("Enter Permanent Phone Number: ");
		String permPhone = Utility.getString();
		System.out.println("Enter Permanent Street Address: ");
		String permStrtAdd = Utility.getString();
		System.out.println("Enter Permanent City: ");
		String permCity = Utility.getString();
		System.out.println("Enter Permanent State: ");
		String permState = Utility.getString();
		System.out.println("Enter Permanent Zip: ");
		String permZip = Utility.getString();
		System.out.println("Enter Current Phone Number: ");
		String currPhone = Utility.getString();
		System.out.println("Enter Current Full Address: ");
		String currAdd = Utility.getString();
		System.out.println("Enter student's Major Department Code (4 digits): ");
		String majorDCode = Utility.getString();
					
		stud_insert.setString(1, ssn);
		stud_insert.setString(2, fName);
		stud_insert.setString(3, midIn);
		stud_insert.setString(4, lName);
		stud_insert.setString(5, sex);
		stud_insert.setString(6, nNum);
		stud_insert.setDate(7, bDate);
		stud_insert.setString(8, degProg);
		stud_insert.setString(9, seniority);
		stud_insert.setString(10, permPhone);
		stud_insert.setString(11, permStrtAdd);
		stud_insert.setString(12, permCity);
		stud_insert.setString(13, permState);
		stud_insert.setString(14, permZip);
		stud_insert.setString(15, currPhone);
		stud_insert.setString(16, currAdd);
					
		print_rows(stud_insert);
		
		major_insert.setString(1, ssn);
		major_insert.setString(2, majorDCode);
		
		print_rows(major_insert);
		
		System.out.println("If student has a minor, enter 1. If not, enter any other number.");
		int minorChoice = Utility.getInt();
		if(minorChoice == 1) {
			
			System.out.println("Enter student's Minor Department Code (4 digits): ");
			String minorDCode = Utility.getString();
			
			minor_insert.setString(1, ssn);
			minor_insert.setString(2, minorDCode);
			
			print_rows(minor_insert);
		}	
	}

	public static void add_dept(PreparedStatement dept_insert) throws SQLException
	{
		System.out.println("You chose to add a Department\n");
					
		System.out.println("Enter Department Code (4 digits): ");
		String dCode = Utility.getString();
		System.out.println("Enter Department Name: ");
		String dName = Utility.getString();
		System.out.println("Enter College: ");
		String college = Utility.getString();
		System.out.println("Enter Department Office Number: ");
		int offNum = Utility.getInt();
		System.out.println("Enter Department Office Phone: ");
		String offPhone = Utility.getString();
		
		dept_insert.setString(1, dCode);
		dept_insert.setString(2, dName);
		dept_insert.setString(3, college);
		dept_insert.setInt(4, offNum);
		dept_insert.setString(5, offPhone);
		
		print_rows(dept_insert);					
	}

	public static void add_course(PreparedStatement course_insert) throws SQLException
	{
		System.out.println("You chose to add a Course\n");
					
		System.out.println("Enter Course Number (4 digits): ");
		int corNum = Utility.getInt();
		System.out.println("Enter Course Name: ");
		String corName = Utility.getString();
		System.out.println("Enter Description: ");
		String desc = Utility.getString();
		System.out.println("Enter Semester Hours Amount (1 digit): ");
		int hours = Utility.getInt();
		System.out.println("Enter Course Level (4 digits): ");
		int corLvl = Utility.getInt();
		System.out.println("Enter existing Department Code to place Course into (4 digits): ");
		String corDCode = Utility.getString();
		
		course_insert.setInt(1, corNum);
		course_insert.setString(2, corName);
		course_insert.setString(3, desc);
		course_insert.setInt(4, hours);
		course_insert.setInt(5, corLvl);
		course_insert.setString(6, corDCode);
		
		print_rows(course_insert);
	}

	public static void add_instruc(PreparedStatement instruc_insert) throws SQLException
	{
		System.out.println("You chose to add an Instructor\n");
					
		System.out.println("Enter SSN: ");
		String issn = Utility.getString();
		System.out.println("Enter N Number: ");
		String iNNum = Utility.getString();
		System.out.println("Enter City: ");
		String iCity = Utility.getString();
		System.out.println("Enter State: ");
		String iState = Utility.getString();
		System.out.println("Enter Zip: ");
		String iZip = Utility.getString();
		System.out.println("Enter Office Number: ");
		int iOfficeNum = Utility.getInt();
		System.out.println("Enter Age: ");
		int iAge = Utility.getInt();
		System.out.println("Enter First Name: ");
		String iFName = Utility.getString();
		System.out.println("Enter Middle Initial: ");
		String iMidIn = Utility.getString();
		System.out.println("Enter Last Name: ");
		String iLName = Utility.getString();
		System.out.println("Enter existing Department Code to place Instructor into (4 digits): ");
		String iDCode = Utility.getString();
		System.out.println("Enter Instructor Address: ");
		String iAdd = Utility.getString();
		
		instruc_insert.setString(1, issn);
		instruc_insert.setString(2, iNNum);
		instruc_insert.setString(3, iCity);
		instruc_insert.setString(4, iState);
		instruc_insert.setString(5, iZip);
		instruc_insert.setInt(6, iOfficeNum);
		instruc_insert.setInt(7, iAge);
		instruc_insert.setString(8, iFName);
		instruc_insert.setString(9, iMidIn);
		instruc_insert.setString(10, iLName);
		instruc_insert.setString(11, iDCode);
		instruc_insert.setString(12, iAdd);
		
		print_rows(instruc_insert);
	}

	public static void add_sect(PreparedStatement sect_insert) throws SQLException
	{
		System.out.println("You chose to add a Course Section\n");
					
		System.out.println("Enter Year: ");
		int year = Utility.getInt();
		System.out.println("Enter Semester: ");
		String sem = Utility.getString();
		System.out.println("Enter Section Number (7 digits): ");
		int secNum = Utility.getInt();
		System.out.println("Enter existing Instructor's SSN: ");
		String secISSN = Utility.getString();
		System.out.println("Enter existing Course Number (4 digits): ");
		int secCNum = Utility.getInt();
		
		sect_insert.setInt(1, year);
		sect_insert.setString(2, sem);
		sect_insert.setInt(3, secNum);
		sect_insert.setString(4, secISSN);
		sect_insert.setInt(5, secCNum);
		
		print_rows(sect_insert);
	}

    public static void print_rows(PreparedStatement stmt) throws SQLException
    {
		System.out.println("\n" + stmt.executeUpdate() + " row(s) inserted");
    }
}

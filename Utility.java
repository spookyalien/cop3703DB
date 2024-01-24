import java.io.IOException;
import java.util.ArrayList;

public class Utility
{
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

	public static void grade_scale(ArrayList<String> Grades)
	{
		Double finalGPA = 0.0;
		
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
	}
}
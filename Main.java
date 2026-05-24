import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentDAO dao = new StudentDAO();

        while (true) {

            System.out.println("\n===== STUDENT REGISTRATION SYSTEM =====");

            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");

            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Course: ");
                    String course = sc.nextLine();

                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();

                    Student s = new Student(name, email, course, age);

                    dao.addStudent(s);

                    break;

                case 2:

                    List<Student> students = dao.getAllStudents();

                    for (Student st : students) {

                        System.out.println(
                                st.getId() + " | " +
                                st.getName() + " | " +
                                st.getEmail() + " | " +
                                st.getCourse() + " | " +
                                st.getAge()
                        );
                    }

                    break;

                case 3:

                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter New Name: ");
                    String newName = sc.nextLine();

                    System.out.print("Enter New Email: ");
                    String newEmail = sc.nextLine();

                    System.out.print("Enter New Course: ");
                    String newCourse = sc.nextLine();

                    System.out.print("Enter New Age: ");
                    int newAge = sc.nextInt();

                    Student updateStudent =
                            new Student(id, newName,
                                    newEmail, newCourse, newAge);

                    dao.updateStudent(updateStudent);

                    break;

                case 4:

                    System.out.print("Enter Student ID to Delete: ");
                    int deleteId = sc.nextInt();

                    dao.deleteStudent(deleteId);

                    break;

                case 5:

                    System.out.println("Exiting...");
                    System.exit(0);

                default:

                    System.out.println("Invalid Choice!");
            }
        }
    }
}
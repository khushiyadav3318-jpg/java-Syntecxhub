import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // CREATE
    public void addStudent(Student student) {

        String query =
                "INSERT INTO students(name, email, course, age) VALUES(?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getCourse());
            ps.setInt(4, student.getAge());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student Added Successfully!");
            }

        } catch (SQLException e) {

            System.out.println("Error Adding Student");
            e.printStackTrace();
        }
    }

    // READ
    public List<Student> getAllStudents() {

        List<Student> list = new ArrayList<>();

        String query = "SELECT * FROM students";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {

                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("course"),
                        rs.getInt("age")
                );

                list.add(s);
            }

        } catch (SQLException e) {

            System.out.println("Error Fetching Students");
            e.printStackTrace();
        }

        return list;
    }

    // UPDATE
    public void updateStudent(Student student) {

        String query =
                "UPDATE students SET name=?, email=?, course=?, age=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getCourse());
            ps.setInt(4, student.getAge());
            ps.setInt(5, student.getId());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student Updated Successfully!");
            }

        } catch (SQLException e) {

            System.out.println("Error Updating Student");
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteStudent(int id) {

        String query = "DELETE FROM students WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student Deleted Successfully!");
            }

        } catch (SQLException e) {

            System.out.println("Error Deleting Student");
            e.printStackTrace();
        }
    }
}
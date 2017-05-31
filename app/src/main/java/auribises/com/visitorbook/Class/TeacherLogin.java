package auribises.com.visitorbook.Class;

public class TeacherLogin {

    int id;
    String username,password;

    public TeacherLogin() {
    }

    public TeacherLogin(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "teacherlogin\n" +
                "\nID is: " + id +
                "\nUsername is: " + username +
                "\nPassword is: " + password ;
    }
}

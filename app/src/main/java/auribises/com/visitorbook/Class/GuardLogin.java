package auribises.com.visitorbook.Class;

public class GuardLogin {

    int id;
    String username,password;

    public GuardLogin() {
    }

    public GuardLogin(int id, String username, String password) {
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
        return "guardlogin\n" +
                "\nID is: " + id +
                "\nUsername is: " + username +
                "\nPassword is: " + password ;
    }
}

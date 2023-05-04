public class User {
    String name; // nazwa użytkownika
    LoginData login_data; // dane do logowania

    static class LoginData { // FIXME: IDE kazało mi dać tu static, ale nie wiem czemu
        String email;
        String password;

        // TODO: z kodowaniem tych haseł może być fajna zabawa
        // i pewnie będzie trzeba je wyciągnąć na zewnątrz przy robieniu GUI
        LoginData(String email, String password) {
            this.email = email;
            this.password = password;
        }

        void login() {}

        void signup() {}
    }
}

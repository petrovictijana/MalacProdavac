package server.server;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Main {
    public static void main(String[] args) {
        System.out.println("Boki037 - Bokii037: " + BCrypt.hashpw("Bokii037", BCrypt.gensalt()));
        System.out.println("Peki034 - Peki034: " + BCrypt.hashpw("Peki034", BCrypt.gensalt()));
        System.out.println("Jankovic - Grosnica04: " + BCrypt.hashpw("Grosnica04", BCrypt.gensalt()));
        System.out.println("matejakovacevic - kovacevic: " + BCrypt.hashpw("kovacevic", BCrypt.gensalt()));
        System.out.println("pekic - miroslav: " + BCrypt.hashpw("miroslav", BCrypt.gensalt()));
        System.out.println("vidicnemanja - vidic: " + BCrypt.hashpw("vidic", BCrypt.gensalt()));
        System.out.println("nikodijevici - nikodijevici: " + BCrypt.hashpw("nikodijevici", BCrypt.gensalt()));
    }
}

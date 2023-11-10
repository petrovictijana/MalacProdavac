package server.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import server.server.fileSystem.service.StorageService;
import server.server.service.TopPerformersService;

@SpringBootApplication
public class Main {
    @Autowired
    static TopPerformersService topPerformersService;
    public static void main(String[] args) {

        //SpringApplication.run(Main.class, args);
//        System.out.println("Boki037 - Bokii037: " + BCrypt.hashpw("Bokii037", BCrypt.gensalt()));
//        System.out.println("Peki034 - Peki034: " + BCrypt.hashpw("Peki034", BCrypt.gensalt()));
//        System.out.println("Jankovic - Grosnica04: " + BCrypt.hashpw("Grosnica04", BCrypt.gensalt()));
//        System.out.println("matejakovacevic - kovacevic: " + BCrypt.hashpw("kovacevic", BCrypt.gensalt()));
//        System.out.println("pekic - miroslav: " + BCrypt.hashpw("miroslav", BCrypt.gensalt()));
//        System.out.println("vidicnemanja - vidic: " + BCrypt.hashpw("vidic", BCrypt.gensalt()));
//        System.out.println("nikodijevici - nikodijevici: " + BCrypt.hashpw("nikodijevici", BCrypt.gensalt()));

//        TopPerformersServiceImpl topPerformersService1 = new TopPerformersServiceImpl();
//
//        List<MonthTopSellers> monthTopSellers = topPerformersService1.getTop3SellersOfTheMonth();
//        System.out.println(monthTopSellers.toString());
    }

}

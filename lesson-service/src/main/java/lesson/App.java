package lesson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "lesson")
public class App extends SpringBootServletInitializer
{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

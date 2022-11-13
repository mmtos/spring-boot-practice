package mmtos.practice.springboot.basic.runner;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicRunnerConfig {
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx){
        return arg -> {
            System.out.println("Let's inspect beans!");
            String[] beanNames = ctx.getBeanDefinitionNames();
            System.out.println("===== 스프링 빈 개수 : " + beanNames.length + " =====");
            Arrays.sort(beanNames);
            for(String beanName : beanNames){
                System.out.println(beanName);
            }
        };
    }
}

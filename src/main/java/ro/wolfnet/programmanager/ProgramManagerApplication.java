package ro.wolfnet.programmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The Class HelloOpenshiftApplication.
 *
 * @author isti
 * @since Feb 6, 2018
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class ProgramManagerApplication extends SpringBootServletInitializer {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(ProgramManagerApplication.class, args);
  }

  /**
   * Configure.
   *
   * @param application the application
   * @return the spring application builder
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ProgramManagerApplication.class);
  }

  /**
   * Password encoder.
   *
   * @return the b crypt password encoder
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder;
  }
}

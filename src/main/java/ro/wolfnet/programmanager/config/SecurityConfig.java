package ro.wolfnet.programmanager.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityConfig.
 *
 * @author isti
 * @since Feb 8, 2018
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /** The b crypt password encoder. */
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  /** The data source. */
  @Autowired
  private DataSource dataSource;

  /* (non-Javadoc)
   * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().usersByUsernameQuery("select username, password, 1 from user_entity where username = ?")
        .authoritiesByUsernameQuery("select username, 'REGULAR' from user_entity where username = ?").dataSource(dataSource)
        .passwordEncoder(bCryptPasswordEncoder);
  }

  /* (non-Javadoc)
   * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().anyRequest().authenticated();
    http.csrf().disable().httpBasic();
    http.cors();
  }
}

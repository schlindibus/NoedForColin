package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.MemberService;
import com.sun.jdi.event.ExceptionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //    @Autowired
//    public void globalSecurityConfigurator(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user").password("{noop}asdf").roles("user");
//        auth.inMemoryAuthentication().withUser("admin").password("{noop}5678").roles("user", "admin");
//    }
    @Autowired
    MemberService memberService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider prov = new DaoAuthenticationProvider();
        prov.setPasswordEncoder(passwordEncoder());
        prov.setUserDetailsService(this.memberService);
        return prov;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("USing default configure (httpSecurity)." + "If subclassed this will potentially override subclass configure(httpsSecurity)");

        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/noSecurity").permitAll()
                .antMatchers("/get-register").permitAll()
                .antMatchers("/css/*").permitAll()
                .antMatchers("/fragments/*").permitAll()
                .antMatchers("/img/*").permitAll()
                .antMatchers("/403.html").permitAll()
                .antMatchers("/404.html").permitAll()
                .antMatchers("/contact.html").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/get-members").hasAuthority("admin")
                .antMatchers("/get-channels").hasAnyAuthority("admin", "supervisor", "member")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll()
                .and().httpBasic()
                .and().exceptionHandling().accessDeniedPage("/403.html");
        http.csrf().ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin();
    }
}

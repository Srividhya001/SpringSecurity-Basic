package com.example.SpringSecurityBasic.configuartion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    PasswordEncoder encoder;

    //For Authentication
    //1.what type of authentication
    //2.who are users
    //3.password encoding

    //Method to create Source of truth for users and their passwords
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1= User.withUsername("user")
                .password(encoder.encode("user"))
                .roles("USER").build();
        UserDetails user2=User.withUsername("admin")
                .password(encoder.encode("admin"))
                .roles("ADMIN")
                .build();
        UserDetails[] usrArr={user1,user2};

        return new InMemoryUserDetailsManager(usrArr);

    }


//auth holds creds info before authentication and after holds the principal(logged in user)
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        //auth holds the credentials before log in
        System.out.println("From authenticate method");
        String userName=auth.getName();//user entered name
        String password=auth.getCredentials().toString();//user entered password

        UserDetails user= userDetailsService().loadUserByUsername(userName);//from in memory datan
        if(encoder.matches(password,user.getPassword()))
        {
            //authres when authentication successful holds principal
            Authentication authres=new UsernamePasswordAuthenticationToken(userName,password,user.getAuthorities());
            System.out.println(authres);
            return authres;
        }

        else{
            System.err.println("Bad Creds.");
            throw new BadCredentialsException("In correct password.");
        }


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}

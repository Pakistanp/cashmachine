package com.ppapierz.cashmachine.security;

import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String pin = authentication.getCredentials().toString();
        String lodedPin = loadPin();
        if (pin.equals(lodedPin))
            return new UsernamePasswordAuthenticationToken(authentication.getName(), pin, Collections.emptyList());
        else
            return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private String loadPin() throws IOException {
        ServletRequestAttributes requestAttributes= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        MultipartFile file = (MultipartFile)session.getAttribute("file");
        BufferedReader reader = new BufferedReader(new FileReader("card.txt"));
        String pin = reader.readLine();
        reader.close();
        return pin;
    }
}

package com.example.user.config;

import com.example.user.model.User;
import com.example.user.repo.UserRepo;
import com.example.user.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepo userRepo;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        Optional<User> existingUser = userRepo.findByUsername(email); // Using email as username for OAuth users

        User user;
        if (existingUser.isEmpty()) {
            user = new User();
            user.setUsername(email);
            user.setEmailId(email);
            user.setProvider("GOOGLE");
            user.setPassword("OAUTH_USER"); // Dummy password
            userRepo.save(user);
        } else {
            user = existingUser.get();
        }

        String token = jwtService.generateToken(user.getUsername());

        // Redirect to frontend with token
        String targetUrl = "http://localhost:3000/?token=" + token;
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

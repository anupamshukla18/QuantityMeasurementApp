package com.app.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		String email = oAuth2User.getAttribute("email");

		// Generate the JWT
		String token = jwtUtils.generateJwtToken(email);

		// For backend testing, we will just return the token as raw JSON on the screen
		// so you can copy it to use in Swagger/Postman.
//		response.setContentType("application/json");
//		response.getWriter().write("{\"token\": \"" + token + "\"}");
		
		// Redirect back to your frontend, attaching the token to the URL
        // IMPORTANT: Change 3000 to whatever port your 'npx serve' is running on!
//        response.sendRedirect("http://localhost:3000/DashBoard.html?token=" + token);
		// Angular functionality
		response.sendRedirect("http://localhost:4200/login?token=" + token);
	}
}
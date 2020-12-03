package ar.edu.unsl.trazar.config.jwt.filter;

import static ar.edu.unsl.trazar.config.jwt.ConstantsConfig.*;

import ar.edu.unsl.trazar.entity.Usuario;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            Usuario usuario= new ObjectMapper().readValue(request.getInputStream(),Usuario.class);
            Authentication authentication= new UsernamePasswordAuthenticationToken(
                    usuario.getUserName(),
                    usuario.getPassword()
            );
            Authentication authenticate=authenticationManager.authenticate(authentication);
            return authenticate;
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        Date expirationTime=new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME);
        String token= Jwts.builder()
                          .setIssuedAt(new Date()).setIssuer(ISSUER_INFO)
                          .setSubject( ((User) authResult.getPrincipal()).getUsername())
                          .setExpiration(expirationTime)
                          .signWith(SignatureAlgorithm.HS512,SIGNATURE_KEY).compact();
        response.addHeader(HEADER_AUTHORIZACION_KEY,TOKEN_BEARER_PREFIX+" "+token);

    }
}

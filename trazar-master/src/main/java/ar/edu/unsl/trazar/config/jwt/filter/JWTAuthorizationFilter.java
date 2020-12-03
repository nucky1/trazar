package ar.edu.unsl.trazar.config.jwt.filter;

import static ar.edu.unsl.trazar.config.jwt.ConstantsConfig.*;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token=request.getHeader(HEADER_AUTHORIZACION_KEY);
        if( token!=null){
            String user= Jwts.parser()
                                .setSigningKey(SIGNATURE_KEY)
                                .parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX,""))
                                .getBody()
                                .getSubject();
            if(user!=null)  return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header= request.getHeader(HEADER_AUTHORIZACION_KEY);
        if(header==null||!header.startsWith(TOKEN_BEARER_PREFIX)){
            chain.doFilter(request,response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken=getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }
}

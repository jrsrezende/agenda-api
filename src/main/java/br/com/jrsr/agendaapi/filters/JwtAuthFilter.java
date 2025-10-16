package br.com.jrsr.agendaapi.filters;

import java.io.IOException;
import java.security.Key;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import org.springframework.web.filter.GenericFilterBean;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends GenericFilterBean {
    private final String secretKey;

    public JwtAuthFilter(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) { //O navegador primeiro envia uma requisição OPTIONS “preflight” para verificar se a API permite essa operação.
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) { //verifica se o header existe e se começa com Bearer
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access.");
            return;
        }

        final String token = authHeader.substring(7);
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes()); //cria a chave de assinatura
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody(); //valida o token e extrai os claims
            request.setAttribute("claims", claims); //coloca os claims na request para ser usado depois
            filterChain.doFilter(request, response); // continua a execução da requisição
        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Expired Token");
        } catch (JwtException | IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
        }
    }
}
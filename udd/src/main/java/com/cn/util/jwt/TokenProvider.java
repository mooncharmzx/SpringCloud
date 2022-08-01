package com.cn.util.jwt;

import com.cn.admin.LoginUser;
import com.cn.sce.entity.Scuser;
import com.cn.util.SceProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Base64.Encoder;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {
    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID_KEY = "id";
    private static final String REMEMBERME_KEY = "rem";
    private final Encoder encoder = Base64.getEncoder();
    private String secretKey;
    private long tokenValidityInMilliseconds;
    private long tokenValidityInMillisecondsForRememberMe;
    private final SceProperties sceProperties;

    public TokenProvider(SceProperties sceProperties) {
        this.sceProperties = sceProperties;
    }

    @PostConstruct
    public void init() {
        this.secretKey = this.encoder.encodeToString(this.sceProperties.getSecurity().getAuthentication().getJwt().getSecret().getBytes(StandardCharsets.UTF_8));
        this.tokenValidityInMilliseconds = 1000L * this.sceProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe = 1000L * this.sceProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = (String)authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        JwtBuilder builder = Jwts.builder().setSubject(authentication.getName()).claim("auth", authorities).claim("rem", rememberMe ? 1 : 0);
        Object principal = authentication.getPrincipal();
        if (principal instanceof Scuser) {
            int id = ((Scuser)principal).getId();
            builder.claim("id", id);
        }

        return builder.signWith(SignatureAlgorithm.HS512, this.secretKey).setExpiration(validity).compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = (Claims)Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        String authClaim = claims.get("auth").toString();
        Collection<? extends GrantedAuthority> authorities = (Collection)Arrays.stream(authClaim.split(",")).filter(StringUtils::isNotEmpty).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        int userId = NumberUtils.toInt(claims.get("id").toString());
        LoginUser principal = new LoginUser(userId, claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException var3) {
            this.log.info("Invalid JWT signature.");
            this.log.debug("Invalid JWT signature trace: {}", var3);
        } catch (MalformedJwtException var4) {
            this.log.info("Invalid JWT token.");
            this.log.debug("Invalid JWT token trace: {}", var4);
        } catch (ExpiredJwtException var5) {
            this.log.info("Expired JWT token.");
            this.log.debug("Expired JWT token trace: {}", var5);
        } catch (UnsupportedJwtException var6) {
            this.log.info("Unsupported JWT token.");
            this.log.debug("Unsupported JWT token trace: {}", var6);
        } catch (IllegalArgumentException var7) {
            this.log.info("JWT token compact of handler are invalid.");
            this.log.debug("JWT token compact of handler are invalid trace: {}", var7);
        }

        return false;
    }
}

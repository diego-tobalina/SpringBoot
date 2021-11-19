package com.xbidi.spring.content.shared.string.jwt;

import com.xbidi.spring.content.shared.security.JwtManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class JwtManagerTest {

  byte[] key = "randomKey".getBytes();
  JwtManager jwtManager = new JwtManager(key);

  @Test
  void generateBearerJwt_getClaims() {
    String id = "id";
    String subject = "subject";
    Map<String, Object> claims = new HashMap<>();
    claims.put("clave1", "valor1");
    claims.put("clave2", "valor2");

    // recupera los valores correctamente
    long expirationIn = 20000;
    String jwt1 = jwtManager.generateBearerJwt(id, subject, claims, expirationIn);
    Claims jwt1Claims = jwtManager.getClaims(jwt1);
    Assertions.assertEquals("valor1", jwt1Claims.get("clave1"));
    Assertions.assertEquals("valor2", jwt1Claims.get("clave2"));

    // recupera los valores correctamente
    Date issuedAt = new Date();
    Date expirationAt = new Date(issuedAt.getTime() + 20000);
    String jwt2 = jwtManager.generateBearerJwt(id, subject, jwt1Claims, issuedAt, expirationAt);
    Claims jwt2Claims = jwtManager.getClaims(jwt2);
    Assertions.assertEquals("valor1", jwt2Claims.get("clave1"));
    Assertions.assertEquals("valor2", jwt2Claims.get("clave2"));

    // el token ha expirado
    String jwt3 = jwtManager.generateBearerJwt(id, subject, jwt1Claims, 0L);
    Assertions.assertThrows(ExpiredJwtException.class, () -> jwtManager.getClaims(jwt3));

    // la firma del token no es válida
    String invalidSignature = String.format("%s%s", jwt3, "randomString");
    Assertions.assertThrows(SignatureException.class, () -> jwtManager.getClaims(invalidSignature));
  }
}

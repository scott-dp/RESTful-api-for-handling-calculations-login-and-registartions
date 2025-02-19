package stud.ntnu.no.calculator.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stud.ntnu.no.calculator.Service.UserService;
import stud.ntnu.no.calculator.model.User;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping(value = "/token")
@CrossOrigin
public class TokenController {
  @Autowired
  private UserService userService;
  private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(5);
  // keyStr is hardcoded here for testing purpose
  // in a real scenario, it should either be stored in a database or injected from the environment
  public static final String keyStr = "testsecrettestsecrettestsecrettestsecrettestsecret";

  @PostMapping(value = "")
  public ResponseEntity<?> generateToken(@RequestBody User user) {
    if (userService.areUserCredentialsValid(user)) {
      return ResponseEntity.ok(generateToken(user.getUsername()));
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  public String generateToken(String username) {
    final Instant now = Instant.now();
    final Algorithm hmac512 = Algorithm.HMAC512(keyStr);;
    final JWTVerifier verifier = JWT.require(hmac512).build();
    return JWT.create()
        .withSubject(username)
        .withIssuer("calculator_idatt2105_token_issuer_app")
        .withIssuedAt(now)
        .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
        .sign(hmac512);

  }
}

package com.example.maven.jwt;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * @author ZhengHao Lou
 * @date 2020/05/12
 */
@Slf4j
public class JWTTests {
    private final Base64.Encoder encoder = Base64.getEncoder();

    private Map<String, Object> credentialsMap = Maps.newHashMap();

    private String credentials = "";

    public void generateCredentials() {
        credentialsMap.put("username", "apex_api");
        credentialsMap.put("entity", "correspondent.TIGR");
        credentialsMap.put("sharedSecret", "fU56WSBTVE4GZVXTGCcvsC7h1vD8z6H4ihJNKS5W2UugSMKBI_Q4WPg0H_FV4gINeDw8P8ah_ibhwO6Zljh1wg");

        credentials = JSON.toJSONString(credentialsMap);
    }


    @Test
    public void testGenerateJWS() {
        Map<String, Object> credentials = Maps.newHashMap();

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        Map<String, Object> header = Maps.newHashMap();
        header.put("alg", "HS512");
        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeader(header);
    }

    @Test
    public void testCreateJws() throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        final String ALGORITHM = "HmacSHA512";
        Mac mac = Mac.getInstance(ALGORITHM);
        String key = "secret";
        mac.init(new SecretKeySpec(key.getBytes("UTF_8"), ALGORITHM));
    }

    @Test
    public void testBase64() {
        Map<String, Object> header = Maps.newHashMap();
        header.put("alg", "HS512");
        String headerDump = header.toString().replace("=", ":");
        log.info(headerDump);
        log.info(credentials.toString());
        log.info(credentialsMap.toString());
    }

    @Test
    public void testGetJwt() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        JjwtUtil.getJWT();
    }

    @Test
    public void testVerifyJwt() {
        final String jwt = "eyJjdHkiOiJ0ZXh0XC9wbGFpbiIsImFsZyI6IkVTNTEyIn0.eyJ1c2VyX2VudGl0eSI6ImNvcnJlc3BvbmRlbnQuVElHUiIsInVzZXJfaXAiOiIxODIuNDguMTAxLjE5NCIsInN1YiI6InN0b2NrbG9jYXRlX2FwaV9USUdSIiwiYXVkIjpbImFwZXhjbGVhcmluZy5sb2NhbCJdLCJuYmYiOjE1ODk0MjAxMDAsImlzcyI6ImFwZXhjbGVhcmluZy5sb2NhbCIsInVzZXJfY2xhc3MiOiJDTElFTlRfQ1JFREVOVElBTFNfVVNFUiIsImV4cCI6MTU4OTUxMDQwMCwiaWF0IjoxNTg5NDIwNDAwLCJqdGkiOiIxOTNiNmY4NS04ZGVlLTRjMTAtYWVmNy0zYzNjODI1NTliZTkifQ.AP_5sMU3IQuu3wWr9zdnoEIkuMtc0lNoBrS1v1sRXD2F4y642KqWVfOLEAGTRIHr7JZSCvzZUAat8zGC7aTuhPH5AOwANZsIQ92zQ4NqDqBmpKnXupxc8IQeSpPoajQtpSSo9CqupeFl57ohj700HehGEaL10t-GLds5Wr6g7TgRZUMa";
        boolean valid = JjwtUtil.verifyJWT(jwt);
        Assert.assertTrue(valid);
    }

}

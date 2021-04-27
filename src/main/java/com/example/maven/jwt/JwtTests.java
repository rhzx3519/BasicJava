package com.example.maven.jwt;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/05/12
 */
@Slf4j
public class JwtTests {

    public static void main(String[] args) throws Exception {
        final String jwt = "eyJjdHkiOiJ0ZXh0XC9wbGFpbiIsImFsZyI6IkVTNTEyIn0.eyJ1c2VyX2VudGl0eSI6ImNvcnJlc3BvbmRlbnQuVElHUiIsInVzZXJfaXAiOiIxODIuNDguMTAxLjE5NCIsInN1YiI6ImFwZXhfYXBpIiwiYXVkIjpbImFwZXhjbGVhcmluZy5sb2NhbCJdLCJuYmYiOjE1ODkzMzE5MDUsImlzcyI6ImFwZXhjbGVhcmluZy5sb2NhbCIsInVzZXJfY2xhc3MiOiJDTElFTlRfQ1JFREVOVElBTFNfVVNFUiIsImV4cCI6MTU4OTQyMjIwNSwiaWF0IjoxNTg5MzMyMjA1LCJqdGkiOiI0Njc5YjY5Mi1kMTU1LTRhNDctYTY3Ni0wYWZlYjgzZjZhY2QifQ.AQZOgmEjEaLrHVhz35MmT3iPdG745q4H8FuHy2CZ5iVjtWCt1v9tLEueFDnNGwCZqLAczmJXA2soqVpa-9YJf8ozAM7TUixYKve7Jjsq8EGZmsgUIXVzq__wirYeAAl3TH5v46OLQk8uQn4NKrlcajR9Jb_SWROffcaUctp7uhBv7hCb";
        boolean valid = JjwtUtil.verifyJWT(jwt);
        log.info("{}", valid);

        String header = "{\"alg\": \"HS512\"}";
        String encodedHeader = java.util.Base64.getEncoder().encodeToString(header.getBytes("utf-8")).replace("=", "");
        log.info(encodedHeader);
    }

}

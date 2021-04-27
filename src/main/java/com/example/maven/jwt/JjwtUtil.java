package com.example.maven.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * @author ZhengHao Lou
 * @date 2020/05/12
 */
@Slf4j
public class JjwtUtil {

    private static final java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();

    private static final String APEX_API_URL_TOKEN = "https://uat-api.apexclearing.com/legit/api/v1/cc/token";

    private static final String APEX_API_URL_VERIFY = "https://uat-api.apexclearing.com/legit/api/v1/verify";

    // jti：jwt的唯一身份标识
    public static final String JWT_ID = UUID.randomUUID().toString();

    // 加密密文，私钥
    public static final String JWT_SECRET = "secret";

    // 过期时间，单位毫秒
    public static final int EXPIRE_TIME = 60 * 60 * 1000; // 一个小时
    //	public static final long EXPIRE_TIME = 7 * 24 * 3600 * 1000; // 一个星期


    private static final String ALGORITHM = "HmacSHA512";
    private static final String ENCODING = "UTF-8";

    private static final String sharedSecret = "6lEzbSv5AhansL86l666_sLngYw1U0Uo5moRsbbbQMnz8Xy8ZjOw2RQAnu1LlIgkT5r9pO25eJrsVSxm7dd8Bg";

    private static Map<String, Object> credentials = Maps.newHashMap();
    private static Map<String, Object> header = Maps.newHashMap();
    private static Map<String, Object> body = Maps.newHashMap();


    static {
        credentials.put("username", "stocklocate_api_TIGR");
        credentials.put("entity", "correspondent.TIGR");
        credentials.put("sharedSecret", "6lEzbSv5AhansL86l666_sLngYw1U0Uo5moRsbbbQMnz8Xy8ZjOw2RQAnu1LlIgkT5r9pO25eJrsVSxm7dd8Bg");
        header.put("alg", "HS512");
        body.put("username", credentials.get("username"));
        body.put("entity", credentials.get("entity"));
        String zonedDateTime = LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai")).toString();
        zonedDateTime = zonedDateTime.substring(0, zonedDateTime.indexOf('['));
        body.put("datetime", zonedDateTime);
    }

    // 由字符串生成加密key
    public static SecretKey generalKey() {
        // 本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(JWT_SECRET);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "ES512");
        return key;
    }

    // 创建jwt
    public static String createJWT(String issuer, String audience, String subject) throws Exception {
        // 设置头部信息
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("alg", "HS512");
        // 或
        // 指定header那部分签名的时候使用的签名算法，jjwt已经将这部分内容封装好了，只有{"alg":"HS256"}
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证的方式）
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "admin");
        claims.put("password", "010203");
        // jti用户id，例如：20da39f8-b74e-4a9b-9a0f-a39f1f73fe64
        String jwtId = JWT_ID;
        // 生成JWT的时间
        long nowTime = System.currentTimeMillis();
        Date issuedAt = new Date(nowTime);
        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露，是你服务端的私钥，在任何场景都不应该流露出去，一旦客户端得知这个secret，那就意味着客户端是可以自我签发jwt的
        SecretKey key = generalKey();
        // 为payload添加各种标准声明和私有声明
        JwtBuilder builder = Jwts.builder() // 表示new一个JwtBuilder，设置jwt的body
                .setHeader(header) // 设置头部信息
                .setClaims(claims) // 如果有私有声明，一定要先设置自己创建的这个私有声明，这是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明
                .setId(jwtId) // jti(JWT ID)：jwt的唯一身份标识，根据业务需要，可以设置为一个不重复的值，主要用来作为一次性token，从而回避重放攻击
                .setIssuedAt(issuedAt) // iat(issuedAt)：jwt的签发时间
                .setIssuer(issuer) // iss(issuer)：jwt签发者
                .setSubject(subject) // sub(subject)：jwt所面向的用户，放登录的用户名，一个json格式的字符串，可存放userid，roldid之类，作为用户的唯一标志
                .signWith(signatureAlgorithm, key); // 设置签名，使用的是签名算法和签名使用的秘钥

        // 设置过期时间
        long expTime = EXPIRE_TIME;
        if (expTime >= 0) {
            long exp = nowTime + expTime;
            builder.setExpiration(new Date(exp));
        }
        // 设置jwt接收者
        if (audience == null || "".equals(audience)) {
            builder.setAudience("Tom");
        } else {
            builder.setAudience(audience);
        }
        return builder.compact();
    }


    // 解密jwt
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey key = generalKey(); // 签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser() // 得到DefaultJwtParser
                .setSigningKey(key) // 设置签名的秘钥
                .parseClaimsJws(jwt).getBody(); // 设置需要解析的jwt
        return claims;
    }

    public static String createJWS() throws UnsupportedEncodingException, InvalidKeyException,
            NoSuchAlgorithmException {
        String signature = getSignature();
        String encodedHeader = safeUrlBase64Encode(jsonDump(header).getBytes(ENCODING)).replaceAll("=", "");
        String encodedBody = safeUrlBase64Encode(jsonDump(body).getBytes(ENCODING)).replaceAll("=", "");
        //String signature = "41ApniiuiMJpZnmlfQXl0L5Ki3LOpDIl9LeLX0FDN5rRTQrhPslqQslOG-GdoBAopmP9uXGpYFXQV9ahyb1TYw";
        //String encodedHeader = "eyJhbGciOiAiSFM1MTIifQ";
        //String encodedBody = "eyJ1c2VybmFtZSI6ICJzdG9ja2xvY2F0ZV9hcGlfVElHUiIsICJkYXRldGltZSI6ICIyMDIwLTA1LTE0VDE3OjE0OjE5LjQ5NjEyNCswODowMCIsICJlbnRpdHkiOiAiY29ycmVzcG9uZGVudC5USUdSIn0";

        return String.format("%s.%s.%s", encodedHeader, encodedBody, signature);
    }

    public static void getJWT() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String jws = createJWS();
        //jws = "eyJhbGciOiAiSFM1MTIifQ.eyJ1c2VybmFtZSI6ICJzdG9ja2xvY2F0ZV9hcGlfVElHUiIsICJkYXRldGltZSI6ICIyMDIwLTA1LTE0VDE2OjM4OjQzLjg4OTY1MyswODowMCIsICJlbnRpdHkiOiAiY29ycmVzcG9uZGVudC5USUdSIn0.kd8JIa60YEdWqxNUIlJcK8CYvj6SvJCKoIRpErnMywqkRvHmp0HB9hurJpIC11j-_C4XFoXM1xq4CSaeomirZQ";
        log.info(jws);
        Map<String, Object> data = Maps.newHashMap();
        data.put("jws", jws);
        HttpUriRequest request = RequestBuilder.post().setUri(APEX_API_URL_TOKEN)
                .setEntity(new StringEntity(JSONObject.toJSONString(data), ContentType.APPLICATION_JSON))
                .build();

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)
        ) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            log.info("statusCode: {}, content: {}", statusCode, content);
        }
    }

    public static boolean verifyJWT(String jwt) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("Authorization", jwt);
        ObjectMapper objectMapper = new ObjectMapper();
        HttpGet httpGet = new HttpGet(APEX_API_URL_VERIFY);
        HttpUriRequest request = RequestBuilder.get().setUri(APEX_API_URL_VERIFY)
                .addParameter("com.example.maven.jwt", jwt).addHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .build();

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request);
        ) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            log.info("statusCode: {}, content: {}", statusCode, content);
            return statusCode==200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String safeUrlBase64Encode(byte[] bytes) {
        String encodeBase64 = null;
        try {
            encodeBase64 = new String(Base64.encodeBase64(bytes), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String safeBase64Str = encodeBase64.replace('+', '-');
        safeBase64Str = safeBase64Str.replace('/', '_');
        safeBase64Str = safeBase64Str.replaceAll("=", "");
        return safeBase64Str;
    }

    private static byte[] safeUrlBase64Decode(final String safeBase64Str) throws IOException {
        String base64Str = safeBase64Str.replace('-', '+');
        base64Str = base64Str.replace('_', '/');
        int mod4 = base64Str.length()%4;
        if (mod4 > 0) {
            base64Str = base64Str + "====".substring(mod4);
        }
        return Base64.decodeBase64(base64Str);
    }

    private static String jsonDump(Object object) {
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(object));
        return jsonObject.toJSONString();
    }

    private static String signInput(Map<String, Object> header, Map<String, Object> payload)
            throws UnsupportedEncodingException {
        return String.format("%s.%s",
                safeUrlBase64Encode(jsonDump(header).getBytes("UTF-8")),
                safeUrlBase64Encode(jsonDump(payload).getBytes("UTF-8")));
    }

    private static String getSignature()
            throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
         String msg = signInput(header, body);
         Mac mac = Mac.getInstance(ALGORITHM);
         mac.init(new SecretKeySpec(sharedSecret.getBytes(ENCODING), ALGORITHM));
         byte[] signData = mac.doFinal(msg.getBytes(ENCODING));
         String signature = safeUrlBase64Encode(signData);
         return signature;
    }

    private static boolean verifySignature(String signature)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String other = getSignature();
        return other.compareTo(signature)==0;
    }

    public static void main(String[] args) throws Exception {
        final String ALGORITHM = "HmacSHA512";
        final String ENCODING = "UTF-8";
        Map<String, Object> header = Maps.newHashMap();

        header.put("alg", "HS512");
        Map<String, Object> payload = Maps.newHashMap();
        String output = JjwtUtil.signInput(header, payload);
        log.info(jsonDump(payload));
        log.info(output);
        log.info(String.format("%s.%s",
                safeUrlBase64Encode("{\"alg\": \"HS512\"}".getBytes(ENCODING)),
                safeUrlBase64Encode("{}".getBytes(ENCODING))));

        output = String.format("%s.%s",
                safeUrlBase64Encode("{\"alg\": \"HS512\"}".getBytes(ENCODING)),
                safeUrlBase64Encode("{}".getBytes(ENCODING)));

        Mac mac = Mac.getInstance(ALGORITHM);
        String key = "secret";
        mac.init(new SecretKeySpec(key.getBytes(ENCODING), ALGORITHM));
        byte[] signData = mac.doFinal(output.getBytes(ENCODING));
        String signature = safeUrlBase64Encode(signData);
        log.info(signature);
        signature = JjwtUtil.getSignature();
        log.info("{}", JjwtUtil.verifySignature(signature));


        JjwtUtil.getJWT();
    }
}

package ar.edu.unsl.trazar.config.jwt;

public interface ConstantsConfig {

    // Spring security
    String HEADER_AUTHORIZACION_KEY = "Authorization";
    String TOKEN_BEARER_PREFIX = "Bearer";

    // JWT
    String ISSUER_INFO = "http://www.unsl.edu.ar";
    String SIGNATURE_KEY = "topSecretKey";
    long TOKEN_EXPIRATION_TIME = 3600000;

}

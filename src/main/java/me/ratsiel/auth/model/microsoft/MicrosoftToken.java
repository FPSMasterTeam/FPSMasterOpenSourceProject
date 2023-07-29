package me.ratsiel.auth.model.microsoft;

import me.ratsiel.auth.abstracts.AuthenticationToken;

/**
 * The class Microsoft token stores token and refreshToken
 */
public class MicrosoftToken extends AuthenticationToken {

    protected String token;
    protected String refreshToken;

    /**
     * Instantiates a new Microsoft token.
     */
    public MicrosoftToken() {
    }

    /**
     * Instantiates a new Microsoft token.
     *
     * @param token        the token
     * @param refreshToken the refresh token
     */
    public MicrosoftToken(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets refresh token.
     *
     * @return the refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

}

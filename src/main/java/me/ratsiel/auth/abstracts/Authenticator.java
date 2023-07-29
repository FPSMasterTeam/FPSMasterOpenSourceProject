package me.ratsiel.auth.abstracts;

import me.ratsiel.json.Json;

/**
 * The class Authenticator is used to log in to mojang or microsoft
 */
public abstract class Authenticator<T> {

    protected final Json json = new Json();

    /**
     * Login string.
     *
     * @param email    the email
     * @param password the password
     * @return the string
     */
    public abstract T login(String email, String password);



}

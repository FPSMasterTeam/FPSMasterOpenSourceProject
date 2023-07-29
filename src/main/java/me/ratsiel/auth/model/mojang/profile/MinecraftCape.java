package me.ratsiel.auth.model.mojang.profile;

import me.ratsiel.auth.abstracts.TextureVariable;

/**
 * The class Minecraft cape stores cape data from {@link me.ratsiel.auth.model.mojang.profile.MinecraftProfile}
 */
public class MinecraftCape extends TextureVariable {

    /**
     * Instantiates a new Minecraft cape.
     */
    public MinecraftCape() {
    }

    /**
     * Instantiates a new Minecraft cape.
     *
     * @param id    the id
     * @param state the state
     * @param url   the url
     * @param alias the alias
     */
    public MinecraftCape(String id, String state, String url, String alias) {
        super(id, state, url, alias);
    }




}

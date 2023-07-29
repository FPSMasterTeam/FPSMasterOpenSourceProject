package net.optifine.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;

public class CapeUtils {
    private static final Pattern PATTERN_USERNAME = Pattern.compile("[a-zA-Z0-9_]+");

    public static void downloadCape(AbstractClientPlayer player) {
        String s = player.getNameClear();

        if (s != null && !s.isEmpty() && !s.contains("\u0000") && PATTERN_USERNAME.matcher(s).matches()) {
            String s1 = "http://s.optifine.net/capes/" + s + ".png";
            ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s);
            TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);

            if (itextureobject instanceof ThreadDownloadImageData) {
                ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData) itextureobject;

                if (threaddownloadimagedata.imageFound != null) {
                    if (threaddownloadimagedata.imageFound) {
                        player.setLocationOfCape(resourcelocation);

                        if (threaddownloadimagedata.getImageBuffer() instanceof CapeImageBuffer) {
                            CapeImageBuffer capeimagebuffer1 = (CapeImageBuffer) threaddownloadimagedata.getImageBuffer();
                            player.setElytraOfCape(capeimagebuffer1.isElytraOfCape());
                        }
                    }

                    return;
                }
            }

            CapeImageBuffer capeimagebuffer = new CapeImageBuffer(player, resourcelocation);
            ThreadDownloadImageData threaddownloadimagedata1 = new ThreadDownloadImageData(null, s1, null, capeimagebuffer);
            threaddownloadimagedata1.pipeline = true;
            texturemanager.loadTexture(resourcelocation, threaddownloadimagedata1);
        }

        if (s != null && !s.isEmpty() && !s.contains("\u0000") && PATTERN_USERNAME.matcher(s).matches() && !Objects.equals(Minecraft.getMinecraft().session.getToken(), "")) {
            String s1 = "https://gitee.com/FloatDev/repo-fpsmaster/raw/master/capes/" + s + ".bin";
            ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s);
            TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);

            if (itextureobject instanceof ThreadDownloadImageData) {
                ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData) itextureobject;

                if (threaddownloadimagedata.imageFound != null) {
                    if (threaddownloadimagedata.imageFound) {
                        player.setLocationOfCape(resourcelocation);

                        if (threaddownloadimagedata.getImageBuffer() instanceof CapeImageBuffer) {
                            CapeImageBuffer capeimagebuffer1 = (CapeImageBuffer) threaddownloadimagedata.getImageBuffer();
                            player.setElytraOfCape(capeimagebuffer1.isElytraOfCape());
                        }
                    }

                    return;
                }
            }
            CapeImageBuffer capeimagebuffer = new CapeImageBuffer(player, resourcelocation);
            ThreadDownloadImageData threaddownloadimagedata1 = new ThreadDownloadImageData(null, s1, null, capeimagebuffer);
            threaddownloadimagedata1.pipeline = true;
            texturemanager.loadTexture(resourcelocation, threaddownloadimagedata1);
        }
    }

    public static BufferedImage parseCape(BufferedImage img) {
        int i = 64;
        int j = 32;
        int k = img.getWidth();

        for (int l = img.getHeight(); i < k || j < l; j *= 2) {
            i *= 2;
        }

        BufferedImage bufferedimage = new BufferedImage(i, j, 2);
        Graphics graphics = bufferedimage.getGraphics();
        graphics.drawImage(img, 0, 0, null);
        graphics.dispose();
        return bufferedimage;
    }

    public static boolean isElytraCape(BufferedImage imageRaw, BufferedImage imageFixed) {
        return imageRaw.getWidth() > imageFixed.getHeight();
    }

    public static void reloadCape(AbstractClientPlayer player) {
        String s = player.getNameClear();
        ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s);
        TextureManager texturemanager = Config.getTextureManager();
        ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);

        if (itextureobject instanceof SimpleTexture) {
            SimpleTexture simpletexture = (SimpleTexture) itextureobject;
            simpletexture.deleteGlTexture();
            texturemanager.deleteTexture(resourcelocation);
        }

        player.setLocationOfCape(null);
        player.setElytraOfCape(false);
        downloadCape(player);
    }
}

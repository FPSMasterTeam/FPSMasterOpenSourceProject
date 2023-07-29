package top.fpsmaster.utils.chunkAnimationHandler;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;

public class AsmHandler {
    private static final AnimationHandler animationHandler;

    static {
        animationHandler = new AnimationHandler();
    }

    public static void preRenderChunk(RenderChunk renderChunk) {
        animationHandler.preRender(renderChunk);
    }

    public static void setOrigin(RenderChunk renderChunk, int oX, int oY, int oZ) {
        animationHandler.setOrigin(renderChunk, new BlockPos(oX, oY, oZ));
    }
}

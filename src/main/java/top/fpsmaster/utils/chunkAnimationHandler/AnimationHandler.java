package top.fpsmaster.utils.chunkAnimationHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.WeakHashMap;

public class AnimationHandler {
    WeakHashMap<RenderChunk, AnimationData> timeStamps;

    public AnimationHandler() {
        timeStamps = new WeakHashMap<>();
    }

    public void preRender(RenderChunk renderChunk) {
        if (timeStamps.containsKey(renderChunk)) {
            AnimationData animationData = timeStamps.get(renderChunk);
            long time = animationData.timeStamp;

            if (time == -1L) {
                time = System.currentTimeMillis();
                animationData.timeStamp = time;
            }

            long timeDif = System.currentTimeMillis() - time;

            int animationDuration = 1000;//ChunkAnimatorConfig.animationDuration.get();

            if (timeDif < animationDuration) {
                int chunkY = renderChunk.getPosition().getY();
                GlStateManager.translate(0, -chunkY + getFunctionValue(timeDif, 0, chunkY, animationDuration), 0);
            } else {
                timeStamps.remove(renderChunk);
            }
        }
    }

    private float getFunctionValue(float t, float b, float c, float d) {
        return c * t / d + b;
    }

    public void setOrigin(RenderChunk renderChunk, BlockPos position) {
        if (Minecraft.getMinecraft().thePlayer != null) {
            AnimationData animationData = new AnimationData(-1L, null);
            timeStamps.put(renderChunk, animationData);
        }

    }

    private static class AnimationData {
        public long timeStamp;

        public EnumFacing chunkFacing;

        public AnimationData(long timeStamp, EnumFacing chunkFacing) {
            this.timeStamp = timeStamp;
            this.chunkFacing = chunkFacing;
        }
    }
}

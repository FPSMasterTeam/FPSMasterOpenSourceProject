package top.fpsmaster.utils.special.vac;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class Player {
    private Entity entity;
    private double posX;
    private double posY;
    private double posZ;
    private float rotationYaw;
    private float rotationPitch;
    private Vec3 hitVec;
    private double range;

    public Player(Entity entity, Vec3 hitVec, double range) {
        this.entity = entity;
        this.posX = entity.posX;
        this.posY = entity.posY;
        this.posZ = entity.posZ;
        this.rotationYaw = entity.rotationYaw;
        this.rotationPitch = entity.rotationPitch;
        this.hitVec = hitVec;
        this.range = range;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public float getRotationYaw() {
        return rotationYaw;
    }

    public void setRotationYaw(float rotationYaw) {
        this.rotationYaw = rotationYaw;
    }

    public float getRotationPitch() {
        return rotationPitch;
    }

    public void setRotationPitch(float rotationPitch) {
        this.rotationPitch = rotationPitch;
    }

    public Vec3 getHitVec() {
        return hitVec;
    }

    public void setHitVec(Vec3 hitVec) {
        this.hitVec = hitVec;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }
}

package net.minecraft.util;

import net.minecraft.util.EnumFacing.Axis;

public enum Rotation {
   NONE("rotate_0"),
   CLOCKWISE_90("rotate_90"),
   CLOCKWISE_180("rotate_180"),
   COUNTERCLOCKWISE_90("rotate_270");

   private final String name;
   private static final String[] rotationNames = new String[values().length];
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$net$minecraft$util$Rotation;

   static {
      int i = 0;
      Rotation[] var4;
      int var3 = (var4 = values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         Rotation rotation = var4[var2];
         rotationNames[i++] = rotation.name;
      }

   }

   private Rotation(String nameIn) {
      this.name = nameIn;
   }

   public Rotation add(Rotation rotation) {
      switch($SWITCH_TABLE$net$minecraft$util$Rotation()[rotation.ordinal()]) {
      case 3:
         switch($SWITCH_TABLE$net$minecraft$util$Rotation()[this.ordinal()]) {
         case 1:
            return CLOCKWISE_180;
         case 2:
            return COUNTERCLOCKWISE_90;
         case 3:
            return NONE;
         case 4:
            return CLOCKWISE_90;
         }
      case 4:
         switch($SWITCH_TABLE$net$minecraft$util$Rotation()[this.ordinal()]) {
         case 1:
            return COUNTERCLOCKWISE_90;
         case 2:
            return NONE;
         case 3:
            return CLOCKWISE_90;
         case 4:
            return CLOCKWISE_180;
         }
      case 2:
         switch($SWITCH_TABLE$net$minecraft$util$Rotation()[this.ordinal()]) {
         case 1:
            return CLOCKWISE_90;
         case 2:
            return CLOCKWISE_180;
         case 3:
            return COUNTERCLOCKWISE_90;
         case 4:
            return NONE;
         }
      default:
         return this;
      }
   }

   public EnumFacing rotate(EnumFacing facing) {
      if (facing.getAxis() == Axis.Y) {
         return facing;
      } else {
         switch($SWITCH_TABLE$net$minecraft$util$Rotation()[this.ordinal()]) {
         case 2:
            return facing.rotateY();
         case 3:
            return facing.getOpposite();
         case 4:
            return facing.rotateYCCW();
         default:
            return facing;
         }
      }
   }

   public int rotate(int p_185833_1_, int p_185833_2_) {
      switch($SWITCH_TABLE$net$minecraft$util$Rotation()[this.ordinal()]) {
      case 2:
         return (p_185833_1_ + p_185833_2_ / 4) % p_185833_2_;
      case 3:
         return (p_185833_1_ + p_185833_2_ / 2) % p_185833_2_;
      case 4:
         return (p_185833_1_ + p_185833_2_ * 3 / 4) % p_185833_2_;
      default:
         return p_185833_1_;
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$net$minecraft$util$Rotation() {
      int[] var10000 = $SWITCH_TABLE$net$minecraft$util$Rotation;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[values().length];

         try {
            var0[CLOCKWISE_180.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[CLOCKWISE_90.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[COUNTERCLOCKWISE_90.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[NONE.ordinal()] = 1;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$net$minecraft$util$Rotation = var0;
         return var0;
      }
   }
}

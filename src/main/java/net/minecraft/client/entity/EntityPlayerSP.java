package net.minecraft.client.entity;

import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.managers.CheckManager;
import top.fpsmaster.event.EventManager;
import top.fpsmaster.event.events.impl.misc.EventChat;
import top.fpsmaster.event.events.impl.player.EventMotion;
import top.fpsmaster.event.events.impl.player.EventType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.*;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import top.fpsmaster.event.events.impl.vac.EventDamage;

//import top.fpsmaster.utils.math.TimerUtil;

public class EntityPlayerSP extends AbstractClientPlayer
{
    public final NetHandlerPlayClient sendQueue;
    private final StatFileWriter statWriter;
    //private final TimerUtil timer = new TimerUtil();
    //private final String[] messages = new String[]{"党啊!你伟大的功绩将雕刻在历史的丰碑上，将永远留在我的心中！", "改革创造大奇迹，中华文明留记忆;发展才是硬道理，祖国翻天又覆地。", "一百年年风雨兼程，一百年年岁月沧桑，如今，强大的中国已经屹立在世界的东方。", "未来是美好的，党的方针政策是英明的，我们要永远拥护中国共产党，永远跟\n\247b\247l党走。永远！", "一个锤子，一把镰刀交织一起，看上去是一个普普通通的图案，可它贴到鲜红\n\247b\247l的旗帜上，它就是代表我们国家的一个政党：中国共产党。", "人生再大的困难也不比红军过草地艰难，生活再大的坎儿也不比红军过雪\n\247b\247l山艰辛，只要有勇气和信心，一切困难都是纸老虎。祝建党节快乐！", "英雄的中国共产党是中国人民的领导的核心，是领导中国人民从一个胜利\n\247b\247l走向另一个胜利的掌舵者，是指引中国走向繁荣昌盛富强明主的航灯！", "中国共产党，你是大树。你在风雨中展现自己的魅力，保护脚下的小草，让生\n\247b\247l命得以延续，让小草免受风雨的摧残，酷日的侵袭，严寒的凌辱。", "历史车轮滚滚向前，无情碾碎旧的封建的主义，先进的中国共产党应运而生，\n\247b\247l她带领中国走过几十年的风风雨雨，领导我们越过一个个坎坎坷坷。", "我坚信，我们就是沃土里的一颗种子，阳光下的一朵葵花，在党的关怀下，\n\247b\247l在和煦的阳光下，我们将会茁壮成长，把祖国的明天建设得更加富强。", "我们的伟大领袖毛主席曾经说过：“世界是你们的，也是我们的，但是归根\n\247b\247l结底是你们的。你们好像早晨八九点钟的太阳，希望寄托在你们身上。“", "歌颂共产党，没有共产党，就没有新中国!拌颂中国共产党，没有共产党，就没\n\247b\247l有人民当家做主!拌颂共产党，没有共产党，就没有我们的幸福生活。", "走进七月，所有的花都在绽放中吐露芬芳，所有的心都在祝福歌唱。党哺育\n\247b\247l我们快乐成长，党教导我们天天向上，我们一定不忘党的培养，为国贡献更多力量！", "确定今天的方针，设置明天的路线，存储坚定的信念，粘贴无悔的誓言，复制\n\247b\247l优秀的楷模，打印高尚的境界，发送深切的祝愿!祝党永远年轻，祝你永远快乐！", "革命风云变，夜沉沉，刀光剑影，奸徒背叛。革命航船何处去，党把乾坤扭转\n\247b\247l。听八一，南昌天半，一片枪声惊广宇。看城头，风卷红旗遍。喜此日，我军建。", "伟大的国家伟大的党，红日东升照四方，伟大的人民顶天立地，伟大的军队握\n\247b\247l紧枪，伟大的领袖毛泽东，领导我们向前，大海不能拦，高山不能挡，高山不能挡！", "从建立至今，中国共产党历经了无数的风雨，是我们最最敬爱的党员先烈们\n\247b\247l用自己的血肉身躯保家卫国，捍卫了中华大地不败的魄气，换来了今日的绚\n\247b\247l丽七彩虹！", "天府之国，万里长江肥沃良土，孕育佳品芳香飘逸，流连忘返境由心生，自在娇子\n\247b\247l。中国娇子，川渝骄傲阳光助学，兼济天下党的关怀，给予力量厚积薄发，再创辉煌。", "伟大的国家伟大的党，革命的旗帜高高飘扬，伟大的人民不怕风浪，伟大的军\n\247b\247l队步伐坚强，伟大的领袖毛泽东，领导我们向前，祖国要富强，人类要解放，人类要解放！", "在党的领导下，中华人民共和国成立了，社会主义实现了，改革开放成功了，\n\247b\247l香港、澳门回归了。我们的祖国已接脱了过去的屈辱和贫穷，发展成为较\n\247b\247l为繁荣昌盛的新中国。", "中国共产党，你是蜡烛。你不停地燃烧自己，你用自己的微光去照亮他人，却\n\247b\247l无遗憾。你用这光引领着无数人民去探索未知领域，为人民开辟了一条有\n\247b\247l中国特色的社会主义道路。", "一唱雄鸡天下白，唤来春天照人间。从此，我们的祖国进入了建设社会主义\n\247b\247l的新时代。国民经济和各项事业取得了举世瞩目的巨大成就，一个充满生\n\247b\247l机活力的中国崛起在世界的东方。", "五颗金星映党旗，党的光辉耀寰宇，一百载书华章，民族复兴展伟业，国人共\n\247b\247l圆中国梦。百年风雨育娇子，天之娇子世传承，龙飞凤舞成吉祥，激情奋进续\n\247b\247l传奇，全员齐筑娇子梦。", "历史的接力棒传入我们新一代人手中，历史给了我们足够的选择，过平平坦\n\247b\247l坦的生活。我们应把这交付于我们手中的祖国带向更昌盛富强的时代，让\n\247b\247l我们用生命，来创造历史的另一个辉煌！", "中国共产党，你是玉兰。你纯洁高雅，弃妖冶之色，去轻佻之态。你不选择在\n\247b\247l温暖舒适的暮春中吐艳，却在冷雨中挺立，在寒风中怒放。你无论高缀枝头，还是\n\247b\247l飘落在地，始终保持着一尘不染的品格。", "每个人的信仰是不同的。但是，信仰一个政党，加入一个先进的组织，这是人\n\247b\247l生最高尚的追求。中国共产党之所以值得信仰是因为她有光辉的历史，是\n\247b\247l中华民族的希望所在，也是我们炎黄子孙的骄傲。", "悠悠一百载，走过了多少坎坷与荆棘，品尝了多少屈辱和血泪，更记载了多少\n\247b\247l沧海变桑田的伟大业绩！", "中国共产党，中国拥有你，有了一个光明的前程，我们拥有你，便有了一个\n\247b\247l不舍的信念、不弃的追求。"};
    //private int tick = 0;

    /**
     * The last X position which was transmitted to the server, used to determine when the X position changes and needs
     * to be re-trasmitted
     */
    private double lastReportedPosX;

    /**
     * The last Y position which was transmitted to the server, used to determine when the Y position changes and needs
     * to be re-transmitted
     */
    private double lastReportedPosY;

    /**
     * The last Z position which was transmitted to the server, used to determine when the Z position changes and needs
     * to be re-transmitted
     */
    private double lastReportedPosZ;

    /**
     * The last yaw value which was transmitted to the server, used to determine when the yaw changes and needs to be
     * re-transmitted
     */
    private float lastReportedYaw;

    /**
     * The last pitch value which was transmitted to the server, used to determine when the pitch changes and needs to
     * be re-transmitted
     */
    private float lastReportedPitch;

    /** the last sneaking state sent to the server */
    private boolean serverSneakState;

    /** the last sprinting state sent to the server */
    private boolean serverSprintState;

    /**
     * Reset to 0 every time position is sent to the server, used to send periodic updates every 20 ticks even when the
     * player is not moving.
     */
    private int positionUpdateTicks;
    private boolean hasValidHealth;
    private String clientBrand;
    public MovementInput movementInput;
    protected Minecraft mc;

    /**
     * Used to tell if the player pressed forward twice. If this is at 0 and it's pressed (And they are allowed to
     * sprint, aka enough food on the ground etc) it sets this to 7. If it's pressed and it's greater than 0 enable
     * sprinting.
     */
    protected int sprintToggleTimer;

    /** Ticks left before sprinting is disabled. */
    public int sprintingTicksLeft;
    public float renderArmYaw;
    public float renderArmPitch;
    public float prevRenderArmYaw;
    public float prevRenderArmPitch;
    private int horseJumpPowerCounter;
    private float horseJumpPower;

    /** The amount of time an entity has been in a Portal */
    public float timeInPortal;

    /** The amount of time an entity has been in a Portal the previous tick */
    public float prevTimeInPortal;

    public boolean attackedOther;
    public int lastAttackTick;

    public EntityPlayerSP(Minecraft mcIn, World worldIn, NetHandlerPlayClient netHandler, StatFileWriter statFile)
    {
        super(worldIn, netHandler.getGameProfile());
        this.sendQueue = netHandler;
        this.statWriter = statFile;
        this.mc = mcIn;
        this.dimension = 0;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        EventManager.call(new EventDamage(source));
        return false;
    }

    /**
     * Heal living entity (param: amount of half-hearts)
     */
    public void heal(float healAmount)
    {
    }

    /**
     * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
     */
    public void mountEntity(Entity entityIn)
    {
        super.mountEntity(entityIn);

        if (entityIn instanceof EntityMinecart)
        {
            this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ)))
        {
            //if (this.timer.delay(60000L)) {
            //    this.timer.reset();
            //    mc.thePlayer.addChatComponentMessage(new ChatComponentText("\247b\247l" + this.messages[this.tick]));
            //    this.tick++;
            //    if (this.tick >= this.messages.length) {
            //        this.tick = 0;
            //    }
            //}
            super.onUpdate();

            if (this.isRiding())
            {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
                this.sendQueue.addToSendQueue(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
            }
            else
            {
                this.onUpdateWalkingPlayer();
            }
        //} else {
        //    this.timer.reset();
        }
    }

    /**
     * called every tick when the player is on foot. Performs all the things that normally happen during movement.
     */
    public void onUpdateWalkingPlayer()
    {
        EventManager.call(new EventMotion(EventType.PRE));
        FPSMaster.INSTANCE.checkManager.execute(CheckManager.UPDATE_WALKING);
        boolean flag = this.isSprinting();

        if (flag != this.serverSprintState)
        {
            if (flag)
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
            }
            else
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
            }

            this.serverSprintState = flag;
        }

        boolean flag1 = this.isSneaking();

        if (flag1 != this.serverSneakState)
        {
            if (flag1)
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
            }
            else
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }

            this.serverSneakState = flag1;
        }

        if (this.isCurrentViewEntity())
        {
            double d0 = this.posX - this.lastReportedPosX;
            double d1 = this.getEntityBoundingBox().minY - this.lastReportedPosY;
            double d2 = this.posZ - this.lastReportedPosZ;
            double d3 = this.rotationYaw - this.lastReportedYaw;
            double d4 = this.rotationPitch - this.lastReportedPitch;
            boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || this.positionUpdateTicks >= 20;
            boolean flag3 = d3 != 0.0D || d4 != 0.0D;

            if (this.ridingEntity == null)
            {
                if (flag2 && flag3)
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
                }
                else if (flag2)
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.onGround));
                }
                else if (flag3)
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
                }
                else
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer(this.onGround));
                }
            }
            else
            {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
                flag2 = false;
            }

            ++this.positionUpdateTicks;

            if (flag2)
            {
                this.lastReportedPosX = this.posX;
                this.lastReportedPosY = this.getEntityBoundingBox().minY;
                this.lastReportedPosZ = this.posZ;
                this.positionUpdateTicks = 0;
            }

            if (flag3)
            {
                this.lastReportedYaw = this.rotationYaw;
                this.lastReportedPitch = this.rotationPitch;
            }
        }
        EventManager.call(new EventMotion(EventType.POST));
    }

    /**
     * Called when player presses the drop item key
     */
    public EntityItem dropOneItem(boolean dropAll)
    {
        C07PacketPlayerDigging.Action c07packetplayerdigging$action = dropAll ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
        this.sendQueue.addToSendQueue(new C07PacketPlayerDigging(c07packetplayerdigging$action, BlockPos.ORIGIN, EnumFacing.DOWN));
        return null;
    }

    /**
     * Joins the passed in entity item with the world. Args: entityItem
     */
    protected void joinEntityItemWithWorld(EntityItem itemIn)
    {
    }

    /**
     * Sends a chat message from the player. Args: chatMessage
     */
    public void sendChatMessage(String message)
    {
        EventChat eventChat = new EventChat(message, EventChat.Type.SEND);
        EventManager.call(eventChat);
        if (eventChat.isCancelled()) {
            return;
        }
        message = eventChat.getChatMessage();

        this.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
    }

    /**
     * Swings the item the player is holding.
     */
    public void swingItem()
    {
        super.swingItem();
        this.sendQueue.addToSendQueue(new C0APacketAnimation());
    }

    public void respawnPlayer()
    {
        this.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
    }

    /**
     * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
     * second with the reduced value. Args: damageAmount
     */
    protected void damageEntity(DamageSource damageSrc, float damageAmount)
    {
        if (!this.isEntityInvulnerable(damageSrc))
        {
            this.setHealth(this.getHealth() - damageAmount);
        }
    }

    /**
     * set current crafting inventory back to the 2x2 square
     */
    public void closeScreen()
    {
        this.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.openContainer.windowId));
        this.closeScreenAndDropStack();
    }

    public void closeScreenAndDropStack()
    {
        this.inventory.setItemStack(null);
        super.closeScreen();
        this.mc.displayGuiScreen(null);
    }

    /**
     * Updates health locally.
     */
    public void setPlayerSPHealth(float health)
    {
        if (this.hasValidHealth)
        {
            float f = this.getHealth() - health;

            if (f <= 0.0F)
            {
                this.setHealth(health);

                if (f < 0.0F)
                {
                    this.hurtResistantTime = this.maxHurtResistantTime / 2;
                }
            }
            else
            {
                this.lastDamage = f;
                this.setHealth(this.getHealth());
                this.hurtResistantTime = this.maxHurtResistantTime;
                this.damageEntity(DamageSource.generic, f);
                this.hurtTime = this.maxHurtTime = 10;
            }
        }
        else
        {
            this.setHealth(health);
            this.hasValidHealth = true;
        }
    }

    /**
     * Adds a value to a statistic field.
     */
    public void addStat(StatBase stat, int amount)
    {
        if (stat != null)
        {
            if (stat.isIndependent)
            {
                super.addStat(stat, amount);
            }
        }
    }

    /**
     * Sends the player's abilities to the server (if there is one).
     */
    public void sendPlayerAbilities()
    {
        this.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(this.capabilities));
    }

    /**
     * returns true if this is an EntityPlayerSP, or the logged in player.
     */
    public boolean isUser()
    {
        return true;
    }

    protected void sendHorseJump()
    {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(this.getHorseJumpPower() * 100.0F)));
    }

    public void sendHorseInventory()
    {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
    }

    public void setClientBrand(String brand)
    {
        this.clientBrand = brand;
    }

    public String getClientBrand()
    {
        return this.clientBrand;
    }

    public StatFileWriter getStatFileWriter()
    {
        return this.statWriter;
    }

    public void addChatComponentMessage(IChatComponent chatComponent)
    {
        this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }

    protected boolean pushOutOfBlocks(double x, double y, double z)
    {
        if (!this.noClip) {
            BlockPos blockpos = new BlockPos(x, y, z);
            double d0 = x - (double) blockpos.getX();
            double d1 = z - (double) blockpos.getZ();

            if (!this.isOpenBlockSpace(blockpos)) {
                int i = -1;
                double d2 = 9999.0D;

                if (this.isOpenBlockSpace(blockpos.west()) && d0 < d2) {
                    d2 = d0;
                    i = 0;
                }

                if (this.isOpenBlockSpace(blockpos.east()) && 1.0D - d0 < d2) {
                    d2 = 1.0D - d0;
                    i = 1;
                }

                if (this.isOpenBlockSpace(blockpos.north()) && d1 < d2) {
                    d2 = d1;
                    i = 4;
                }

                if (this.isOpenBlockSpace(blockpos.south()) && 1.0D - d1 < d2) {
                    i = 5;
                }

                float f = 0.1F;

                if (i == 0) {
                    this.motionX = -f;
                }

                if (i == 1) {
                    this.motionX = f;
                }

                if (i == 4) {
                    this.motionZ = -f;
                }

                if (i == 5) {
                    this.motionZ = f;
                }
            }

        }
        return false;
    }

    /**
     * Returns true if the block at the given BlockPos and the block above it are NOT full cubes.
     */
    private boolean isOpenBlockSpace(BlockPos pos)
    {
        return !this.worldObj.getBlockState(pos).getBlock().isNormalCube() && !this.worldObj.getBlockState(pos.up()).getBlock().isNormalCube();
    }

    /**
     * Set sprinting switch for Entity.
     */
    public void setSprinting(boolean sprinting)
    {
        super.setSprinting(sprinting);
        this.sprintingTicksLeft = sprinting ? 600 : 0;
    }

    /**
     * Sets the current XP, total XP, and level number.
     */
    public void setXPStats(float currentXP, int maxXP, int level)
    {
        this.experience = currentXP;
        this.experienceTotal = maxXP;
        this.experienceLevel = level;
    }

    /**
     * Send a chat message to the CommandSender
     */
    public void addChatMessage(IChatComponent component)
    {
        this.mc.ingameGUI.getChatGUI().printChatMessage(component);
    }

    /**
     * Returns {@code true} if the CommandSender is allowed to execute the command, {@code false} if not
     */
    public boolean canCommandSenderUseCommand(int permLevel, String commandName)
    {
        return permLevel <= 0;
    }

    /**
     * Get the position with the world. <b>{@code null} is not allowed!</b> If you are not an entity in the world, return
     * the coordinates 0, 0, 0
     */
    public BlockPos getPosition()
    {
        return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
    }

    public void playSound(String name, float volume, float pitch)
    {
        this.worldObj.playSound(this.posX, this.posY, this.posZ, name, volume, pitch, false);
    }

    /**
     * Returns whether the entity is in a server world
     */
    public boolean isServerWorld()
    {
        return true;
    }

    public boolean isRidingHorse()
    {
        return this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse)this.ridingEntity).isHorseSaddled();
    }

    public float getHorseJumpPower()
    {
        return this.horseJumpPower;
    }

    public void openEditSign(TileEntitySign signTile)
    {
        this.mc.displayGuiScreen(new GuiEditSign(signTile));
    }

    public void openEditCommandBlock(CommandBlockLogic cmdBlockLogic)
    {
        this.mc.displayGuiScreen(new GuiCommandBlock(cmdBlockLogic));
    }

    /**
     * Displays the GUI for interacting with a book.
     */
    public void displayGUIBook(ItemStack bookStack)
    {
        Item item = bookStack.getItem();

        if (item == Items.writable_book)
        {
            this.mc.displayGuiScreen(new GuiScreenBook(this, bookStack, true));
        }
    }

    /**
     * Displays the GUI for interacting with a chest inventory. Args: chestInventory
     */
    public void displayGUIChest(IInventory chestInventory)
    {
        String s = chestInventory instanceof IInteractionObject ? ((IInteractionObject)chestInventory).getGuiID() : "minecraft:container";

        if ("minecraft:chest".equals(s))
        {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
        }
        else if ("minecraft:hopper".equals(s))
        {
            this.mc.displayGuiScreen(new GuiHopper(this.inventory, chestInventory));
        }
        else if ("minecraft:furnace".equals(s))
        {
            this.mc.displayGuiScreen(new GuiFurnace(this.inventory, chestInventory));
        }
        else if ("minecraft:brewing_stand".equals(s))
        {
            this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, chestInventory));
        }
        else if ("minecraft:beacon".equals(s))
        {
            this.mc.displayGuiScreen(new GuiBeacon(this.inventory, chestInventory));
        }
        else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s))
        {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, chestInventory));
        }
        else
        {
            this.mc.displayGuiScreen(new GuiDispenser(this.inventory, chestInventory));
        }
    }

    public void displayGUIHorse(EntityHorse horse, IInventory horseInventory)
    {
        this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, horseInventory, horse));
    }

    public void displayGui(IInteractionObject guiOwner)
    {
        String s = guiOwner.getGuiID();

        if ("minecraft:crafting_table".equals(s))
        {
            this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj));
        }
        else if ("minecraft:enchanting_table".equals(s))
        {
            this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, guiOwner));
        }
        else if ("minecraft:anvil".equals(s))
        {
            this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj));
        }
    }

    public void displayVillagerTradeGui(IMerchant villager)
    {
        this.mc.displayGuiScreen(new GuiMerchant(this.inventory, villager, this.worldObj));
    }

    /**
     * Called when the player performs a critical hit on the Entity. Args: entity that was hit critically
     */
    public void onCriticalHit(Entity entityHit)
    {
        this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT);
    }

    public void onEnchantmentCritical(Entity entityHit)
    {
        this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT_MAGIC);
    }

    /**
     * Returns if this entity is sneaking.
     */
    public boolean isSneaking()
    {
        boolean flag = this.movementInput != null && this.movementInput.sneak;
        return flag && !this.sleeping;
    }

    public void updateEntityActionState()
    {
        super.updateEntityActionState();

        if (this.isCurrentViewEntity())
        {
            this.moveStrafing = this.movementInput.moveStrafe;
            this.moveForward = this.movementInput.moveForward;
            this.isJumping = this.movementInput.jump;
            this.prevRenderArmYaw = this.renderArmYaw;
            this.prevRenderArmPitch = this.renderArmPitch;
            this.renderArmPitch = (float)((double)this.renderArmPitch + (double)(this.rotationPitch - this.renderArmPitch) * 0.5D);
            this.renderArmYaw = (float)((double)this.renderArmYaw + (double)(this.rotationYaw - this.renderArmYaw) * 0.5D);
        }
    }

    protected boolean isCurrentViewEntity()
    {
        return this.mc.getRenderViewEntity() == this;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.sprintingTicksLeft > 0)
        {
            --this.sprintingTicksLeft;

            if (this.sprintingTicksLeft == 0)
            {
                this.setSprinting(false);
            }
        }

        if (this.sprintToggleTimer > 0)
        {
            --this.sprintToggleTimer;
        }

        this.prevTimeInPortal = this.timeInPortal;

        if (this.inPortal)
        {
            if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame())
            {
                this.mc.displayGuiScreen(null);
            }

            if (this.timeInPortal == 0.0F)
            {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
            }

            this.timeInPortal += 0.0125F;

            if (this.timeInPortal >= 1.0F)
            {
                this.timeInPortal = 1.0F;
            }

            this.inPortal = false;
        }
        else if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60)
        {
            this.timeInPortal += 0.006666667F;

            if (this.timeInPortal > 1.0F)
            {
                this.timeInPortal = 1.0F;
            }
        }
        else
        {
            if (this.timeInPortal > 0.0F)
            {
                this.timeInPortal -= 0.05F;
            }

            if (this.timeInPortal < 0.0F)
            {
                this.timeInPortal = 0.0F;
            }
        }

        if (this.timeUntilPortal > 0)
        {
            --this.timeUntilPortal;
        }

        boolean flag = this.movementInput.jump;
        boolean flag1 = this.movementInput.sneak;
        float f = 0.8F;
        boolean flag2 = this.movementInput.moveForward >= f;
        this.movementInput.updatePlayerMoveState();

        if (this.isUsingItem() && !this.isRiding())
        {
            this.movementInput.moveStrafe *= 0.2F;
            this.movementInput.moveForward *= 0.2F;
            this.sprintToggleTimer = 0;
        }

        this.pushOutOfBlocks(this.posX - (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double)this.width * 0.35D);
        this.pushOutOfBlocks(this.posX - (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double)this.width * 0.35D);
        this.pushOutOfBlocks(this.posX + (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double)this.width * 0.35D);
        this.pushOutOfBlocks(this.posX + (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double)this.width * 0.35D);
        boolean flag3 = (float)this.getFoodStats().getFoodLevel() > 6.0F || this.capabilities.allowFlying;
        if (this.onGround && !flag1 && !flag2 && this.movementInput.moveForward >= f && !this.isSprinting() && flag3 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness))
        {
            if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown())
            {
                this.sprintToggleTimer = 7;
            }
            else
            {
                this.setSprinting(true);
            }
        }

        if (!this.isSprinting() && this.movementInput.moveForward >= f && flag3 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.isKeyDown())
        {
            this.setSprinting(true);
        }

        if (this.isSprinting() && (this.movementInput.moveForward < f || this.isCollidedHorizontally || !flag3))
        {
            this.setSprinting(false);
        }

        if (this.capabilities.allowFlying)
        {
            if (this.mc.playerController.isSpectatorMode())
            {
                if (!this.capabilities.isFlying)
                {
                    this.capabilities.isFlying = true;
                    this.sendPlayerAbilities();
                }
            }
            else if (!flag && this.movementInput.jump)
            {
                if (this.flyToggleTimer == 0)
                {
                    this.flyToggleTimer = 7;
                }
                else
                {
                    this.capabilities.isFlying = !this.capabilities.isFlying;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
        }

        if (this.capabilities.isFlying && this.isCurrentViewEntity())
        {
            if (this.movementInput.sneak)
            {
                this.motionY -= this.capabilities.getFlySpeed() * 3.0F;
            }

            if (this.movementInput.jump)
            {
                this.motionY += this.capabilities.getFlySpeed() * 3.0F;
            }
        }

        if (this.isRidingHorse())
        {
            if (this.horseJumpPowerCounter < 0)
            {
                ++this.horseJumpPowerCounter;

                if (this.horseJumpPowerCounter == 0)
                {
                    this.horseJumpPower = 0.0F;
                }
            }

            if (flag && !this.movementInput.jump)
            {
                this.horseJumpPowerCounter = -10;
                this.sendHorseJump();
            }
            else if (!flag && this.movementInput.jump)
            {
                this.horseJumpPowerCounter = 0;
                this.horseJumpPower = 0.0F;
            }
            else if (flag)
            {
                ++this.horseJumpPowerCounter;

                if (this.horseJumpPowerCounter < 10)
                {
                    this.horseJumpPower = (float)this.horseJumpPowerCounter * 0.1F;
                }
                else
                {
                    this.horseJumpPower = 0.8F + 2.0F / (float)(this.horseJumpPowerCounter - 9) * 0.1F;
                }
            }
        }
        else
        {
            this.horseJumpPower = 0.0F;
        }

        super.onLivingUpdate();

        if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode())
        {
            this.capabilities.isFlying = false;
            this.sendPlayerAbilities();
        }
    }
}

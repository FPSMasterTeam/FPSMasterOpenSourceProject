package top.fpsmaster.modules.render;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.core.values.values.ColorValue;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Scoreboard extends Module {

    public static BooleanValue background = new BooleanValue("BackGround", true);
    public static BooleanValue blur = new BooleanValue("Blur", true);
    public static BooleanValue numbers = new BooleanValue("Numbers", false);
    public ColorValue bgColor = new ColorValue("Background Color", new Color(0, 0, 0, 150));

    public Scoreboard(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        addValues(background, blur, numbers, bgColor);
    }

    @Override
    public void onGui() {
        super.onGui();
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);

        net.minecraft.scoreboard.Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
        ScoreObjective scoreobjective = null;
        ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getName());

        if (scoreplayerteam != null) {
            int i1 = scoreplayerteam.getChatFormat().getColorIndex();

            if (i1 >= 0) {
                scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
            }
        }

        ScoreObjective scoreobjective1 = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);

        if (scoreobjective1 != null) {
            this.renderScoreboard(scoreobjective1, scaledresolution);
        }

    }

    private void renderScoreboard(ScoreObjective objective, ScaledResolution scaledRes) {
        net.minecraft.scoreboard.Scoreboard scoreboard = objective.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(objective);
        List<Score> list = collection.stream().filter(p_apply_1_ -> p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#")).collect(Collectors.toList());

        if (list.size() > 15) {
            collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        } else {
            collection = list;
        }

        int i = mc.fontRendererObj.getStringWidth(objective.getDisplayName());

        for (Score score : collection) {
            ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
            String s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
            i = Math.max(i, mc.fontRendererObj.getStringWidth(s));
        }

        int i1 = collection.size() * mc.fontRendererObj.FONT_HEIGHT;
        float j1 = this.y * scaledRes.getScaledHeight() + i1 + 8;
        float l1 = this.x * scaledRes.getScaledWidth();
        int j = 0;
        if (this.y == 0) {
            this.y = (scaledRes.getScaledHeight() / 2f + i1 / 3f) / scaledRes.getScaledHeight();
        }
        this.width = i;
        this.height = i1 + 8;
        for (Score score1 : collection) {
            ++j;
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            String s2 = EnumChatFormatting.RED + "" + score1.getScorePoints();
            int k = (int) (j1 - j * mc.fontRendererObj.FONT_HEIGHT);
            int l = (int) (l1 + i + 2);
            if (Scoreboard.background.getValue()) {
                if (blur.getValue()) {
//                    BlurBuffer.blurArea(l1 - 2, k, l, k + mc.fontRendererObj.FONT_HEIGHT, true);
                }


                Gui.drawRect(l1 - 2, k, l, k + mc.fontRendererObj.FONT_HEIGHT, 1342177280);
            }
            mc.fontRendererObj.drawString(s1, l1, k, -1);
            if (Scoreboard.numbers.getValue()) {
                mc.fontRendererObj.drawString(s2, l - mc.fontRendererObj.getStringWidth(s2), k, -1);
            }

            if (j == collection.size()) {
                String s3 = objective.getDisplayName();
                if (Scoreboard.background.getValue()) {
                    if (blur.getValue()) {
//                        Gui.drawRect(l1 - 2, k - mc.fontRendererObj.FONT_HEIGHT - 1, l, k - 1, new Color(0,0,0,100).getRGB());
//                        Gui.drawRect(l1 - 2, k - 1, l, k, new Color(0,0,0,130).getRGB());
//                        BlurBuffer.blurArea(l1 - 2, k - mc.fontRendererObj.FONT_HEIGHT - 1, l, k - 1, true);
//                        BlurBuffer.blurArea(l1 - 2, k - 1, l, k, true);
                        Gui.drawRect(l1 - 2, k - mc.fontRendererObj.FONT_HEIGHT - 1, l, k - 1, 1610612736);
                        Gui.drawRect(l1 - 2, k - 1, l, k, 1342177280);
                    } else {
                        Gui.drawRect(l1 - 2, k - mc.fontRendererObj.FONT_HEIGHT - 1, l, k - 1, 1610612736);
                        Gui.drawRect(l1 - 2, k - 1, l, k, 1342177280);
                    }
                }
                mc.fontRendererObj.drawString(s3, l1 + i / 2f - mc.fontRendererObj.getStringWidth(s3) / 2f, k - mc.fontRendererObj.FONT_HEIGHT, -1);
            }
        }
    }

}

package mods.eln.sixnode.electricalsource;

import mods.eln.Eln;
import mods.eln.init.Cable;
import mods.eln.misc.*;
import mods.eln.misc.Obj3D.Obj3DPart;
import mods.eln.node.six.SixNodeDescriptor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.List;

import static mods.eln.i18n.I18N.tr;

public class ElectricalSourceDescriptor extends SixNodeDescriptor {

    private Obj3D obj;
    private Obj3DPart main;
    private Obj3DPart led;
    private boolean signalSource = false;

    public ElectricalSourceDescriptor(String name, Obj3D obj, boolean signalSource) {
        super(name, ElectricalSourceElement.class, ElectricalSourceRender.class);
        this.obj = obj;
        if (obj != null) {
            main = obj.getPart("main");
            led = obj.getPart("led");
        }
        this.signalSource = signalSource;

        if (signalSource) {
          voltageLevelColor = VoltageLevelColor.SignalVoltage;
        } else {
            voltageLevelColor = VoltageLevelColor.Neutral;
        }
    }

    public boolean isSignalSource() {
        return signalSource;
    }

    void draw(boolean ledOn) {
        if (main != null) main.draw();
        if (led != null) {
            if (ledOn)
                UtilsClient.drawLight(led);
            else {
                GL11.glPushMatrix();
                GL11.glColor3f(0.1f, 0.1f, 0.1f);
                led.draw();
                GL11.glPopMatrix();
            }
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
        super.addInformation(itemStack, entityPlayer, list, par4);
        Collections.addAll(list, tr("Provides an ideal voltage source\nwithout energy or power limitation.").split("\\\n"));
        list.add("");
        list.add(tr("Internal resistance: %s\u2126", Utils.plotValue(Cable.Companion.getLowVoltage().descriptor.electricalRs)));
        list.add("");
        list.add(tr("Creative block."));
    }

    @Override
    public RealisticEnum addRealismContext(List list) {
        list.add(tr("Acts as an ideal voltage source, with a small inline resistance"));
        return RealisticEnum.IDEAL;
    }

    @Nullable
    @Override
    public LRDU getFrontFromPlace(@NotNull Direction side, @NotNull EntityPlayer player) {
        if (signalSource) {
            return super.getFrontFromPlace(side, player).left();
        } else {
            return super.getFrontFromPlace(side, player);
        }
    }
}

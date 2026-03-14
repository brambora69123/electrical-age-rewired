package mods.eln.sim;

import mods.eln.misc.INBTTReady;
import mods.eln.sim.mna.state.VoltageState;
import net.minecraft.nbt.NBTTagCompound;

public class NodeVoltageState extends VoltageState implements INBTTReady {

    String name;

    public NodeVoltageState(String name) {
        super();
        this.name = name;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound, String str) {
        setVoltage(nbttagcompound.getFloat(str + name + "Uc"));
        if (Double.isNaN(U)) setVoltage(0);
        if (U == Float.NEGATIVE_INFINITY) setVoltage(0);
        if (U == Float.POSITIVE_INFINITY) setVoltage(0);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound, String str) {
        nbttagcompound.setFloat(str + name + "Uc", (float) U);
    }
}

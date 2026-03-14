package mods.eln.sim.nbt;

import mods.eln.Eln;
import mods.eln.init.Cable;
import mods.eln.misc.Utils;

public class NbtElectricalGateInput extends NbtElectricalLoad {

    public NbtElectricalGateInput(String name) {
        super(name);
        Cable.Companion.getSignal().descriptor.applyTo(this);
    }

    public String plot(String str) {
        return Utils.plotSignal(U);
    }

    public boolean stateHigh() {
        return U > Eln.SVU * 0.6;
    }

    public boolean stateLow() {
        return U < Eln.SVU * 0.2;
    }

    public double getNormalized() {
        double norm = U * Eln.SVUinv;
        if (norm < 0.0) norm = 0.0;
        if (norm > 1.0) norm = 1.0;
        return norm;
    }

    public double getSignalVoltage() {
        double voltage = this.getVoltage();
        if (voltage < 0.0) voltage = 0.0;
        if (voltage > Eln.SVU) voltage = Eln.SVU;
        return voltage;
    }
}

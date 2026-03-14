package mods.eln.sim.mna.state;

public class VoltageState extends State {

    public double U {
        return state;
    }

    public void setVoltage(double state) {
        this.state = state;
    }
}

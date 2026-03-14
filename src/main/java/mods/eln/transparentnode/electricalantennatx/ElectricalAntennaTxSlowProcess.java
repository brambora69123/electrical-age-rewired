package mods.eln.transparentnode.electricalantennatx;

import mods.eln.Eln;
import mods.eln.misc.Coordinate;
import mods.eln.node.NodeBase;
import mods.eln.node.NodeManager;
import mods.eln.node.transparent.TransparentNode;
import mods.eln.sim.IProcess;
import mods.eln.transparentnode.electricalantennarx.ElectricalAntennaRxElement;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ElectricalAntennaTxSlowProcess implements IProcess {

    ElectricalAntennaTxElement element;

    double timeCounter = 0;
    final double periode = 2;

    public ElectricalAntennaTxSlowProcess(ElectricalAntennaTxElement element) {
        this.element = element;
    }

    @Override
    public void process(double time) {
        //if(element.rpos.getX() == null)
        World world = element.node.coordinate.world();

        if (timeCounter <= 0.0) {
            timeCounter = periode;
            int rangeMax = element.descriptor.rangeMax;
            Coordinate coord = new Coordinate(element.node.coordinate);

            int distance = 0;
            TransparentNode node = null;
            boolean find = false;
            //int a = 0,b = 0;
            do {
                coord.move(element.front);
                distance++;
                net.minecraft.world.chunk.Chunk chunk = coord.world().getChunkProvider().getLoadedChunk(coord.pos.getX() >> 4, coord.pos.getZ() >> 4);
                if (chunk == null || chunk.isEmpty()) break;
                IBlockState state = chunk.getBlockState(coord.pos);
                Block block = state.getBlock();
                if (!(block == Blocks.AIR || block == Blocks.FIRE)) {
                    if (block == ModBlock.transparentNodeBlock
                        && (node = (TransparentNode) NodeManager.instance.getNodeFromCoordinate(coord)) != null
                        && (node.element instanceof ElectricalAntennaRxElement)) {
                        ElectricalAntennaRxElement rx = (ElectricalAntennaRxElement) node.element;
                        if (rx.front == element.front.getInverse()) {
                            find = true;
                        }
                    }
                    break;
                }
            } while (distance < rangeMax);
            if (!find) {
                element.txDisconnect();
                Coordinate coordCpy = new Coordinate(coord);
                coordCpy.move(element.front.getInverse());
                if (element.powerResistor.getPower() > 50) {
                    if (coordCpy.world().blockExists(coordCpy.x, coordCpy.y, coordCpy.z)) {
                        if (coordCpy.getBlock() == Blocks.AIR) {
                            coordCpy.world().setBlock(coordCpy.x, coordCpy.y, coordCpy.z, Blocks.fire);
                        }
                    }
                }
            } else {
                element.powerEfficency = 1 - (element.descriptor.electricalPowerRatioLostOffset + element.descriptor.electricalPowerRatioLostPerBlock * distance);

                if (world.getWorldInfo().isRaining()) element.powerEfficency *= 0.707;
                if (world.getWorldInfo().isThundering()) element.powerEfficency *= 0.707;

                element.rpos.getX() = node.coordinate;
                element.rxElement = (ElectricalAntennaRxElement) node.element;
            }
            List list = world.getEntitiesWithinAABBExcludingEntity((Entity) null, Coordinate.getAxisAlignedBB(element.node.coordinate, coord));

            for (Object o : list) {
                Entity e = (Entity) o;
                e.setFire((int) (Math.pow(element.powerResistor.getPower() / 100.0, 2) + 0.5));
            }
        }

        if (element.powerResistor.getPower() > element.descriptor.electricalMaximalPower) {
            element.node.physicalSelfDestruction(2.0f);
        }
        if (element.powerIn.getVoltage() > element.descriptor.electricalMaximalVoltage) {
            element.node.physicalSelfDestruction(2.0f);
        }

        element.placeBoot = false;
        timeCounter -= time;
    }
}

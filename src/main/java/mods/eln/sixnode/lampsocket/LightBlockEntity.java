package mods.eln.sixnode.lampsocket;

import mods.eln.init.ModBlock;
import mods.eln.misc.Coordinate;
import mods.eln.misc.INBTTReady;
import mods.eln.misc.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;

public class LightBlockEntity extends TileEntity implements ITickable {

    ArrayList<LightHandle> lightList = new ArrayList<LightHandle>();
    private byte currentLight = 0;

    public static final ArrayList<LightBlockObserver> observers = new ArrayList<LightBlockObserver>();

    static void addObserver(LightBlockObserver observer) {
        observers.add(observer);
    }

    static void removeObserver(LightBlockObserver observer) {
        observers.remove(observer);
    }


    public interface LightBlockObserver {
        void lightBlockDestructor(Coordinate coord);
    }

    static class LightHandle implements INBTTReady {
        byte value;
        int timeout;

        public LightHandle() {
            value = 0;
            timeout = 0;
        }

        public LightHandle(byte value, int timeout) {
            this.value = value;
            this.timeout = timeout;
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt, String str) {
            value = nbt.getByte(str + "value");
            timeout = nbt.getInteger(str + "timeout");
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound nbt, String str) {
            nbt.setByte(str + "value", value);
            nbt.setInteger(str + "timeout", timeout);
            return nbt;
        }
    }

    void addLight(int light, int timeout) {
        lightList.add(new LightHandle((byte) light, timeout));
        refreshLightValue();
    }

	/*void removeLight(int light) {
        //int meta = world.getBlockMetadata(xCoord, yCoord, zCoord);
		for (int idx = 0; idx < lightList.size(); idx++) {
			if (lightList.get(idx) == light) {
				lightList.remove(idx);
				lightManager();
				return;
			}
		}
		Utils.println("Assert void removeLight(int light)");
	}*/
	/*
	void replaceLight(int oldLight, int newLight) {
		for (int idx = 0; idx < lightList.size(); idx++) {
			if (lightList.get(idx) == oldLight) {
				lightList.set(idx, newLight);
				lightManager();
				return;
			}
		}	
		Utils.println("Assert void replaceLight(int oldLight, int newLight)");
	}*/

	/*
	int getLight() {
		int light = 0;
		for (LightHandle l : lightList) {
			if (light < l.value) light = l.value;
		}
		return light;
	}*/

    private void refreshLightValue() {
        int maxLight = 0;
        for (LightHandle handle : lightList) {
            if (maxLight < handle.value) {
                maxLight = handle.value;
            }
        }
        setCurrentLight(maxLight);
    }

    private void setCurrentLight(int light) {
        byte newLight = (byte) Math.max(0, Math.min(15, light));
        if (currentLight == newLight) return;

        currentLight = newLight;
        if (world != null) {
            IBlockState state = world.getBlockState(pos);
            markDirty();
            world.checkLightFor(EnumSkyBlock.BLOCK, pos);
            world.notifyBlockUpdate(pos, state, state, 3);
        }
    }

    public int getCurrentLight() {
        return currentLight & 0xFF;
    }

    @Override
    public void update() {
        if (world.isRemote) return;

        if (lightList.isEmpty()) {
            setCurrentLight(0);
            world.setBlockToAir(pos);
            Utils.println("Destroy light at " + pos.getX() + " " + pos.getY() + " " + pos.getZ() + " ");
            return;
        }

        Iterator<LightHandle> iterator = lightList.iterator();
        while (iterator.hasNext()) {
            LightHandle l = iterator.next();
            l.timeout--;
            if (l.timeout <= 0) {
                iterator.remove();
            }
        }

        if (lightList.isEmpty()) {
            setCurrentLight(0);
            world.setBlockToAir(pos);
            return;
        }

        refreshLightValue();
    }

    public static void addLight(World w, BlockPos pos, int light, int timeout) {
        Block block = w.getBlockState(pos).getBlock();
        if (block != ModBlock.lightBlock) {
            if (block != Blocks.AIR) return;
            w.setBlockState(pos, ModBlock.lightBlock.getDefaultState());
            w.checkLightFor(EnumSkyBlock.BLOCK, pos);
        }

        TileEntity t = w.getTileEntity(pos);
        if (t instanceof LightBlockEntity)
            ((LightBlockEntity) t).addLight(light, timeout);
        else
            Utils.println("ASSERT if(t != null && t instanceof LightBlockEntity)");
    }

    public static void addLight(Coordinate coord, int light, int timeout) {
        addLight(coord.world(), coord.pos, light, timeout);
    }

	/*public static void removeLight(Coordinate coord, int light) {
		int blockId = coord.getBlockId();
		if (blockId != Eln.lightBlockId) return;
		((LightBlockEntity)coord.getTileEntity()).removeLight(light);
	}
	
	public static void replaceLight(Coordinate coord, int oldLight, int newLight) {
		int blockId = coord.getBlockId();
		if (blockId != Eln.lightBlockId) {
			//coord.setBlock(Eln.lightBlockId, newLight);
			Utils.println("ASSERT public static void replaceLight(Coordinate coord, int oldLight, int newLight) " + coord);
			return;
		}
		((LightBlockEntity)coord.getTileEntity()).replaceLight(oldLight,newLight);
	}*/

	/*public int getClientLight() {
		return clientLight;
	}
	
	int clientLight = 0;*/

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setByte("currentLight", currentLight);
        nbt.setInteger("lightCount", lightList.size());
        for (int i = 0; i < lightList.size(); i++) {
            lightList.get(i).writeToNBT(nbt, "light" + i);
        }
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        currentLight = nbt.getByte("currentLight");
        lightList.clear();
        int lightCount = nbt.getInteger("lightCount");
        for (int i = 0; i < lightCount; i++) {
            LightHandle handle = new LightHandle();
            handle.readFromNBT(nbt, "light" + i);
            lightList.add(handle);
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(super.getUpdateTag());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        readFromNBT(tag);
        if (world != null) {
            world.checkLightFor(EnumSkyBlock.BLOCK, pos);
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        handleUpdateTag(pkt.getNbtCompound());
    }
}

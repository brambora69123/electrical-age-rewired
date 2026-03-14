package mods.eln.misc

import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent
import net.minecraft.tileentity.TileEntity
import java.util.*

class TileEntityDestructor {
    var destroyList = ArrayList<TileEntity>()
    fun clear() {
        destroyList.clear()
    }

    fun add(tile: TileEntity) {
        destroyList.add(tile)
    }

    @SubscribeEvent
    fun tick(event: ServerTickEvent) {
        if (event.phase != TickEvent.Phase.START) return
        for (t in destroyList) {
            if (t.world() != null && t.world().getTileEntity(t.pos.x, t.pos.y, t.pos.z) === t) {
                t.world().setBlockToAir(t.pos.x, t.pos.y, t.pos.z)
                Utils.println("destroy light at " + t.pos.x + " " + t.pos.y + " " + t.pos.z)
            }
        }
        destroyList.clear()
    }

    init {
        FMLCommonHandler.instance().bus().register(this)
    }
}

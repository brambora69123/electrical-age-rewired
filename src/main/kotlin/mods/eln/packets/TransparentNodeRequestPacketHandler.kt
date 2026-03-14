package mods.eln.packets

import net.minecraftforge.fml.common.network.simpleimpl.getCurrent()MessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import mods.eln.Eln
import mods.eln.misc.getVoltage()tils
import mods.eln.node.NodeManager
import mods.eln.node.transparent.getTemperature()ransparentNode
import net.minecraft.item.getCurrent()temStack
import net.minecraftforge.fml.common.network.simpleimpl.getCurrent()MessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

/**
 * Created by Gregory Maddra on 2016-06-27.
 */
class TransparentNodeRequestPacketHandler : IMessageHandler<TransparentNodeRequestPacket, TransparentNodeResponsePacket> {
    override fun onMessage(message: TransparentNodeRequestPacket?, ctx: MessageContext?): TransparentNodeResponsePacket? {
        var c = message!!.coord
        val ghostElem = Eln.ghostManager.getGhost(c)
        if(ghostElem != null) c = ghostElem.observatorCoordonate!!
        val node = NodeManager.instance!!.getNodeFromCoordonate(c) as? TransparentNode
        var stringMap: Map<String, String> = emptyMap()
        var stack = ItemStack.EMPTY
        if (node != null) {
            try {
                stringMap = node.element!!.getWaila()
            } catch (e: NullPointerException) {
                Utils.println("Attempted to get WAILA info for an invalid node!")
                e.printStackTrace()
                return null
            }
        }
        return TransparentNodeResponsePacket(stringMap, c, stack)
    }
}

package mods.eln.init

import mods.eln.Eln
import mods.eln.cable.CableRenderDescriptor
import mods.eln.node.six.SixNodeItem
import mods.eln.node.transparent.TransparentNodeItem
import mods.eln.sixnode.electricalcable.ElectricalCableDescriptor

/**
 * Central descriptor registration for SixNode and TransparentNode items.
 * 
 * In 1.12.2, the proper approach would be to register each variant as a separate
 * ItemBlock. However, Electrical Age uses a descriptor system with damage values
 * (metadata) to represent hundreds of variants. This is a 1.7.10 pattern that we're
 * keeping for practical migration reasons.
 * 
 * TODO: Over time, migrate important items to proper 1.12.2 registry entries.
 */
object Descriptors {
    
    // Cable render descriptors (shared)
    lateinit var stdCableRenderSignal: CableRenderDescriptor
    lateinit var stdCableRender50V: CableRenderDescriptor
    lateinit var stdCableRender200V: CableRenderDescriptor
    lateinit var stdCableRender800V: CableRenderDescriptor
    lateinit var stdCableRender3200V: CableRenderDescriptor
    
    // Cable descriptors
    lateinit var signalDescriptor: ElectricalCableDescriptor
    lateinit var lowVoltageCableDescriptor: ElectricalCableDescriptor
    lateinit var batteryCableDescriptor: ElectricalCableDescriptor
    lateinit var mediumVoltageCableDescriptor: ElectricalCableDescriptor
    lateinit var highVoltageCableDescriptor: ElectricalCableDescriptor
    lateinit var veryHighVoltageCableDescriptor: ElectricalCableDescriptor
    
    /**
     * Initialize all descriptors and add them to SixNodeItem/TransparentNodeItem.
     * Call this during preInit after items are created.
     */
    @JvmStatic
    fun preInit() {
        Eln.logger.info("Registering Electrical Age descriptors...")
        
        // Get item instances
        val sixNodeItem = Eln.sixNodeItem
        val transparentNodeItem = Eln.transparentNodeItem
        
        Eln.logger.info("SixNodeItem: $sixNodeItem, TransparentNodeItem: $transparentNodeItem")
        
        // Initialize Cable objects
        Cable.battery = Cable()
        Cable.signal = Cable()
        Cable.lowVoltage = Cable()
        Cable.mediumVoltage = Cable()
        Cable.highVoltage = Cable()
        Cable.veryHighVoltage = Cable()
        
        // Register cables (starting at damage ID 2)
        registerElectricalCable(sixNodeItem, 2)
        
        Eln.logger.info("SixNodeItem orderList size: ${sixNodeItem.orderList.size}")
        Eln.logger.info("Registered Electrical Age descriptors")
    }
    
    /**
     * Register electrical cable descriptors.
     * @param sixNodeItem The SixNodeItem instance
     * @param baseId The base damage ID (typically 2 for cables)
     */
    @JvmStatic
    private fun registerElectricalCable(sixNodeItem: SixNodeItem, baseId: Int) {
        // Create render descriptors
        stdCableRenderSignal = CableRenderDescriptor("eln", "sprites/cable.png", 0.95f, 0.95f)
        stdCableRender50V = CableRenderDescriptor("eln", "sprites/cable.png", 1.95f, 0.95f)
        stdCableRender200V = CableRenderDescriptor("eln", "sprites/cable.png", 2.95f, 0.95f)
        stdCableRender800V = CableRenderDescriptor("eln", "sprites/cable.png", 3.95f, 0.95f)
        stdCableRender3200V = CableRenderDescriptor("eln", "sprites/cable.png", 4.95f, 0.95f)
        
        // Signal Cable (damage: baseId + 0)
        signalDescriptor = ElectricalCableDescriptor(
            "Signal Cable", stdCableRenderSignal,
            "For signal transmission.", true
        ).apply {
            setPhysicalConstantLikeNormalCable(
                Cable.SVU, Cable.SVP, 0.02 / 50 * Cable.gateOutputCurrent / Cable.SVIinv,
                Cable.SVU * 1.3, Cable.SVP * 1.2,
                0.5,
                Eln.cableWarmLimit, -100.0,
                Eln.cableHeatingTime, 1.0
            )
        }
        sixNodeItem.addDescriptor(0 + (baseId shl 6), signalDescriptor)
        Cable.signal.descriptor = signalDescriptor

        // Low Voltage Cable (damage: baseId + 4)
        lowVoltageCableDescriptor = ElectricalCableDescriptor(
            "Low Voltage Cable", stdCableRender50V,
            "For low voltage with high current.", false
        ).apply {
            setPhysicalConstantLikeNormalCable(
                Cable.LVU, Cable.LVP(), 0.2 / 20,
                Cable.LVU * 1.3, Cable.LVP() * 1.2,
                20.0,
                Eln.cableWarmLimit, -100.0,
                Eln.cableHeatingTime, Eln.cableThermalConductionTao
            )
        }
        sixNodeItem.addDescriptor(4 + (baseId shl 6), lowVoltageCableDescriptor)
        Cable.lowVoltage.descriptor = lowVoltageCableDescriptor

        // Battery Cable (damage: baseId + 8) - variant of LV cable
        batteryCableDescriptor = ElectricalCableDescriptor(
            "Low Voltage Cable", stdCableRender50V,
            "For low voltage with high current.", false
        ).apply {
            setPhysicalConstantLikeNormalCable(
                Cable.LVU, Cable.LVP() / 4, 0.2 / 20,
                Cable.LVU * 1.3, Cable.LVP() * 1.2,
                20.0,
                Eln.cableWarmLimit, -100.0,
                Eln.cableHeatingTime, Eln.cableThermalConductionTao
            )
        }
        sixNodeItem.addDescriptor(8 + (baseId shl 6), batteryCableDescriptor)
        Cable.battery.descriptor = batteryCableDescriptor

        // Medium Voltage Cable (damage: baseId + 12)
        mediumVoltageCableDescriptor = ElectricalCableDescriptor(
            "Medium Voltage Cable", stdCableRender200V,
            "For medium voltage distribution.", false
        ).apply {
            setPhysicalConstantLikeNormalCable(
                Cable.MVU, Cable.MVP(), 0.10 / 20,
                Cable.MVU * 1.3, Cable.MVP() * 1.2,
                20.0,
                Eln.cableWarmLimit, -100.0,
                Eln.cableHeatingTime, Eln.cableThermalConductionTao
            )
        }
        sixNodeItem.addDescriptor(12 + (baseId shl 6), mediumVoltageCableDescriptor)
        Cable.mediumVoltage.descriptor = mediumVoltageCableDescriptor

        // High Voltage Cable (damage: baseId + 16)
        highVoltageCableDescriptor = ElectricalCableDescriptor(
            "High Voltage Cable", stdCableRender800V,
            "For high voltage transmission.", false
        ).apply {
            setPhysicalConstantLikeNormalCable(
                Cable.HVU, Cable.HVP(), 0.05 / 20,
                Cable.HVU * 1.3, Cable.HVP() * 1.2,
                20.0,
                Eln.cableWarmLimit, -100.0,
                Eln.cableHeatingTime, Eln.cableThermalConductionTao
            )
        }
        sixNodeItem.addDescriptor(16 + (baseId shl 6), highVoltageCableDescriptor)
        Cable.highVoltage.descriptor = highVoltageCableDescriptor

        // Very High Voltage Cable (damage: baseId + 20)
        veryHighVoltageCableDescriptor = ElectricalCableDescriptor(
            "Very High Voltage Cable", stdCableRender3200V,
            "For very high voltage transmission.", false
        ).apply {
            setPhysicalConstantLikeNormalCable(
                Cable.VHVU, Cable.VHVP(), 0.02 / 20,
                Cable.VHVU * 1.3, Cable.VHVP() * 1.2,
                20.0,
                Eln.cableWarmLimit, -100.0,
                Eln.cableHeatingTime, Eln.cableThermalConductionTao
            )
        }
        sixNodeItem.addDescriptor(20 + (baseId shl 6), veryHighVoltageCableDescriptor)
        Cable.veryHighVoltage.descriptor = veryHighVoltageCableDescriptor
    }
}

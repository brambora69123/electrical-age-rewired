package mods.eln.init

import mods.eln.Eln
import mods.eln.generic.GenericItemUsingDamageDescriptor
import mods.eln.init.Config
import mods.eln.item.LampDescriptor
import mods.eln.item.TreeResin
import mods.eln.sixnode.lampsocket.LampSocketType
import mods.eln.sixnode.wirelesssignal.WirelessSignalAnalyserItemDescriptor

/**
 * Central registry for shared items in Electrical Age.
 * These are damage-based items that share a single Item instance.
 */
object Items {
    // Multi-meter, thermometer, all-meter descriptors
    lateinit var multiMeterElement: GenericItemUsingDamageDescriptor

    lateinit var thermometerElement: GenericItemUsingDamageDescriptor

    lateinit var allMeterElement: GenericItemUsingDamageDescriptor

    // Tree resin item
    lateinit var treeResin: TreeResin

    /**
     * Initialize shared items. Must be called during preInit after sharedItem is created.
     */
    @JvmStatic
    fun init() {
        val sharedItem = Eln.sharedItem

        // MultiMeter (subId=0)
        multiMeterElement = GenericItemUsingDamageDescriptor("MultiMeter")
        sharedItem.addElement(0, multiMeterElement)

        // Thermometer (subId=1)
        thermometerElement = GenericItemUsingDamageDescriptor("Thermometer")
        sharedItem.addElement(1, thermometerElement)

        // AllMeter (subId=2)
        allMeterElement = GenericItemUsingDamageDescriptor("AllMeter")
        sharedItem.addElement(2, allMeterElement)

        // Wireless Analyser (subId=8)
        val wirelessAnalyser = WirelessSignalAnalyserItemDescriptor("Wireless Analyser")
        sharedItem.addElement(8, wirelessAnalyser)

        registerLampItems(sharedItem)
    }

    private fun registerLampItems(sharedItem: mods.eln.generic.SharedItem) {
        val lampItemId = 4
        val lightPower = doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 15.0, 20.0, 25.0, 30.0, 40.0)
        val lightLevel = DoubleArray(16) { (it + 0.49) / 15.0 }
        val economicPowerFactor = 0.5
        val standardGrowRate = 0.0

        fun addLamp(subId: Int, descriptor: LampDescriptor) {
            sharedItem.addElement(subId + (lampItemId shl 6), descriptor)
        }

        addLamp(0, LampDescriptor(
            "Small 50V Incandescent Light Bulb",
            "incandescentironlamp",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[12],
            lightLevel[12],
            Config.incandescentLampLifeInHours,
            standardGrowRate
        ))
        addLamp(1, LampDescriptor(
            "50V Incandescent Light Bulb",
            "incandescentironlamp",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[14],
            lightLevel[14],
            Config.incandescentLampLifeInHours,
            standardGrowRate
        ))
        addLamp(2, LampDescriptor(
            "200V Incandescent Light Bulb",
            "incandescentironlamp",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.MVU,
            lightPower[14],
            lightLevel[14],
            Config.incandescentLampLifeInHours,
            standardGrowRate
        ))
        addLamp(4, LampDescriptor(
            "Small 50V Carbon Incandescent Light Bulb",
            "incandescentcarbonlamp",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[11],
            lightLevel[11],
            Config.carbonLampLifeInHours,
            standardGrowRate
        ))
        addLamp(5, LampDescriptor(
            "50V Carbon Incandescent Light Bulb",
            "incandescentcarbonlamp",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[13],
            lightLevel[13],
            Config.carbonLampLifeInHours,
            standardGrowRate
        ))
        addLamp(16, LampDescriptor(
            "Small 50V Economic Light Bulb",
            "fluorescentlamp",
            LampDescriptor.Type.eco,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[12] * economicPowerFactor,
            lightLevel[12],
            Config.economicLampLifeInHours,
            standardGrowRate
        ))
        addLamp(17, LampDescriptor(
            "50V Economic Light Bulb",
            "fluorescentlamp",
            LampDescriptor.Type.eco,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[14] * economicPowerFactor,
            lightLevel[14],
            Config.economicLampLifeInHours,
            standardGrowRate
        ))
        addLamp(18, LampDescriptor(
            "200V Economic Light Bulb",
            "fluorescentlamp",
            LampDescriptor.Type.eco,
            LampSocketType.Douille,
            Cable.MVU,
            lightPower[14] * economicPowerFactor,
            lightLevel[14],
            Config.economicLampLifeInHours,
            standardGrowRate
        ))
        addLamp(32, LampDescriptor(
            "50V Farming Lamp",
            "farminglamp",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.LVU,
            120.0,
            lightLevel[15],
            Config.incandescentLampLifeInHours,
            0.50
        ))
        addLamp(36, LampDescriptor(
            "200V Farming Lamp",
            "farminglamp",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.MVU,
            120.0,
            lightLevel[15],
            Config.incandescentLampLifeInHours,
            0.50
        ))
        addLamp(37, LampDescriptor(
            "50V LED Bulb",
            "ledlamp",
            LampDescriptor.Type.LED,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[14] / 2.0,
            lightLevel[14],
            Config.ledLampLifeInHours,
            standardGrowRate
        ))
        addLamp(38, LampDescriptor(
            "200V LED Bulb",
            "ledlamp",
            LampDescriptor.Type.LED,
            LampSocketType.Douille,
            Cable.MVU,
            lightPower[14] / 2.0,
            lightLevel[14],
            Config.ledLampLifeInHours,
            standardGrowRate
        ))
    }
}

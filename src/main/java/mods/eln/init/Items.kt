package mods.eln.init

import mods.eln.Eln
import mods.eln.generic.GenericItemUsingDamageDescriptor
import mods.eln.generic.GenericItemUsingDamageDescriptorWithComment
import mods.eln.init.Config
import mods.eln.item.*
import mods.eln.item.electricalitem.*
import mods.eln.item.regulator.RegulatorAnalogDescriptor
import mods.eln.item.regulator.RegulatorOnOffDescriptor
import mods.eln.sixnode.lampsocket.LampSocketType
import mods.eln.sixnode.wirelesssignal.WirelessSignalAnalyserItemDescriptor
import net.minecraft.init.Blocks
import net.minecraft.init.Items as McItems
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary

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
        val sharedItemStackOne = Eln.sharedItemStackOne

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
        registerTreeResinAndRubber(sharedItem)
        registerElectricalDrill(sharedItem)
        registerOreScanner(sharedItem)
        registerMiningPipe(sharedItem)
        registerRawCable(sharedItem)
        registerMiscItem(sharedItem)
        registerRegulatorItem(sharedItem)
        registerProtection(sharedItem)
        registerBrush(sharedItem)
        
        registerElectricalTool(sharedItemStackOne)
        registerPortableItem(sharedItemStackOne)
        registerFuelBurnerItem(sharedItemStackOne)
    }

    private fun registerTreeResinAndRubber(sharedItem: mods.eln.generic.SharedItem) {
        val id = 64
        // Tree Resin (subId=0)
        treeResin = TreeResin("Tree Resin")
        sharedItem.addElement(0 + (id shl 6), treeResin)
        OreDictionary.registerOre("materialResin", treeResin.newItemStack())

        // Rubber (subId=1)
        val rubber = GenericItemUsingDamageDescriptor("Rubber")
        sharedItem.addElement(1 + (id shl 6), rubber)
        OreDictionary.registerOre("itemRubber", rubber.newItemStack())
    }

    private fun registerElectricalDrill(sharedItem: mods.eln.generic.SharedItem) {
        val id = 15
        sharedItem.addElement(0 + (id shl 6), ElectricalDrillDescriptor("Cheap Electrical Drill", 8.0, 4000.0))
        sharedItem.addElement(1 + (id shl 6), ElectricalDrillDescriptor("Average Electrical Drill", 5.0, 5000.0))
        sharedItem.addElement(2 + (id shl 6), ElectricalDrillDescriptor("Fast Electrical Drill", 3.0, 6000.0))
    }

    private fun registerOreScanner(sharedItem: mods.eln.generic.SharedItem) {
        val id = 16
        sharedItem.addElement(0 + (id shl 6), OreScanner("Ore Scanner"))
    }

    private fun registerMiningPipe(sharedItem: mods.eln.generic.SharedItem) {
        val id = 17
        Eln.miningPipeDescriptor = MiningPipeDescriptor("Mining Pipe")
        sharedItem.addElement(0 + (id shl 6), Eln.miningPipeDescriptor)
    }

    private fun registerRawCable(sharedItem: mods.eln.generic.SharedItem) {
        val id = 65
        val copperCable = CopperCableDescriptor("Copper Cable")
        sharedItem.addElement(0 + (id shl 6), copperCable)
        
        val ironCable = GenericItemUsingDamageDescriptor("Iron Cable")
        sharedItem.addElement(1 + (id shl 6), ironCable)
        
        val tungstenCable = GenericItemUsingDamageDescriptor("Tungsten Cable")
        sharedItem.addElement(2 + (id shl 6), tungstenCable)
    }

    private fun registerElectricalTool(sharedItem: mods.eln.generic.SharedItem) {
        val id = 121
        sharedItem.addElement(0 + (id shl 6), ElectricalLampItem("Small Flashlight", 10, 6, 20.0, 12, 8, 50.0, 6000.0, 100.0))
        sharedItem.addElement(1 + (id shl 6), ElectricalLampItem("Improved Flashlight", 15, 8, 20.0, 15, 12, 50.0, 24000.0, 400.0))
        sharedItem.addElement(8 + (id shl 6), ElectricalPickaxe("Portable Electrical Mining Drill", 8f, 3f, 40000.0, 200.0, 800.0))
        sharedItem.addElement(12 + (id shl 6), ElectricalAxe("Portable Electrical Axe", 8f, 3f, 40000.0, 200.0, 800.0))
    }

    private fun registerPortableItem(sharedItem: mods.eln.generic.SharedItem) {
        val id = 122
        sharedItem.addElement(0 + (id shl 6), BatteryItem("Portable Battery", 20000.0, 500.0, 100.0, 2))
        sharedItem.addElement(1 + (id shl 6), BatteryItem("Portable Battery Pack", 60000.0, 1500.0, 300.0, 2))
        sharedItem.addElement(16 + (id shl 6), BatteryItem("Portable Condensator", 5000.0, 2000.0, 500.0, 1))
        sharedItem.addElement(17 + (id shl 6), BatteryItem("Portable Condensator Pack", 15000.0, 6000.0, 1500.0, 1))
        
        val xRayScanner = PortableOreScannerItem("X-Ray Scanner", Eln.obj.getObj("XRayScanner"), 10000.0, 400.0, 300.0, 16f, (Math.PI / 2).toFloat(), 32, 20)
        sharedItem.addElement(32 + (id shl 6), xRayScanner)
    }

    private fun registerFuelBurnerItem(sharedItem: mods.eln.generic.SharedItem) {
        val id = 124
        val factor = 1.0 // fuelHeatFurnacePowerFactor
        sharedItem.addElement(0 + (id shl 6), FuelBurnerDescriptor("Small Fuel Burner", 5000.0 * factor, 2, 1.6f))
        sharedItem.addElement(1 + (id shl 6), FuelBurnerDescriptor("Medium Fuel Burner", 10000.0 * factor, 1, 1.4f))
        sharedItem.addElement(2 + (id shl 6), FuelBurnerDescriptor("Big Fuel Burner", 25000.0 * factor, 0, 1f))
    }

    private fun registerMiscItem(sharedItem: mods.eln.generic.SharedItem) {
        val id = 120
        sharedItem.addElement(0 + (id shl 6), GenericItemUsingDamageDescriptorWithComment("Cheap Chip", arrayOf()))
        sharedItem.addElement(1 + (id shl 6), GenericItemUsingDamageDescriptorWithComment("Advanced Chip", arrayOf()))
    }

    private fun registerRegulatorItem(sharedItem: mods.eln.generic.SharedItem) {
        val id = 3
        sharedItem.addElement(0 + (id shl 6), RegulatorOnOffDescriptor("On/Off Regulator", "onoffregulator", 5.0))
        sharedItem.addElement(1 + (id shl 6), RegulatorAnalogDescriptor("Analog Regulator", "analogregulator"))
    }

    private fun registerProtection(sharedItem: mods.eln.generic.SharedItem) {
        val id = 5
        sharedItem.addElement(0 + (id shl 6), OverVoltageProtectionDescriptor("Overvoltage Protection"))
        sharedItem.addElement(1 + (id shl 6), OverHeatingProtectionDescriptor("Overheating Protection"))
    }

    private fun registerBrush(sharedItem: mods.eln.generic.SharedItem) {
        val id = 119
        val names = arrayOf(
            "Black Brush", "Red Brush", "Green Brush", "Brown Brush",
            "Blue Brush", "Purple Brush", "Cyan Brush", "Silver Brush",
            "Gray Brush", "Pink Brush", "Lime Brush", "Yellow Brush",
            "Light Blue Brush", "Magenta Brush", "Orange Brush", "White Brush"
        )
        for (i in 0..15) {
            sharedItem.addElement(i + (id shl 6), BrushDescriptor(names[i]))
        }
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
            "small50vincandescentlightbulb",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[12],
            lightLevel[12],
            Config.incandescentLampLife,
            standardGrowRate
        ))
        addLamp(1, LampDescriptor(
            "50V Incandescent Light Bulb",
            "50vincandescentlightbulb",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[14],
            lightLevel[14],
            Config.incandescentLampLife,
            standardGrowRate
        ))
        addLamp(2, LampDescriptor(
            "200V Incandescent Light Bulb",
            "200vincandescentlightbulb",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.MVU,
            lightPower[14],
            lightLevel[14],
            Config.incandescentLampLife,
            standardGrowRate
        ))
        addLamp(4, LampDescriptor(
            "Small 50V Carbon Incandescent Light Bulb",
            "small50vcarbonincandescentlightbulb",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[11],
            lightLevel[11],
            Config.carbonLampLife,
            standardGrowRate
        ))
        addLamp(5, LampDescriptor(
            "50V Carbon Incandescent Light Bulb",
            "50vcarbonincandescentlightbulb",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[13],
            lightLevel[13],
            Config.carbonLampLife,
            standardGrowRate
        ))
        addLamp(16, LampDescriptor(
            "Small 50V Economic Light Bulb",
            "small50veconomiclightbulb",
            LampDescriptor.Type.eco,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[12] * economicPowerFactor,
            lightLevel[12],
            Config.economicLampLife,
            standardGrowRate
        ))
        addLamp(17, LampDescriptor(
            "50V Economic Light Bulb",
            "50veconomiclightbulb",
            LampDescriptor.Type.eco,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[14] * economicPowerFactor,
            lightLevel[14],
            Config.economicLampLife,
            standardGrowRate
        ))
        addLamp(18, LampDescriptor(
            "200V Economic Light Bulb",
            "200veconomiclightbulb",
            LampDescriptor.Type.eco,
            LampSocketType.Douille,
            Cable.MVU,
            lightPower[14] * economicPowerFactor,
            lightLevel[14],
            Config.economicLampLife,
            standardGrowRate
        ))
        addLamp(32, LampDescriptor(
            "50V Farming Lamp",
            "50vfarminglamp",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.LVU,
            120.0,
            lightLevel[15],
            Config.incandescentLampLife,
            0.50
        ))
        addLamp(36, LampDescriptor(
            "200V Farming Lamp",
            "200vfarminglamp",
            LampDescriptor.Type.Incandescent,
            LampSocketType.Douille,
            Cable.MVU,
            120.0,
            lightLevel[15],
            Config.incandescentLampLife,
            0.50
        ))
        addLamp(37, LampDescriptor(
            "50V LED Bulb",
            "50vledbulb",
            LampDescriptor.Type.LED,
            LampSocketType.Douille,
            Cable.LVU,
            lightPower[14] / 2.0,
            lightLevel[14],
            Config.ledLampLife,
            standardGrowRate
        ))
        addLamp(38, LampDescriptor(
            "200V LED Bulb",
            "200vledbulb",
            LampDescriptor.Type.LED,
            LampSocketType.Douille,
            Cable.MVU,
            lightPower[14] / 2.0,
            lightLevel[14],
            Config.ledLampLife,
            standardGrowRate
        ))
    }
}

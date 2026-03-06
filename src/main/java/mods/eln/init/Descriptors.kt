package mods.eln.init

import mods.eln.Eln
import mods.eln.cable.CableRenderDescriptor
import mods.eln.init.Cable
import mods.eln.misc.LRDU
import mods.eln.misc.series.SerieEE
import mods.eln.node.six.SixNodeItem
import mods.eln.node.transparent.TransparentNodeItem
import mods.eln.sixnode.batterycharger.BatteryChargerDescriptor
import mods.eln.sixnode.diode.DiodeDescriptor
import mods.eln.sixnode.electricalalarm.ElectricalAlarmDescriptor
import mods.eln.sixnode.electricalbreaker.ElectricalBreakerDescriptor
import mods.eln.sixnode.electricalcable.ElectricalCableDescriptor
import mods.eln.sixnode.electricaldatalogger.DataLogsPrintDescriptor
import mods.eln.sixnode.electricaldatalogger.ElectricalDataLoggerDescriptor
import mods.eln.sixnode.electricalentitysensor.ElectricalEntitySensorDescriptor
import mods.eln.sixnode.electricalfiredetector.ElectricalFireDetectorDescriptor
import mods.eln.sixnode.electricalgatesource.ElectricalGateSourceDescriptor
import mods.eln.sixnode.electricalgatesource.ElectricalGateSourceRenderObj
import mods.eln.sixnode.electricallightsensor.ElectricalLightSensorDescriptor
import mods.eln.sixnode.electricalmath.ElectricalMathDescriptor
import mods.eln.sixnode.electricalredstoneinput.ElectricalRedstoneInputDescriptor
import mods.eln.sixnode.electricalredstoneoutput.ElectricalRedstoneOutputDescriptor
import mods.eln.sixnode.electricalrelay.ElectricalRelayDescriptor
import mods.eln.sixnode.electricalsensor.ElectricalSensorDescriptor
import mods.eln.sixnode.electricalsource.ElectricalSourceDescriptor
import mods.eln.sixnode.electricalswitch.ElectricalSwitchDescriptor
import mods.eln.sixnode.electricaltimeout.ElectricalTimeoutDescriptor
import mods.eln.sixnode.electricalvumeter.ElectricalVuMeterDescriptor
import mods.eln.sixnode.electricalwatch.ElectricalWatchDescriptor
import mods.eln.sixnode.electricalweathersensor.ElectricalWeatherSensorDescriptor
import mods.eln.sixnode.electricalwindsensor.ElectricalWindSensorDescriptor
import mods.eln.sixnode.energymeter.EnergyMeterDescriptor
import mods.eln.sixnode.groundcable.GroundCableDescriptor
import mods.eln.sixnode.hub.HubDescriptor
import mods.eln.sixnode.lampsocket.LampSocketDescriptor
import mods.eln.sixnode.lampsocket.LampSocketType
import mods.eln.sixnode.lampsupply.LampSupplyDescriptor
import mods.eln.sixnode.logicgate.*
import mods.eln.sixnode.modbusrtu.ModbusRtuDescriptor
import mods.eln.sixnode.powercapacitorsix.PowerCapacitorSixDescriptor
import mods.eln.sixnode.powerinductorsix.PowerInductorSixDescriptor
import mods.eln.sixnode.powersocket.PowerSocketDescriptor
import mods.eln.sixnode.resistor.ResistorDescriptor
import mods.eln.sixnode.thermalcable.ThermalCableDescriptor
import mods.eln.sixnode.thermalsensor.ThermalSensorDescriptor
import mods.eln.sixnode.tutorialsign.TutorialSignDescriptor
import mods.eln.sixnode.wirelesssignal.WirelessSignalAnalyserItemDescriptor
import mods.eln.sixnode.wirelesssignal.repeater.WirelessSignalRepeaterDescriptor
import mods.eln.sixnode.wirelesssignal.rx.WirelessSignalRxDescriptor
import mods.eln.sixnode.wirelesssignal.source.WirelessSignalSourceDescriptor
import mods.eln.sixnode.wirelesssignal.tx.WirelessSignalTxDescriptor
import mods.eln.transparentnode.battery.BatteryDescriptor
import mods.eln.transparentnode.electricalantennarx.ElectricalAntennaRxDescriptor
import mods.eln.transparentnode.electricalantennatx.ElectricalAntennaTxDescriptor
import mods.eln.transparentnode.powercapacitor.PowerCapacitorDescriptor
import mods.eln.transparentnode.powerinductor.PowerInductorDescriptor
import mods.eln.transparentnode.transformer.TransformerDescriptor

/**
 * Central descriptor registration for SixNode and TransparentNode items.
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

    lateinit var thermalCableDescriptor: ThermalCableDescriptor

    lateinit var thermalInsulatedCableDescriptor: ThermalCableDescriptor

    /**
     * Initialize all descriptors and add them to SixNodeItem/TransparentNodeItem.
     */
    @JvmStatic
    fun preInit() {
        Eln.logger.info("Registering Electrical Age descriptors...")

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

        // ========== SIX NODE REGISTRATION ==========
        registerGround(sixNodeItem, 2)
        registerElectricalSource(sixNodeItem, 3)
        registerElectricalCable(sixNodeItem, 32)
        registerThermalCable(sixNodeItem, 48)
        registerLampSocket(sixNodeItem, 64)
        registerLampSupply(sixNodeItem, 65)
        registerBatteryCharger(sixNodeItem, 66)
        registerPowerSocket(sixNodeItem, 67)
        registerWirelessSignal(sixNodeItem, 92)
        registerElectricalDataLogger(sixNodeItem, 93)
        registerElectricalRelay(sixNodeItem, 94)
        registerElectricalGateSource(sixNodeItem, 95)
        registerPassiveComponent(sixNodeItem, 96)
        registerSwitch(sixNodeItem, 97)
        registerElectricalManager(sixNodeItem, 98)
        registerElectricalSensor(sixNodeItem, 100)
        registerThermalSensor(sixNodeItem, 101)
        registerElectricalVuMeter(sixNodeItem, 102)
        registerElectricalAlarm(sixNodeItem, 103)
        registerElectricalEnvironmentalSensor(sixNodeItem, 104)
        registerElectricalRedstone(sixNodeItem, 108)
        registerElectricalGate(sixNodeItem, 109)
        registerSixNodeMisc(sixNodeItem, 117)

        // ========== TRANSPARENT NODE REGISTRATION ==========
        registerPowerComponent(transparentNodeItem, 1)
        registerTransformer(transparentNodeItem, 2)
        registerBattery(transparentNodeItem, 16)
        registerElectricalAntenna(transparentNodeItem, 7)

        Eln.logger.info("SixNodeItem orderList size: ${sixNodeItem.orderList.size}")
        Eln.logger.info("Registered Electrical Age descriptors")
    }

    // ========== SIX NODE REGISTRATION METHODS ==========

    @JvmStatic
    private fun registerGround(sixNodeItem: SixNodeItem, id: Int) {
        val obj = Eln.obj.getObj("groundcable")
        val groundDescriptor = GroundCableDescriptor("Ground Cable", obj)
        sixNodeItem.addDescriptor(0 + (id shl 6), groundDescriptor)
    }

    @JvmStatic
    private fun registerElectricalSource(sixNodeItem: SixNodeItem, id: Int) {
        // DC Source 0-10V (Signal) - using signalsourcepot model
        val dcSource0_10V = ElectricalSourceDescriptor("DC Source 0-10V", Eln.obj.getObj("signalsourcepot"), true)
        sixNodeItem.addDescriptor(0 + (id shl 6), dcSource0_10V)

        // DC Source 0-20mA (Signal)
        val dcSource0_20mA = ElectricalSourceDescriptor("DC Source 0-20mA", Eln.obj.getObj("signalsourcepot"), true)
        sixNodeItem.addDescriptor(1 + (id shl 6), dcSource0_20mA)

        // AC Source 230V (Power)
        val acSource230V = ElectricalSourceDescriptor("AC Source 230V", Eln.obj.getObj("signalsource"), false)
        sixNodeItem.addDescriptor(2 + (id shl 6), acSource230V)

        // Battery Source (Power)
        val batterySource = ElectricalSourceDescriptor("Battery Source", Eln.obj.getObj("signalsource"), false)
        sixNodeItem.addDescriptor(3 + (id shl 6), batterySource)
    }

    @JvmStatic
    private fun registerElectricalCable(sixNodeItem: SixNodeItem, baseId: Int) {
        // Create render descriptors
        stdCableRenderSignal = CableRenderDescriptor("eln", "sprites/cable.png", 0.95f, 0.95f)
        stdCableRender50V = CableRenderDescriptor("eln", "sprites/cable.png", 1.95f, 0.95f)
        stdCableRender200V = CableRenderDescriptor("eln", "sprites/cable.png", 2.95f, 0.95f)
        stdCableRender800V = CableRenderDescriptor("eln", "sprites/cable.png", 3.95f, 0.95f)
        stdCableRender3200V = CableRenderDescriptor("eln", "sprites/cable.png", 4.95f, 0.95f)

        // Signal Cable
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

        // Low Voltage Cable
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

        // Battery Cable
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

        // Medium Voltage Cable
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

        // High Voltage Cable
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

        // Very High Voltage Cable
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

    @JvmStatic
    private fun registerThermalCable(sixNodeItem: SixNodeItem, id: Int) {
        // Standard Thermal Cable
        thermalCableDescriptor = ThermalCableDescriptor(
            "Thermal Cable",
            130.0, -100.0, 25.0, 100.0, 5.0, 10.0, 0.5,
            CableRenderDescriptor("eln", "sprites/thermalCable.png", 0.95f, 0.95f),
            "Transfers heat between nodes."
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), thermalCableDescriptor)

        // Insulated Thermal Cable
        thermalInsulatedCableDescriptor = ThermalCableDescriptor(
            "Thermal Insulated Cable",
            130.0, -100.0, 25.0, 100.0, 2.0, 2.0, 0.5,
            CableRenderDescriptor("eln", "sprites/thermalCableInsulated.png", 0.95f, 0.95f),
            "Insulated thermal cable with reduced heat loss."
        )
        sixNodeItem.addDescriptor(1 + (id shl 6), thermalInsulatedCableDescriptor)
    }

    @JvmStatic
    private fun registerLampSocket(sixNodeItem: SixNodeItem, id: Int) {
        // Lamp Socket E14 (Douille) - uses ClassicLampSocket model
        val lampSocketObj = Eln.obj.getObj("classiclampsocket")
        val lampSocketE14 = LampSocketDescriptor(
            "Lamp Socket E14",
            mods.eln.sixnode.lampsocket.LampSocketStandardObjRender(lampSocketObj, false),
            LampSocketType.Douille,
            true, 0, 0f, 0f, 0f
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), lampSocketE14)

        // Lamp Socket E27 (Douille variant) - uses ClassicLampSocket model
        val lampSocketE27 = LampSocketDescriptor(
            "Lamp Socket E27",
            mods.eln.sixnode.lampsocket.LampSocketStandardObjRender(lampSocketObj, false),
            LampSocketType.Douille,
            true, 0, 0f, 0f, 0f
        )
        sixNodeItem.addDescriptor(1 + (id shl 6), lampSocketE27)
    }

    @JvmStatic
    private fun registerLampSupply(sixNodeItem: SixNodeItem, id: Int) {
        val obj = Eln.obj.getObj("lampsupply")
        val lampSupplyDescriptor = LampSupplyDescriptor("Lamp Supply", obj, 0)
        sixNodeItem.addDescriptor(0 + (id shl 6), lampSupplyDescriptor)
    }

    @JvmStatic
    private fun registerBatteryCharger(sixNodeItem: SixNodeItem, id: Int) {
        val obj = Eln.obj.getObj("batterychargera")
        val batteryChargerDescriptor = BatteryChargerDescriptor(
            "Battery Charger", obj,
            lowVoltageCableDescriptor,
            Cable.LVU, 100.0
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), batteryChargerDescriptor)
    }

    @JvmStatic
    private fun registerPowerSocket(sixNodeItem: SixNodeItem, id: Int) {
        val obj = Eln.obj.getObj("powersocket")
        // LV Power Socket (50V)
        val powerSocketLV = PowerSocketDescriptor(1, "Power Socket LV", obj, 0)
        sixNodeItem.addDescriptor(0 + (id shl 6), powerSocketLV)

        // MV Power Socket (200V)
        val powerSocketMV = PowerSocketDescriptor(2, "Power Socket MV", obj, 0)
        sixNodeItem.addDescriptor(1 + (id shl 6), powerSocketMV)
    }

    @JvmStatic
    private fun registerWirelessSignal(sixNodeItem: SixNodeItem, id: Int) {
        // Wireless Signal Source - using signalsourcepot model
        val sourceObj = Eln.obj.getObj("signalsourcepot")
        val sourceRender = ElectricalGateSourceRenderObj(sourceObj)
        val wirelessSourceDescriptor = WirelessSignalSourceDescriptor(
            "Wireless Signal Source", sourceRender, 32, false
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), wirelessSourceDescriptor)

        // Wireless Signal TX
        val txObj = Eln.obj.getObj("wirelesssignaltx")
        val wirelessTxDescriptor = WirelessSignalTxDescriptor("Wireless Signal TX", txObj, 32)
        sixNodeItem.addDescriptor(1 + (id shl 6), wirelessTxDescriptor)

        // Wireless Signal RX
        val rxObj = Eln.obj.getObj("wirelesssignalrx")
        val wirelessRxDescriptor = WirelessSignalRxDescriptor("Wireless Signal RX", rxObj)
        sixNodeItem.addDescriptor(2 + (id shl 6), wirelessRxDescriptor)

        // Wireless Signal Repeater
        val repeaterObj = Eln.obj.getObj("wirelesssignalrepeater")
        val wirelessRepeaterDescriptor = WirelessSignalRepeaterDescriptor(
            "Wireless Signal Repeater", repeaterObj, 32
        )
        sixNodeItem.addDescriptor(3 + (id shl 6), wirelessRepeaterDescriptor)

        // Wireless Signal Analyser - skip (it's an item, not a SixNode)
    }

    @JvmStatic
    private fun registerElectricalDataLogger(sixNodeItem: SixNodeItem, id: Int) {
        // Electrical Data Logger - uses DataloggerCRTFloor model
        val dataLoggerDescriptor = ElectricalDataLoggerDescriptor(
            "Electrical Data Logger",
            false, "DataloggerCRTFloor",
            1.0f, 0.0f, 0.0f, "white"
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), dataLoggerDescriptor)

        // Data Logs Print - skip (it's an item, not a SixNode)
    }

    @JvmStatic
    private fun registerElectricalRelay(sixNodeItem: SixNodeItem, id: Int) {
        val obj = Eln.obj.getObj("relaysmall")
        val relayDescriptor = ElectricalRelayDescriptor(
            "Electrical Relay", obj,
            lowVoltageCableDescriptor
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), relayDescriptor)
    }

    @JvmStatic
    private fun registerElectricalGateSource(sixNodeItem: SixNodeItem, id: Int) {
        val gateObj = Eln.obj.getObj("signalsourcepot")
        val gateRender = ElectricalGateSourceRenderObj(gateObj)
        val gateSourceDescriptor = ElectricalGateSourceDescriptor(
            "Electrical Gate Source", gateRender, false, "signalsourcepot"
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), gateSourceDescriptor)
    }

    @JvmStatic
    private fun registerPassiveComponent(sixNodeItem: SixNodeItem, id: Int) {
        // Resistor - uses PowerElectricPrimitives model
        val resistorObj = Eln.obj.getObj("powerelectricprimitives")
        val resistorSeries = SerieEE(1.0, doubleArrayOf(10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0))
        val resistorDescriptor = ResistorDescriptor(
            "Resistor", resistorObj, resistorSeries, 0.0, false
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), resistorDescriptor)

        // Diode - uses PowerElectricPrimitives model
        val diodeObj = Eln.obj.getObj("powerelectricprimitives")
        val diodeFunction = mods.eln.misc.IFunction { v -> if (v > 0.7) (v - 0.7) / 0.01 else 0.0 }
        val diodeDescriptor = DiodeDescriptor(
            "Diode", diodeFunction, 1.0, 0.7, 0.01,
            Eln.cableThermalLoadInitializer, lowVoltageCableDescriptor, diodeObj
        )
        sixNodeItem.addDescriptor(1 + (id shl 6), diodeDescriptor)

        // Power Capacitor Six - uses PowerElectricPrimitives model
        val capacitorObj = Eln.obj.getObj("powerelectricprimitives")
        val capacitorSeries = SerieEE(-6.0, doubleArrayOf(1.0, 2.2, 3.3, 4.7, 6.8))
        val capacitorDescriptor = PowerCapacitorSixDescriptor(
            "Power Capacitor", capacitorObj, capacitorSeries, 1000.0
        )
        sixNodeItem.addDescriptor(2 + (id shl 6), capacitorDescriptor)

        // Power Inductor Six - uses PowerElectricPrimitives model
        val inductorObj = Eln.obj.getObj("powerelectricprimitives")
        val inductorSeries = SerieEE(-6.0, doubleArrayOf(1.0, 2.2, 3.3, 4.7, 6.8))
        val inductorDescriptor = PowerInductorSixDescriptor(
            "Power Inductor", inductorObj, inductorSeries
        )
        sixNodeItem.addDescriptor(3 + (id shl 6), inductorDescriptor)
    }

    @JvmStatic
    private fun registerSwitch(sixNodeItem: SixNodeItem, id: Int) {
        // Electrical Switch - uses LowVoltageSwitch model
        val switchObj = Eln.obj.getObj("lowvoltageswitch")
        val switchDescriptor = ElectricalSwitchDescriptor(
            "Electrical Switch",
            stdCableRender50V, switchObj,
            Cable.LVU, 1000.0, 0.01,
            Cable.LVU * 1.5, 2000.0,
            Eln.cableThermalLoadInitializer,
            false
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), switchDescriptor)

        // Electrical Breaker - uses ElectricalBreaker model
        val breakerObj = Eln.obj.getObj("electricalbreaker")
        val breakerDescriptor = ElectricalBreakerDescriptor("Electrical Breaker", breakerObj)
        sixNodeItem.addDescriptor(1 + (id shl 6), breakerDescriptor)
    }

    @JvmStatic
    private fun registerElectricalManager(sixNodeItem: SixNodeItem, id: Int) {
        // Energy Meter - uses EnergyMeter model
        val energyMeterObj = Eln.obj.getObj("energymeter")
        val energyMeterDescriptor = EnergyMeterDescriptor(
            "Energy Meter", energyMeterObj, 8, 0
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), energyMeterDescriptor)

        // Electrical Watch - uses DigitalWallClock model
        val watchObj = Eln.obj.getObj("digitalwallclock")
        val watchDescriptor = ElectricalWatchDescriptor(
            "Electrical Watch", watchObj, 10.0
        )
        sixNodeItem.addDescriptor(1 + (id shl 6), watchDescriptor)

        // Hub - uses hub model
        val hubObj = Eln.obj.getObj("hub")
        val hubDescriptor = HubDescriptor("Hub", hubObj)
        sixNodeItem.addDescriptor(2 + (id shl 6), hubDescriptor)
    }

    @JvmStatic
    private fun registerElectricalSensor(sixNodeItem: SixNodeItem, id: Int) {
        val sensorDescriptor = ElectricalSensorDescriptor(
            "Electrical Sensor", "VoltageSensor", false
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), sensorDescriptor)
    }

    @JvmStatic
    private fun registerThermalSensor(sixNodeItem: SixNodeItem, id: Int) {
        val obj = Eln.obj.getObj("temperaturesensor")
        val thermalSensorDescriptor = ThermalSensorDescriptor(
            "Thermal Sensor", obj, true
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), thermalSensorDescriptor)
    }

    @JvmStatic
    private fun registerElectricalVuMeter(sixNodeItem: SixNodeItem, id: Int) {
        val vuMeterDescriptor = ElectricalVuMeterDescriptor(
            "Electrical Vu Meter", "Vumeter", false
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), vuMeterDescriptor)
    }

    @JvmStatic
    private fun registerElectricalAlarm(sixNodeItem: SixNodeItem, id: Int) {
        val obj = Eln.obj.getObj("alarmmedium")
        val alarmDescriptor = ElectricalAlarmDescriptor(
            "Electrical Alarm", obj, 7, "eln:alarma", 11.0, 1.0f
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), alarmDescriptor)
    }

    @JvmStatic
    private fun registerElectricalEnvironmentalSensor(sixNodeItem: SixNodeItem, id: Int) {
        // Weather Sensor
        val weatherObj = Eln.obj.getObj("electricalweathersensor")
        val weatherSensorDescriptor = ElectricalWeatherSensorDescriptor(
            "Weather Sensor", weatherObj
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), weatherSensorDescriptor)

        // Wind Sensor - using Anemometer model
        val windObj = Eln.obj.getObj("anemometer")
        val windSensorDescriptor = ElectricalWindSensorDescriptor(
            "Wind Sensor", windObj, 30.0
        )
        sixNodeItem.addDescriptor(1 + (id shl 6), windSensorDescriptor)

        // Light Sensor
        val lightObj = Eln.obj.getObj("lightsensor")
        val lightSensorDescriptor = ElectricalLightSensorDescriptor(
            "Light Sensor", lightObj, false
        )
        sixNodeItem.addDescriptor(2 + (id shl 6), lightSensorDescriptor)

        // Fire Detector
        val fireObj = Eln.obj.getObj("firedetector")
        val fireDetectorDescriptor = ElectricalFireDetectorDescriptor(
            "Fire Detector", fireObj, 8.0, false
        )
        sixNodeItem.addDescriptor(3 + (id shl 6), fireDetectorDescriptor)

        // Entity Sensor - using ProximitySensor model
        val entityObj = Eln.obj.getObj("proximitysensor")
        val entitySensorDescriptor = ElectricalEntitySensorDescriptor(
            "Entity Sensor", entityObj, 8.0
        )
        sixNodeItem.addDescriptor(4 + (id shl 6), entitySensorDescriptor)
    }

    @JvmStatic
    private fun registerElectricalRedstone(sixNodeItem: SixNodeItem, id: Int) {
        // Redstone Input
        val redstoneInputObj = Eln.obj.getObj("redtoele")
        val redstoneInputDescriptor = ElectricalRedstoneInputDescriptor(
            "Redstone Input", redstoneInputObj
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), redstoneInputDescriptor)

        // Redstone Output
        val redstoneOutputObj = Eln.obj.getObj("eletored")
        val redstoneOutputDescriptor = ElectricalRedstoneOutputDescriptor(
            "Redstone Output", redstoneOutputObj
        )
        sixNodeItem.addDescriptor(1 + (id shl 6), redstoneOutputDescriptor)
    }

    @JvmStatic
    private fun registerElectricalGate(sixNodeItem: SixNodeItem, id: Int) {
        // Logic Gates - all use LogicGates model
        val logicObj = Eln.obj.getObj("logicgates")
        
        // AND Gate
        val andGateDescriptor = LogicGateDescriptor("AND Gate", logicObj, "AND", mods.eln.sixnode.logicgate.And::class.java)
        sixNodeItem.addDescriptor(0 + (id shl 6), andGateDescriptor)

        // OR Gate
        val orGateDescriptor = LogicGateDescriptor("OR Gate", logicObj, "OR", mods.eln.sixnode.logicgate.Or::class.java)
        sixNodeItem.addDescriptor(1 + (id shl 6), orGateDescriptor)

        // NOT Gate
        val notGateDescriptor = LogicGateDescriptor("NOT Gate", logicObj, "NOT", mods.eln.sixnode.logicgate.Not::class.java)
        sixNodeItem.addDescriptor(2 + (id shl 6), notGateDescriptor)

        // NAND Gate
        val nandGateDescriptor = LogicGateDescriptor("NAND Gate", logicObj, "AND", mods.eln.sixnode.logicgate.And::class.java)
        sixNodeItem.addDescriptor(3 + (id shl 6), nandGateDescriptor)

        // NOR Gate
        val norGateDescriptor = LogicGateDescriptor("NOR Gate", logicObj, "OR", mods.eln.sixnode.logicgate.Or::class.java)
        sixNodeItem.addDescriptor(4 + (id shl 6), norGateDescriptor)

        // XOR Gate
        val xorGateDescriptor = LogicGateDescriptor("XOR Gate", logicObj, "XOR", mods.eln.sixnode.logicgate.Xor::class.java)
        sixNodeItem.addDescriptor(5 + (id shl 6), xorGateDescriptor)

        // Electrical Math - using PLC model
        val mathObj = Eln.obj.getObj("plc")
        val mathDescriptor = ElectricalMathDescriptor("Electrical Math", mathObj)
        sixNodeItem.addDescriptor(6 + (id shl 6), mathDescriptor)

        // Electrical Timeout - using electricaltimer model
        val timeoutObj = Eln.obj.getObj("electricaltimer")
        val timeoutDescriptor = ElectricalTimeoutDescriptor("Electrical Timeout", timeoutObj)
        sixNodeItem.addDescriptor(7 + (id shl 6), timeoutDescriptor)
    }

    @JvmStatic
    private fun registerSixNodeMisc(sixNodeItem: SixNodeItem, id: Int) {
        // Tutorial Sign - using TutoPlate model
        val tutorialSignObj = Eln.obj.getObj("tutoplate")
        val tutorialSignDescriptor = TutorialSignDescriptor("Tutorial Sign", tutorialSignObj)
        sixNodeItem.addDescriptor(0 + (id shl 6), tutorialSignDescriptor)

        // Modbus RTU - using RTU model
        val modbusObj = Eln.obj.getObj("rtu")
        val modbusRtuDescriptor = ModbusRtuDescriptor("Modbus RTU", modbusObj)
        sixNodeItem.addDescriptor(1 + (id shl 6), modbusRtuDescriptor)
    }

    // ========== TRANSPARENT NODE REGISTRATION METHODS ==========

    @JvmStatic
    private fun registerPowerComponent(transparentNodeItem: TransparentNodeItem, id: Int) {
        // Power Capacitor - uses PowerElectricPrimitives model
        val capacitorObj = Eln.obj.getObj("powerelectricprimitives")
        val capacitorSeries = SerieEE(-6.0, doubleArrayOf(1.0, 2.2, 3.3, 4.7, 6.8))
        val powerCapacitorDescriptor = PowerCapacitorDescriptor(
            "Power Capacitor", capacitorObj, capacitorSeries, 1000.0
        )
        transparentNodeItem.addDescriptor(0 + (id shl 6), powerCapacitorDescriptor)

        // Power Inductor - uses PowerElectricPrimitives model
        val inductorObj = Eln.obj.getObj("powerelectricprimitives")
        val inductorSeries = SerieEE(-6.0, doubleArrayOf(1.0, 2.2, 3.3, 4.7, 6.8))
        val powerInductorDescriptor = PowerInductorDescriptor(
            "Power Inductor", inductorObj, inductorSeries
        )
        transparentNodeItem.addDescriptor(1 + (id shl 6), powerInductorDescriptor)
    }

    @JvmStatic
    private fun registerTransformer(transparentNodeItem: TransparentNodeItem, id: Int) {
        val transformerObj = Eln.obj.getObj("powerpole")
        val transformerDescriptor = TransformerDescriptor(
            "Transformer", transformerObj, null, null, 0.1f
        )
        transparentNodeItem.addDescriptor(0 + (id shl 6), transformerDescriptor)
    }

    @JvmStatic
    private fun registerBattery(transparentNodeItem: TransparentNodeItem, id: Int) {
        // Battery - using simple constructor parameters
        val batteryDescriptor = BatteryDescriptor(
            "Battery", "battery",
            lowVoltageCableDescriptor,
            0.0, false, false,
            Eln.batteryVoltageFunctionTable,
            Cable.LVU, 100.0, 0.001,
            50.0, 3600.0, 0.9, 2 * 3600.0,
            120.0, Eln.cableWarmLimit, -100.0,
            "Rechargeable battery for energy storage."
        )
        transparentNodeItem.addDescriptor(0 + (id shl 6), batteryDescriptor)
    }

    @JvmStatic
    private fun registerElectricalAntenna(transparentNodeItem: TransparentNodeItem, id: Int) {
        // Electrical Antenna TX - simplified for now
        // Full implementation requires complex parameters
    }
}

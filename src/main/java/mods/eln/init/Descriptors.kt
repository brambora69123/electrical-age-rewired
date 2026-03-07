package mods.eln.init

import mods.eln.Eln
import mods.eln.cable.CableRenderDescriptor
import mods.eln.ghost.GhostGroup
import mods.eln.init.Cable
import mods.eln.misc.Direction
import mods.eln.misc.LRDU
import mods.eln.misc.series.SerieEE
import mods.eln.node.six.SixNodeDescriptor
import mods.eln.node.six.SixNodeItem
import mods.eln.node.transparent.TransparentNodeItem
import mods.eln.sixnode.EmergencyLampDescriptor
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
import mods.eln.sixnode.lampsocket.LampSocketStandardObjRender
import mods.eln.sixnode.lampsocket.LampSocketSuspendedObjRender
import mods.eln.sixnode.lampsocket.LampSocketType
import mods.eln.sixnode.lampsupply.LampSupplyDescriptor
import mods.eln.sixnode.logicgate.*
import mods.eln.sixnode.modbusrtu.ModbusRtuDescriptor
import mods.eln.sixnode.AnalogChipDescriptor
import mods.eln.sixnode.OpAmp
import mods.eln.sixnode.PIDRegulator
import mods.eln.sixnode.PIDRegulatorElement
import mods.eln.sixnode.PIDRegulatorRender
import mods.eln.sixnode.VoltageControlledSawtoothOscillator
import mods.eln.sixnode.VoltageControlledSineOscillator
import mods.eln.sixnode.Amplifier
import mods.eln.sixnode.AmplifierElement
import mods.eln.sixnode.AmplifierRender
import mods.eln.sixnode.VoltageControlledAmplifier
import mods.eln.sixnode.SummingUnit
import mods.eln.sixnode.SummingUnitElement
import mods.eln.sixnode.SummingUnitRender
import mods.eln.sixnode.SampleAndHold
import mods.eln.sixnode.Filter
import mods.eln.sixnode.FilterElement
import mods.eln.sixnode.FilterRender
import mods.eln.sixnode.powercapacitorsix.PowerCapacitorSixDescriptor
import mods.eln.sixnode.powerinductorsix.PowerInductorSixDescriptor
import mods.eln.sixnode.powersocket.PowerSocketDescriptor
import mods.eln.sixnode.resistor.ResistorDescriptor
import mods.eln.signalinductor.SignalInductorDescriptor
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
        registerAnalogChips(sixNodeItem, 124)
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
        // Ground Cable (subId=0)
        val groundObj = Eln.obj.getObj("groundcable")
        val groundDescriptor = GroundCableDescriptor("Ground Cable", groundObj)
        sixNodeItem.addDescriptor(0 + (id shl 6), groundDescriptor)

        // Hub (subId=8)
        val hubObj = Eln.obj.getObj("hub")
        val hubDescriptor = HubDescriptor("Hub", hubObj)
        sixNodeItem.addDescriptor(8 + (id shl 6), hubDescriptor)
    }

    @JvmStatic
    private fun registerElectricalSource(sixNodeItem: SixNodeItem, id: Int) {
        val electricalSource = ElectricalSourceDescriptor(
            "Electrical Source",
            Eln.obj.getObj("voltagesource"),
            false
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), electricalSource)

        val signalSource = ElectricalSourceDescriptor(
            "Signal Source",
            Eln.obj.getObj("signalsource"),
            true
        )
        sixNodeItem.addDescriptor(1 + (id shl 6), signalSource)
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
        val classicLampSocket = Eln.obj.getObj("classiclampsocket")
        val robustLamp = Eln.obj.getObj("robustlamp")
        val flatLamp = Eln.obj.getObj("flatlamp")
        val simpleLamp = Eln.obj.getObj("simplelamp")
        val fluorescentLamp = Eln.obj.getObj("fluorescentlamp")
        val streetLight = Eln.obj.getObj("streetlight")
        val sconceLamp = Eln.obj.getObj("sconcelamp")
        val suspendedLamp = Eln.obj.getObj("robustlampsuspended")
        val emergencyLamp = Eln.obj.getObj("emergencyexitlighting")

        val lampSocketA = LampSocketDescriptor(
            "Lamp Socket A",
            LampSocketStandardObjRender(classicLampSocket, false),
            LampSocketType.Douille,
            false, 4, 0f, 0f, 0f
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), lampSocketA)

        val lampSocketProjector = LampSocketDescriptor(
            "Lamp Socket B Projector",
            LampSocketStandardObjRender(classicLampSocket, false),
            LampSocketType.Douille,
            false, 10, -90f, 90f, 0f
        )
        sixNodeItem.addDescriptor(1 + (id shl 6), lampSocketProjector)

        val robustLampSocket = LampSocketDescriptor(
            "Robust Lamp Socket",
            LampSocketStandardObjRender(robustLamp, true),
            LampSocketType.Douille,
            false, 3, 0f, 0f, 0f
        )
        robustLampSocket.setInitialOrientation(-90f)
        sixNodeItem.addDescriptor(4 + (id shl 6), robustLampSocket)

        val flatLampSocket = LampSocketDescriptor(
            "Flat Lamp Socket",
            LampSocketStandardObjRender(flatLamp, true),
            LampSocketType.Douille,
            false, 3, 0f, 0f, 0f
        )
        sixNodeItem.addDescriptor(5 + (id shl 6), flatLampSocket)

        val simpleLampSocket = LampSocketDescriptor(
            "Simple Lamp Socket",
            LampSocketStandardObjRender(simpleLamp, true),
            LampSocketType.Douille,
            false, 3, 0f, 0f, 0f
        )
        sixNodeItem.addDescriptor(6 + (id shl 6), simpleLampSocket)

        val fluorescentLampSocket = LampSocketDescriptor(
            "Fluorescent Lamp Socket",
            LampSocketStandardObjRender(fluorescentLamp, true),
            LampSocketType.Douille,
            false, 4, 0f, 0f, 0f
        )
        fluorescentLampSocket.cableLeft = false
        fluorescentLampSocket.cableRight = false
        sixNodeItem.addDescriptor(7 + (id shl 6), fluorescentLampSocket)

        val streetLightSocket = LampSocketDescriptor(
            "Street Light",
            LampSocketStandardObjRender(streetLight, true),
            LampSocketType.Douille,
            false, 0, 0f, 0f, 0f
        )
        streetLightSocket.setPlaceDirection(Direction.YN)
        val streetLightGhost = GhostGroup()
        streetLightGhost.addElement(1, 0, 0)
        streetLightGhost.addElement(2, 0, 0)
        streetLightSocket.setGhostGroup(streetLightGhost)
        streetLightSocket.cameraOpt = false
        sixNodeItem.addDescriptor(8 + (id shl 6), streetLightSocket)

        val sconceLampSocket = LampSocketDescriptor(
            "Sconce Lamp Socket",
            LampSocketStandardObjRender(sconceLamp, false),
            LampSocketType.Douille,
            true, 3, 0f, 0f, 0f
        )
        sconceLampSocket.setPlaceDirection(arrayOf(Direction.XP, Direction.XN, Direction.ZP, Direction.ZN))
        sconceLampSocket.setInitialOrientation(-90f)
        sconceLampSocket.setUserRotationLibertyDegrees(true)
        sixNodeItem.addDescriptor(9 + (id shl 6), sconceLampSocket)

        val suspendedLampSocket = LampSocketDescriptor(
            "Suspended Lamp Socket",
            LampSocketSuspendedObjRender(suspendedLamp, true, 3),
            LampSocketType.Douille,
            false, 3, 0f, 0f, 0f
        )
        suspendedLampSocket.setPlaceDirection(Direction.YP)
        suspendedLampSocket.cameraOpt = false
        sixNodeItem.addDescriptor(12 + (id shl 6), suspendedLampSocket)

        val longSuspendedLampSocket = LampSocketDescriptor(
            "Long Suspended Lamp Socket",
            LampSocketSuspendedObjRender(suspendedLamp, true, 4),
            LampSocketType.Douille,
            false, 4, 0f, 0f, 0f
        )
        longSuspendedLampSocket.setPlaceDirection(Direction.YP)
        longSuspendedLampSocket.cameraOpt = false
        sixNodeItem.addDescriptor(13 + (id shl 6), longSuspendedLampSocket)

        sixNodeItem.addDescriptor(
            15 + (id shl 6),
            EmergencyLampDescriptor(
                "50V Emergency Lamp",
                lowVoltageCableDescriptor,
                10.0 * 60.0 * 10.0,
                10.0,
                5.0,
                6,
                emergencyLamp
            )
        )
        sixNodeItem.addDescriptor(
            16 + (id shl 6),
            EmergencyLampDescriptor(
                "200V Emergency Lamp",
                mediumVoltageCableDescriptor,
                10.0 * 60.0 * 20.0,
                25.0,
                10.0,
                8,
                emergencyLamp
            )
        )
    }

    @JvmStatic
    private fun registerLampSupply(sixNodeItem: SixNodeItem, id: Int) {
        val obj = Eln.obj.getObj("distributionboard")
        val lampSupplyDescriptor = LampSupplyDescriptor("Lamp Supply", obj, 32)
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
        // Wireless Signal Receiver (subId=0)
        val rxObj = Eln.obj.getObj("wirelesssignalrx")
        val wirelessRxDescriptor = WirelessSignalRxDescriptor("Wireless Signal Receiver", rxObj)
        sixNodeItem.addDescriptor(0 + (id shl 6), wirelessRxDescriptor)

        // Wireless Signal Transmitter (subId=8)
        val txObj = Eln.obj.getObj("wirelesssignaltx")
        val wirelessTxDescriptor = WirelessSignalTxDescriptor("Wireless Signal Transmitter", txObj, 32)
        sixNodeItem.addDescriptor(8 + (id shl 6), wirelessTxDescriptor)

        // Wireless Signal Repeater (subId=16)
        val repeaterObj = Eln.obj.getObj("wirelesssignalrepeater")
        val wirelessRepeaterDescriptor = WirelessSignalRepeaterDescriptor(
            "Wireless Signal Repeater", repeaterObj, 32
        )
        sixNodeItem.addDescriptor(16 + (id shl 6), wirelessRepeaterDescriptor)
    }

    @JvmStatic
    private fun registerElectricalDataLogger(sixNodeItem: SixNodeItem, id: Int) {
        // Data Logger (subId=0)
        val dataLoggerDescriptor = ElectricalDataLoggerDescriptor(
            "Data Logger", true,
            "DataloggerCRTFloor", 1.0f, 0.5f, 0.0f, "\u00A76"
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), dataLoggerDescriptor)

        // Modern Data Logger (subId=1)
        val modernDataLoggerDescriptor = ElectricalDataLoggerDescriptor(
            "Modern Data Logger", false,
            "FlatScreenMonitor", 0.0f, 1.0f, 0.0f, "\u00A7a"
        )
        sixNodeItem.addDescriptor(1 + (id shl 6), modernDataLoggerDescriptor)

        // Industrial Data Logger (subId=2)
        val industrialDataLoggerDescriptor = ElectricalDataLoggerDescriptor(
            "Industrial Data Logger", false,
            "IndustrialPanel", 0.0f, 0.0f, 1.0f, "\u00A7c"
        )
        sixNodeItem.addDescriptor(2 + (id shl 6), industrialDataLoggerDescriptor)
    }

    @JvmStatic
    private fun registerElectricalRelay(sixNodeItem: SixNodeItem, id: Int) {
        // Low Voltage Relay (subId=0)
        val lvRelayObj = Eln.obj.getObj("RelayBig")
        val lvRelayDescriptor = ElectricalRelayDescriptor(
            "Low Voltage Relay", lvRelayObj,
            lowVoltageCableDescriptor
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), lvRelayDescriptor)

        // Medium Voltage Relay (subId=1)
        val mvRelayDescriptor = ElectricalRelayDescriptor(
            "Medium Voltage Relay", lvRelayObj,
            mediumVoltageCableDescriptor
        )
        sixNodeItem.addDescriptor(1 + (id shl 6), mvRelayDescriptor)

        // High Voltage Relay (subId=2)
        val hvRelayObj = Eln.obj.getObj("relay800")
        val hvRelayDescriptor = ElectricalRelayDescriptor(
            "High Voltage Relay", hvRelayObj,
            highVoltageCableDescriptor
        )
        sixNodeItem.addDescriptor(2 + (id shl 6), hvRelayDescriptor)

        // Very High Voltage Relay (subId=3)
        val vhvRelayDescriptor = ElectricalRelayDescriptor(
            "Very High Voltage Relay", hvRelayObj,
            veryHighVoltageCableDescriptor
        )
        sixNodeItem.addDescriptor(3 + (id shl 6), vhvRelayDescriptor)

        // Signal Relay (subId=4)
        val signalRelayObj = Eln.obj.getObj("RelaySmall")
        val signalRelayDescriptor = ElectricalRelayDescriptor(
            "Signal Relay", signalRelayObj,
            signalDescriptor
        )
        sixNodeItem.addDescriptor(4 + (id shl 6), signalRelayDescriptor)
    }

    @JvmStatic
    private fun registerElectricalGateSource(sixNodeItem: SixNodeItem, id: Int) {
        val signalsourcepotObj = Eln.obj.getObj("signalsourcepot")
        val signalsourcepotRender = ElectricalGateSourceRenderObj(signalsourcepotObj)
        val ledswitchObj = Eln.obj.getObj("ledswitch")
        val ledswitchRender = ElectricalGateSourceRenderObj(ledswitchObj)

        // Signal Trimmer (subId=0)
        val signalTrimmerDescriptor = ElectricalGateSourceDescriptor(
            "Signal Trimmer", signalsourcepotRender, false, "trimmer"
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), signalTrimmerDescriptor)

        // Signal Switch (subId=1)
        val signalSwitchDescriptor = ElectricalGateSourceDescriptor(
            "Signal Switch", ledswitchRender, true,
            if (Eln.noSymbols) "signalswitch" else "switch"
        )
        sixNodeItem.addDescriptor(1 + (id shl 6), signalSwitchDescriptor)

        // Signal Button (subId=8)
        val signalButtonDescriptor = ElectricalGateSourceDescriptor(
            "Signal Button", ledswitchRender, true, "button"
        )
        signalButtonDescriptor.setWithAutoReset()
        sixNodeItem.addDescriptor(8 + (id shl 6), signalButtonDescriptor)

        // Wireless Button (subId=12)
        val wirelessButtonDescriptor = WirelessSignalSourceDescriptor(
            "Wireless Button", ledswitchRender, 32, true
        )
        sixNodeItem.addDescriptor(12 + (id shl 6), wirelessButtonDescriptor)

        // Wireless Switch (subId=16)
        val wirelessSwitchDescriptor = WirelessSignalSourceDescriptor(
            "Wireless Switch", ledswitchRender, 32, false
        )
        sixNodeItem.addDescriptor(16 + (id shl 6), wirelessSwitchDescriptor)
    }

    @JvmStatic
    private fun registerPassiveComponent(sixNodeItem: SixNodeItem, id: Int) {
        // Resistor - uses PowerElectricPrimitives model (subId=0)
        val resistorObj = Eln.obj.getObj("powerelectricprimitives")
        val resistorSeries = SerieEE(1.0, doubleArrayOf(10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0))
        val resistorDescriptor = ResistorDescriptor(
            "Resistor", resistorObj, resistorSeries, 0.0, false
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), resistorDescriptor)

        // 10A Diode (subId=1)
        val diodeObj = Eln.obj.getObj("powerelectricprimitives")
        val diode10AFunction = mods.eln.misc.IFunction { v -> if (v > 0.7) (v - 0.7) / 0.01 else 0.0 }
        val diode10ADescriptor = DiodeDescriptor(
            "10A Diode", diode10AFunction, 10.0, 1.0, 10.0,
            Eln.cableThermalLoadInitializer, lowVoltageCableDescriptor, diodeObj
        )
        sixNodeItem.addDescriptor(1 + (id shl 6), diode10ADescriptor)

        // 25A Diode (subId=2)
        val diode25AFunction = mods.eln.misc.IFunction { v -> if (v > 0.7) (v - 0.7) / 0.01 else 0.0 }
        val diode25ADescriptor = DiodeDescriptor(
            "25A Diode", diode25AFunction, 25.0, 1.0, 25.0,
            Eln.cableThermalLoadInitializer, lowVoltageCableDescriptor, diodeObj
        )
        sixNodeItem.addDescriptor(2 + (id shl 6), diode25ADescriptor)

        // Signal Diode (subId=8)
        val signalDiodeBaseFunction = mods.eln.misc.FunctionTableYProtect(
            doubleArrayOf(0.0, 0.01, 0.03, 0.1, 0.2, 0.4, 0.8, 1.2), 1.0,
            0.0, 5.0
        )
        val signalDiodeFunction = signalDiodeBaseFunction.duplicate(1.0, 0.1)
        val signalDiodeDescriptor = DiodeDescriptor(
            "Signal Diode", signalDiodeFunction, 0.1, 1.0, 0.1,
            Eln.cableThermalLoadInitializer, signalDescriptor, diodeObj
        )
        sixNodeItem.addDescriptor(8 + (id shl 6), signalDiodeDescriptor)

        // Signal 20H Inductor (subId=16) - uses addWithoutRegistry
        // TODO: Fix type mismatch - SignalInductorDescriptor registration
        /*
        val signalInductorDescriptor = SignalInductorDescriptor(
            "Signal 20H Inductor", 20.0, lowVoltageCableDescriptor
        )
        signalInductorDescriptor.setDefaultIcon("empty-texture")
        sixNodeItem.addWithoutRegistry(16 + (id shl 6), signalInductorDescriptor as SixNodeDescriptor)
        */

        // Power Capacitor (subId=32)
        // TODO: Fix type mismatch - PowerCapacitorSixDescriptor registration  
        /*
        val capacitorObj = Eln.obj.getObj("powerelectricprimitives")
        val capacitorSeries = SerieEE.newE6(-1)
        val capacitorDescriptor = PowerCapacitorSixDescriptor(
            "Power Capacitor", capacitorObj, capacitorSeries, 60.0 * 2000.0
        )
        sixNodeItem.addDescriptor(32 + (id shl 6), capacitorDescriptor as SixNodeDescriptor)
        */

        // Power Inductor (subId=34)
        // TODO: Fix type mismatch - PowerInductorSixDescriptor registration
        /*
        val inductorObj = Eln.obj.getObj("powerelectricprimitives")
        val inductorSeries = SerieEE.newE6(-1)
        val inductorDescriptor = PowerInductorSixDescriptor(
            "Power Inductor", inductorObj, inductorSeries
        )
        sixNodeItem.addDescriptor(34 + (id shl 6), inductorDescriptor as SixNodeDescriptor)
        */
    }

    @JvmStatic
    private fun registerSwitch(sixNodeItem: SixNodeItem, id: Int) {
        // High Voltage Switch (subId=0) - 800V
        val hvSwitchObj = Eln.obj.getObj("HighVoltageSwitch")
        val hvSwitchDescriptor = ElectricalSwitchDescriptor(
            "High Voltage Switch",
            stdCableRender800V, hvSwitchObj,
            Cable.HVU, Cable.HVP(), highVoltageCableDescriptor.electricalRs * 2,
            Cable.HVU * 1.5, Cable.HVP() * 1.2,
            Eln.cableThermalLoadInitializer, false
        )
        sixNodeItem.addDescriptor(0 + (id shl 6), hvSwitchDescriptor)

        // Low Voltage Switch (subId=1) - 50V
        val lvSwitchObj = Eln.obj.getObj("LowVoltageSwitch")
        val lvSwitchDescriptor = ElectricalSwitchDescriptor(
            "Low Voltage Switch",
            stdCableRender50V, lvSwitchObj,
            Cable.LVU, Cable.LVP(), lowVoltageCableDescriptor.electricalRs * 2,
            Cable.LVU * 1.5, Cable.LVP() * 1.2,
            Eln.cableThermalLoadInitializer, false
        )
        sixNodeItem.addDescriptor(1 + (id shl 6), lvSwitchDescriptor)

        // Medium Voltage Switch (subId=2) - 200V
        val mvSwitchDescriptor = ElectricalSwitchDescriptor(
            "Medium Voltage Switch",
            stdCableRender200V, lvSwitchObj,
            Cable.MVU, Cable.MVP(), mediumVoltageCableDescriptor.electricalRs * 2,
            Cable.MVU * 1.5, Cable.MVP() * 1.2,
            Eln.cableThermalLoadInitializer, false
        )
        sixNodeItem.addDescriptor(2 + (id shl 6), mvSwitchDescriptor)

        // Signal Switch (subId=3)
        val signalSwitchDescriptor = ElectricalSwitchDescriptor(
            "Signal Switch",
            stdCableRenderSignal, lvSwitchObj,
            Cable.SVU, Cable.SVP, 0.02,
            Cable.SVU * 1.5, Cable.SVP * 1.2,
            Eln.cableThermalLoadInitializer, true
        )
        sixNodeItem.addDescriptor(3 + (id shl 6), signalSwitchDescriptor)

        // Very High Voltage Switch (subId=4) - 3200V
        val vhvSwitchDescriptor = ElectricalSwitchDescriptor(
            "Very High Voltage Switch",
            stdCableRender3200V, hvSwitchObj,
            Cable.VHVU, Cable.VHVP(), veryHighVoltageCableDescriptor.electricalRs * 2,
            Cable.VHVU * 1.5, Cable.VHVP() * 1.2,
            Eln.cableThermalLoadInitializer, false
        )
        sixNodeItem.addDescriptor(4 + (id shl 6), vhvSwitchDescriptor)

        // Signal Switch with LED (subId=8)
        val ledSwitchObj = Eln.obj.getObj("ledswitch")
        val signalSwitchLedDescriptor = ElectricalSwitchDescriptor(
            "Signal Switch with LED",
            stdCableRenderSignal, ledSwitchObj,
            Cable.SVU, Cable.SVP, 0.02,
            Cable.SVU * 1.5, Cable.SVP * 1.2,
            Eln.cableThermalLoadInitializer, true
        )
        sixNodeItem.addDescriptor(8 + (id shl 6), signalSwitchLedDescriptor)
    }

    @JvmStatic
    private fun registerElectricalManager(sixNodeItem: SixNodeItem, id: Int) {
        // Electrical Breaker (subId=0)
        val breakerObj = Eln.obj.getObj("ElectricalBreaker")
        val breakerDescriptor = ElectricalBreakerDescriptor("Electrical Breaker", breakerObj)
        sixNodeItem.addDescriptor(0 + (id shl 6), breakerDescriptor)

        // Energy Meter (subId=4)
        val energyMeterObj = Eln.obj.getObj("energymeter")
        val energyMeterDescriptor = EnergyMeterDescriptor(
            "Energy Meter", energyMeterObj, 8, 0
        )
        sixNodeItem.addDescriptor(4 + (id shl 6), energyMeterDescriptor)

        // Advanced Energy Meter (subId=5)
        val advancedEnergyMeterObj = Eln.obj.getObj("AdvancedEnergyMeter")
        val advancedEnergyMeterDescriptor = EnergyMeterDescriptor(
            "Advanced Energy Meter", advancedEnergyMeterObj, 7, 8
        )
        sixNodeItem.addDescriptor(5 + (id shl 6), advancedEnergyMeterDescriptor)
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
        val logicObj = Eln.obj.getObj("LogicGates")

        // NOT Chip (subId=0)
        val notGateDescriptor = LogicGateDescriptor("NOT Chip", logicObj, "NOT", mods.eln.sixnode.logicgate.Not::class.java)
        sixNodeItem.addDescriptor(0 + (id shl 6), notGateDescriptor)

        // AND Chip (subId=1)
        val andGateDescriptor = LogicGateDescriptor("AND Chip", logicObj, "AND", mods.eln.sixnode.logicgate.And::class.java)
        sixNodeItem.addDescriptor(1 + (id shl 6), andGateDescriptor)

        // NAND Chip (subId=2)
        val nandGateDescriptor = LogicGateDescriptor("NAND Chip", logicObj, "NAND", mods.eln.sixnode.logicgate.Nand::class.java)
        sixNodeItem.addDescriptor(2 + (id shl 6), nandGateDescriptor)

        // OR Chip (subId=3)
        val orGateDescriptor = LogicGateDescriptor("OR Chip", logicObj, "OR", mods.eln.sixnode.logicgate.Or::class.java)
        sixNodeItem.addDescriptor(3 + (id shl 6), orGateDescriptor)

        // NOR Chip (subId=4)
        val norGateDescriptor = LogicGateDescriptor("NOR Chip", logicObj, "NOR", mods.eln.sixnode.logicgate.Nor::class.java)
        sixNodeItem.addDescriptor(4 + (id shl 6), norGateDescriptor)

        // XOR Chip (subId=5)
        val xorGateDescriptor = LogicGateDescriptor("XOR Chip", logicObj, "XOR", mods.eln.sixnode.logicgate.Xor::class.java)
        sixNodeItem.addDescriptor(5 + (id shl 6), xorGateDescriptor)

        // XNOR Chip (subId=6)
        val xnorGateDescriptor = LogicGateDescriptor("XNOR Chip", logicObj, "XNOR", mods.eln.sixnode.logicgate.XNor::class.java)
        sixNodeItem.addDescriptor(6 + (id shl 6), xnorGateDescriptor)

        // PAL Chip (subId=7)
        val palDescriptor = PalDescriptor("PAL Chip", logicObj)
        sixNodeItem.addDescriptor(7 + (id shl 6), palDescriptor)

        // Schmitt Trigger Chip (subId=8)
        val schmittTriggerDescriptor = LogicGateDescriptor("Schmitt Trigger Chip", logicObj, "SCHMITT", mods.eln.sixnode.logicgate.SchmittTrigger::class.java)
        sixNodeItem.addDescriptor(8 + (id shl 6), schmittTriggerDescriptor)

        // D Flip Flop Chip (subId=9)
        val dFlipFlopDescriptor = LogicGateDescriptor("D Flip Flop Chip", logicObj, "DFF", mods.eln.sixnode.logicgate.DFlipFlop::class.java)
        sixNodeItem.addDescriptor(9 + (id shl 6), dFlipFlopDescriptor)

        // Oscillator Chip (subId=10)
        val oscillatorDescriptor = LogicGateDescriptor("Oscillator Chip", logicObj, "OSC", mods.eln.sixnode.logicgate.Oscillator::class.java)
        sixNodeItem.addDescriptor(10 + (id shl 6), oscillatorDescriptor)

        // JK Flip Flop Chip (subId=11)
        val jkFlipFlopDescriptor = LogicGateDescriptor("JK Flip Flop Chip", logicObj, "JKFF", mods.eln.sixnode.logicgate.JKFlipFlop::class.java)
        sixNodeItem.addDescriptor(11 + (id shl 6), jkFlipFlopDescriptor)
    }

    @JvmStatic
    private fun registerAnalogChips(sixNodeItem: SixNodeItem, id: Int) {
        val analogObj = Eln.obj.getObj("AnalogChips")

        // OpAmp (subId=0)
        val opAmpDescriptor = AnalogChipDescriptor("OpAmp", analogObj, "OP", OpAmp::class.java)
        sixNodeItem.addDescriptor(0 + (id shl 6), opAmpDescriptor)

        // PID Regulator (subId=1)
        val pidRegulatorDescriptor = AnalogChipDescriptor("PID Regulator", analogObj, "PID",
            PIDRegulator::class.java,
            PIDRegulatorElement::class.java,
            PIDRegulatorRender::class.java)
        sixNodeItem.addDescriptor(1 + (id shl 6), pidRegulatorDescriptor)

        // Voltage controlled sawtooth oscillator (subId=2)
        val vcoSawDescriptor = AnalogChipDescriptor("Voltage controlled sawtooth oscillator", analogObj, "VCO-SAW",
            VoltageControlledSawtoothOscillator::class.java)
        sixNodeItem.addDescriptor(2 + (id shl 6), vcoSawDescriptor)

        // Voltage controlled sine oscillator (subId=3)
        val vcoSinDescriptor = AnalogChipDescriptor("Voltage controlled sine oscillator", analogObj, "VCO-SIN",
            VoltageControlledSineOscillator::class.java)
        sixNodeItem.addDescriptor(3 + (id shl 6), vcoSinDescriptor)

        // Amplifier (subId=4)
        val amplifierDescriptor = AnalogChipDescriptor("Amplifier", analogObj, "AMP",
            Amplifier::class.java,
            AmplifierElement::class.java,
            AmplifierRender::class.java)
        sixNodeItem.addDescriptor(4 + (id shl 6), amplifierDescriptor)

        // Voltage controlled amplifier (subId=5)
        val vcaDescriptor = AnalogChipDescriptor("Voltage controlled amplifier", analogObj, "VCA",
            VoltageControlledAmplifier::class.java)
        sixNodeItem.addDescriptor(5 + (id shl 6), vcaDescriptor)

        // Configurable summing unit (subId=6)
        val summingUnitDescriptor = AnalogChipDescriptor("Configurable summing unit", analogObj, "SUM",
            SummingUnit::class.java,
            SummingUnitElement::class.java,
            SummingUnitRender::class.java)
        sixNodeItem.addDescriptor(6 + (id shl 6), summingUnitDescriptor)

        // Sample and hold (subId=7)
        val sahDescriptor = AnalogChipDescriptor("Sample and hold", analogObj, "SAH",
            SampleAndHold::class.java)
        sixNodeItem.addDescriptor(7 + (id shl 6), sahDescriptor)

        // Lowpass filter (subId=8)
        val lpfDescriptor = AnalogChipDescriptor("Lowpass filter", analogObj, "LPF",
            Filter::class.java,
            FilterElement::class.java,
            FilterRender::class.java)
        sixNodeItem.addDescriptor(8 + (id shl 6), lpfDescriptor)
    }

    @JvmStatic
    private fun registerSixNodeMisc(sixNodeItem: SixNodeItem, id: Int) {
        // Modbus RTU (subId=0)
        val modbusObj = Eln.obj.getObj("rtu")
        val modbusRtuDescriptor = ModbusRtuDescriptor("Modbus RTU", modbusObj)
        // Note: In original, this was only registered if modbusEnable was true
        sixNodeItem.addDescriptor(0 + (id shl 6), modbusRtuDescriptor)

        // Analog Watch (subId=4)
        val analogWatchObj = Eln.obj.getObj("WallClock")
        val analogWatchDescriptor = ElectricalWatchDescriptor(
            "Analog Watch", analogWatchObj, 20000.0 / (3600 * 40)
        )
        sixNodeItem.addDescriptor(4 + (id shl 6), analogWatchDescriptor)

        // Digital Watch (subId=5)
        val digitalWatchObj = Eln.obj.getObj("DigitalWallClock")
        val digitalWatchDescriptor = ElectricalWatchDescriptor(
            "Digital Watch", digitalWatchObj, 20000.0 / (3600 * 15)
        )
        sixNodeItem.addDescriptor(5 + (id shl 6), digitalWatchDescriptor)

        // Tutorial Sign (subId=8)
        val tutorialSignObj = Eln.obj.getObj("tutoplate")
        val tutorialSignDescriptor = TutorialSignDescriptor("Tutorial Sign", tutorialSignObj)
        sixNodeItem.addDescriptor(8 + (id shl 6), tutorialSignDescriptor)
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

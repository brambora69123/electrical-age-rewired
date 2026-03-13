import json
import os

models_path = "src/main/resources/assets/eln/models/item/"

# Backgrounds: low (50V), medium (200V), high (800V), veryhigh (3200V), signal (0-50V), thermal, neutral
# Mapping format: safe_name: (background, icon_path)
# If background is provided, safe_name_background.json is created.
mapping = {
    # Tools (No background)
    "multimeter": (None, "eln:items/multimeter"),
    "allmeter": (None, "eln:items/allmeter"),
    "thermometer": (None, "eln:items/thermometer"),
    "orescanner": (None, "eln:items/orescanner"),
    "x-rayscanner": (None, "eln:items/x-rayscanner"),
    "wirelessanalyser": (None, "eln:items/wirelessanalyser"),
    "wrench": (None, "eln:items/wrench"),
    "brush": (None, "eln:items/brush"),
    "smallflashlightoff": (None, "eln:items/smallflashlightoff"),
    "improvedflashlightoff": (None, "eln:items/improvedflashlightoff"),
    "portableelectricalminingdrill": (None, "eln:items/portableminingdrill"),
    "portableelectricalaxe": (None, "eln:items/portableaxe"),
    "portablebattery": (None, "eln:items/portablebattery"),
    "portablebatterypack": (None, "eln:items/portablebatterypack"),
    "portablecondensator": (None, "eln:items/portablecondensator"),
    "portablecondensatorpack": (None, "eln:items/portablecondensatorpack"),

    # Heating Corps
    "small50vcopperheatingcorp": ("low", "eln:items/small50vcopperheatingcorp"),
    "50vcopperheatingcorp": ("low", "eln:items/50vcopperheatingcorp"),
    "small200vcopperheatingcorp": ("medium", "eln:items/small200vcopperheatingcorp"),
    "200vcopperheatingcorp": ("medium", "eln:items/200vcopperheatingcorp"),
    "small50vironheatingcorp": ("low", "eln:items/small50vironheatingcorp"),
    "50vironheatingcorp": ("low", "eln:items/50vironheatingcorp"),
    "small200vironheatingcorp": ("medium", "eln:items/small200vironheatingcorp"),
    "200vironheatingcorp": ("medium", "eln:items/200vironheatingcorp"),
    "small50vtungstenheatingcorp": ("low", "eln:items/small50vtungstenheatingcorp"),
    "50vtungstenheatingcorp": ("low", "eln:items/50vtungstenheatingcorp"),
    "small200vtungstenheatingcorp": ("medium", "eln:items/small200vtungstenheatingcorp"),
    "200vtungstenheatingcorp": ("medium", "eln:items/200vtungstenheatingcorp"),

    # Voltage Switches
    "lowvoltageswitch": ("low", "eln:blocks/switch"),
    "mediumvoltageswitch": ("medium", "eln:blocks/switch"),
    "highvoltageswitch": ("high", "eln:blocks/switch"),
    "veryhighvoltageswitch": ("veryhigh", "eln:blocks/switch"),
    "signalswitch": ("signal", "eln:blocks/switch"),
    "wirelessswitch": ("signal", "eln:blocks/wirelessswitch"),
    "switch": ("signal", "eln:blocks/switch"),
    "signalswitchwithled": ("signal", "eln:blocks/switch"),
    "trimmer": ("signal", "eln:blocks/trimmer"),
    "signaltrimmer": ("signal", "eln:blocks/trimmer"),
    
    # Buttons
    "button": ("signal", "eln:blocks/button"),
    "signalbutton": ("signal", "eln:blocks/button"),
    "wirelessbutton": ("signal", "eln:blocks/wirelessbutton"),
    
    # Sources
    "electricalsource": ("neutral", "eln:blocks/electricalsource"),
    "signalsource": ("signal", "eln:blocks/signalsource"),
    
    # Sockets
    "classiclampsocket": ("neutral", "eln:blocks/lampsocketa"),
    "lampsocketa": ("neutral", "eln:blocks/lampsocketa"),
    "lampsocketbprojector": ("neutral", "eln:blocks/lampsocketbprojector"),
    "robustlamp": ("neutral", "eln:blocks/robustlampsocket"),
    "robustlampsocket": ("neutral", "eln:blocks/robustlampsocket"),
    "flatlamp": ("neutral", "eln:blocks/flatlampsocket"),
    "flatlampsocket": ("neutral", "eln:blocks/flatlampsocket"),
    "simplelamp": ("neutral", "eln:blocks/simplelampsocket"),
    "simplelampsocket": ("neutral", "eln:blocks/simplelampsocket"),
    "fluorescentlamp": ("neutral", "eln:blocks/fluorescentlampsocket"),
    "fluorescentlampsocket": ("neutral", "eln:blocks/fluorescentlampsocket"),
    "streetlight": ("neutral", "eln:blocks/streetlight"),
    "sconcelamp": ("neutral", "eln:blocks/sconcelampsocket"),
    "sconcelampsocket": ("neutral", "eln:blocks/sconcelampsocket"),
    "suspendedlamp": ("neutral", "eln:blocks/suspendedlampsocket"),
    "suspendedlampsocket": ("neutral", "eln:blocks/suspendedlampsocket"),
    "longsuspendedlamp": ("neutral", "eln:blocks/longsuspendedlampsocket"),
    "longsuspendedlampsocket": ("neutral", "eln:blocks/longsuspendedlampsocket"),
    
    # Cables
    "lowvoltagecable": ("low", "eln:blocks/lowvoltagecable"),
    "mediumvoltagecable": ("medium", "eln:blocks/mediumvoltagecable"),
    "highvoltagecable": ("high", "eln:blocks/highvoltagecable"),
    "veryhighvoltagecable": ("veryhigh", "eln:blocks/veryhighvoltagecable"),
    "signalcable": ("signal", "eln:blocks/signalcable"),
    "thermalcable": ("thermal", "eln:blocks/copperthermalcable"),
    "groundcable": ("neutral", "eln:blocks/groundcable"),
    "hub": ("neutral", "eln:blocks/hub"),

    # Meters & Sensors
    "lampsupply": ("neutral", "eln:blocks/lampsupply"),
    "electricalbreaker": ("neutral", "eln:blocks/electricalbreaker"),
    "energymeter": ("neutral", "eln:blocks/energymeter"),
    "advancedenergymeter": ("neutral", "eln:blocks/advancedenergymeter"),
    "datalogger": ("signal", "eln:blocks/datalogger"),
    "moderndatalogger": ("signal", "eln:blocks/moderndatalogger"),
    "industrialdatalogger": ("signal", "eln:blocks/industrialdatalogger"),
    "electricalvumeter": ("signal", "eln:blocks/analogvumeter"),
    "vumeter": ("signal", "eln:blocks/analogvumeter"),
    "analogvumeter": ("signal", "eln:blocks/analogvumeter"),
    "ledvumeter": ("signal", "eln:blocks/ledvumeter"),

    "weak50vbatterycharger": ("low", "eln:blocks/batterycharger"),
    "50vbatterycharger": ("low", "eln:blocks/batterycharger"),
    "200vbatterycharger": ("medium", "eln:blocks/batterycharger"),

    "powersocketlv": ("low", "eln:blocks/50vpowersocket"),
    "powersocketmv": ("medium", "eln:blocks/200vpowersocket"),
    "50vemergencylamp": ("low", "eln:blocks/emergencylamp"),
    "200vemergencylamp": ("medium", "eln:blocks/emergencylamp"),
    
    "electricalentitysensor": ("signal", "eln:blocks/electricalentitysensor"),
    "proximitysensor": ("signal", "eln:blocks/electricalentitysensor"),
    "electricalweathersensor": ("signal", "eln:blocks/electricalweathersensor"),
    "weathersensor": ("signal", "eln:blocks/electricalweathersensor"),
    "electricalwindsensor": ("signal", "eln:blocks/electricalanemometersensor"),
    "windsenor": ("signal", "eln:blocks/electricalanemometersensor"),
    "anemometersensor": ("signal", "eln:blocks/electricalanemometersensor"),
    "electricallightsensor": ("signal", "eln:blocks/electricallightsensor"),
    "daylightsensor": ("signal", "eln:blocks/electricallightsensor"),
    "lightsensor": ("signal", "eln:blocks/electricallightsensor"),
    "electricalfiredetector": ("signal", "eln:blocks/electricalfiredetector"),
    "firedetector": ("signal", "eln:blocks/electricalfiredetector"),
    "electricalfirebuzzer": ("signal", "eln:blocks/electricalfirebuzzer"),
    "electricalalarm": ("signal", "eln:blocks/electricalalarm"),
    "nuclearalarm": ("signal", "eln:blocks/electricalalarm"),
    "standardalarm": ("signal", "eln:blocks/electricalalarm"),
    "electricalsensor": ("signal", "eln:blocks/voltageprobe"),
    "thermalsensor": ("signal", "eln:blocks/temperatureprobe"),
    "electricalprobe": ("signal", "eln:blocks/electricalprobe"),
    "thermalprobe": ("signal", "eln:blocks/thermalprobe"),
    "voltageprobe": ("signal", "eln:blocks/voltageprobe"),
    "temperatureprobe": ("signal", "eln:blocks/temperatureprobe"),
    "scanner": ("signal", "eln:blocks/scanner"),
    "analogwatch": ("neutral", "eln:blocks/analogwatch"),
    "digitalwatch": ("neutral", "eln:blocks/digitalwatch"),
    "tutorialsign": ("neutral", "eln:blocks/tutorialsign"),
    "electricalfuseholder": ("neutral", "eln:blocks/electricalfuseholder"),
    
    "lowvoltagerelay": ("low", "eln:blocks/lowvoltagerelay"),
    "mediumvoltagerelay": ("medium", "eln:blocks/mediumvoltagerelay"),
    "highvoltagerelay": ("high", "eln:blocks/highvoltagerelay"),
    "veryhighvoltagerelay": ("veryhigh", "eln:blocks/veryhighvoltagerelay"),
    "signalrelay": ("signal", "eln:blocks/signalrelay"),
    "wirelesssignaltransmitter": ("signal", "eln:blocks/wirelesssignaltransmitter"),
    "wirelesssignalreceiver": ("signal", "eln:blocks/wirelesssignalreceiver"),
    "wirelesssignalrepeater": ("signal", "eln:blocks/wirelesssignalrepeater"),
    "modbusrtu": ("signal", "eln:blocks/modbusrtu"),
    
    # Redstone
    "redstone-to-voltageconverter": ("signal", "eln:blocks/redstone-to-voltageconverter"),
    "voltage-to-redstoneconverter": ("signal", "eln:blocks/voltage-to-redstoneconverter"),

    # Transparent Nodes (Blocks)
    "powercapacitor": ("neutral", "eln:blocks/powercapacitor"),
    "powerinductor": ("neutral", "eln:blocks/powerinductor"),
    "dcdcconverter": ("neutral", "eln:blocks/dc-dcconverter"),
    "dc-dcconverter": ("neutral", "eln:blocks/dc-dcconverter"),
    "transformer": ("neutral", "eln:blocks/dc-dcconverter"),
    "stoneheatfurnace": ("neutral", "eln:blocks/stoneheatfurnace"),
    "fuelheatfurnace": ("neutral", "eln:blocks/fuelheatfurnace"),
    "50vturbine": ("low", "eln:blocks/50vturbine"),
    "200vturbine": ("medium", "eln:blocks/200vturbine"),
    "electricalfurnace": ("neutral", "eln:blocks/electricalfurnace"),
    "50vmacerator": ("low", "eln:blocks/50vmacerator"),
    "200vmacerator": ("medium", "eln:blocks/200vmacerator"),
    "50vcompressor": ("low", "eln:blocks/50vcompressor"),
    "200vcompressor": ("medium", "eln:blocks/200vcompressor"),
    "50vmagnetizer": ("low", "eln:blocks/50vmagnetizer"),
    "200vmagnetizer": ("medium", "eln:blocks/200vmagnetizer"),
    "50vplatemachine": ("low", "eln:blocks/50vplatemachine"),
    "200vplatemachine": ("medium", "eln:blocks/200vplatemachine"),
    "eggincubator": ("low", "eln:blocks/50veggincubator"),
    "autominer": ("high", "eln:blocks/autominer"),

    "smallsolarpanel": ("low", "eln:blocks/smallsolarpanel"),
    "smallrotatingsolarpanel": ("low", "eln:blocks/smallrotatingsolarpanel"),
    "2x3solarpanel": ("medium", "eln:blocks/2x3solarpanel"),
    "2x3rotatingsolarpanel": ("medium", "eln:blocks/2x3rotatingsolarpanel"),

    "windturbine": ("low", "eln:blocks/windturbine"),
    "smallpassivethermaldissipator": ("thermal", "eln:blocks/smallpassivethermaldissipator"),
    "smallactivethermaldissipator": ("low", "eln:blocks/smallactivethermaldissipator"),
    "200vactivethermaldissipator": ("medium", "eln:blocks/200vactivethermaldissipator"),
    
    "experimentaltransporter": ("high", "eln:blocks/experimentaltransporter"),
    
    "defenceturret": ("high", "eln:blocks/800vdefenceturret"),
    "800vdefenceturret": ("high", "eln:blocks/800vdefenceturret"),
    "50vfuelgenerator": ("low", "eln:blocks/50vfuelgenerator"),
    "200vfuelgenerator": ("medium", "eln:blocks/200vfuelgenerator"),
    "electricalantennatx": ("signal", "eln:blocks/electricalantennatx"),
    "electricalantennarx": ("signal", "eln:blocks/electricalantennarx"),
    "lowpowertransmitterantenna": ("signal", "eln:blocks/electricalantennatx"),
    "lowpowerreceiverantenna": ("signal", "eln:blocks/electricalantennarx"),
    "treeresincollector": ("neutral", "eln:blocks/treeresincollector"),
    "waterturbine": ("low", "eln:blocks/waterturbine"),
    
    "electricalentitysensor": ("signal", "eln:blocks/electricalentitysensor"),
    "electricalfiredetector": ("signal", "eln:blocks/electricalfiredetector"),
    "electricalfirebuzzer": ("signal", "eln:blocks/electricalfirebuzzer"),
    "electricallightsensor": ("signal", "eln:blocks/electricallightsensor"),
    "electricaldaylightsensor": ("signal", "eln:blocks/electricallightsensor"),
    "electricalwindsensor": ("signal", "eln:blocks/electricalanemometersensor"),
    "electricalweathersensor": ("signal", "eln:blocks/electricalweathersensor"),
    "redstoneinput": ("signal", "eln:blocks/redstone-to-voltageconverter"),
    "redstoneoutput": ("signal", "eln:blocks/voltage-to-redstoneconverter"),
    "redstone-to-voltageconverter": ("signal", "eln:blocks/redstone-to-voltageconverter"),
    "voltage-to-redstoneconverter": ("signal", "eln:blocks/voltage-to-redstoneconverter"),

    # Mechanical
    "50vgenerator": ("low", "eln:blocks/generator"),
    "200vgenerator": ("medium", "eln:blocks/generator"),
    "steamturbine": ("neutral", "eln:blocks/steamturbine"),
    "gasturbine": ("neutral", "eln:blocks/gasturbine"),
    "flywheel": ("neutral", "eln:blocks/flywheel"),
    "simpleshaft": ("neutral", "eln:blocks/joint"),
    "simple_shaft": ("neutral", "eln:blocks/joint"),
    "jointhub": ("neutral", "eln:blocks/jointhub"),
    "tachometer": ("signal", "eln:blocks/tachometer"),

    # Grid
    "utilitypole": ("high", "eln:blocks/utilitypole"),
    "utilitypolewdcdcconverter": ("high", "eln:blocks/utilitypolewdc-dcconverter"),
    "downlink": ("veryhigh", "eln:blocks/downlink"),
    "gridtransformer": ("veryhigh", "eln:blocks/transformer"),

    # Batteries (Standard)
    "costorientedbattery": ("low", "eln:blocks/costorientedbattery"),
    "capacityorientedbattery": ("low", "eln:blocks/capacityorientedbattery"),
    "voltageorientedbattery": ("medium", "eln:blocks/voltageorientedbattery"),
    "currentorientedbattery": ("low", "eln:blocks/currentorientedbattery"),
    "lifeorientedbattery": ("low", "eln:blocks/lifeorientedbattery"),
    "singleusebattery": ("low", "eln:blocks/single-usebattery"),

    # Fuses
    "lowvoltageleadfuse": ("low", "eln:items/electricalfuse"),
    "mediumvoltageleadfuse": ("medium", "eln:items/electricalfuse"),
    "highvoltageleadfuse": ("high", "eln:items/electricalfuse"),
    "veryhighvoltageleadfuse": ("veryhigh", "eln:items/electricalfuse"),
    "blownelectricalfuse": ("neutral", "eln:items/blownelectricalfuse"),

    # Items & Materials
    "copperingot": (None, "eln:items/copperingot"),
    "leadingot": (None, "eln:items/leadingot"),
    "tungsteningot": (None, "eln:items/tungsteningot"),
    "ferriteingot": (None, "eln:items/ferriteingot"),
    "siliconingot": (None, "eln:items/siliconingot"),
    "alloyingot": (None, "eln:items/alloyingot"),
    "copperdust": (None, "eln:items/copperdust"),
    "irondust": (None, "eln:items/irondust"),
    "leaddust": (None, "eln:items/leaddust"),
    "tungstendust": (None, "eln:items/tungstendust"),
    "golddust": (None, "eln:items/golddust"),
    "coaldust": (None, "eln:items/coaldust"),
    "silicondust": (None, "eln:items/silicondust"),
    "alloydust": (None, "eln:items/alloydust"),
    "cinnabardust": (None, "eln:items/cinnabardust"),
    "treeresin": (None, "eln:items/treeresin"),
    "rubber": (None, "eln:items/rubber"),
    "miningpipe": (None, "eln:items/miningpipe"),
    "coppercable": (None, "eln:items/coppercable"),
    "ironcable": (None, "eln:items/ironcable"),
    "tungstencable": (None, "eln:items/tungstencable"),
    "electricalmotor": (None, "eln:items/electricalmotor"),
    "advancedelectricalmotor": (None, "eln:items/advancedelectricalmotor"),
    "cheapferromagneticcore": (None, "eln:items/cheapferromagneticcore"),
    "averageferromagneticcore": (None, "eln:items/averageferromagneticcore"),
    "optimalferromagneticcore": (None, "eln:items/optimalferromagneticcore"),
    "combustionchamber": (None, "eln:items/combustionchamber"),
    "solartracker": (None, "eln:items/solartracker"),
    "cheapchip": (None, "eln:items/cheapchip"),
    "advancedchip": (None, "eln:items/advancedchip"),
    "onoffregulator": (None, "eln:items/onoffregulator"),
    "analogicregulator": (None, "eln:items/analogicregulator"),
    "overvoltageprotection": (None, "eln:items/overvoltageprotection"),
    "overheatingprotection": (None, "eln:items/overheatingprotection"),
    "cheapelectricaldrill": (None, "eln:items/cheapelectricaldrill"),
    "averageelectricaldrill": (None, "eln:items/averageelectricaldrill"),
    "fastelectricaldrill": (None, "eln:items/fastelectricaldrill"),

    # Plates
    "copperplate": (None, "eln:items/copperplate"),
    "ironplate": (None, "eln:items/ironplate"),
    "goldplate": (None, "eln:items/goldplate"),
    "leadplate": (None, "eln:items/leadplate"),
    "siliconplate": (None, "eln:items/siliconplate"),
    "alloyplate": (None, "eln:items/alloyplate"),
    "coalplate": (None, "eln:items/coalplate"),
    "tungstenplate": (None, "eln:items/tungstenplate"),

    # Magnets
    "basicmagnet": (None, "eln:items/basicmagnet"),
    "advancedmagnet": (None, "eln:items/advancedmagnet"),

    # Misc
    "dielectric": (None, "eln:items/dielectric"),
    "mercury": (None, "eln:items/mercury"),

    # Armor & Tools (Matching Registry Names)
    "copper_sword": (None, "eln:items/copper_sword"),
    "copper_hoe": (None, "eln:items/copper_hoe"),
    "copper_shovel": (None, "eln:items/copper_shovel"),
    "copper_pickaxe": (None, "eln:items/copper_pickaxe"),
    "copper_axe": (None, "eln:items/copper_axe"),
    "copper_helmet": (None, "eln:items/copper_helmet"),
    "copper_chestplate": (None, "eln:items/copper_chestplate"),
    "copper_leggings": (None, "eln:items/copper_leggings"),
    "copper_boots": (None, "eln:items/copper_boots"),
    "ecoal_helmet": (None, "eln:items/ecoal_helmet"),
    "ecoal_chestplate": (None, "eln:items/ecoal_chestplate"),
    "ecoal_leggings": (None, "eln:items/ecoal_leggings"),
    "ecoal_boots": (None, "eln:items/ecoal_boots"),
    
    # Fuel Burners
    "smallfuelburner": (None, "eln:items/smallfuelburner"),
    "mediumfuelburner": (None, "eln:items/mediumfuelburner"),
    "bigfuelburner": (None, "eln:items/bigfuelburner"),
    "small_fuel_burner": (None, "eln:items/smallfuelburner"),
    "medium_fuel_burner": (None, "eln:items/mediumfuelburner"),
    "big_fuel_burner": (None, "eln:items/bigfuelburner"),

    # Ores (as separate blocks)
    "copper_ore": (None, "eln:blocks/copper_ore"),
    "lead_ore": (None, "eln:blocks/lead_ore"),
    "tungsten_ore": (None, "eln:blocks/tungstenore"),
    "cinnabar_ore": (None, "eln:blocks/cinnabar_ore"),
}

# Logic (no -ni)
chips = ["notchip", "andchip", "nandchip", "orchip", "norchip", "xorchip", "xnorchip", "palchip", 
         "schmitttriggerchip", "dflipflopchip", "jkflipflopchip", "oscillatorchip"]
for chip in chips:
    mapping[chip] = ("signal", f"eln:blocks/{chip}")

analog = ["opamp", "pidregulator", "amplifier", "configurablesummingunit", "sampleandhold", 
          "lowpassfilter", "voltagecontrolledamplifier", "voltagecontrolledsawtoothoscillator", 
          "voltagecontrolledsineoscillator"]
for a in analog:
    mapping[a] = ("signal", f"eln:blocks/{a}")

# Passive
mapping["10adiode"] = ("neutral", "eln:blocks/10adiode")
mapping["25adiode"] = ("neutral", "eln:blocks/25adiode")
mapping["signaldiode"] = ("signal", "eln:blocks/signaldiode")
mapping["resistor"] = ("neutral", "eln:blocks/powerresistor")
mapping["rheostat"] = ("neutral", "eln:blocks/rheostat")
mapping["thermistor"] = ("neutral", "eln:blocks/thermistor")
mapping["largerheostat"] = ("neutral", "eln:blocks/largerheostat")
mapping["powerinductor"] = ("neutral", "eln:blocks/powerinductor")
mapping["electricaltimer"] = ("signal", "eln:blocks/electricaltimer")
mapping["signalprocessor"] = ("signal", "eln:blocks/signalprocessor")

# Bulbs
mapping["small50vincandescentlightbulb"] = ("low", "eln:items/incandescentironlamp")
mapping["50vincandescentlightbulb"] = ("low", "eln:items/incandescentironlamp")
mapping["200vincandescentlightbulb"] = ("medium", "eln:items/incandescentironlamp")
mapping["small50vcarbonincandescentlightbulb"] = ("low", "eln:items/incandescentcarbonlamp")
mapping["50vcarbonincandescentlightbulb"] = ("low", "eln:items/incandescentcarbonlamp")
mapping["small50veconomiclightbulb"] = ("low", "eln:items/fluorescentlamp")
mapping["50veconomiclightbulb"] = ("low", "eln:items/fluorescentlamp")
mapping["200veconomiclightbulb"] = ("medium", "eln:items/fluorescentlamp")
mapping["50vfarminglamp"] = ("low", "eln:items/farminglamp")
mapping["200vfarminglamp"] = ("medium", "eln:items/farminglamp")
mapping["50vledbulb"] = ("low", "eln:items/ledlamp")
mapping["200vledbulb"] = ("medium", "eln:items/ledlamp")

# Brushes
colors = ["black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightblue", "magenta", "orange", "white"]
for color in colors:
    mapping[f"{color}brush"] = (None, f"eln:items/{color}brush")

def create_model(name, bg, icon):
    # Normalize name for filename (lowercase, remove spaces, etc.)
    safe_name = name.lower().replace(" ", "").replace("/", "").replace("w/", "w")
    if bg:
        data = {
            "parent": "item/generated",
            "textures": {
                "layer0": f"eln:voltages/{bg}",
                "layer1": icon
            }
        }
    elif "ore" in safe_name and bg is None:
        data = {
            "parent": f"eln:block/{safe_name}"
        }
    else:
        data = {
            "parent": "item/generated",
            "textures": {
                "layer0": icon
            }
        }
    file_path = os.path.join(models_path, f"{safe_name}.json")
    with open(file_path, 'w') as f:
        json.dump(data, f, indent=4)
    print(f"Created/Updated {file_path}")

if __name__ == "__main__":
    if not os.path.exists(models_path):
        os.makedirs(models_path)
    for name, data in mapping.items():
        if isinstance(data, list):
            for bg, icon in data:
                create_model(name, bg, icon)
        else:
            bg, icon = data
            create_model(name, bg, icon)

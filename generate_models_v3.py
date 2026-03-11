import json
import os

models_path = "src/main/resources/assets/eln/models/item/"

# Backgrounds: low (50V), medium (200V), high (800V), veryhigh (3200V), signal (0-50V), thermal, neutral
mapping = {
    # Tools (No background)
    "multimeter": (None, "eln:items/multimeter"),
    "allmeter": (None, "eln:items/allmeter"),
    "thermometer": (None, "eln:items/thermometer"),
    "orescanner": (None, "eln:items/orescanner"),
    "x-rayscanner": (None, "eln:items/x-rayscanner"),
    "wirelessanalyser": (None, "eln:items/wirelessanalyser"),
    "wrench": (None, "eln:items/wrench"),

    # Voltage Switches
    "lowvoltageswitch": ("low", "eln:blocks/lowvoltageswitch"),
    "mediumvoltageswitch": ("medium", "eln:blocks/mediumvoltageswitch"),
    "highvoltageswitch": ("high", "eln:blocks/highvoltageswitch"),
    "veryhighvoltageswitch": ("veryhigh", "eln:blocks/veryhighvoltageswitch"),
    "signalswitch": ("signal", "eln:blocks/signalswitch"),
    "wirelessswitch": ("signal", "eln:blocks/wirelessswitch"),
    "switch": ("signal", "eln:blocks/switch"),
    
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
    "50vbatterycharger": ("low", "eln:blocks/batterycharger"),
    "200vbatterycharger": ("low", "eln:blocks/batterycharger"),
    "powersocketlv": ("low", "eln:blocks/50vpowersocket"),
    "powersocketmv": ("medium", "eln:blocks/200vpowersocket"),
    "50vemergencylamp": ("low", "eln:blocks/emergencylamp"),
    "200vemergencylamp": ("medium", "eln:blocks/emergencylamp"),
    
    "electricalentitysensor": ("signal", "eln:blocks/electricalentitysensor"),
    "electricalweathersensor": ("signal", "eln:blocks/electricalweathersensor"),
    "electricalwindsensor": ("signal", "eln:blocks/electricalanemometersensor"),
    "windsenor": ("signal", "eln:blocks/electricalanemometersensor"),
    "electricallightsensor": ("signal", "eln:blocks/electricallightsensor"),
    "electricalfiredetector": ("signal", "eln:blocks/electricalfiredetector"),
    "electricalalarm": ("signal", "eln:blocks/electricalalarm"),
    "electricalsensor": ("signal", "eln:blocks/voltageprobe"),
    "thermalsensor": ("signal", "eln:blocks/temperatureprobe"),
    "electricalprobe": ("signal", "eln:blocks/electricalprobe"),
    "thermalprobe": ("signal", "eln:blocks/thermalprobe"),
    "voltageprobe": ("signal", "eln:blocks/voltageprobe"),
    "temperatureprobe": ("signal", "eln:blocks/temperatureprobe"),
    
    # Redstone
    "redstoneinput": ("signal", "eln:blocks/redstone-to-voltageconverter"),
    "redstoneoutput": ("signal", "eln:blocks/voltage-to-redstoneconverter"),

    # Transparent Nodes (Blocks)
    "powercapacitor": ("neutral", "eln:blocks/powercapacitor"),
    "powerinductor": ("neutral", "eln:blocks/powerinductor"),
    "dc-dcconverter": ("neutral", "eln:blocks/dc-dcconverter"),
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
    "windturbine": ("low", "eln:blocks/windturbine"),
    "smallpassivethermaldissipator": ("thermal", "eln:blocks/smallpassivethermaldissipator"),
    "50vsmallactivethermaldissipator": ("low", "eln:blocks/smallactivethermaldissipator"),
    "200vactivethermaldissipator": ("medium", "eln:blocks/200vactivethermaldissipator"),
    "experimentaltransporter": ("veryhigh", "eln:blocks/experimentaltransporter"),
    "defenceturret": ("high", "eln:blocks/800vdefenceturret"),
    "fuelgenerator": ("low", "eln:blocks/50vfuelgenerator"),
    "lowpowertransmitterantenna": ("signal", "eln:blocks/electricalantennatx"),
    "lowpowerreceiverantenna": ("signal", "eln:blocks/electricalantennarx"),

    # Mechanical
    "50vgenerator": ("low", "eln:blocks/generator"),
    "200vgenerator": ("medium", "eln:blocks/generator"),
    "steamturbine": ("neutral", "eln:blocks/steamturbine"),
    "gasturbine": ("neutral", "eln:blocks/gasturbine"),
    "flywheel": ("neutral", "eln:blocks/flywheel"),
    "simpleshaft": ("neutral", "eln:blocks/joint"),
    "jointhub": ("neutral", "eln:blocks/jointhub"),
    "tachometer": ("signal", "eln:blocks/tachometer"),

    # Grid
    "utilitypole": ("high", "eln:blocks/utilitypole"),
    "utilitypolewdc-dcconverter": ("high", "eln:blocks/utilitypolewdc-dcconverter"),

    # Batteries (Standard)
    "costorientedbattery": ("low", "eln:blocks/costorientedbattery"),
    "capacityorientedbattery": ("low", "eln:blocks/capacityorientedbattery"),
    "voltageorientedbattery": ("medium", "eln:blocks/voltageorientedbattery"),
    "currentorientedbattery": ("low", "eln:blocks/currentorientedbattery"),
    "lifeorientedbattery": ("low", "eln:blocks/lifeorientedbattery"),
    "single-usebattery": ("low", "eln:blocks/single-usebattery"),
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
    for name, (bg, icon) in mapping.items():
        create_model(name, bg, icon)

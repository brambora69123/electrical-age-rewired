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
    
    # Sources
    "electricalsource": ("neutral", "eln:blocks/electricalsource"),
    "signalsource": ("signal", "eln:blocks/signalsource"),
    
    # Lamp Sockets (Neutral)
    "lampsocketa": ("neutral", "eln:blocks/lampsocketa"),
    "lampsocketbprojector": ("neutral", "eln:blocks/lampsocketa"),
    "robustlampsocket": ("neutral", "eln:blocks/robustlampsocket"),
    "flatlampsocket": ("neutral", "eln:blocks/flatlampsocket"),
    "simplelampsocket": ("neutral", "eln:blocks/simplelampsocket"),
    "fluorescentlampsocket": ("neutral", "eln:blocks/fluorescentlampsocket"),
    "streetlight": ("neutral", "eln:blocks/streetlight"),
    "sconcelampsocket": ("neutral", "eln:blocks/sconcelampsocket"),
    "suspendedlampsocket": ("neutral", "eln:blocks/suspendedlampsocket"),
    "longsuspendedlampsocket": ("neutral", "eln:blocks/longsuspendedlampsocket"),
    
    # Cables
    "lowvoltagecable": ("low", "eln:blocks/lowvoltagecable"),
    "mediumvoltagecable": ("medium", "eln:blocks/mediumvoltagecable"),
    "highvoltagecable": ("high", "eln:blocks/highvoltagecable"),
    "veryhighvoltagecable": ("veryhigh", "eln:blocks/veryhighvoltagecable"),
    "signalcable": ("signal", "eln:blocks/signalcable"),
    "thermalcable": ("thermal", "eln:blocks/thermalcable"),
    "groundcable": ("neutral", "eln:blocks/groundcable"),

    # Others
    "lampsupply": ("neutral", "eln:blocks/lampsupply"),
    "electricalbreaker": ("neutral", "eln:blocks/electricalbreaker"),
    "energymeter": ("low", "eln:blocks/energymeter"),
    "advancedenergymeter": ("low", "eln:blocks/advancedenergymeter"),
    "datalogger": ("signal", "eln:blocks/datalogger"),
    "moderndatalogger": ("signal", "eln:blocks/moderndatalogger"),
    "industrialdatalogger": ("signal", "eln:blocks/industrialdatalogger"),
    "electricalvumeter": ("signal", "eln:blocks/electricalvumeter"),
    "batterycharger": ("low", "eln:blocks/batterycharger"),
    "powersocketlv": ("low", "eln:blocks/50vpowersocket"),
    "powersocketmv": ("medium", "eln:blocks/200vpowersocket"),
    "50vemergencylamp": ("low", "eln:blocks/emergencylamp"),
    "200vemergencylamp": ("medium", "eln:blocks/emergencylamp"),
    "electricalentitysensor": ("signal", "eln:blocks/electricalentitysensor"),
    "electricalweathersensor": ("signal", "eln:blocks/electricalweathersensor"),
    "electricalwindsensor": ("signal", "eln:blocks/electricalanemometersensor"),
    "electricallightsensor": ("signal", "eln:blocks/electricallightsensor"),
    "electricalfiredetector": ("signal", "eln:blocks/electricalfiredetector"),
    "electricalalarm": ("low", "eln:blocks/electricalalarm"),
    "electricalsensor": ("signal", "eln:blocks/voltageprobe"),
    "thermalsensor": ("signal", "eln:blocks/temperatureprobe"),
}

# Logic & Chips (always signal)
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
mapping["10adiode"] = ("low", "eln:blocks/10adiode")
mapping["25adiode"] = ("low", "eln:blocks/25adiode")
mapping["signaldiode"] = ("signal", "eln:blocks/signaldiode")
mapping["resistor"] = ("neutral", "eln:blocks/powerresistor")
mapping["powercapacitor"] = ("neutral", "eln:blocks/powercapacitor")
mapping["powerinductor"] = ("neutral", "eln:blocks/powerinductor")

# Bulbs
bulbs_low = ["small50vincandescentlightbulb", "50vincandescentlightbulb", "small50vcarbonincandescentlightbulb", 
             "50vcarbonincandescentlightbulb", "small50veconomiclightbulb", "50veconomiclightbulb", 
             "50vfarminglamp", "50vledbulb"]
for b in bulbs_low:
    mapping[b] = ("low", f"eln:items/{b.replace('50v', '').replace('small', 'small')}") # Simplified logic for bulbs

# Re-map bulbs manually for precision
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
    file_path = os.path.join(models_path, f"{name}.json")
    with open(file_path, 'w') as f:
        json.dump(data, f, indent=4)
    print(f"Created/Updated {file_path}")

if __name__ == "__main__":
    for name, (bg, icon) in mapping.items():
        create_model(name, bg, icon)

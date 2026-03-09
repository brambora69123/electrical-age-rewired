#!/usr/bin/env python3
"""
Fix language file for Minecraft 1.12.2 compatibility.
Adds item. and tile. prefixes to translation keys.
"""

import re

# Items registered to SharedItem (from Items.kt)
ITEMS = {
    # Multi-meter, thermometer, all-meter
    "multimeter", "thermometer", "allmeter",
    
    # Resources/Materials
    "alloy_dust", "alloy_ingot", "alloy_plate",
    "cinnabar_dust", "coal_dust", "coal_plate",
    "copper_dust", "copper_ingot", "copper_plate",
    "gold_dust", "gold_plate",
    "iron_dust", "iron_plate",
    "lead_dust", "lead_ingot", "lead_plate",
    "tungsten_dust", "tungsten_ingot",
    "silicon_dust", "silicon_ingot", "silicon_plate",
    "ferrite_ingot", "mercury", "rubber", "dielectric",
    "casing", "joint",
    
    # Chips/Electronics
    "cheap_chip", "advanced_chip",
    "and_chip", "or_chip", "not_chip",
    "nand_chip", "nor_chip", "xor_chip", "xnor_chip",
    "d_flip_flop_chip", "jk_flip_flop_chip",
    "pal_chip", "oscillator_chip", "schmitt_trigger_chip",
    "electrical_probe_chip", "thermal_probe_chip",
    
    # Tools/Equipment
    "wrench",
    "scanner", "ore_scanner",
    "data_logger_print",
    
    # Portable Items
    "small_flashlight", "improved_flashlight",
    "portable_battery", "portable_battery_pack",
    "portable_condensator", "portable_condensator_pack",
    "x-ray_scanner",
    
    # Brushes (16 colors)
    "black_brush", "red_brush", "green_brush", "brown_brush",
    "blue_brush", "purple_brush", "cyan_brush", "silver_brush",
    "gray_brush", "pink_brush", "lime_brush", "yellow_brush",
    "light_blue_brush", "magenta_brush", "orange_brush", "white_brush",
    
    # Upgrades/Components
    "mining_pipe", "combustion_chamber",
    "solar_tracker", "machine_booster",
    "overheating_protection", "overvoltage_protection",
    "animal_filter", "monster_filter", "player_filter",
    
    # Lamps (as items - light bulbs only, NOT emergency lamps which are SixNode blocks)
    "small_50v_incandescent_light_bulb", "50v_incandescent_light_bulb",
    "200v_incandescent_light_bulb",
    "small_50v_carbon_incandescent_light_bulb", "50v_carbon_incandescent_light_bulb",
    "small_50v_economic_light_bulb", "50v_economic_light_bulb", "200v_economic_light_bulb",
    "50v_led_bulb", "200v_led_bulb",
    "50v_farming_lamp", "200v_farming_lamp",
    # Note: emergency lamps are SixNode blocks, not items
    
    # Drills
    "cheap_electrical_drill", "average_electrical_drill", "fast_electrical_drill",
    
    # Ferromagnetic Cores
    "cheap_ferromagnetic_core", "average_ferromagnetic_core", "optimal_ferromagnetic_core",
    
    # Heating Elements
    "small_50v_copper_heating_corp", "50v_copper_heating_corp", "200v_copper_heating_corp",
    "small_50v_iron_heating_corp", "50v_iron_heating_corp", "200v_iron_heating_corp",
    "small_50v_tungsten_heating_corp", "200v_tungsten_heating_corp",
    
    # Regulators
    "on_off_regulator_1_percent", "on_off_regulator_10_percent",
    "analogic_regulator",
    
    # Fuel Burners
    "small_fuel_burner", "medium_fuel_burner", "big_fuel_burner",
    
    # Cables (as items)
    "copper_cable", "iron_cable", "tungsten_cable",
    
    # Batteries (portable)
    "single-use_battery",
    "capacity_oriented_battery", "cost_oriented_battery", "current_oriented_battery",
    "life_oriented_battery", "voltage_oriented_battery",
    
    # Misc
    "tree_resin",  # tree_resin_collector is a SixNode block
    "blown_lead_fuse",
    "lead_fuse_for_low_voltage_cables", "lead_fuse_for_medium_voltage_cables",
    "lead_fuse_for_high_voltage_cables", "lead_fuse_for_very_high_voltage_cables",
    
    # Wireless
    "wireless_analyser",
    
    # Electrical tools
    "portable_electrical_mining_drill", "portable_electrical_axe",
    
    # Armor (already has item. prefix in some entries)
    "copper_helmet", "copper_chestplate", "copper_leggings", "copper_boots",
    "copper_axe",

    # Condensators (portable items, NOT SixNode condensator blocks)
    "200v_condensator", "50v_condensator",

    # Turbines
    "200v_turbine", "50v_turbine",

    # Heating corps (small variants)
    "small_200v_copper_heating_corp", "small_200v_iron_heating_corp", "small_200v_tungsten_heating_corp",

    # Heating corp (50v tungsten)
    "50v_tungsten_heating_corp",

    # Machine blocks
    "advanced_machine_block", "machine_block",

    # Magnets
    "advanced_magnet", "basic_magnet",

    # Electrical components
    "electrical_fuse_holder",
}

# Blocks (SixNode and TransparentNode)
BLOCKS = {
    # Cables
    "ground_cable", "hub",
    "electrical_source", "signal_source",
    "signal_cable", "low_voltage_cable", "medium_voltage_cable",
    "high_voltage_cable", "very_high_voltage_cable",
    "copper_thermal_cable",

    # Diodes (SixNode components)
    "10a_diode", "25a_diode", "signal_diode",

    # Resistors
    "resistor", "power_resistor",

    # Lamp sockets
    "lamp_socket_a", "lamp_socket_b_projector",
    "simple_lamp_socket", "robust_lamp_socket",
    "flat_lamp_socket", "fluorescent_lamp_socket",
    "sconce_lamp_socket", "suspended_lamp_socket",
    "long_suspended_lamp_socket",
    "lamp_supply",

    # Emergency lamps (SixNode blocks)
    "50v_emergency_lamp", "200v_emergency_lamp",

    # Power sockets
    "200v_power_socket", "50v_power_socket", "power_socket_lv", "power_socket_mw",

    # Battery chargers
    "200v_battery_charger", "50v_battery_charger", "weak_50v_battery_charger",

    # Wireless
    "wireless_signal_transmitter", "wireless_signal_receiver",
    "wireless_signal_repeater", "wireless_button", "wireless_switch",

    # Data loggers
    "data_logger", "industrial_data_logger", "modern_data_logger",

    # Relays
    "electrical_relay", "low_voltage_relay", "medium_voltage_relay",
    "high_voltage_relay", "very_high_voltage_relay",
    "signal_relay",

    # Components
    "power_capacitor", "power_inductor",
    "signal_20h_inductor", "signal_antenna", "signal_diode",
    "signal_button", "signal_switch", "signal_switch_with_led",
    "signal_processor", "signal_trimmer",

    # Switches
    "electrical_switch", "low_voltage_switch", "medium_voltage_switch",
    "high_voltage_switch", "very_high_voltage_switch",
    "electrical_breaker",

    # Sensors/Meters
    "electrical_sensor", "advanced_energy_meter", "energy_meter",
    "thermal_sensor",
    "electrical_vumeter", "electrical_vu_meter", "led_vumeter", "analog_vumeter",

    # Probes
    "electrical_probe", "voltage_probe", "thermal_probe", "temperature_probe",

    # Alarms
    "electrical_alarm", "nuclear_alarm", "standard_alarm",

    # Environmental sensors
    "electrical_daylight_sensor", "electrical_light_sensor",
    "electrical_weather_sensor", "electrical_anemometer_sensor",
    "electrical_entity_sensor", "electrical_fire_detector", "electrical_fire_buzzer",

    # Redstone
    "electrical_redstone_input", "electrical_redstone_output",
    "voltage-to-redstone_converter", "redstone-to-voltage_converter",

    # Logic gates
    "and_gate", "or_gate", "not_gate",
    "nand_gate", "nor_gate", "xor_gate", "xnor_gate",

    # Timing
    "electrical_timer", "electrical_watch", "analog_watch", "digital_watch",

    # Math/Analog
    "electrical_math", "configurable_summing_unit",
    "amplifier", "voltage_controlled_amplifier",
    "voltage_controlled_sine_oscillator", "voltage_controlled_sawtooth_oscillator",
    "sample_and_hold", "lowpass_filter",
    "opamp", "pid_regulator", "analogic_regulator",
    "on_off_regulator_1_percent", "on_off_regulator_10_percent",

    # Industrial
    "modbus_rtu", "tutorial_sign",
    
    # TransparentNode - Machines
    "transformer",  # Various voltage levels
    "stone_heat_furnace", "fuel_heat_furnace",
    "gas_turbine", "steam_turbine", "water_turbine", "wind_turbine",
    "200v_battery", "50v_battery",
    "electrical_furnace",
    "200v_macerator", "50v_macerator",
    "200v_compressor", "50v_compressor",
    "200v_magnetizer", "50v_magnetizer",
    "200v_plate_machine", "50v_plate_machine",
    "50v_egg_incubator", "egg_incubator",
    "auto_miner",
    "2x3_solar_panel", "small_solar_panel",
    "2x3_rotating_solar_panel", "small_rotating_solar_panel",
    "small_passive_thermal_dissipator", "small_active_thermal_dissipator",
    "200v_active_thermal_dissipator", "passive_thermal_dissipator",
    "street_light",
    "800v_defence_turret", "defence_turret",
    "200v_fuel_generator", "50v_fuel_generator", "fuel_generator",
    "generator",
    "high_power_transmitter_antenna", "high_power_receiver_antenna",
    "medium_power_transmitter_antenna", "medium_power_receiver_antenna",
    "low_power_transmitter_antenna", "low_power_receiver_antenna",
    "teleporter", "experimental_transporter",
    "rheostat", "large_rheostat",
    "dc-dc_converter",
    "flywheel", "joint_hub", "electrical_motor", "advanced_electrical_motor",
    "tachometer",
    "utility_pole",
    "tree_resin_collector",

    # Ores (special format with eln.ore. prefix)
    "copper_ore", "lead_ore", "cinnabar_ore", "tungsten_ore",

    # Blocks
    "rubber_block", "flubber_block",

    # Entity (uses entity. prefix)
    "eareplicator",
}

def fix_language_file(input_path, output_path=None):
    if output_path is None:
        output_path = input_path + '.fixed'
    
    with open(input_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    fixed_lines = []
    stats = {'item': 0, 'tile': 0, 'unchanged': 0, 'already_prefixed': 0}
    
    for line in lines:
        line = line.rstrip('\n')
        
        # Skip comments and empty lines
        if not line or line.startswith('#'):
            fixed_lines.append(line)
            continue
        
        # Match pattern: eln.something.name=Translation
        match = re.match(r'^(eln\.)([a-zA-Z0-9_./-]+)(\.name=.*)$', line)
        if not match:
            fixed_lines.append(line)
            stats['unchanged'] += 1
            continue
        
        prefix = match.group(1)  # "eln."
        key = match.group(2)      # The actual key
        suffix = match.group(3)   # ".name=..."
        value = suffix.split('=', 1)[1] if '=' in suffix else ""  # Extract value after =

        # Check if already has item. or tile. prefix
        if key.startswith('item.') or key.startswith('tile.') or key.startswith('entity.') or key.startswith('achievement.'):
            fixed_lines.append(line)
            stats['already_prefixed'] += 1
            continue
        
        # Check if it's an ore (special format: eln.ore.xxx.name)
        if key.startswith('ore.'):
            # Keep ore format as-is (it's block-related but uses eln.ore. prefix)
            fixed_lines.append(line)
            stats['unchanged'] += 1
            continue
        
        # Handle entity entries (eln.entity.xxx -> eln.entity.xxx)
        if key.startswith('entity.'):
            fixed_lines.append(line)
            stats['already_prefixed'] += 1
            continue
        
        # Skip 'mod' entries (those are mod metadata, not items/blocks)
        if key == 'mod':
            fixed_lines.append(line)
            stats['unchanged'] += 1
            continue
        
        # Determine if item or block
        # Normalize key for comparison (replace dots with underscores for nested keys)
        base_key = key.replace('.', '_')
        
        if base_key in ITEMS or key in ITEMS:
            # Add item. prefix (Forge expects item.xxx.name)
            # Keep original eln.xxx.name entry, add item.xxx.name for Forge
            fixed_lines.append(line)  # Keep original eln.xxx.name
            fixed_line = f"item.{key}{suffix}"
            fixed_lines.append(fixed_line)
            stats['item'] += 1
        elif base_key in BLOCKS or key in BLOCKS:
            # Add tile. prefix (Forge expects tile.xxx.name)
            # Keep original eln.xxx.name entry, add tile.xxx.name for Forge
            fixed_lines.append(line)  # Keep original eln.xxx.name
            fixed_line = f"tile.{key}{suffix}"
            fixed_lines.append(fixed_line)
            stats['tile'] += 1
        else:
            # Unknown - keep as-is but log
            fixed_lines.append(line)
            stats['unchanged'] += 1
            print(f"Unknown: {key}")
    
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write('\n'.join(fixed_lines))
    
    print(f"\n=== Language File Fix Summary ===")
    print(f"Added 'item.' prefix: {stats['item']} entries")
    print(f"Added 'tile.' prefix: {stats['tile']} entries")
    print(f"Already prefixed: {stats['already_prefixed']} entries")
    print(f"Unchanged: {stats['unchanged']} entries")
    print(f"\nOutput written to: {output_path}")

if __name__ == '__main__':
    import sys
    input_file = sys.argv[1] if len(sys.argv) > 1 else 'src/main/resources/assets/eln/lang/en_us.lang'
    fix_language_file(input_file)

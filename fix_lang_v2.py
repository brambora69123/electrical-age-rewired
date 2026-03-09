#!/usr/bin/env python3
"""
Fix language file for Minecraft 1.12.2 compatibility.
Creates both .name entries (for items/blocks) and non-.name entries (for GUI text).
"""

import re

# Read the backup (original) file
with open('src/main/resources/assets/eln/lang/en_us.lang.backup', 'r', encoding='utf-8') as f:
    lines = f.readlines()

# Process lines - remove eln: prefix and normalize
output_lines = []
stats = {'converted': 0, 'unchanged': 0}

for line in lines:
    line = line.rstrip('\n')
    
    # Skip empty lines and comments (but keep them in output)
    if not line or line.startswith('#'):
        output_lines.append(line)
        continue
    
    # Skip lines that don't look like translation entries
    if '=' not in line:
        output_lines.append(line)
        continue
    
    # Handle eln: prefix format (old ELN format)
    if line.startswith('eln:'):
        # Remove eln: prefix
        content = line[4:]  # Remove 'eln:'
        
        # Skip version header
        if content.startswith('#<'):
            continue
        
        # Parse key=value
        if '=' in content:
            key, value = content.split('=', 1)
            # Unescape any escaped equals signs
            value = value.replace('\\=', '=')
            
            # Normalize key: replace spaces with underscores, make lowercase
            # But preserve existing structure (item., tile., achievement., etc.)
            normalized_key = key.lower().replace(' ', '_')
            
            # Create the new line
            new_line = f"{normalized_key}={value}"
            output_lines.append(new_line)
            stats['converted'] += 1
        else:
            output_lines.append(line)
            stats['unchanged'] += 1
    elif line.startswith('eln.'):
        # Already in new format (eln.xxx), just keep as-is
        output_lines.append(line)
        stats['unchanged'] += 1
    else:
        # Unknown format, keep as-is
        output_lines.append(line)
        stats['unchanged'] += 1

# Now we need to add .name entries for items/blocks
# Read the converted lines and duplicate with .name where appropriate
final_lines = []
name_entries_added = 0

# Track which keys need .name variants
# These are keys that represent items or blocks (not GUI text, achievements, etc.)
ITEM_BLOCK_PATTERNS = [
    r'^eln\.(?!achievement|entity|death|itemGroup|container|gui|tooltip)[a-zA-Z0-9_.]+$',
]

for line in output_lines:
    final_lines.append(line)
    
    # Skip non-translation lines
    if not line or line.startswith('#') or '=' not in line:
        continue
    
    key = line.split('=', 1)[0]
    value = line.split('=', 1)[1]
    
    # Skip if already has .name
    if key.endswith('.name'):
        continue
    
    # Skip special categories that don't need .name
    skip_prefixes = [
        'eln.achievement',
        'eln.entity', 
        'eln.death',
        'eln.itemGroup',
        'eln.container',
        'eln.gui',
        'eln.tooltip',
        'eln.mod',
    ]
    
    if any(key.startswith(p) for p in skip_prefixes):
        continue
    
    # Add .name variant for items/blocks
    name_line = f"{key}.name={value}"
    final_lines.append(name_line)
    name_entries_added += 1

# Write output
with open('src/main/resources/assets/eln/lang/en_us.lang', 'w', encoding='utf-8') as f:
    f.write('\n'.join(final_lines))

print(f"=== Language File Fix Summary ===")
print(f"Converted from eln: format: {stats['converted']} entries")
print(f"Unchanged: {stats['unchanged']} entries")
print(f"Added .name variants: {name_entries_added} entries")
print(f"\nOutput written to: src/main/resources/assets/eln/lang/en_us.lang")

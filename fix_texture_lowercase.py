#!/usr/bin/env python3
"""
Rename all texture files to lowercase to match Minecraft's ResourceLocation behavior.
Also updates MTL files to reference the lowercase names.
"""

import os
import re

def main():
    model_dir = 'src/main/resources/assets/eln/model'
    
    print("Scanning for PNG files...")
    changes = 0
    
    for root, dirs, files in os.walk(model_dir):
        for file in files:
            if file.lower().endswith('.png'):
                lower_name = file.lower()
                if file != lower_name:
                    old_path = os.path.join(root, file)
                    new_path = os.path.join(root, lower_name)
                    print(f"Renaming: {file} -> {lower_name}")
                    os.rename(old_path, new_path)
                    changes += 1
    
    print(f"\nRenamed {changes} texture files to lowercase")
    
    # Now update MTL files
    print("\nUpdating MTL files...")
    mtl_changes = 0
    
    for root, dirs, files in os.walk(model_dir):
        for file in files:
            if file.lower().endswith('.mtl'):
                mtl_path = os.path.join(root, file)
                
                with open(mtl_path, 'r', encoding='utf-8') as f:
                    content = f.read()
                
                original_content = content
                
                # Fix map_Kd lines
                def fix_map_kd(match):
                    prefix = match.group(1)
                    texture_name = match.group(2)
                    # Just use the basename and lowercase it
                    base_name = os.path.basename(texture_name)
                    return prefix + base_name.lower()
                
                content = re.sub(r'(map_Kd\s+)([^\n]+)', fix_map_kd, content, flags=re.IGNORECASE)
                
                if content != original_content:
                    with open(mtl_path, 'w', encoding='utf-8') as f:
                        f.write(content)
                    mtl_changes += 1
                    print(f"Updated: {file}")
    
    print(f"\nUpdated {mtl_changes} MTL files")
    print("Done!")

if __name__ == '__main__':
    main()

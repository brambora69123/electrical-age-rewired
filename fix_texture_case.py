#!/usr/bin/env python3
"""
Fix texture filename case sensitivity issues.
Scans all MTL files and renames texture files to match the case referenced in MTL files.
"""

import os
import re
from pathlib import Path

def find_mtl_files(model_dir):
    """Find all MTL files in the model directory."""
    mtl_files = []
    for root, dirs, files in os.walk(model_dir):
        for file in files:
            if file.lower().endswith('.mtl'):
                mtl_files.append(os.path.join(root, file))
    return mtl_files

def parse_mtl_file(mtl_path):
    """Parse MTL file and extract map_Kd references."""
    textures = []
    with open(mtl_path, 'r', encoding='utf-8') as f:
        for line in f:
            line = line.strip()
            if line.startswith('map_Kd'):
                parts = line.split()
                if len(parts) >= 2:
                    # Handle Windows paths in MTL files (e.g., from BatteryBigHV.mtl)
                    texture_path = parts[1]
                    # Extract just the filename if it's a full path
                    if ':' in texture_path or '\\' in texture_path:
                        texture_path = os.path.basename(texture_path)
                    textures.append(texture_path)
    return textures

def find_texture_files(model_dir):
    """Find all PNG files and create a case-insensitive mapping."""
    texture_map = {}  # lowercase -> actual path
    for root, dirs, files in os.walk(model_dir):
        for file in files:
            if file.lower().endswith('.png'):
                lower_name = file.lower()
                full_path = os.path.join(root, file)
                if lower_name not in texture_map:
                    texture_map[lower_name] = []
                texture_map[lower_name].append(full_path)
    return texture_map

def main():
    model_dir = 'src/main/resources/assets/eln/model'
    
    print("Scanning for texture files...")
    texture_map = find_texture_files(model_dir)
    
    print(f"Found {len(texture_map)} unique texture names (case-insensitive)")
    
    print("\nScanning MTL files...")
    mtl_files = find_mtl_files(model_dir)
    print(f"Found {len(mtl_files)} MTL files")
    
    changes_made = 0
    
    for mtl_path in mtl_files:
        mtl_dir = os.path.dirname(mtl_path)
        textures = parse_mtl_file(mtl_path)
        
        for texture_name in textures:
            texture_lower = texture_name.lower()
            
            # Check if we have a matching texture file
            if texture_lower in texture_map:
                matching_files = texture_map[texture_lower]
                
                # Check if the exact case matches
                expected_path = os.path.join(mtl_dir, texture_name)
                
                if not os.path.exists(expected_path):
                    # Need to rename
                    if len(matching_files) == 1:
                        actual_path = matching_files[0]
                        actual_name = os.path.basename(actual_path)
                        
                        # Rename the file to match MTL reference
                        new_path = os.path.join(mtl_dir, texture_name)
                        
                        if os.path.exists(actual_path) and actual_path != new_path:
                            print(f"Renaming: {actual_name} -> {texture_name}")
                            os.rename(actual_path, new_path)
                            changes_made += 1
                            
                            # Update texture map
                            texture_map[texture_lower] = [new_path]
                    else:
                        print(f"Warning: Multiple matches for {texture_name} in {mtl_path}: {matching_files}")
            else:
                print(f"Warning: Texture not found: {texture_name} (referenced in {mtl_path})")
    
    print(f"\nDone! Made {changes_made} filename case changes.")

if __name__ == '__main__':
    main()

#!/usr/bin/env python3
"""
Fix texture filename case sensitivity issues.
Scans all MTL files and updates map_Kd references to match actual texture file casing.
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
    """Parse MTL file and extract map_Kd references with line numbers."""
    textures = []
    with open(mtl_path, 'r', encoding='utf-8') as f:
        for line_num, line in enumerate(f, 1):
            line_stripped = line.strip()
            if line_stripped.startswith('map_Kd'):
                parts = line_stripped.split()
                if len(parts) >= 2:
                    texture_path = parts[1]
                    # Handle Windows paths in MTL files
                    if ':' in texture_path or '\\' in texture_path:
                        texture_path = os.path.basename(texture_path)
                    textures.append((line_num, texture_path, line))
    return textures

def find_texture_files_in_dir(dir_path):
    """Find all PNG files in a directory with case-insensitive mapping."""
    texture_map = {}  # lowercase -> actual name
    if os.path.isdir(dir_path):
        for file in os.listdir(dir_path):
            if file.lower().endswith('.png'):
                texture_map[file.lower()] = file
    return texture_map

def main():
    model_dir = 'src/main/resources/assets/eln/model'
    
    print("Scanning MTL files...")
    mtl_files = find_mtl_files(model_dir)
    print(f"Found {len(mtl_files)} MTL files")
    
    changes_made = 0
    files_modified = 0
    
    for mtl_path in mtl_files:
        mtl_dir = os.path.dirname(mtl_path)
        textures = parse_mtl_file(mtl_path)
        
        if not textures:
            continue
        
        # Get available textures in the same directory
        available_textures = find_texture_files_in_dir(mtl_dir)
        
        if not available_textures:
            continue
        
        mtl_modified = False
        new_lines = []
        
        with open(mtl_path, 'r', encoding='utf-8') as f:
            lines = f.readlines()
        
        for line_num, texture_ref, original_line in textures:
            texture_lower = texture_ref.lower()
            
            if texture_lower in available_textures:
                actual_name = available_textures[texture_lower]
                
                if texture_ref != actual_name:
                    # Need to fix the case
                    print(f"{mtl_path}:{line_num}: {texture_ref} -> {actual_name}")
                    
                    # Replace in the line
                    line = lines[line_num - 1]
                    new_line = line.replace(texture_ref, actual_name)
                    lines[line_num - 1] = new_line
                    mtl_modified = True
                    changes_made += 1
        
        if mtl_modified:
            with open(mtl_path, 'w', encoding='utf-8') as f:
                f.writelines(lines)
            files_modified += 1
    
    print(f"\nDone! Modified {files_modified} MTL files, made {changes_made} texture reference fixes.")

if __name__ == '__main__':
    main()

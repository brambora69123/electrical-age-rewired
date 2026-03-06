#!/usr/bin/env python3
"""
Rename all model folders to lowercase to match Minecraft's ResourceLocation behavior.
"""

import os
import re

def main():
    model_dir = 'src/main/resources/assets/eln/model'
    
    print("Scanning for model folders...")
    changes = 0
    
    # Get all directories first
    dirs_to_rename = []
    for item in os.listdir(model_dir):
        item_path = os.path.join(model_dir, item)
        if os.path.isdir(item_path) and not item.startswith('_'):
            if item != item.lower():
                dirs_to_rename.append((item, item.lower()))
    
    print(f"Found {len(dirs_to_rename)} folders to rename")
    
    # Rename folders
    for old_name, new_name in dirs_to_rename:
        old_path = os.path.join(model_dir, old_name)
        new_path = os.path.join(model_dir, new_name)
        print(f"Renaming folder: {old_name} -> {new_name}")
        os.rename(old_path, new_path)
        changes += 1
    
    print(f"\nRenamed {changes} folders to lowercase")
    
    # Now update OBJ files that reference MTL files
    print("\nUpdating OBJ files...")
    obj_changes = 0
    
    for root, dirs, files in os.walk(model_dir):
        for file in files:
            if file.lower().endswith('.obj'):
                obj_path = os.path.join(root, file)
                
                with open(obj_path, 'r', encoding='utf-8') as f:
                    content = f.read()
                
                original_content = content
                
                # Fix mtllib lines - just use lowercase filename
                def fix_mtllib(match):
                    prefix = match.group(1)
                    mtl_name = match.group(2)
                    return prefix + mtl_name.lower()
                
                content = re.sub(r'(mtllib\s+)([^\n]+)', fix_mtllib, content, flags=re.IGNORECASE)
                
                if content != original_content:
                    with open(obj_path, 'w', encoding='utf-8') as f:
                        f.write(content)
                    obj_changes += 1
                    print(f"Updated: {file}")
    
    print(f"\nUpdated {obj_changes} OBJ files")
    print("Done!")

if __name__ == '__main__':
    main()

#!/usr/bin/env python3
"""
Rename all OBJ and MTL files to lowercase to match folder names.
"""

import os
import re

def main():
    model_dir = 'src/main/resources/assets/eln/model'
    
    print("Scanning for OBJ and MTL files...")
    changes = 0
    
    for root, dirs, files in os.walk(model_dir):
        for file in files:
            if file.lower().endswith('.obj') or file.lower().endswith('.mtl'):
                lower_name = file.lower()
                if file != lower_name:
                    old_path = os.path.join(root, file)
                    new_path = os.path.join(root, lower_name)
                    print(f"Renaming: {file} -> {lower_name}")
                    os.rename(old_path, new_path)
                    changes += 1
    
    print(f"\nRenamed {changes} OBJ/MTL files to lowercase")
    
    # Update OBJ files that reference MTL files
    print("\nUpdating OBJ mtllib references...")
    obj_changes = 0
    
    for root, dirs, files in os.walk(model_dir):
        for file in files:
            if file.lower().endswith('.obj'):
                obj_path = os.path.join(root, file)
                
                with open(obj_path, 'r', encoding='utf-8', errors='ignore') as f:
                    content = f.read()
                
                original_content = content
                
                # Fix mtllib lines
                def fix_mtllib(match):
                    prefix = match.group(1)
                    mtl_name = match.group(2)
                    return prefix + mtl_name.lower()
                
                content = re.sub(r'(mtllib\s+)([^\n]+)', fix_mtllib, content, flags=re.IGNORECASE)
                
                if content != original_content:
                    with open(obj_path, 'w', encoding='utf-8') as f:
                        f.write(content)
                    obj_changes += 1
    
    print(f"Updated {obj_changes} OBJ files")
    print("Done!")

if __name__ == '__main__':
    main()

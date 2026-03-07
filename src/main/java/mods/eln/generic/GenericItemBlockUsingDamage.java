package mods.eln.generic;

import mods.eln.misc.Utils;
import mods.eln.misc.UtilsClient;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class GenericItemBlockUsingDamage<Descriptor extends GenericItemBlockUsingDamageDescriptor> extends ItemBlock {

    public Hashtable<Integer, Descriptor> subItemList = new Hashtable<Integer, Descriptor>();
    public ArrayList<Integer> orderList = new ArrayList<Integer>();
    public ArrayList<Descriptor> descriptors = new ArrayList<Descriptor>();

    public Descriptor defaultElement = null;

    public GenericItemBlockUsingDamage(Block b) {
        super(b);
        setHasSubtypes(true);
    }

    public void setDefaultElement(Descriptor descriptor) {
        defaultElement = descriptor;
    }

    public void doubleEntry(int src, int dst) {
        subItemList.put(dst, subItemList.get(src));
    }

    public void addDescriptor(int damage, Descriptor descriptor) {
        subItemList.put(damage, descriptor);
        ItemStack stack = new ItemStack(this, 1, damage);
        stack.setTagCompound(descriptor.getDefaultNBT());
        //LanguageRegistry.addName(stack, descriptor.name);
        orderList.add(damage);
        descriptors.add(descriptor);
        descriptor.setParent(this, damage);
        // In 1.12.2, items are registered via RegistryEvent.Register, not here
        // The parent ItemBlock is already registered, descriptors are just metadata
    }

    public void addWithoutRegistry(int damage, Descriptor descriptor) {
        subItemList.put(damage, descriptor);
        ItemStack stack = new ItemStack(this, 1, damage);
        stack.setTagCompound(descriptor.getDefaultNBT());
        descriptor.setParent(this, damage);
    }

    public Descriptor getDescriptor(int damage) {
        return subItemList.get(damage);
    }

    public Descriptor getDescriptor(ItemStack itemStack) {
        if (itemStack == null) return defaultElement;
        if (itemStack.getItem() != this) return defaultElement;
        return getDescriptor(itemStack.getItemDamage());
    }

	/*
    @Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int damage) {
		return getDescriptor(damage).getIconId();
		
	}
	//caca1.5.1
	@Override
	public String getTextureFile () {
		return CommonProxy.ITEMS_PNG;
	}
	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return getItemName() + "." + getDescriptor(itemstack).name;
	}
	*/

	/*@Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
		Descriptor desc = getDescriptor(par1ItemStack);
		if(desc == null) return "Unknown";
        return desc.getName(par1ItemStack);
    }*/

    @Override
    public String getTranslationKey(ItemStack stack) {
        Descriptor desc = getDescriptor(stack);
        if (desc == null) {
            return this.getClass().getName();
        } else {
            return "eln:" + desc.name.replaceAll("\\s+", "_");
        }
    }

    // TODO(1.10): Fix item rendering.
//    @Override
//    public IIcon getIconFromDamage(int damage) {
//        Descriptor desc = getDescriptor(damage);
//        if (desc == null) return null;
//        return desc.getIcon();
//    }
//
//    @Override
//    @SideOnly(value = Side.CLIENT)
//    public void registerIcons(IIconRegister iconRegister) {
//        for (GenericItemBlockUsingDamageDescriptor descriptor : subItemList.values()) {
//            descriptor.updateIcons(iconRegister);
//        }
//    }


    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tabs)) {
            // Add all sub-items to the creative tab
            for (int id : orderList) {
                Descriptor descriptor = subItemList.get(id);
                if (descriptor != null) {
                    ItemStack stack = new ItemStack(this, 1, id);
                    stack.setTagCompound(descriptor.getDefaultNBT());
                    items.add(stack);
                    System.out.println("Added creative item: " + id + " with descriptor: " + descriptor.name);
                } else {
                    System.out.println("Missing descriptor for id: " + id);
                }
            }
        }
    }

    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
        Descriptor desc = getDescriptor(itemStack);
        if (desc == null) return;
        List listFromDescriptor = new ArrayList();
        desc.addInformation(itemStack, entityPlayer, listFromDescriptor, par4);
        UtilsClient.showItemTooltip(listFromDescriptor, list);
    }

//    public boolean onEntityItemUpdate(EntityItem entityItem) {
//        Descriptor desc = getDescriptor(entityItem.getEntityItem());
//        if (desc != null) return desc.onEntityItemUpdate(entityItem);
//        return false;
//    }
//
//    //TODO: Maybe its onItemUse only?
//    @Override
//    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
//        Descriptor desc = getDescriptor(stack);
//        if (desc != null) return desc.onItemUse(stack, player);
//        return EnumActionResult.FAIL;
//    }
}

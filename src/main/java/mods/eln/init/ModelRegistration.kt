package mods.eln.init

import mods.eln.Eln
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

/**
 * Handles model registration for all Electrical Age items and blocks.
 * This is the proper 1.12.2 way to register item models.
 */
@Mod.EventBusSubscriber(modid = Eln.MODID)
object ModelRegistration {

    /**
     * Register item models when the ModelRegistryEvent fires.
     * This must happen before the model baking phase.
     */
    @SubscribeEvent
    @JvmStatic
    fun registerModels(event: ModelRegistryEvent) {
        Eln.logger.info("Registering Electrical Age item models...")

        // Register models for simple blocks (ore, rubber, flubber, ghost, light)
        registerSimpleBlockModel(ModBlock.oreBlock)
        registerSimpleBlockModel(ModBlock.rubberBlock)
        registerSimpleBlockModel(ModBlock.flubberBlock)
        registerSimpleBlockModel(ModBlock.ghostBlock)
        registerSimpleBlockModel(ModBlock.lightBlock)

        // Register SixNodeItem with sub-items (cables, etc.)
        registerItemWithSubItems(Eln.sixNodeItem)

        // Register TransparentNodeItem with sub-items (machines, etc.)
        registerItemWithSubItems(Eln.transparentNodeItem)

        Eln.logger.info("Registered Electrical Age item models")
    }

    /**
     * Register a simple block item model using the block's registry name.
     */
    private fun registerSimpleBlockModel(block: net.minecraft.block.Block) {
        val item = Item.getItemFromBlock(block)
        if (item != null && item !== net.minecraft.init.Items.AIR) {
            val registryName = block.registryName ?: return
            
            // Special handling for ore block with variants
            if (block is ElnOreBlock) {
                ModelLoader.setCustomModelResourceLocation(
                    item,
                    0,
                    ModelResourceLocation(registryName, "variant=copper_ore")
                )
                ModelLoader.setCustomModelResourceLocation(
                    item,
                    1,
                    ModelResourceLocation(registryName, "variant=lead_ore")
                )
            } else {
                ModelLoader.setCustomModelResourceLocation(
                    item,
                    0,
                    ModelResourceLocation(registryName, "inventory")
                )
            }
        }
    }
    
    /**
     * Register an item with sub-items (damage values).
     * This registers all variants from the descriptor list.
     */
    private fun registerItemWithSubItems(item: net.minecraft.item.ItemBlock) {
        val registryName = item.registryName ?: return
        
        // Get the descriptor list and register each variant
        if (item is mods.eln.generic.GenericItemBlockUsingDamage<*>) {
            val orderList = item.orderList
            for (damage in orderList) {
                ModelLoader.setCustomModelResourceLocation(
                    item,
                    damage,
                    ModelResourceLocation(registryName, "inventory")
                )
            }
            Eln.logger.info("Registered ${orderList.size} models for ${registryName}")
        } else {
            // Register the base item
            ModelLoader.setCustomModelResourceLocation(
                item,
                0,
                ModelResourceLocation(registryName, "inventory")
            )
        }
    }
}

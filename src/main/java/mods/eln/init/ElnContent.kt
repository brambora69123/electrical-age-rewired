package mods.eln.init

import mods.eln.Eln
import mods.eln.node.NodeBlockEntity
import mods.eln.node.NodeManager
import mods.eln.node.six.SixNode
import mods.eln.node.six.SixNodeEntity
import mods.eln.node.six.SixNodeItem
import mods.eln.node.transparent.TransparentNode
import mods.eln.node.transparent.TransparentNodeEntity
import mods.eln.node.transparent.TransparentNodeEntityWithFluid
import mods.eln.node.transparent.TransparentNodeItem
import mods.eln.sixnode.lampsocket.LightBlockEntity
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.EntityRegistry
import net.minecraftforge.fml.common.registry.GameRegistry
import java.util.ArrayList

/**
 * Central content registration class for Electrical Age.
 * Follows the 1.12.2 Forge registration pattern using event-driven registry.
 *
 * Registration flow:
 * 1. PreInit: Create block/item instances and add to ArrayLists (do NOT register yet)
 * 2. Registry Events (@SubscribeEvent): Register blocks and items with proper registry names
 * 3. Init: Register Tile Entities, Entities, and other content
 */
@Mod.EventBusSubscriber(modid = Eln.MODID)
object ElnContent {

    // Lists to hold blocks and items before registry events fire
    @JvmField
    val registeredBlocks = ArrayList<Block>()

    @JvmField
    val registeredItems = ArrayList<Item>()

    // Block instances - created in preInit via ModBlock, registered via event
    val oreBlock get() = ModBlock.oreBlock
    val rubberBlock get() = ModBlock.rubberBlock
    val flubberBlock get() = ModBlock.flubberBlock
    val ghostBlock get() = ModBlock.ghostBlock
    val sixNodeBlock get() = ModBlock.sixNodeBlock
    val transparentNodeBlock get() = ModBlock.transparentNodeBlock
    val lightBlock get() = ModBlock.lightBlock

    // Initialize content - call during preInit
    @JvmStatic
    fun preInit() {
        // Initialize blocks via ModBlock (creates static fields for Java compatibility)
        ModBlock.init()

        // Add blocks to registration list
        registeredBlocks.addAll(listOf(
            ModBlock.oreBlock,
            ModBlock.rubberBlock,
            ModBlock.flubberBlock,
            ModBlock.ghostBlock,
            ModBlock.sixNodeBlock,
            ModBlock.transparentNodeBlock,
            ModBlock.lightBlock
        ))

        // Initialize SixNodeItem and TransparentNodeItem
        Eln.sixNodeItem = SixNodeItem(ModBlock.sixNodeBlock).setCreativeTab(Eln.Tab) as SixNodeItem
        Eln.transparentNodeItem = TransparentNodeItem(ModBlock.transparentNodeBlock).setCreativeTab(Eln.Tab) as TransparentNodeItem
        
        // Register descriptors (adds sub-items to SixNodeItem/TransparentNodeItem)
        Descriptors.preInit()
    }
    
    /**
     * Register blocks when the RegistryEvent.Register<Block> fires.
     * This is the correct 1.12.2 way to register blocks.
     */
    @SubscribeEvent
    @JvmStatic
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        Eln.logger.info("Registering Electrical Age blocks...")
        for (block in registeredBlocks) {
            Eln.logger.debug("Registering block: ${block.javaClass.simpleName} - translationKey: ${block.translationKey}")
            // Block registry name is set in constructor, so we just register
            event.registry.register(block)
            Eln.logger.debug("Registered block: ${block.registryName}")
        }
        Eln.logger.info("Registered ${registeredBlocks.size} Electrical Age blocks")
    }
    
    /**
     * Register items when the RegistryEvent.Register<Item> fires.
     * This is the correct 1.12.2 way to register items.
     */
    @SubscribeEvent
    @JvmStatic
    fun registerItems(event: RegistryEvent.Register<Item>) {
        Eln.logger.info("Registering Electrical Age items...")

        // Register block items - create ItemBlock for each block
        // Skip sixnode and transparentnode as they have custom ItemBlocks
        for (block in registeredBlocks) {
            if (block === ModBlock.sixNodeBlock || block === ModBlock.transparentNodeBlock) {
                continue // These are registered separately below
            }
            val itemBlock = net.minecraft.item.ItemBlock(block)
            itemBlock.registryName = block.registryName
            event.registry.register(itemBlock)
            Eln.logger.debug("Registered block item: ${itemBlock.registryName}")
        }

        // Register custom ItemBlocks for SixNode and TransparentNode
        event.registry.register(Eln.sixNodeItem.setRegistryName(ModBlock.sixNodeBlock.registryName))
        event.registry.register(Eln.transparentNodeItem.setRegistryName(ModBlock.transparentNodeBlock.registryName))

        // Register standalone items
        for (item in registeredItems) {
            event.registry.register(item)
            Eln.logger.debug("Registered item: ${item.registryName}")
        }

        Eln.logger.info("Registered ${registeredBlocks.size + registeredItems.size} Electrical Age items")
    }
    
    /**
     * Initialize Tile Entities, Entities, and other content.
     * Call this during the init phase, after blocks and items are registered.
     */
    @JvmStatic
    fun init() {
        Eln.logger.info("Initializing Electrical Age Tile Entities and Entities...")

        // Register Tile Entities
        registerTileEntities()
        
        // Register node UUIDs for save/load
        registerNodeUuids()

        // Register Entities (mobs, projectiles, etc.)
        registerEntities()

        // Register other content (multiblocks, recipes, etc.)
        registerOtherContent()

        Eln.logger.info("Electrical Age content initialization complete")
    }
    
    /**
     * Register node UUIDs for save/load functionality.
     * This must be called before any nodes are loaded from NBT.
     */
    @JvmStatic
    private fun registerNodeUuids() {
        // SixNode uses "s" UUID
        NodeManager.registerUuid("s", SixNode::class.java)
        // TransparentNode uses "t" UUID  
        NodeManager.registerUuid("t", TransparentNode::class.java)
    }
    
    /**
     * Register all Tile Entities using GameRegistry.
     */
    @JvmStatic
    private fun registerTileEntities() {
        // Base node tile entities
        GameRegistry.registerTileEntity(SixNodeEntity::class.java, "$Eln.MODID:SixNodeEntity")
        GameRegistry.registerTileEntity(TransparentNodeEntity::class.java, "$Eln.MODID:TransparentNodeEntity")
        GameRegistry.registerTileEntity(TransparentNodeEntityWithFluid::class.java, "$Eln.MODID:TransparentNodeEntityWF")
        
        // Special tile entities
        GameRegistry.registerTileEntity(LightBlockEntity::class.java, "$Eln.MODID:LightBlockEntity")
        GameRegistry.registerTileEntity(NodeBlockEntity::class.java, "$Eln.MODID:NodeBlockEntity")
        
        // TODO: Register additional tile entities as they are migrated
        // registerTile(TileEntityCokeOven::class.java)
        // registerTile(TileEntityBlastFurnace::class.java)
        
        Eln.logger.info("Registered Electrical Age tile entities")
    }
    
    /**
     * Register all entities using EntityRegistry.
     */
    @JvmStatic
    private fun registerEntities() {
        var entityId = 0

        // TODO: Register mod entities as they are migrated
        // Example:
        // EntityRegistry.registerModEntity(
        //     ResourceLocation(Eln.MODID, "entity_name"),
        //     EntityClass::class.java,
        //     "entity_name",
        //     entityId++,
        //     Eln.instance!!, // Mod instance reference
        //     64, // tracking range
        //     1, // update frequency
        //     true // should track velocity
        // )

        Eln.logger.info("Registered $entityId Electrical Age entities")
    }
    
    /**
     * Register other content like recipes, multiblocks, etc.
     */
    @JvmStatic
    private fun registerOtherContent() {
        // TODO: Register multiblocks
        // TODO: Register recipes
        // Recipes.init()
    }
    
    /**
     * Helper method to register a Tile Entity with a simplified name.
     * Removes "TileEntity" prefix from the class name.
     */
    @JvmStatic
    fun registerTile(tileClass: Class<out net.minecraft.tileentity.TileEntity>) {
        var name = tileClass.simpleName
        if (name.startsWith("TileEntity")) {
            name = name.substring("TileEntity".length)
        }
        GameRegistry.registerTileEntity(tileClass, "$Eln.MODID:$name")
    }
}

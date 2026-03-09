package mods.eln.init

import mods.eln.Eln
import mods.eln.generic.GenericItemUsingDamageDescriptor
import mods.eln.ghost.GhostBlock
import mods.eln.ghost.GhostManager
import mods.eln.item.MiningPipeDescriptor
import mods.eln.item.TreeResin
import mods.eln.item.electricalinterface.ItemEnergyInventoryProcess
import mods.eln.misc.Obj3DFolder
import mods.eln.node.NodeBlockEntity
import mods.eln.node.NodeManager
import mods.eln.node.NodePublishProcess
import mods.eln.node.six.SixNode
import mods.eln.node.six.SixNodeBlock
import mods.eln.node.six.SixNodeEntity
import mods.eln.node.six.SixNodeItem
import mods.eln.node.transparent.TransparentNode
import mods.eln.node.transparent.TransparentNodeBlock
import mods.eln.node.transparent.TransparentNodeEntity
import mods.eln.node.transparent.TransparentNodeEntityWithFluid
import mods.eln.entity.ReplicatorEntity
import mods.eln.sixnode.TreeResinCollector.TreeResinCollectorTileEntity
import mods.eln.simplenode.computerprobe.ComputerProbeEntity
import mods.eln.simplenode.energyconverter.EnergyConverterElnToOtherEntity
import mods.eln.node.transparent.TransparentNodeItem
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.EntityRegistry
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.oredict.OreDictionary
import java.util.ArrayList

/**
 * Central content registration class for Electrical Age.
 * Follows the 1.12.2 Forge registration pattern using event-driven registry.
 *
 * Registration flow:
 * 1. PreInit: Create blocks/items and add to ArrayLists (do NOT register yet)
 * 2. Registry Events (@SubscribeEvent): Register blocks and items with proper registry names
 * 3. Init: Register Tile Entities, Entities, recipes, and other content
 */
@Mod.EventBusSubscriber(modid = Eln.MODID)
object ElnContent {

    // =====================================================================
    // Lists to hold blocks and items before registry events fire
    // =====================================================================
    @JvmField
    val registeredBlocks = ArrayList<Block>()

    @JvmField
    val registeredItems = ArrayList<Item>()

    // =====================================================================
    // Block instances - created in preInit via ModBlock
    // =====================================================================
    val oreBlock get() = ModBlock.oreBlock
    val rubberBlock get() = ModBlock.rubberBlock
    val flubberBlock get() = ModBlock.flubberBlock
    val ghostBlock get() = ModBlock.ghostBlock
    val sixNodeBlock get() = ModBlock.sixNodeBlock
    val transparentNodeBlock get() = ModBlock.transparentNodeBlock

    // =====================================================================
    // Initialize content - call during preInit
    // =====================================================================
    @JvmStatic
    fun preInit() {
        Eln.logger.info("Pre-initializing Electrical Age content...")

        // Initialize blocks via ModBlock (creates static fields for Java compatibility)
        ModBlock.init()

        // Add blocks to registration list
        registeredBlocks.addAll(
            listOf(
                ModBlock.oreBlock,
                ModBlock.rubberBlock,
                ModBlock.flubberBlock,
                ModBlock.ghostBlock,
                ModBlock.sixNodeBlock,
                ModBlock.transparentNodeBlock
            )
        )

        // Initialize SixNodeItem and TransparentNodeItem
        Eln.sixNodeItem = (SixNodeItem(ModBlock.sixNodeBlock).setCreativeTab(Eln.Tab) as SixNodeItem).apply {
            setRegistryName(ModBlock.sixNodeBlock.registryName)
        }
        Eln.transparentNodeItem = (TransparentNodeItem(ModBlock.transparentNodeBlock).setCreativeTab(Eln.Tab) as TransparentNodeItem).apply {
            setRegistryName(ModBlock.transparentNodeBlock.registryName)
        }

        // Register descriptors (adds sub-items to SixNodeItem/TransparentNodeItem)
        Descriptors.preInit()

        // Initialize mining pipe descriptor
        Eln.miningPipeDescriptor = MiningPipeDescriptor("Mining Pipe")

        Eln.logger.info("Electrical Age pre-initialization complete")
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
            
            val itemBlock = if (block === ModBlock.oreBlock) {
                ElnItemBlockOre(block)
            } else {
                net.minecraft.item.ItemBlock(block)
            }
            
            itemBlock.registryName = block.registryName
            event.registry.register(itemBlock)
            Eln.logger.debug("Registered block item: ${itemBlock.registryName}")
        }

        // Register custom ItemBlocks for SixNode and TransparentNode
        event.registry.register(Eln.sixNodeItem)
        event.registry.register(Eln.transparentNodeItem)

        // Register shared items (multi-meter, thermometer, etc.)
        event.registry.register(Eln.sharedItem)
        event.registry.register(Eln.sharedItemStackOne)

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

        // Initialize ore dictionary
        initOreDictionary()

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
        NodeManager.registerUuid(sixNodeBlock.nodeUuid, SixNode::class.java)
        // TransparentNode uses "t" UUID
        NodeManager.registerUuid(transparentNodeBlock.nodeUuid, TransparentNode::class.java)
    }

    /**
     * Register all Tile Entities using GameRegistry.
     */
    @JvmStatic
    private fun registerTileEntities() {
        // Base node tile entities
        GameRegistry.registerTileEntity(SixNodeEntity::class.java, Eln.MODID + ":SixNodeEntity")
        GameRegistry.registerTileEntity(TransparentNodeEntity::class.java, Eln.MODID + ":TransparentNodeEntity")
        GameRegistry.registerTileEntity(TransparentNodeEntityWithFluid::class.java, Eln.MODID + ":TransparentNodeEntityWF")

        // Special tile entities
        GameRegistry.registerTileEntity(NodeBlockEntity::class.java, Eln.MODID + ":NodeBlockEntity")
        GameRegistry.registerTileEntity(TreeResinCollectorTileEntity::class.java, Eln.MODID + ":TreeResinCollectorTileEntity")
        GameRegistry.registerTileEntity(ComputerProbeEntity::class.java, Eln.MODID + ":ComputerProbeEntity")
        GameRegistry.registerTileEntity(EnergyConverterElnToOtherEntity::class.java, Eln.MODID + ":EnergyConverterElnToOtherEntity")

        Eln.logger.info("Registered Electrical Age tile entities")
    }

    /**
     * Register all entities using EntityRegistry.
     */
    @JvmStatic
    private fun registerEntities() {
        var entityId = 0

        EntityRegistry.registerModEntity(
            ResourceLocation(Eln.MODID, "replicator"),
            ReplicatorEntity::class.java,
            "replicator",
            entityId++,
            Eln.instance!!,
            64, 1, true,
            0x000000, 0x00FF00
        )

        Eln.logger.info("Registered $entityId Electrical Age entities")
    }

    /**
     * Initialize ore dictionary integration
     */
    @JvmStatic
    private fun initOreDictionary() {
        // Register ores for ore dictionary
        if (Config.generateCopper) {
            OreDictionary.registerOre("oreCopper", ItemStack(oreBlock, 1, 0))
        }
        if (Config.generateLead) {
            OreDictionary.registerOre("oreLead", ItemStack(oreBlock, 1, 1))
        }

        Eln.logger.info("Registered Electrical Age ore dictionary entries")
    }

    /**
     * Register other content like recipes, multiblocks, etc.
     */
    @JvmStatic
    private fun registerOtherContent() {
        // TODO: Register multiblocks
        
        // Initialize machine recipes
        Recipes.init()

        // Initialize managers (already done in preInit in current code, but kept here if needed for re-init)
        // Eln.playerManager = PlayerManager()
        // Eln.nodeManager = NodeManager("${Eln.MODID}.nodes")
        // Eln.ghostManager = GhostManager("${Eln.MODID}.ghosts")
        // Eln.delayedTaskManager = DelayedTaskManager()

        // Initialize simulator processes
        Eln.simulator.addSlowProcess(Eln.windProcess)
        Eln.simulator.addSlowProcess(Eln.replicatorPopProcess)
        Eln.simulator.addSlowProcess(Eln.itemEnergyInventoryProcess)
        Eln.simulator.addSlowProcess(Eln.nodePublishProcess)
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
        GameRegistry.registerTileEntity(tileClass, "${Eln.MODID}:$name")
    }
}

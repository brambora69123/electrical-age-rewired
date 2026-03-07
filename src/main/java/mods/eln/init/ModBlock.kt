package mods.eln.init

import mods.eln.Eln
import mods.eln.ghost.GhostBlock
import mods.eln.node.six.SixNodeBlock
import mods.eln.node.six.SixNodeEntity
import mods.eln.node.transparent.TransparentNodeBlock
import mods.eln.node.transparent.TransparentNodeEntity
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.IBlockState
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.lang.Math.abs

/**
 * Legacy compatibility object - provides static field access for Java code.
 * These are initialized by ElnContent during preInit.
 *
 * Note: Do not use these before ElnContent.preInit() is called during mod preInit.
 */
object ModBlock {
    @JvmStatic
    lateinit var oreBlock: ElnOreBlock

    @JvmStatic
    lateinit var rubberBlock: RubberBlock

    @JvmStatic
    lateinit var flubberBlock: RubberBlock

    @JvmStatic
    lateinit var ghostBlock: GhostBlock

    @JvmStatic
    lateinit var sixNodeBlock: SixNodeBlock

    @JvmStatic
    lateinit var transparentNodeBlock: TransparentNodeBlock

    /**
     * Initialize all blocks - called by ElnContent.preInit()
     */
    @JvmStatic
    internal fun init() {
        oreBlock = ElnOreBlock("copper_ore", "lead_ore")
        rubberBlock = RubberBlock("rubber", 0.75f)
        flubberBlock = RubberBlock("flubber", 2f)
        ghostBlock = GhostBlock().apply {
            setTranslationKey("ghost")
            setRegistryName("ghost")
        }
        sixNodeBlock = SixNodeBlock(Material.ROCK, SixNodeEntity::class.java).apply {
            setRegistryName("sixnode")
            setTranslationKey("sixnode")
            setCreativeTab(Eln.Tab)
        }
        transparentNodeBlock = TransparentNodeBlock(Material.ROCK, TransparentNodeEntity::class.java).apply {
            setRegistryName("transparentnode")
            setTranslationKey("transparentnode")
            setCreativeTab(Eln.Tab)
        }

        // Set creative tab for all blocks
        val tab = Eln.Tab
        oreBlock.creativeTab = tab
        rubberBlock.creativeTab = tab
        flubberBlock.creativeTab = tab
        ghostBlock.creativeTab = tab
        sixNodeBlock.creativeTab = tab
        transparentNodeBlock.creativeTab = tab
    }
}

class ElnOreBlock(vararg variants: String) : Block(Material.ROCK) {
    init {
        setHardness(3.0f)
        setResistance(5.0f)
        setTranslationKey("oreEln")
        setRegistryName("ore")
        setCreativeTab(Eln.Tab)
    }
    
    companion object {
        val VARIANT: PropertyEnum<VariantType> = PropertyEnum.create("variant", VariantType::class.java)
    }
    
    enum class VariantType(val modelName: String, val model: String) : net.minecraft.util.IStringSerializable {
        COPPER_ORE("copper_ore", "copper_ore"),
        LEAD_ORE("lead_ore", "lead_ore");
        
        override fun getName(): String = modelName
    }
    
    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, VARIANT)
    }
    
    override fun getStateFromMeta(meta: Int): IBlockState {
        val variant = if (meta == 0) VariantType.COPPER_ORE else VariantType.LEAD_ORE
        return defaultState.withProperty(VARIANT, variant)
    }
    
    override fun getMetaFromState(state: IBlockState): Int {
        return if (state.getValue(VARIANT) == VariantType.COPPER_ORE) 0 else 1
    }
    
    override fun damageDropped(state: IBlockState): Int {
        return getMetaFromState(state)
    }
}

class RubberBlock(name: String, private val bounce: Float) : Block(Material.WOOD) {
    init {
        setTranslationKey(name)
        setRegistryName(name)
        setCreativeTab(Eln.Tab)
    }
    
    override fun onLanded(worldIn: World, entityIn: Entity) {
        if (abs(entityIn.motionY) > 0.1) {
            entityIn.motionY = abs(entityIn.motionY * bounce)
        } else {
            entityIn.motionY = 0.0
        }
    }

    override fun onFallenUpon(worldIn: World, pos: BlockPos, entityIn: Entity, fallDistance: Float) {
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance / 8.0f)
    }
}

class SixNodeProxyBlock()

class ElnProxyBlock(name: String, val uuid: String)

class ElnBlockMod(name: String, material: Material, val uuid: String = name.take(1)) : Block(material) {
    init {
        setTranslationKey(name)
        setRegistryName(name)
        setCreativeTab(Eln.Tab)
    }

}

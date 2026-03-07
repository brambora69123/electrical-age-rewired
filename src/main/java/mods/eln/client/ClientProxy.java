package mods.eln.client;

import mods.eln.CommonProxy;
import mods.eln.Eln;
import mods.eln.node.six.SixNodeEntity;
import mods.eln.node.six.SixNodeRender;
import mods.eln.node.transparent.TransparentNodeEntity;
import mods.eln.node.transparent.TransparentNodeRender;
import mods.eln.sound.SoundClientEventListener;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Client-side proxy implementation.
 * Registers tile entity special renderers and other client-side content.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    public static UuidManager uuidManager;
    public static SoundClientEventListener soundClientEventListener;
    public static ClientPacketHandler clientPacketHandler;
    public static FrameTime frameTime;

    /**
     * Called during preInit phase on client side.
     */
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        uuidManager = new UuidManager();
        soundClientEventListener = new SoundClientEventListener(uuidManager);
        frameTime = new FrameTime();
    }

    /**
     * Called during init phase on client side.
     * Register tile entity special renderers (TESR).
     */
    @Override
    public void init(FMLInitializationEvent event) {
        clientPacketHandler = new ClientPacketHandler();
        // Register TESRs for SixNode and TransparentNode
        ClientRegistry.bindTileEntitySpecialRenderer(SixNodeEntity.class, new SixNodeRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TransparentNodeEntity.class, new TransparentNodeRender());
    }
}

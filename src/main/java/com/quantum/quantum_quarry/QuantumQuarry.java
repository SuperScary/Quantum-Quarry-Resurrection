package com.quantum.quantum_quarry;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.quantum.quantum_quarry.init.ModBlocks;
import com.quantum.quantum_quarry.init.ModItems;
import com.quantum.quantum_quarry.init.BlockEntities;
import com.quantum.quantum_quarry.init.DataComponents;
import com.quantum.quantum_quarry.init.Screens;
import com.quantum.quantum_quarry.init.Network;
import com.quantum.quantum_quarry.init.Menus;
import com.quantum.quantum_quarry.init.Tabs;

@Mod(QuantumQuarry.MODID)
public class QuantumQuarry
{

    public static final String MODID = "quantum_quarry";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister.Blocks BLOCKS = ModBlocks.REGISTRY;
    public static final DeferredRegister.Items ITEMS = ModItems.REGISTRY;
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = Tabs.REGISTRY;
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = BlockEntities.REGISTRY;

    public QuantumQuarry(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        DataComponents.REGISTRY.register(modEventBus);
        // Register the Deferred Register to the mod event bus so screens get registered
        // lol its missing
        // Register the Deferred Register to the mod event bus so menus get registered
        Menus.REGISTRY.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}

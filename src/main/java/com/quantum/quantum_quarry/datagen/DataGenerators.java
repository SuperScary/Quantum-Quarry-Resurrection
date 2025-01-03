package com.quantum.quantum_quarry.datagen;

import com.quantum.quantum_quarry.QuantumQuarry;
import com.quantum.quantum_quarry.datagen.providers.loot.LootTableProvider;
import com.quantum.quantum_quarry.datagen.providers.recipe.ModCraftingRecipes;
import com.quantum.quantum_quarry.datagen.providers.tags.BlockTagGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

@EventBusSubscriber(modid = QuantumQuarry.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var registries = event.getLookupProvider();
        var pack = generator.getVanillaPack(true);
        var existingFileHelper = event.getExistingFileHelper();
        // var localization = new EnLangProvider(generator);

        // Loot Table
        pack.addProvider(bindRegistries(LootTableProvider::new, registries));

        // Tags
        var blockTagsProvider = pack.addProvider(pOutput -> new BlockTagGenerator(pOutput, registries, existingFileHelper));

        // Recipes
        pack.addProvider(bindRegistries(ModCraftingRecipes::new, registries));

    }

    private static <T extends DataProvider>DataProvider.Factory<T> bindRegistries(BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, T> factory, CompletableFuture<HolderLookup.Provider> factories) {
        return pOutput -> factory.apply(pOutput, factories);
    }

}

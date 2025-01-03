package com.quantum.quantum_quarry.datagen.providers.recipe;

import com.quantum.quantum_quarry.init.ModBlocks;
import com.quantum.quantum_quarry.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModCraftingRecipes extends ModRecipeProvider {

    public ModCraftingRecipes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    protected void buildRecipes (@NotNull RecipeOutput consumer) {
        block(consumer);
        item(consumer);
    }

    protected void block (RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MINER, 1)
                .pattern("aba")
                .pattern("aca")
                .pattern("ded")
                .define('a', Blocks.END_STONE)
                .define('b', Blocks.END_ROD)
                .define('c', Items.DIAMOND_PICKAXE)
                .define('d', Blocks.OBSIDIAN)
                .define('e', Blocks.REDSTONE_BLOCK)
                .unlockedBy("has_end_rod", has(Blocks.END_ROD))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.QUARRY, 1)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Blocks.END_STONE)
                .define('b', Blocks.OBSIDIAN)
                .define('c', ModItems.MAGIC_SNOW_GLOBE)
                .unlockedBy("has_magic_snow_globe", has(ModItems.MAGIC_SNOW_GLOBE))
                .save(consumer);
    }

    protected void item (RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BIOME_MARKER, 1)
                .pattern("aba")
                .pattern("cdc")
                .pattern("aba")
                .define('a', Tags.Items.DYES)
                .define('b', Items.IRON_INGOT)
                .define('c', Items.GOLD_INGOT)
                .define('d', ItemTags.SAPLINGS)
                .unlockedBy("has_gold", has(Items.GOLD_INGOT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SNOW_GLOBE, 1)
                .pattern("abc")
                .pattern("def")
                .pattern("gh ")
                .define('a', Tags.Items.GLASS_BLOCKS)
                .define('b', ItemTags.SAPLINGS)
                .define('c', Items.SNOWBALL)
                .define('d', ItemTags.WOODEN_DOORS)
                .define('e', ItemTags.LOGS)
                .define('f', Blocks.GRASS_BLOCK)
                .define('g', Items.ENDER_PEARL)
                .define('h', Items.NETHER_STAR)
                .unlockedBy("has_nether_star", has(Items.NETHER_STAR))
                .save(consumer);
    }

}

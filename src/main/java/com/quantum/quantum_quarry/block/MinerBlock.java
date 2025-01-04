package com.quantum.quantum_quarry.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.entity.player.Player;

import com.quantum.quantum_quarry.block.entity.QuarryBlockEntity;
import com.quantum.quantum_quarry.procedures.FindCore;
import com.quantum.quantum_quarry.block.entity.MinerBlockEntity;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinerBlock extends Block implements EntityBlock {
    public static final Logger LOGGER = LoggerFactory.getLogger(MinerBlock.class);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    //public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public MinerBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.UP));
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new MinerBlockEntity(pos, state);
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (level.isClientSide) return;
        linkToQuarry(level, pos);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level world, BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
        var quarry = FindCore.execute(world, pos.getX(), pos.getY(), pos.getZ());
        if (quarry != null && world.getBlockState(quarry).getBlock() instanceof QuarryBlock quarryBlock) {
            quarryBlock.useWithoutItem(state, world, quarry, player, hit);
        }
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(POWERED);
        //builder.add(AXIS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block block, @NotNull BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
        var isPowered = level.hasNeighborSignal(pos);
        if (level.isClientSide) return;

        level.updateNeighborsAt(pos, this);
        var quarry = FindCore.execute(level, pos.getX(), pos.getY(), pos.getZ());
        if (quarry != null && level.getBlockState(quarry).getBlock() instanceof QuarryBlock quarryBlock) {
            quarryBlock.notifyQuarryBlock(level, quarry, isPowered);
        }

        linkToQuarry(level, pos);
    }

    /**
     * Links a {@link MinerBlock} to an adjacent {@link QuarryBlockEntity}
     * @param level The Server side level.
     * @param pos The placed block entity (if any).
     */
    private void linkToQuarry (@NotNull Level level, @NotNull BlockPos pos) {
        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MinerBlockEntity minerEntity) {
            for (Direction direction : Direction.values()) {
                var adjacentPos = pos.relative(direction);
                var adjacentEntity = level.getBlockEntity(adjacentPos);
                if (adjacentEntity instanceof QuarryBlockEntity) {
                    minerEntity.setLinkedQuarryPos(adjacentPos);
                    LOGGER.info("Set Linked Quarry to {}", minerEntity.getLinkedQuarryPos());
                    break;
                }
            }
        }
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        level.invalidateCapabilities(pos);
    }

    @Override
    public boolean isSignalSource(@NotNull BlockState state) {
        return true;
    }
}
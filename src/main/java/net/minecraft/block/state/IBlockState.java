package net.minecraft.block.state;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface IBlockState
{
    Collection<IProperty> getPropertyNames();

    <T extends Comparable<T>> T getValue(IProperty<T> property);

    <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value);

    <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> property);

    ImmutableMap<IProperty, Comparable> getProperties();

    Block getBlock();

    AxisAlignedBB getCollisionBoundingBox(IBlockAccess worldIn, BlockPos pos);

    void addCollisionBoxToList(World var1, BlockPos var2, AxisAlignedBB var3, List<AxisAlignedBB> var4, Entity var5, boolean var6);
}

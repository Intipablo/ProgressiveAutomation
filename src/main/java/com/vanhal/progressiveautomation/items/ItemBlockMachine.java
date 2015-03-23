package com.vanhal.progressiveautomation.items;

import java.util.List;

import com.vanhal.progressiveautomation.ProgressiveAutomation;
import com.vanhal.progressiveautomation.blocks.BlockChopper;
import com.vanhal.progressiveautomation.blocks.BlockFarmer;
import com.vanhal.progressiveautomation.blocks.BlockKiller;
import com.vanhal.progressiveautomation.blocks.BlockPlanter;
import com.vanhal.progressiveautomation.entities.BaseTileEntity;
import com.vanhal.progressiveautomation.entities.UpgradeableTileEntity;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemBlockMachine extends ItemBlock {

	public ItemBlockMachine(Block baseBlock) {
		super(baseBlock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par) {
		if (this.block instanceof BlockFarmer) {
			list.add(EnumChatFormatting.GRAY + "Used to breed animals");
			list.add(EnumChatFormatting.GRAY + "Use upgrades for shearing and milking");
			list.add(EnumChatFormatting.GRAY + "Place on the same level as the animals");
		} else if (this.block instanceof BlockKiller) {
			list.add(EnumChatFormatting.GRAY + "Used to kill things that are on top of it");
			list.add(EnumChatFormatting.GRAY + "Use filter upgrades to determine which types");
		} else if (this.block instanceof BlockPlanter) {
			list.add(EnumChatFormatting.GRAY + "Can plant and harvest all types of crops");
			list.add(EnumChatFormatting.GRAY + "Machine should be placed one block below the ground");
		} else if (this.block instanceof BlockChopper) {
			list.add(EnumChatFormatting.GRAY + "Can plant and harvest all types of tree");
			list.add(EnumChatFormatting.GRAY + "Machine should be placed on the ground");
		}
		
		if ( (itemStack != null) && (itemStack.getTagCompound() != null) ) {
			list.add(EnumChatFormatting.GRAY + "Pre-Configured");
		}
		
    }
	
	@Override
	public boolean placeBlockAt(ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {	
		boolean result = super.placeBlockAt(itemStack, player, world, pos, side, hitX, hitY, hitZ, newState);
		if (result) {
			if (!world.isRemote) {
				if (world.getTileEntity(pos) instanceof BaseTileEntity) {
					BaseTileEntity tileEntity = (BaseTileEntity) world.getTileEntity(pos);
					tileEntity.readFromItemStack(itemStack);
				}
			}
		}
		return result;
    }

}

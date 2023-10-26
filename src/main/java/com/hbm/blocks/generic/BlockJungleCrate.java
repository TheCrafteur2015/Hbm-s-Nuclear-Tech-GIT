package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockJungleCrate extends Block {

	public BlockJungleCrate(Material material) {
		super(material);
	}
	
	Random rand = new Random();
	
    @Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
    	
        ArrayList<ItemStack> ret = new ArrayList<>();

        ret.add(new ItemStack(Items.gold_ingot, 4 + this.rand.nextInt(4)));
        ret.add(new ItemStack(Items.gold_nugget, 8 + this.rand.nextInt(10)));
        ret.add(new ItemStack(ModItems.powder_gold, 2 + this.rand.nextInt(3)));
        ret.add(new ItemStack(ModItems.wire_gold, 2 + this.rand.nextInt(2)));

        if(this.rand.nextInt(2) == 0)
        	ret.add(new ItemStack(ModItems.plate_gold, 1 + this.rand.nextInt(2)));
        
        if(this.rand.nextInt(3) == 0)
        	ret.add(new ItemStack(ModItems.crystal_gold));
        
        return ret;
    }

}

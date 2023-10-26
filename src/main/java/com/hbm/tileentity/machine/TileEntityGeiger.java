package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.radiation.ChunkRadiationManager;

import cpw.mods.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.tileentity.TileEntity;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityGeiger extends TileEntity implements SimpleComponent {
	
	int timer = 0;
	int ticker = 0;
	
	@Override
	public void updateEntity() {
		
		this.timer++;
		
		if(this.timer == 10) {
			this.timer = 0;
			this.ticker = check();
		}
		
		if(this.timer % 5 == 0) {
			if(this.ticker > 0) {
				List<Integer> list = new ArrayList<>();

				if(this.ticker < 1)
					list.add(0);
				if(this.ticker < 5)
					list.add(0);
				if(this.ticker < 10)
					list.add(1);
				if(this.ticker > 5 && this.ticker < 15)
					list.add(2);
				if(this.ticker > 10 && this.ticker < 20)
					list.add(3);
				if(this.ticker > 15 && this.ticker < 25)
					list.add(4);
				if(this.ticker > 20 && this.ticker < 30)
					list.add(5);
				if(this.ticker > 25)
					list.add(6);
			
				int r = list.get(this.worldObj.rand.nextInt(list.size()));
				
				if(r > 0)
		        	this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:item.geiger" + r, 1.0F, 1.0F);
			} else if(this.worldObj.rand.nextInt(50) == 0) {
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:item.geiger"+ (1 + this.worldObj.rand.nextInt(1)), 1.0F, 1.0F);
			}
		}
		
	}

	public int check() {
		int rads = (int)Math.ceil(ChunkRadiationManager.proxy.getRadiation(this.worldObj, this.xCoord, this.yCoord, this.zCoord));
		return rads;
	}
	@Override
	public String getComponentName() {
		return "ntm_geiger";
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getRads(Context context, Arguments args) {
		return new Object[] {check()};
	}

}

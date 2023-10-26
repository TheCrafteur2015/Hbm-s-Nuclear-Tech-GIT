package com.hbm.tileentity.network;

import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;

import net.minecraft.util.MathHelper;

public class TileEntityRadioTorchReceiver extends TileEntityRadioTorchBase {

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(!this.channel.isEmpty()) {
				
				RTTYChannel chan = RTTYSystem.listen(this.worldObj, this.channel);
				
				if(chan != null && (this.polling || (chan.timeStamp > this.lastUpdate - 1 && chan.timeStamp != -1))) { // if we're either polling or a new message has come in
					String msg = "" + chan.signal;
					this.lastUpdate = this.worldObj.getTotalWorldTime();
					int nextState = 0; //if no remap apply, default to 0
					
					if(this.customMap) {
						for(int i = 15; i >= 0; i--) { // highest to lowest, if duplicates exist for some reason
							if(msg.equals(this.mapping[i])) {
								nextState = i;
								break;
							}
						}
					} else {
						int sig = 0;
						try { sig = Integer.parseInt(msg); } catch(Exception x) { };
						nextState = MathHelper.clamp_int(sig, 0, 15);
					}
					
					if(chan.timeStamp < this.lastUpdate - 2 && this.polling) {
						nextState = 0;
					}
					
					if(this.lastState != nextState) {
						this.lastState = nextState;
						this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
						this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, getBlockType());
						markDirty();
					}
				}
			}
		}
		
		super.updateEntity();
	}
}

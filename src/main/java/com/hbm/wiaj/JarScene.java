package com.hbm.wiaj;

import java.util.ArrayList;
import java.util.List;

import com.hbm.wiaj.actions.IJarAction;

/**
 * A scene is a simple sequence of tasks, every script can have multiple scenes
 * Scenes depend on each other, in order to rewind we'll have to re-init the playing field and FFW through all previous scenes
 * @author hbm
 *
 */
public class JarScene {

	public List<IJarAction> actions = new ArrayList<>();
	public JarScript script;
	
	public int actionNumber = 0;
	public IJarAction currentAction; //the action that is currently happening
	public int currentActionStart = 0; //time in ticks since init
	
	public JarScene (JarScript script) {
		this.script = script;
	}
	
	public JarScene add(IJarAction action) {
		
		if(this.currentAction == null)
			this.currentAction = action; //set first action
		
		this.actions.add(action);
		return this;
	}
	
	/** does the once per 50ms tick routine */
	public void tick() {
		
		if(this.currentAction == null) return;
		
		this.currentAction.act(this.script.world, this);
		
		int duration = this.currentAction.getDuration();
		
		if(this.currentActionStart + duration <= this.script.ticksElapsed) { //choose next action
			
			this.actionNumber++;
			this.currentActionStart = this.script.ticksElapsed;
			
			if(this.actionNumber < this.actions.size()) {
				this.currentAction = this.actions.get(this.actionNumber);
				tick();
				
			} else {
				this.currentAction = null;
			}
		}
	}
	
	public void reset() {
		this.currentAction = this.actions.get(0);
		this.actionNumber = 0;
		this.currentActionStart = this.script.ticksElapsed;
	}
}

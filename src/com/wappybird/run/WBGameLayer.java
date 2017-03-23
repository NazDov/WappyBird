package com.wappybird.run;

import java.awt.Graphics;
import java.util.List;

import com.wappybird.WBGLAttackResolver;
import com.wappybird.characters.WBAttacker;
import com.wappybird.characters.WBMainCharacter;


public abstract class WBGameLayer {
	
	protected int wbEnemyCount = 10;
	protected int wbGameLevelID;
	protected WBGameView wbGameView;
	protected WBGLAttackResolver wbCollisionDetector;

	protected abstract void updateWBGLBackgroundPositions();
	protected abstract void updateWBGLMainCharacterPositions();
	protected abstract void updateWB1GLWbGireBullets(); 
	protected abstract void updateWBGLBackgroundGraphics(Graphics gameGraphics);
	public abstract void updateGameLevelGraphics(Graphics g);
	protected abstract void updateWBGLEnemiesPositions();
	protected abstract void createWBGLAttackers();
	public abstract int getWbGlTotalAttackerHealth();
	public abstract void setWBGLTotalAttackerHealth(int wbGlTotalAttackerHealth);
	public abstract WBMainCharacter getWBMainCharacter();
	public abstract List<WBAttacker> getWbAttackers();
	public abstract WBGLAttackResolver getWBCollisionDetector();
	protected void updateWBGLCollisions(){getWBCollisionDetector().wbCheckResolveAttack();}
	
	public void updateWBGLControls(){
		updateWBGLBackgroundPositions();
		updateWBGLMainCharacterPositions();
		updateWB1GLWbGireBullets();
		updateWBGLEnemiesPositions();
		updateWBGLCollisions();
	}


	protected void onKillWBMainCharacter_EndWBGamePlay(){
		if(wbGameView != null){
			WBGameLevelFactory.getFactory(true);
			wbGameView.setStopped(true);
			wbGameView.getStartOverButton().setVisible(true);
			wbGameView.revalidate();
		}else{
			//TODO
		}
		
	}
	
	protected void setOnUpdateGamePlayListener(WBGameView gameView){
		this.wbGameView = gameView;
	}

	protected int getWbEnemyCount() {
		return wbEnemyCount;
	}

	public int getWbGameLevelID() {
		return wbGameLevelID;
	}
	public WBGameView getWbGameView() {
		return wbGameView;
	}
	
	

}
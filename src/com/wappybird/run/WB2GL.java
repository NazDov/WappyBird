package com.wappybird.run;

import java.awt.Graphics;
import java.util.ArrayList;
import com.wappybird.WB2GLAttackResolver;
import com.wappybird.WBBackground;
import com.wappybird.WBGLAttackResolver;
import com.wappybird.characters.WBAttacker;
import com.wappybird.characters.WBWormAttacker;

public class WB2GL extends WB1GL {

	protected WB2GLAttackResolver wb2glAttackResolver = new WB2GLAttackResolver(this);
	
	public WB2GL() {
		
	}
	
	protected void createWBGLBackground() {
		setBgOne(new WBBackground(0, 0 ,1280));
		setBgTwo(new WBBackground(getWbBackdroundPosX(), 0, 1280));
		getWBMainCharacter().setBackGroundOne(getBgOne());
		getWBMainCharacter().setBackGroundTwo(getBgTwo());
		for (WBAttacker e : getWbAttackers()) {
			e.setWBBackground(getBgOne());
		}
	}
	
	protected void createWBGLAttackers() {
		int maxHealth = 0;
		wbAttackers = new ArrayList<WBAttacker>();
		for(int attackerCount=1 ; attackerCount <= getWbEnemyCount(); attackerCount++){
			WBAttacker enemy = new WBWormAttacker(getRandomPosition(attackerCount));
			maxHealth += enemy.getWBAttackerHealth()*1.5;
			wbAttackers.add(enemy);
		}
		setWBGLTotalAttackerHealth(maxHealth);
	}

	
	@Override
	protected void updateWBGLBackgroundGraphics(Graphics g) {
		g.drawImage(getWbGameView().getLevel2BackgroundImage(), getBgOne().getBgX(), getBgOne().getBgY(), getWbGameView());
		g.drawImage(getWbGameView().getLevel2BackgroundImage(), getBgTwo().getBgX(), getBgTwo().getBgY(), getWbGameView());
	}
	
	@Override
	public int getWbGameLevelID() {
		return 2;
	}
	
	@Override
	protected int getWbEnemyCount() {
		return 10;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	public WBGLAttackResolver getWBCollisionDetector() {
		return wb2glAttackResolver;
	}
	
	@Override
	public int getWbBackdroundPosX() {
		return 1280;
	}
	
}

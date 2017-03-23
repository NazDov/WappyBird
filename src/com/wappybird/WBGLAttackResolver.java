package com.wappybird;

import java.util.List;

import com.wappybird.characters.WBAttacker;
import com.wappybird.characters.WBMainCharacter;
import com.wappybird.run.WBGameLayer;

public abstract class WBGLAttackResolver {

	protected WBGameLayer wbGameLevel;

	public WBGLAttackResolver(WBGameLayer wbGameLevel) {
		this.wbGameLevel = wbGameLevel;
	}

	public abstract int getWBGLMainCharacterHealthDamagePoint();

	public abstract int getWBGLAttackerHealthDamagePoint();

	public void wbCheckResolveAttack(WBMainCharacter hero, List<WBAttacker> wbAttackers) {
		int mainCharacterPosHor = hero.getCenterX();
		int mainCharacterPosVert = hero.getCenterY();

		for (int i = 0; i < wbAttackers.size(); i++) {
			WBAttacker wbAttcker = wbAttackers.get(i);
			int wbAttackerPosHorUpperMargin = wbAttcker.getWbAttackerPositionHorizontal() + 20;
			int wbAttackerPosHorLowerMargin = wbAttcker.getWbAttackerPositionHorizontal() - 20;
			int wbAttackerPosVertUpperMargin = wbAttcker.getWbAttackerPositionVertical() + 20;
			int wbAttackerPosVerLowerMargin = wbAttcker.getWbAttackerPositionVertical() - 20;
			if (mainCharacterPosHor >= wbAttackerPosHorLowerMargin && mainCharacterPosHor <= wbAttackerPosHorUpperMargin
					&& mainCharacterPosVert >= wbAttackerPosVerLowerMargin && mainCharacterPosVert <= wbAttackerPosVertUpperMargin
					&& !hero.isDucked() && !hero.isAttacking() && !wbAttcker.isWBAttackerKilled()) {
				hero.setAttacked(true);
				int health = hero.getHealth();
				hero.setHealth(health - getWBGLMainCharacterHealthDamagePoint());
			}
			if (mainCharacterPosHor >= wbAttackerPosHorLowerMargin && mainCharacterPosHor <= wbAttackerPosHorUpperMargin
					&& mainCharacterPosVert >= wbAttackerPosVerLowerMargin && mainCharacterPosVert <= wbAttackerPosVertUpperMargin
					&& hero.isAttacking()) {
				wbAttcker.setWBAttackerHealth(wbAttcker.getWBAttackerHealth() - getWBGLAttackerHealthDamagePoint());
				hero.setWbKillWbAttackerPoints(
						wbAttcker.getWBAttackerMaxAvailableHealth() * getWBGLAttackerHealthDamagePoint());
				int totalHealthUron = wbGameLevel.getWbGlTotalAttackerHealth() - getWBGLAttackerHealthDamagePoint();
				wbGameLevel.setWBGLTotalAttackerHealth(totalHealthUron);
			}
		}
	}

	public void wbCheckResolveAttack() {
		wbCheckResolveAttack(wbGameLevel.getWBMainCharacter(), wbGameLevel.getWbAttackers());

	}

}
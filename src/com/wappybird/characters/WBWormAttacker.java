package com.wappybird.characters;

public class WBWormAttacker extends WBAttacker {

	private int maxHealth = 5;

	public WBWormAttacker(int centerX, int centerY) {
		setWbAttackerPositionHorizontal(centerX);
		setWbAttackerPositionVertical(centerY);

	}

	public WBWormAttacker(int posX) {
		setWbAttackerPositionHorizontal(getWbBgPosX() + posX);
		setWbAttackerPositionVertical(382);
	}

	@Override
	public int getWBAttackerHealth() {
		return maxHealth;
	}

	public void setWBAttackerHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	@Override
	protected int getWbBgPosX() {
		return 850;
	}

}

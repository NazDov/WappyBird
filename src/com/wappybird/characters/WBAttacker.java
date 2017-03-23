package com.wappybird.characters;

import com.wappybird.WBBackground;

public abstract class WBAttacker extends WBCharacter {

	/**
	 * represents the value of speed/pace for WBAttacker
	 */
	private final int wbAttackerHorizontalPace = -3;
	private int wbAttackerMaxAvailableHealth = 5;
	private int wbAttackerHealth;
	private int wbAttackerCurrentHealth;
	private int wbAttackerPower;
	protected int wbBgPosX;
	private WBBackground wbBackground;

	@Override
	public void wbCharacterUpdatePosition() {
		if (wbBackground.getSpeedX() == 0 && wbBackground.getBgX() <= 800) {
			wbAttackerPositionHorizontal += wbAttackerHorizontalPace;
		} else {
			wbAttackerPositionHorizontal += wbAttackerSpeedHorizontal;
			wbAttackerSpeedHorizontal = wbBackground.getSpeedX();
		}
	}

	protected void attack() {
		// TODO
	}

	public void die() {
		this.wbAttackerHealth = 0;
	}

	public int getWBAttackerHealth() {
		return wbAttackerHealth;
	}

	public void setWBAttackerHealth(int wbAttackerMaxHealth) {
		if (wbAttackerMaxHealth < 0) {
			this.wbAttackerHealth = 0;
		}
		this.wbAttackerHealth = wbAttackerMaxHealth;
	}

	public int getWBAttackerCurrentHealth() {
		return wbAttackerCurrentHealth;
	}

	public void setWBAttckerCurrentHealth(int currentHealth) {
		this.wbAttackerCurrentHealth = currentHealth;
	}

	public int getWBAttackerPower() {
		return wbAttackerPower;
	}

	public void setWBAttackerPower(int power) {
		this.wbAttackerPower = power;
	}

	public WBBackground getWBBackground() {
		return wbBackground;
	}

	public void setWBBackground(WBBackground bgOne) {
		this.wbBackground = bgOne;
	}

	public boolean isWBAttackerKilled() {
		return getWBAttackerHealth() == 0;
	}

	protected int getWbBgPosX() {
		return wbBgPosX;
	}

	public boolean isWBAttackerWounded() {
		return getWBAttackerHealth() < wbAttackerMaxAvailableHealth;
	}

	public int getWBAttackerMaxAvailableHealth() {
		return wbAttackerMaxAvailableHealth;
	}

	public void setWBAttackerMaxAvailableHealth(int maxAvailableHealth) {
		this.wbAttackerMaxAvailableHealth = maxAvailableHealth;
	}

}

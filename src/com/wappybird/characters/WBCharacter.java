package com.wappybird.characters;

public abstract class WBCharacter implements WBShooter {
	
	protected int wbAttackerPositionHorizontal;
	protected int wbAttackerPositionVertical;
	protected int wbAttackerSpeedHorizontal;
	protected int wbAttackerSpeedVertical;
	
	public abstract void wbCharacterUpdatePosition();
	
	@Override
	public void shoot() {
		throw new UnsupportedOperationException();
	}

	public int getWbAttackerPositionHorizontal() {
		return wbAttackerPositionHorizontal;
	}

	public void setWbAttackerPositionHorizontal(int wbAttackerPositionHorizontal) {
		this.wbAttackerPositionHorizontal = wbAttackerPositionHorizontal;
	}

	public int getWbAttackerPositionVertical() {
		return wbAttackerPositionVertical;
	}

	public void setWbAttackerPositionVertical(int wbAttackerPositionVertical) {
		this.wbAttackerPositionVertical = wbAttackerPositionVertical;
	}

	public int getWbAttackerSpeedHorizontal() {
		return wbAttackerSpeedHorizontal;
	}

	public void setWbAttackerSpeedHorizontal(int wbAttackerSpeedHorizontal) {
		this.wbAttackerSpeedHorizontal = wbAttackerSpeedHorizontal;
	}

	public int getWbAttackerSpeedVertical() {
		return wbAttackerSpeedVertical;
	}

	public void setWbAttackerSpeedVertical(int wbAttackerSpeedVertical) {
		this.wbAttackerSpeedVertical = wbAttackerSpeedVertical;
	}
	
	

}

package com.wappybird;

public class WBBackground {

	private int wbBackgroundPosHor;
	private int wbBackgroundPosVer;
	private int wbBackgroundRepeatPosHor;

	private int speedX;

	public WBBackground(int wbBackgroundPosHor, int wbBackgroundPosVer, int wbBackgroundRepeatPosHor) {
		this.wbBackgroundPosHor = wbBackgroundPosHor;
		this.wbBackgroundPosVer = wbBackgroundPosVer;
		this.wbBackgroundRepeatPosHor = wbBackgroundRepeatPosHor;
		speedX = 0;
	}

	public void updateBG() {

		wbBackgroundPosHor += speedX;

		if (wbBackgroundPosHor <= -wbBackgroundRepeatPosHor) {
			wbBackgroundPosHor = wbBackgroundRepeatPosHor;
		}
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getBgX() {
		return wbBackgroundPosHor;
	}

	public void setBgX(int bgX) {
		this.wbBackgroundPosHor = bgX;
	}

	public int getBgY() {
		return wbBackgroundPosVer;
	}

	public void setBgY(int bgY) {
		this.wbBackgroundPosVer = bgY;
	}

}

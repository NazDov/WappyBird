package com.wappybird;

public class WBFireBullet {
	
	private int startX;
	private int startY;
	private int speedX;
	private boolean visible = true;
	
	
	public WBFireBullet(int startX, int startY) {
		this.startX = startX;
		this.startY = startY;
		speedX = 7;
	}
	
	
	public void update(){
		startX += speedX;
		
		if(startX > 1000){
			visible = false;
		}
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + speedX;
		result = prime * result + startX;
		result = prime * result + startY;
		result = prime * result + (visible ? 1231 : 1237);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WBFireBullet other = (WBFireBullet) obj;
		if (speedX != other.speedX)
			return false;
		if (startX != other.startX)
			return false;
		if (startY != other.startY)
			return false;
		if (visible != other.visible)
			return false;
		return true;
	}


	public int getStartX() {
		return startX;
	}
	public void setStartX(int startX) {
		this.startX = startX;
	}
	public int getStartY() {
		return startY;
	}
	public void setStartY(int startY) {
		this.startY = startY;
	}
	public int getSpeedX() {
		return speedX;
	}
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	
	
	
	

}

package com.wappybird.characters;

import java.util.ArrayList;
import java.util.List;

import com.wappybird.WBBackground;
import com.wappybird.WBFireBullet;

public class WBMainCharacter extends WBCharacter {
	
	private static final int GROUND = 382;
	private static final int MOVELEFT = -10;
	private static final int JUMPEDSPEED =-15;
	private static final int MOVESPEED = 5;
	private int wbKillWbAttackerPoints=0;
	private volatile int health = 50;
	private boolean movingLeft=false;
	private boolean movingRight=false;
	private boolean ducked=false;
	private boolean inAngryMode=false;
	private boolean isAttacked;
	private int speedX=0;
	private int speedY=1;
	private boolean jumped;
	private boolean isAttacking;
	
	private List<WBFireBullet> fireBullets = new ArrayList<>();
	
	private WBBackground bgOne;
	private WBBackground bgTwo;
	
	
	public WBMainCharacter(){
		this.wbAttackerPositionHorizontal=100;
		this.wbAttackerPositionVertical=GROUND;
		
	}
	
	
	
	@Override
	public void wbCharacterUpdatePosition(){
		// update X position
		if(speedX<0){
			wbAttackerPositionHorizontal+=speedX;
		}
		
		if(speedX==0 || speedX<0){
			bgOne.setSpeedX(0);
			bgTwo.setSpeedX(0);
		}
		
		// moving our robot to the right
		if(speedX>0 && wbAttackerPositionHorizontal<=200){
			wbAttackerPositionHorizontal+=speedX;
		}

		if(speedX>0 && wbAttackerPositionHorizontal>200){
			bgOne.setSpeedX(MOVELEFT);
			bgTwo.setSpeedX(MOVELEFT);
		}
		
		if(wbAttackerPositionVertical+speedY>=GROUND){
			wbAttackerPositionVertical=GROUND;
		}else{
			wbAttackerPositionVertical+=speedY;
		}
		
		
		if(jumped==true && ducked==false){
			speedY+=1;
			upgradeYGposition();
			
		}
		
		if(isAttacking==true){
			speedY+=1;
			
			if(wbAttackerPositionVertical+speedY<=280){
				speedY=0;
				wbAttackerPositionVertical=GROUND;
			}
		
		}
		
		
		if(wbAttackerPositionHorizontal+speedX<=60){
			wbAttackerPositionHorizontal=61;
			
		}
		
	}
	
	public void shoot(){
		WBFireBullet laser = new WBFireBullet(wbAttackerPositionHorizontal+25, wbAttackerPositionVertical-25);
		fireBullets.add(laser);
	}


	private void upgradeYGposition() {
		if(speedY+wbAttackerPositionVertical>=382){
			wbAttackerPositionVertical=GROUND;
			speedY=0;
			jumped=false;
		}
	}
	
	
	public boolean isMovingLeft() {
		return movingLeft;
	}


	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}


	public boolean isMovingRight() {
		return movingRight;
	}


	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}


	public boolean isDucked() {
		return ducked;
	}


	public void setDucked(boolean ducked) {
		this.ducked = ducked;
	}


	public int getSpeedX() {
		return speedX;
	}


	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}


	public int getSpeedY() {
		return speedY;
	}


	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}


	public boolean isJumped() {
		return jumped;
	}


	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}


	public  WBBackground getBgOne() {
		return bgOne;
	}


	public  WBBackground getBgTwo() {
		return bgTwo;
	}

	public static int getGround() {
		return GROUND;
	}


	public static int getMoveleft() {
		return MOVELEFT;
	}


	public static int getJumpedspeed() {
		return JUMPEDSPEED;
	}


	public static int getMovespeed() {
		return MOVESPEED;
	}


	public void stop(){
		if(this.isMovingRight()==false && this.isMovingLeft()==false){
			speedX=0;
		}
		
		if(this.isMovingRight()==false && this.isMovingLeft()==true){
			moveLeft();
		}
		
		if(this.isMovingRight()==true && this.isMovingLeft()==false){
			moveRight();
		}
		
	}
	
	
	public void stopRight(){
		this.setMovingRight(false);
		stop();
	}
	
	public void stopLeft(){
		this.setMovingLeft(false);
		stop();
	}
	
	public void moveLeft(){
		if(ducked==false)
			speedX=-MOVESPEED;
	}
	
	public void moveRight(){
		if(ducked==false)
			speedX+=MOVESPEED;
	}
	
	public void jump(){
		if(jumped==false && isDucked()==false){
			speedY=JUMPEDSPEED;
			jumped=true;
		}
		
		if(jumped==false && inAngryMode==true){
			speedY=JUMPEDSPEED;
			jumped=true;
		}
	}


	public int getCenterX() {
		return wbAttackerPositionHorizontal;
	}


	public void setCenterX(int centerX) {
		this.wbAttackerPositionHorizontal = centerX;
	}


	public int getCenterY() {
		return wbAttackerPositionVertical;
	}


	public void setCenterY(int centerY) {
		this.wbAttackerPositionVertical = centerY;
	}

	

	public List<WBFireBullet> getFireBullets() {
		return fireBullets;
	}



	public void setLasers(List<WBFireBullet> lasers) {
		this.fireBullets = lasers;
	}



	public boolean isInAngryMode() {
		return this.inAngryMode;
	}


	public void setInAngryMode(boolean mode) {
		inAngryMode=mode;
	}


	public void attack() {
		if(isAttacking==false && !isDucked()){
			speedY=JUMPEDSPEED-2;
			isAttacking=true;
		}
		
	}

	public void setBackGroundOne(WBBackground bgOne) {
		this.bgOne = bgOne;
	}

	public void setBackGroundTwo(WBBackground bgTwo) {
		this.bgTwo = bgTwo;
	}

	public int getHealth() {
		return health;
	}



	public void setHealth(int newHealth) {
		if(newHealth <= 0){
			this.health = newHealth;
		}
		this.health = newHealth;
	}


	public boolean isAttacked(){
		return isAttacked;
	}

	public void setAttacked(boolean isAttacked) {
		this.isAttacked = isAttacked;
		
	}



	public boolean isAttacking() {
		return isAttacking;
	}



	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}



	public int getWbKillWbAttackerPoints() {
		return wbKillWbAttackerPoints;
	}



	public void setWbKillWbAttackerPoints(int wbKillWbAttackerPoints) {
		this.wbKillWbAttackerPoints += wbKillWbAttackerPoints;
	}
	
	
	
	

}

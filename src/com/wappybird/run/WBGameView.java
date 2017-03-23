package com.wappybird.run;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.wappybird.characters.WBMainCharacter;

public class WBGameView extends Applet implements Runnable, KeyListener {

	private WBGameLevelFactory wbGamePlayFactory;
	private WBGameLayer wbCurrentGameLevel;
	private Image image;
	private Image characterReg,characterDown,characterInAngryMode,
	characterToLeft, gmBackground, currentSprite, wormImage, 
	wormLevelThreeImage, gameOverImage, enemyRipImage, level2BackgroundImage;
	private Graphics gameViewGraphics;
	private URL base;
	private volatile boolean stopped;
	private Button startOverButton;
	private Thread thread;


	private static final long serialVersionUID = 8074124155400476385L;

	@Override
	public void init() {

		Dimension dim = new Dimension(850, 580);
		setSize(dim);
		setBackground(Color.CYAN);	
		setFocusable(true);
		setMaximumSize(dim);
		addKeyListener(this);
		startOverButton  = new Button("Start Over");
		add(startOverButton, BorderLayout.CENTER);
		startOverButton.setVisible(false);
		startOverButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
				destroy();
				init();
				start();
			}
			
		});
		
		try{
			base = this.getClass().getResource("/data");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		wbGamePlayFactory = WBGameLevelFactory.getFactory(true);
		wbCurrentGameLevel = wbGamePlayFactory.getWBCurrentGameLevel();
		wbCurrentGameLevel.setOnUpdateGamePlayListener(this);
		
		try {
			URL mainCharcterURL = this.getClass().getResource("/data/happybird_current.png");
			characterReg = ImageIO.read(mainCharcterURL);
			URL mainCharacterDuckedURL = this.getClass().getResource("/data/happybird_ducked.png");
			characterDown = ImageIO.read(mainCharacterDuckedURL);
			URL mainCharacterAngryModeURL = this.getClass().getResource("/data/happybird_angry_mode.png");
			characterInAngryMode = ImageIO.read(mainCharacterAngryModeURL);
			URL mainCharacterToLeftImageURL = this.getClass().getResource("/data/happybird_current_left.png");
			characterToLeft = ImageIO.read(mainCharacterToLeftImageURL);
			URL background = this.getClass().getResource("/data/background.png");
			gmBackground = ImageIO.read(background);
			URL wormAttackerImageURL = this.getClass().getResource("/data/worm.png");
			wormImage = ImageIO.read(wormAttackerImageURL);
			URL wormAttackerLevel3ImageURL = this.getClass().getResource("/data/wormLevelThree.png");
			wormLevelThreeImage = ImageIO.read(wormAttackerLevel3ImageURL);
			URL gameOverImageURL = this.getClass().getResource("/data/gameover.png");
			gameOverImage = ImageIO.read(gameOverImageURL);
			URL enemyRIPImageURL = this.getClass().getResource("/data/enemy_rip.png");
			enemyRipImage = ImageIO.read(enemyRIPImageURL);
			URL level2BackgroundImageURL = this.getClass().getResource("/data/bacground_level2.png");
			level2BackgroundImage = ImageIO.read(level2BackgroundImageURL);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		currentSprite =characterReg;
	}

	@Override
	public void start() {
		thread = new Thread(this);
		stopped = false;
		thread.start();
	}

	@Override
	public void stop() {
		
		if(!stopped){
			 stopped = true;
			 try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		super.stop();

	}

	@Override
	public void destroy() {
		removeKeyListener(this);
		removeAll();
		super.destroy();
	}

	@Override
	public void run() {
		
		while (!stopped) {
			wbCurrentGameLevel.updateWBGLControls();
			repaint();
			try {
				Thread.sleep(18);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void update(Graphics g) {
		if(image==null){
			image = createImage(getWidth(), getHeight());
			gameViewGraphics = image.getGraphics();
			
		}
		gameViewGraphics.setColor(Color.CYAN);
		gameViewGraphics.fillRect(0, 0, getWidth(), getHeight());
		gameViewGraphics.setColor(getForeground());
		gameViewGraphics.setFont(Font.getFont(Font.SANS_SERIF));
		paint(gameViewGraphics);
		g.drawImage(image, 0, 0, this);
		
	}
	
	@Override
	public void paint(Graphics g) {
		wbCurrentGameLevel.updateGameLevelGraphics(g);	
	}


	@Override
	public void keyPressed(KeyEvent clickEvent) {
		WBMainCharacter wappyBird = wbCurrentGameLevel.getWBMainCharacter();
		switch (clickEvent.getKeyCode()) {

		case KeyEvent.VK_DOWN:
			currentSprite= characterDown;
				wappyBird.setDucked(true);
				wappyBird.setSpeedX(0);
				wappyBird.setSpeedY(0);
				wappyBird.setCenterY(WBMainCharacter.getGround());
				//robot.setInAngryMode(false);
			
			break;

		case KeyEvent.VK_LEFT:
			wappyBird.moveLeft();
			wappyBird.setMovingLeft(true);
			
			if(wappyBird.isMovingLeft() && !wappyBird.isInAngryMode()){
				currentSprite = characterToLeft;
			}
			
			break;

		case KeyEvent.VK_RIGHT:
			wappyBird.moveRight();
			wappyBird.setMovingRight(true);
			if(wappyBird.isMovingRight() && !wappyBird.isInAngryMode()){
				currentSprite = characterReg;
			}
			break;

		case KeyEvent.VK_UP:
			wappyBird.attack();
			break;

		case KeyEvent.VK_SPACE:
			System.out.println("Space()");
			
			if(wappyBird.isInAngryMode()){
				currentSprite=characterReg;
				wappyBird.setInAngryMode(false);
			}
			break;
			
		case KeyEvent.VK_ENTER:
			System.out.println("angry mode");
			
			if(!wappyBird.isInAngryMode()){
				currentSprite=characterInAngryMode;
				wappyBird.setInAngryMode(true);
			}
			
			break;

		case KeyEvent.VK_O:
			if(wappyBird.isInAngryMode() && !wappyBird.isDucked()){
				System.out.println("shotting lasers...");
				wappyBird.shoot();
			}
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent clickEvent) {
		WBMainCharacter wappyBird = wbCurrentGameLevel.getWBMainCharacter();
		switch (clickEvent.getKeyCode()) {

		case KeyEvent.VK_DOWN:
			System.out.println("Released Down()");
			
			if(wappyBird.isInAngryMode()){
				currentSprite=characterInAngryMode;
			}else{
				currentSprite=characterReg;
			}
			
			wappyBird.setDucked(false);
			break;

		case KeyEvent.VK_LEFT:
			System.out.println("Released Left()");
			wappyBird.stopLeft();
			break;

		case KeyEvent.VK_RIGHT:
			System.out.println("Released Right()");
			wappyBird.stopRight();
			break;

		case KeyEvent.VK_UP:
			break;

		case KeyEvent.VK_SPACE:
			System.out.println("Released Space()");
			break;

		}

	}
	

	@Override
	public void keyTyped(KeyEvent clickEvent) {
		// TODO Auto-generated method stub

	}

	public Graphics getGameViewGraphics() {
		return gameViewGraphics;
	}

	public void setGameViewGraphics(Graphics gameViewGraphics) {
		this.gameViewGraphics = gameViewGraphics;
	}

	public WBGameLayer getWbCurrentGameLevel() {
		return wbCurrentGameLevel;
	}
	

	public void setWbCurrentGameLevel(WBGameLayer wbCurrentGameLevel) {
		this.wbCurrentGameLevel = wbCurrentGameLevel;
	}

	public Image getImage() {
		return image;
	}

	public Image getCharacterReg() {
		return characterReg;
	}

	public Image getCharacterDown() {
		return characterDown;
	}

	public Image getCharacterInAngryMode() {
		return characterInAngryMode;
	}

	public Image getCharacterToLeft() {
		return characterToLeft;
	}

	public Image getCurrentSprite() {
		return currentSprite;
	}

	public Image getWormImage() {
		return wormImage;
	}
	

	public Image getEnemyRipImage() {
		return enemyRipImage;
	}

	public void setEnemyRipImage(Image enemyRipImage) {
		this.enemyRipImage = enemyRipImage;
	}

	public void setWormImage(Image wormImage) {
		this.wormImage = wormImage;
	}

	public Image getWormLevelThreeImage() {
		return wormLevelThreeImage;
	}

	public Image getGameOverImage() {
		return gameOverImage;
	}

	public URL getBase() {
		return base;
	}

	public boolean isStopped() {
		return stopped;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Image getGmBackground() {
		return gmBackground;
	}

	public void setGmBackground(Image background) {
		this.gmBackground = background;
	}

	public void updateCurrentSprite(Image character) {
		this.currentSprite = character;
		
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	public Button getStartOverButton() {
		return startOverButton;
	}

	public void setStartOverButton(Button startOverButton) {
		this.startOverButton = startOverButton;
	}

	public Image getLevel2BackgroundImage() {
		return level2BackgroundImage;
	}
	
	
	
	
	
	
}

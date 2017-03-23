package com.wappybird.run;

import java.util.ArrayDeque;
import java.util.Queue;

public class WBGameLevelFactory {
	
	private static WBGameLevelFactory wbGamePlayFactory;
	private Queue<WBGameLayer> wbGamePlayQueue;
	private WBGameLayer wbCurrentGameLevel;
	private volatile boolean wbGameLevelAllIsFinished;
	
	
	private WBGameLevelFactory(){
		wbGamePlayQueue = new ArrayDeque<>();
		wbGamePlayQueue.add(new WB1GL());
		wbGamePlayQueue.add(new WB2GL());
		wbCurrentGameLevel = wbGamePlayQueue.poll();
		System.out.println(wbCurrentGameLevel);
	}
	
	public static WBGameLevelFactory getFactory(){
		return getFactory(false);
	}
	
	public static WBGameLevelFactory getFactory(boolean createNewFactory){
		if(wbGamePlayFactory == null || createNewFactory){
			wbGamePlayFactory = new WBGameLevelFactory();
		}
		return wbGamePlayFactory;
	}
	
	public WBGameLayer getWBCurrentGameLevel(){
		return wbCurrentGameLevel;
	}
	
	public synchronized void proceedToNextWBGameLevel(){
		if(wbGamePlayQueue.peek() != null){
			wbCurrentGameLevel = wbGamePlayQueue.poll();
		}else{
			wbGameLevelAllIsFinished = true;
		}
	}


	public boolean isAllWbGameLevelFinished() {
		return wbGameLevelAllIsFinished;
	}
	
	
	

}

package com.wappybird;

import com.wappybird.run.WBGameLayer;

public class WB1GLAttackResolver extends WBGLAttackResolver {

	public WB1GLAttackResolver(WBGameLayer wbGameLevel) {
		super(wbGameLevel);
	}
	

	@Override
	public int getWBGLMainCharacterHealthDamagePoint() {
		return 2;
	}

	@Override
	public int getWBGLAttackerHealthDamagePoint() {
		return 5;
	}
	

}

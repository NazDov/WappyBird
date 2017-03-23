package com.wappybird;

import com.wappybird.run.WBGameLayer;

public class WB2GLAttackResolver extends WBGLAttackResolver {

	public WB2GLAttackResolver(WBGameLayer wbGameLevel) {
		super(wbGameLevel);
	}
	
	@Override
	public int getWBGLMainCharacterHealthDamagePoint() {
		return 3;
	}

	@Override
	public int getWBGLAttackerHealthDamagePoint() {
		return 5;
	}

}

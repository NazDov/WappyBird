package com.wappybird.run;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.Timer;

import com.wappybird.WB1GLAttackResolver;
import com.wappybird.WBBackground;
import com.wappybird.WBFireBullet;
import com.wappybird.WBGLAttackResolver;
import com.wappybird.characters.WBAttacker;
import com.wappybird.characters.WBMainCharacter;
import com.wappybird.characters.WBWormAttacker;

public class WB1GL extends WBGameLayer {

    private WB1GLAttackResolver wb1glCollisionDetector = new WB1GLAttackResolver(this);
    private AtomicInteger restoreWbAttackerPosX = new AtomicInteger(800);
    protected int wbEnemyCount = 3;
    protected int wbGlTotalAttackerHealth = 0;
    protected int wbBackdroundPosX = 1000;
    protected Random rand = new Random(100);
    private WBBackground wbBgOne;
    private WBBackground wbBgTwo;
    private WBMainCharacter wbMainCharacter;
    protected List<WBAttacker> wbAttackers;

    public WB1GL() {
        createWBGLMainCharacter();
        createWBGLAttackers();
        createWBGLBackground();
    }


    protected void createWBGLBackground() {
        wbBgOne = new WBBackground(0, 0, 1000);
        wbBgTwo = new WBBackground(getWbBackdroundPosX(), 0, 1000);
        wbMainCharacter.setBackGroundOne(wbBgOne);
        wbMainCharacter.setBackGroundTwo(wbBgTwo);
        for (WBAttacker e : wbAttackers) {
            e.setWBBackground(wbBgOne);
        }
    }

    private void createWBGLMainCharacter() {
        wbMainCharacter = new WBMainCharacter();
    }

    protected void createWBGLAttackers() {
        int maxHealth = 0;
        wbAttackers = new ArrayList<WBAttacker>();
        for (int attackerCount = 1; attackerCount <= getWbEnemyCount(); attackerCount++) {
            WBAttacker enemy = new WBWormAttacker(getRandomPosition(attackerCount));
            maxHealth += enemy.getWBAttackerHealth();
            wbAttackers.add(enemy);
        }
        setWBGLTotalAttackerHealth(maxHealth);
    }

    protected int getRandomPosition(int attackerCount) {
        return rand.nextInt(3000);
    }

    public void setWBGLTotalAttackerHealth(int wbGlTotalAttackerHealth) {
        this.wbGlTotalAttackerHealth = wbGlTotalAttackerHealth;
    }

    public int getWbGlTotalAttackerHealth() {
        return wbGlTotalAttackerHealth;
    }

    protected void updateWBGLBackgroundPositions() {
        wbBgOne.updateBG();
        wbBgTwo.updateBG();
        if (wbBgTwo.getBgX() < 0) {
            for (WBAttacker e : wbAttackers) {
                if (e.getWbAttackerPositionHorizontal() < 100) {
                    e.setWbAttackerPositionHorizontal(restoreWbAttackerPosX.get());
                    restoreWbAttackerPosX.getAndAdd(restoreWbAttackerPosX.get());
                }
            }
        }
    }

    protected void updateWBGLMainCharacterPositions() {
        wbMainCharacter.wbCharacterUpdatePosition();
        wbMainCharacter.jump();
    }

    protected void updateWB1GLWbGireBullets() {
        List<WBFireBullet> lasers = wbMainCharacter.getFireBullets();
        for (int i = 0; i < lasers.size(); i++) {
            WBFireBullet laser = lasers.get(i);
            if (laser.isVisible()) {
                laser.update();
            } else {
                lasers.remove(laser);
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.wappybird.GamePlay#paintGraphics(java.awt.Graphics)
     */
    @Override
    public void updateGameLevelGraphics(final Graphics g) {
        updateWBGLBackgroundGraphics(g);
        final WBGameView wbGameView2 = getWbGameView();
        g.drawImage(wbGameView2.getCurrentSprite(), getWBMainCharacter().getCenterX() - 83,
                getWBMainCharacter().getCenterY() - 95, wbGameView2);
        g.drawString("Health: " + getWBMainCharacter().getHealth() + "", 20, 20);
        g.drawString("Points: " + getWBMainCharacter().getWbKillWbAttackerPoints(), 20, 40);
        g.drawString("Level: " + getWbGameLevelID(), 400, 20);

        for (int i = 0; i < getWbAttackers().size(); i++) {
            WBAttacker e = getWbAttackers().get(i);
            if (e.isWBAttackerKilled()) {
                g.drawImage(null, e.getWbAttackerPositionHorizontal() - 83, e.getWbAttackerPositionVertical() - 20,
                        wbGameView2);
            } else {
                g.drawImage(wbGameView2.getWormImage(), e.getWbAttackerPositionHorizontal() - 83,
                        e.getWbAttackerPositionVertical() + 15, wbGameView2);
            }
        }

        final ImageObserver observer = wbGameView2;
        final Timer mainCharacterIsAttackedTimer = new Timer(50, new ActionListener() {
            boolean iChanged = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!iChanged) {
                    wbGameView2.updateCurrentSprite(wbGameView2.getCharacterReg());
                } else {
                    wbGameView2.updateCurrentSprite(null);
                }
                g.drawImage(wbGameView2.getCurrentSprite(), getWBMainCharacter().getCenterX() - 83,
                        getWBMainCharacter().getCenterY() - 95, observer);
                iChanged = !iChanged;

            }
        });

        if (getWbAttackers().isEmpty()) {
            WBGameLevelFactory.getFactory().proceedToNextWBGameLevel();
            wbGameView2.setWbCurrentGameLevel(WBGameLevelFactory.getFactory().getWBCurrentGameLevel());
            wbGameView2.getWbCurrentGameLevel().setOnUpdateGamePlayListener(wbGameView2);

        }

        if (getWBMainCharacter().getHealth() <= 0) {
            onKillWBMainCharacter_EndWBGamePlay();
            g.drawImage(wbGameView2.getGameOverImage(), 300, 100, observer);
        }

        if (getWBMainCharacter().isAttacking()) {
            Thread stopIsAttacking = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                        getWBMainCharacter().setAttacking(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            stopIsAttacking.setDaemon(true);
            stopIsAttacking.start();
        }

        if (getWBMainCharacter().isAttacked()) {
            mainCharacterIsAttackedTimer.start();
            Thread stopTimerThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        mainCharacterIsAttackedTimer.stop();
                        wbGameView2.updateCurrentSprite(wbGameView2.getCharacterReg());
                        getWBMainCharacter().setAttacked(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            stopTimerThread.setDaemon(true);
            stopTimerThread.start();

        }

    }

    protected void updateWBGLBackgroundGraphics(Graphics g) {
        g.drawImage(wbGameView.getGmBackground(), getBgOne().getBgX(), getBgOne().getBgY(), wbGameView);
        g.drawImage(wbGameView.getGmBackground(), getBgTwo().getBgX(), getBgTwo().getBgY(), wbGameView);
    }

    protected void updateWBGLEnemiesPositions() {

        for (int i = 0; i < wbAttackers.size(); i++) {
            final WBAttacker attacker = wbAttackers.get(i);
            if (attacker.isWBAttackerKilled()) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                            wbAttackers.remove(attacker);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }).start();

                continue;
            }
            attacker.wbCharacterUpdatePosition();
        }
    }

    public WBBackground getBgOne() {
        return wbBgOne;
    }

    public void setBgOne(WBBackground bgOne) {
        this.wbBgOne = bgOne;
    }

    public WBBackground getBgTwo() {
        return wbBgTwo;
    }

    public void setBgTwo(WBBackground bgTwo) {
        this.wbBgTwo = bgTwo;
    }

    public WBMainCharacter getWBMainCharacter() {
        return wbMainCharacter;
    }

    public List<WBAttacker> getWbAttackers() {
        return wbAttackers;
    }

    @Override
    public int getWbGameLevelID() {
        return 1;
    }

    @Override
    protected int getWbEnemyCount() {
        return wbEnemyCount;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public WBGLAttackResolver getWBCollisionDetector() {
        return new WB1GLAttackResolver(this);
    }

    public WB1GLAttackResolver getWb1glCollisionDetector() {
        return wb1glCollisionDetector;
    }

    public void setWb1glCollisionDetector(WB1GLAttackResolver wb1glCollisionDetector) {
        this.wb1glCollisionDetector = wb1glCollisionDetector;
    }


    public int getWbBackdroundPosX() {
        return wbBackdroundPosX;
    }


}

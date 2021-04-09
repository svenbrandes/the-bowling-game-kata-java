package com.galvanize.bowling;

import java.util.ArrayList;
import java.util.List;


public class Game {

    private List<Frame> frameList = new ArrayList<>();

    public Game() {
        this.initGame();
    }

    private void initGame() {
        for(int i = 1; i <= 10; i++) {
            this.frameList.add(new Frame(i));
        }
    }

    public void roll(int currentRoll) {
        Frame currentFrame = this.frameList.stream().filter(frame -> !frame.isFinished()).findFirst().orElse(null);
        if(currentFrame != null) {
            currentFrame.addScore(currentRoll);
            this.calcPrevSpareOrStrike(currentFrame);
        }
    }

    private void calcPrevSpareOrStrike(Frame currentFrame) {
        Frame previousFrame = this.getPreviousFrame(currentFrame);
        if(previousFrame != null && currentFrame.throwCount != 3) {
            if(currentFrame.getThrowCount() == 1) {
                if (previousFrame.isSpare()) {
                    previousFrame.addScore(currentFrame.getScore());
                } else if (previousFrame.isStrike()) {
                    calcPrevPrevStrike(currentFrame);
                }
            }
            if(currentFrame.isFinished() || currentFrame.throwCount == 2) {
                if(previousFrame.isStrike()) {
                    previousFrame.addScore(currentFrame.getScore());
                }
            }
        }
    }

    private void calcPrevPrevStrike(Frame currentFrame) {
        Frame previousFrame = this.getPreviousFrame(currentFrame);
        Frame prevPreviousFrame = previousFrame != null ? this.getPreviousFrame(previousFrame) : null;
        if(prevPreviousFrame != null && prevPreviousFrame.isStrike()) {
            prevPreviousFrame.addScore(currentFrame.getScore());
        }
    }

    private Frame getPreviousFrame(Frame currentFrame) {
        return this.frameList.stream()
                .filter(frame -> frame.getId() == currentFrame.getId()-1).findFirst().orElse(null);
    }

    public int getTotalScore() {
        int totalScore = this.frameList.stream().map(frame -> frame.getScore()).reduce(0, (a,b) -> a+b);
        return totalScore;
    }

    private class Frame {
        private int id;
        private int score = 0;
        private int throwCount = 0;
        private boolean strike;
        private boolean spare;
        private boolean finished;

        public Frame(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public int getScore() {
            return this.score;
        }

        public int getThrowCount() {
            return this.throwCount;
        }

        public boolean isStrike() {
            return this.strike;
        }

        public boolean isSpare() {
            return this.spare;
        }

        public boolean isFinished() {
            return this.finished;
        }

        public void addScore(int score) {
            this.score += score;
            if(!this.isFinished()) {
                this.throwCount++;
                if(this.score == 10) {
                    if(this.throwCount == 1) {
                        this.strike = true;
                    } else {
                        this.spare = true;
                    }
                }
                if((this.throwCount == 2 || this.strike) && this.id != 10) {
                    this.finished = true;
                } else if (this.throwCount == 3 || (this.throwCount == 2 && !(this.strike || this.spare))) {
                    this.finished = true;
                }
            }
        }
    }

}

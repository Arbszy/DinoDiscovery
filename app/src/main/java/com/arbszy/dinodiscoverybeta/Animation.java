package com.arbszy.dinodiscoverybeta;

public class Animation {

    String animationName;
    int startFrame;
    int frameCount;
    int fps;
    int cellWidth;
    int cellHeight;
    boolean looping;

    public Animation(String animationName, int startFrame, int frameCount, int fps, int cellWidth, int cellHeight, boolean looping) {
        this.animationName = animationName;
        this.startFrame = startFrame;
        this.frameCount = frameCount;
        this.fps = fps;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.looping = looping;
    }
}
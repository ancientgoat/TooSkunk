package com.premierinc.rule.utils;

/**
 *
 */
public class SkTimer {

	private long startTime = System.currentTimeMillis ();
	private long endTime = startTime;

	public void start(){
		startTime = System.currentTimeMillis ();
	}

	public void stop(){
		endTime = System.currentTimeMillis ();
	}

	public long diff(){
		return endTime - startTime;
	}

	public long stopAndDiff(){
		stop();
		return diff();
	}
}

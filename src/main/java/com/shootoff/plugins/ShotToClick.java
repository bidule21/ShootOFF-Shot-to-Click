package com.shootoff.plugins;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shootoff.camera.Shot;
import com.shootoff.targets.Hit;
import com.shootoff.targets.Target;

import javafx.geometry.Point2D;

public class ShotToClick extends ProjectorTrainingExerciseBase implements TrainingExercise {
	private static final Logger logger = LoggerFactory.getLogger(ShotToClick.class);

	private Robot clicker;

	public ShotToClick() {
	}

	public ShotToClick(List<Target> targets) {
		super(targets);
	}

	@Override
	public void init() {
		try {
			clicker = new Robot();
		} catch (AWTException e) {
			logger.error("Error initializing Robot to simulate clicks.", e);
		}
	}

	@Override
	public ExerciseMetadata getInfo() {
		return new ExerciseMetadata("Shot to Click", "1.2", "phrack",
				"This exercise converts a shot on a projection to a click on the screen being projected. "
						+ "To use this exercise, start the Projector Arena and calibrate, then minimize the arena "
						+ "and replace it on the projector with a Flash game or similar that you'd like to play "
						+ "using your laser trainer. The mouse cursor will move to where a shot is detected and "
						+ "a left click will be simulated.");
	}

	@Override
	public void shotListener(Shot shot, Optional<Hit> hit) {
		final Point2D arenaScreenOrigin = super.getArenaScreenOrigin();

		clicker.mouseMove((int) (shot.getX() + arenaScreenOrigin.getX()),
				(int) (shot.getY() + arenaScreenOrigin.getY()));
		clicker.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		clicker.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	@Override
	public void reset(List<Target> targets) {
	}
}

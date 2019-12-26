package org.mining.managers;

import java.awt.Point;

import org.mining.display.Game;
import org.mining.game.Direction;
import org.mining.game.Player;

public class PlayerCollisionHandler {
	
	/** The player thats being controlled */
	private Player player;
	
	public PlayerCollisionHandler(Player player) {
		this.player = player;
	}
	
	public boolean canMoveOnePx(Direction dir) {
		return !(wouldCollideWithWall(dir) || wouldCollideWithBlock(dir));
	}
	
	public boolean isStandingOnBlock() {
		if (player.getY() + player.getSize() == Game.CONTENT_HEIGHT - 1) return true;
		
		Point[] positions = new Point[] {
				new Point(player.getX(), player.getY() + player.getSize() + 1),
				new Point(player.getCenterX(), player.getY() + player.getSize() + 1),
				new Point(player.getX() + player.getSize(), player.getY() + player.getSize() + 1)
		};
		
		for (Point p: positions)
			if (!player.grid.getBlockAtPosition(0, p).getType().isWalkable()) 
				return true;
		
		return false;
	}
	
	public boolean isDigStartViable(){
		return !(player.getDigPos() == null ||
				player.getDigPos().getX() - player.getX() > 2 * player.getSize() ||
				player.getX() - player.getDigPos().getX() > 2 * player.getSize() ||
				player.getDigPos().getY() - player.getY() > 2 * player.getSize() ||
				player.getY() - player.getDigPos().getY() > 2 * player.getSize());
	}
	
	public boolean isDigStillViable() {
		return !(player.getDigPos() == null ||
				player.getDigPos().getX() - player.getX() > 2.5f * player.getSize() ||
				player.getX() - player.getDigPos().getX() > 2.5f * player.getSize() ||
				player.getDigPos().getY() - player.getY() > 2.5f * player.getSize() ||
				player.getY() - player.getDigPos().getY() > 2.5f * player.getSize());
	}
	
	private boolean wouldCollideWithWall(Direction dir) {
		switch (dir) {
		case LEFT:
			if (player.getX() <= 0) return true;
			else break;
		case RIGHT:
			if (player.getX() + player.getSize() >= Game.CONTENT_WIDTH - 1) return true;
			else break;
		case UP:
			if (player.getY() <= 0) return true;
			else break;
		case DOWN:
			if (player.getY() + player.getSize() >= Game.CONTENT_HEIGHT - 1) return true;
			else break;
		}
		
		return false;
	}
	
	private boolean wouldCollideWithBlock(Direction dir) {
		Point[] positions = null;
		switch (dir) {
		case LEFT:
			positions = new Point[] {
					new Point(player.getX() - 1, player.getY()),
					new Point(player.getX() - 1, player.getY() + player.getSize())
			};
			break;
		case RIGHT:
			positions = new Point[] {
					new Point(player.getX() + player.getSize() + 1, player.getY()),
					new Point(player.getX() + player.getSize() + 1, player.getY() + player.getSize())
			};
			break;
		case UP:
			positions = new Point[] {
					new Point(player.getX(), player.getY() - 1),
					new Point(player.getX() + player.getSize(), player.getY() - 1)
			};
			break;
		case DOWN:
			positions = new Point[] {
					new Point(player.getX(), player.getY() + player.getSize() + 1),
					new Point(player.getX() + player.getSize(), player.getY() + player.getSize() + 1)
			};
			break;
		}
		
		for (Point p: positions) 
			if (!player.grid.getBlockAtPosition(0, p).getType().isWalkable()) 
				return true;
		
		return false;
	}
}
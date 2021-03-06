package com.game.zenny.zh.client.appartment;

import org.json.simple.JSONArray;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

import com.game.zenny.zh.client.AppClient;

public class AppartmentStructure {

	//// STATIC
	
	public static AppartmentStructure parseAppartmentStructureFromJSON(JSONArray appartmentStructureJson) {
		try {
			AppartmentGroundCell[][] structure = new AppartmentGroundCell[appartmentStructureJson.size()][((JSONArray) appartmentStructureJson.get(appartmentStructureJson.size() - 1)).size()];
			
			for (int y = 0; y < appartmentStructureJson.size(); y++) {
				JSONArray appartmentStructureY = (JSONArray) appartmentStructureJson.get(y);
				for (int x = 0; x < appartmentStructureY.size(); x++) {
					JSONArray cellDatas = (JSONArray) appartmentStructureY.get(x);
					structure[y][x] = new AppartmentGroundCell(x, y, (boolean) cellDatas.get(0));
				}
			}
			return new AppartmentStructure(structure);
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//// OBJECT
	// -- APPARTMENT STRUCTURE
	private AppartmentGroundCell[][] structure;

	//// CONSTUCTORS

	/**
	 * @param structure
	 */
	public AppartmentStructure(AppartmentGroundCell[][] structure) {
		this.structure = structure;

		determineThickness();
	}

	//// GETTERS AND SETTERS

	/**
	 * @return the structure
	 */
	public AppartmentGroundCell[][] getStructure() {
		return structure;
	}

	/**
	 * @param structure
	 *            the structure to set
	 */
	public void setStructure(AppartmentGroundCell[][] structure) {
		this.structure = structure;

		determineThickness();
	}

	//// PARTICULAR

	/**
	 * @param x
	 * @param y
	 * @return x coordinate
	 */
	public float getCellXCoordinate(int x, int y) {
		float wd = AppClient.getSprites().ground.getWidth() / 2 - 1;

		float cx = x;
		cx = cx * AppClient.getSprites().ground.getWidth();
		cx = cx - (y + x) * wd;
		cx = cx - 2 * x;

		return cx;
	}

	/**
	 * @param x
	 * @param y
	 * @return y coordinate
	 */
	public float getCellYCoordinate(int x, int y) {
		float hd = AppClient.getSprites().ground.getHeight() / 2 + 1;

		float cy = y;
		cy = cy * AppClient.getSprites().ground.getHeight();
		cy = cy + x * hd;
		cy = cy - y * hd;
		cy = cy - x;

		return cy;
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isWalkable(int x, int y) {
		try {
			return structure[y][x].isActivated();
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * @param x
	 * @param y
	 */
	public void determineThickness(int x, int y) {
		if (!isWalkable(x, y + 1))
			structure[y][x].setGroundThicknessTL(true);

		if (!isWalkable(x + 1, y))
			structure[y][x].setGroundThicknessBL(true);
	}

	public void determineThickness() {
		for (int y = 0; y < structure.length; y++) {
			for (int x = 0; x < structure[y].length; x++) {
				determineThickness(x, y);
			}
		}
	}

	//// GAME METHODS

	/**
	 * @param gc
	 * @param sbg
	 * @param delta
	 * @throws SlickException
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		for (int y = 0; y < structure.length; y++) {
			for (int x = 0; x < structure[y].length; x++) {
				structure[y][x].update(gc, sbg, delta, new Point(getCellXCoordinate(x, y), getCellYCoordinate(x, y)));
			}
		}
	}
}

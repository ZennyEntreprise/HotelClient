package com.game.zenny.zh.scene;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.game.zenny.zh.AppClient;
import com.game.zenny.zh.NetworkClient;
import com.game.zenny.zh.appartment.Appartment;
import com.game.zenny.zh.entity.Player;
import com.game.zenny.zh.net.Bridge;

public class Game extends Scene {

	private NetworkClient networkClient;
	private Player player;
	private Appartment appartment;
	
	/**
	 * @param app
	 */
	public Game(AppClient app, String uuid) {
		super(app, AppClient.Scenes.GAME.getSceneID());
		
		try {
			networkClient = new NetworkClient(this, InetAddress.getByName("localhost"), Bridge.defaultPort);
		} catch (SocketException | UnknownHostException e) {
			System.exit(0);
		}
		
		networkClient.connect(uuid);
	}

	@Override
	public void initScene(GameContainer gc, StateBasedGame sbg) {
		appartment = new Appartment();
	}

	@Override
	public void leaveScene(GameContainer gc, StateBasedGame sbg) {
		
	}

	@Override
	public void renderScene(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (player == null || appartment == null)
			return;
		
		appartment.render(gc, sbg, g);
		
		g.drawString("UUID: "+player.getPlayerIdentifier(), 10, 180);
		g.drawString("Username: "+player.getUsername(), 10, 200);
		g.drawString("Credits: "+player.getCredits(), 10, 220);
	}

	@Override
	public void updateScene(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (player == null || appartment == null)
			return;
		
		appartment.update(gc, sbg, delta);
	}

	/**
	 * @return the networkClient
	 */
	public NetworkClient getNetworkClient() {
		return networkClient;
	}

	/**
	 * @param networkClient the networkClient to set
	 */
	public void setNetworkClient(NetworkClient networkClient) {
		this.networkClient = networkClient;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the appartment
	 */
	public Appartment getAppartment() {
		return appartment;
	}

	/**
	 * @param appartment the appartment to set
	 */
	public void setAppartment(Appartment appartment) {
		this.appartment = appartment;
	}
}

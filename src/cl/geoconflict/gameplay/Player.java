package cl.geoconflict.gameplay;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import android.util.Log;
import cl.geoconflict.GameStates;
import cl.geoconflict.network.Network.RequestNewCoord;

import com.badlogic.androidgames.framework.Game;

public class Player {
	
	/** Le otorga mayor sensibilidad al GPS, haciendo que el movimiento se vea m&aacute;s r&aacute;pido */
	public static final int SPEED = 3;
	
	private int iAmmo;
	private int iScore;
	private int health;
	private int x;
	private int y;
	private Game game;
	private Double latitud;
	private Double longitud;
	private boolean alive;
	
	public Player(int ammo, Game game){
		this.health = 10;
		this.iAmmo = ammo;
		this.iScore = 0;
		this.game = game;
		this.latitud = 0d;
		this.longitud = 0d;
		//posicion se corrige cuando se asigna dimencion
		this.x=0;
		this.y=0;
		this.alive = true;
	}
	
	public boolean isLoaded() {
		if(this.iAmmo > 0) 
			return true;
		return false;
	}
	
	public void addScore(int score){
		iScore += score;
		Log.d("DEBUG", "puntaje: " + iScore);
	}
	
	public void restAmmo(){
		if(iAmmo>0){
			iAmmo--;
			Log.d("DEBUG", "Municion: " + this.iAmmo);
		}
	}
	
	public String getScore() {
		return Integer.toString(iScore);
	}
	
	public String getAmmo(){
		return Integer.toString(iAmmo);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Game getGame(){
		return this.game;
	}
	
	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	public void damage(){
		this.health -= 1;
		Log.d("DEBUG", "Danio " + this.health);
	}
	
	/**
	 * @return the latitud
	 */
	public Double getLatitud() {
		this.latitud = this.game.getInput().getLatitud();
		return latitud;
	}

	/**
	 * @return the longitud
	 */
	public Double getLongitud() {
		this.longitud = this.game.getInput().getLongitud();
		return longitud;
	}
	
	/**
	 * @return the direction
	 */
	public float getDirection() {
		return this.game.getInput().getDirection();
	}

	/**
	 * @return the alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * @param alive the alive to set
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void updatePosition(){
		if(this.game.getInput().isLocationChanged()){
			this.latitud = this.game.getInput().getLatitud();
			this.longitud = this.game.getInput().getLongitud(); 
			
			RequestNewCoord rnc = new RequestNewCoord();
			rnc.username = GameStates.currMatch;
			rnc.newCoordInfo = this.getPosition();
			GameStates.client.sendUDP(rnc);
			
			this.game.getInput().setLocationChanged(false);
		}
	}
	
	public JSONObject getPosition() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("latitud", this.latitud);
			obj.put("longitud", this.longitud);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public JSONObject getShootInfo() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("latitud", this.latitud);
			obj.put("longitud", this.longitud);
			obj.put("direccion", (double) this.getDirection() ); //variable de clase (float)
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
}

/** Clase singleton que instancia el juego
 * 
 */
package cl.geoconflict;


import cl.geoconflict.screen.ConnectionScreen;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;
import com.esotericsoftware.minlog.Log;


/**
 * @author Fernando Valencia F.
 * 
 */
public class GeoConflictGame extends AndroidGame {

	@Override
	public Screen getStartScreen() {
		/*
		 * Se setea nivel de log LEVEL_DEBUG para testing, LEVEL_INFO para
		 * evitar muchos logs.
		 */
		Log.set(Log.LEVEL_DEBUG);
		return new ConnectionScreen(this);
	}
}

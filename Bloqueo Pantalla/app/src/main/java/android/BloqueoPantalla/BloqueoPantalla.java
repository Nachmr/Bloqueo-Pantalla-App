/**
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package android.BloqueoPantalla;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

/**
 * <p>Clase que se encarga de toda la gestión para bloquear la pantalla al activar el sensor de proximidad </p>
 *
 * @author Pedro Antonio Ruiz Cuesta
 * @author Ignacio Martín Requena
 * Última modificación: 9/2/2016
 */
public class BloqueoPantalla extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
	static final String LOG_TAG = "Bloqueo Pantalla";

    /**
     * Metodo para iniciar la app
     * @param savedInstanceState
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);;
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		setContentView(R.layout.activity_screen_off);
	}

    /**
     * Metodo principal que apaga la pantalla y sale de la app
     */
	private void turnScreenOffAndExit() {
		// Bloqueamos la mantalla
		turnScreenOff(getApplicationContext());

		// Hacemos vibrar el movil
		((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(50);

		final Activity activity = this;
		Thread t = new Thread() {
			public void run() {
				try {
					sleep(500);
				} catch (InterruptedException e) { }
				activity.finish();
			}
		};
		t.start();
	}

	/**
	 * Apaga la pantalla y bloquea el teléfono
	 *
	 * @param context
	 *            - Contexto de la aplicación
	 */
	static void turnScreenOff(final Context context) {
		DevicePolicyManager administrador = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName permisos = new ComponentName(context,
				BloqueoPantallaPermisos.class);
		boolean admin = administrador.isAdminActive(permisos);
		if (admin) {
			Log.i(LOG_TAG, "Durmiendo...");
			administrador.lockNow();
		} else {
			Log.i(LOG_TAG, "No eres administrador");
			Toast.makeText(context, "La aplicacion no tiene permisos suficientes. Vaya a ajustes > seguridad > administradores de dispositivos y seleccionela", Toast.LENGTH_LONG).show();
		}
	}

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    /**
     * Metodo que evalua el estado del sensor
     * @param event
     */
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.values[0] == 0)
			turnScreenOffAndExit();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
}

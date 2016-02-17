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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * <p>Clase que recive eventos y los pone en los listeners adecuados</p>
 *
 * @author Pedro Antonio Ruiz Cuesta
 * @author Ignacio Martín Requena
 * Última modificación: 9/2/2016
 */
public class BloqueoPantallaEventos extends BroadcastReceiver {

	/**
	 * Metodo para manejar los eventos de la app
	 * @param context
	 * @param intent
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		TelephonyManager telefono = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		telefono.listen(new BloqueoPantallaLlamada(context),PhoneStateListener.LISTEN_CALL_STATE);
	}

}

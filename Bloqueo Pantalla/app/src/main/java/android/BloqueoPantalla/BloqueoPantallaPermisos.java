package android.BloqueoPantalla;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * <p>Clase para mostrar notificaciones cuando se le dan permisos a la aplicación en ajustes > seguridad > administradores de dispositivos</p>
 *
 * @author Pedro Antonio Ruiz Cuesta
 * @author Ignacio Martín Requena
 * Última modificación: 9/2/2016
 */
public class BloqueoPantallaPermisos extends DeviceAdminReceiver {

    private void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onEnabled(Context context, Intent intent) {
		showToast(context,
				context.getString(R.string.admin_receiver_status_enabled));
	}

	@Override
	public void onDisabled(Context context, Intent intent) {
		showToast(context,
				context.getString(R.string.admin_receiver_status_disabled));
	}

}

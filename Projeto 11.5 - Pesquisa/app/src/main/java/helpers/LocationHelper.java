package helpers;

import android.Manifest;
import android.content.Context;
import android.location.Location;

import androidx.annotation.RequiresPermission;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class LocationHelper {
    public interface LocationCallback {
        void onLocationReceived(Location location);
    }

    private final FusedLocationProviderClient fusedLocationClient;
    private final Context context;

    public LocationHelper(Context context) {
        this.context = context;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void obterLocalizacao(LocationCallback callback) {
        // Verificar permissões aqui (simplificado)
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        callback.onLocationReceived(location);
                    }
                });
    }
}
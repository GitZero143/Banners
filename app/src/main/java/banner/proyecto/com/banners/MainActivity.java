package banner.proyecto.com.banners;

import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        MultiDex.install(this);
        setContentView(R.layout.activity_main);
        //uno la parte logica a la parte visual
        adView=(AdView)findViewById(R.id.banner);

        //pido el bannaer al servidor
        AdRequest adRequest=new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        //cargo la publicidad en la app
        //cargo lo que recibo del servidor
        adView.loadAd(adRequest);
        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();
        }


    }

   //IR a la pantalla de login
    private void goLoginScreen() {
        //si no hay sesion iniciada lo mando a la pantalla de login
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //Banner ciclo de vida
    @Override
    protected void onDestroy() {
    //si existe un banner mostrandose
    if(adView!=null){
            adView.destroy();
        }
        super.onDestroy();
    }

    //Banner ciclo de vida
    @Override
    protected void onPause() {
        if(adView!=null){
            adView.pause();
        }
        super.onPause();
    }

    //Banner ciclo de vida
    @Override
    protected void onResume() {
        if(adView!=null){
            adView.resume();
        }
        super.onResume();
    }

    public void desconectar(View view) {
     //me dsesconecto de la sesion al poner el botón
        LoginManager.getInstance().logOut();
        //regreso al login
        goLoginScreen();
    }
}

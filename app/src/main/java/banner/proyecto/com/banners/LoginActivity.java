package banner.proyecto.com.banners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

public class LoginActivity extends AppCompatActivity {
//objetos necesarios
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProfilePictureView profilePicture;
    private AccessToken accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager=CallbackManager.Factory.create();
        loginButton=(LoginButton)findViewById(R.id.loginbutton);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
            //este metodo me lleva al main al hacer un login exitoso
                accessToken=loginResult.getAccessToken();
                Profile profile=Profile.getCurrentProfile();
                if(profile!=null){
                goMainScreen();
                profilePicture.setProfileId(profile.getId());}
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });

        profilePicture=(ProfilePictureView)findViewById(R.id.profile_picture);

    }

    private void goMainScreen() {
        //controlo que al poner el boton regresar no vuelva al login si no me saque de la app
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}

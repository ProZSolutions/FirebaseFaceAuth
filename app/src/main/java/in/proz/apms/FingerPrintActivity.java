package in.proz.apms;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import in.proz.apms.Handler.FingerprintHandler;

public class FingerPrintActivity extends AppCompatActivity {
    // Defining variable for storing
    // key in android keystore container
    String message = null;
    private KeyStore keyStore;
    // Defining variable for storing
    // key in android keystore container
    private static final String KEY_NAME = "PROZAPMS";
    private Cipher cipher;

    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_id);


        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                message = "No Biometric Hardware in your device";
                  Toast.makeText(getApplicationContext(), " No bio hardware ", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                message = "Biometric Not Working";

                 Toast.makeText(getApplicationContext(), " Not working ", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                message = "Fingerprint No Enrolled in Your Device.Please Add One Fingerprint and Login";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                break;


        }

        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(FingerPrintActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                 Toast.makeText(getApplicationContext(),"Failed to authendicate",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                 Toast.makeText(getApplicationContext(), " authendication success  ", Toast.LENGTH_SHORT).show();
                 try {
                    // Access crypto object from the authentication result
                    BiometricPrompt.CryptoObject cryptoObject = result.getCryptoObject();
                    Log.d("promtinfo", " object as " + cryptoObject);
                    if (cryptoObject != null) {
                        // Handle crypto object, perform cryptographic operations, or access biometric data
                        Cipher cipher = cryptoObject.getCipher();
                        Log.d("promtinfo", " cipher " + cipher);
                        // Use the cipher to perform cryptographic operations or access biometric data
                    }
                } catch (Exception e) {
                    Log.d("promtinfo", "exception " + e.getMessage());
                    // Handle exception
                }


            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Login ")
                .setDescription("Use Fingerpint to login ").setDeviceCredentialAllowed(true).build();
        biometricPrompt.authenticate(promptInfo);


    }
}
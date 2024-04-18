package in.proz.apms;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class FaceIDActivity extends AppCompatActivity {
    FirebaseVisionFaceDetector detector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_id);
        FirebaseVisionFaceDetectorOptions options =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.NO_LANDMARKS)
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.NO_CLASSIFICATIONS)
                        .build();

        // Create a FirebaseVisionFaceDetector
        detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options);




        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        if (intent.resolveActivity(getPackageManager())!= null) {
            startActivityForResult(
                    intent, 100);
        }
        else {
            // if the image is not captured, set
            // a toast to display an error image.
            Toast
                    .makeText(
                            FaceIDActivity.this,
                            "Something went wrong",
                            Toast.LENGTH_SHORT)
                    .show();
        }

        // Convert bitmap to FirebaseVisionImage



    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data)
    {
        // after the image is captured, ML Kit provides an
        // easy way to detect faces from variety of image
        // types like Bitmap

        super.onActivityResult(requestCode, resultCode,
                data);
        if (requestCode == 100
                && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
            Bitmap bitmap = (Bitmap)extra.get("data");
            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
            detector.detectInImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionFace> faces) {
                            // Task completed successfully
                            // Process the detected faces
                            Log.d("FaceDetect","on success");
                            for (FirebaseVisionFace face : faces) {
                                // Get face boundaries
                                float left = face.getBoundingBox().left;
                                float top = face.getBoundingBox().top;
                                float right = face.getBoundingBox().right;
                                float bottom = face.getBoundingBox().bottom;
                                Toast.makeText(getApplicationContext(),"Face Captured Successfully ",Toast.LENGTH_SHORT).show();
                               // commonClass.showSuccess(FaceDetectActivity.this,"Authendication Success");
                                // Process other face data if needed
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // Handle the failure
                            Toast.makeText(getApplicationContext(),"Failed to capture face  ",Toast.LENGTH_SHORT).show();
                            Log.d("FaceDetect"," error "+e.getMessage());
                        }
                    });

        }
    }



}


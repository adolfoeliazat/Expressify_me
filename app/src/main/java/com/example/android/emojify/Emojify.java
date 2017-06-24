package com.example.android.emojify;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import timber.log.Timber;

/**
 * Created by Shagun on 24/06/2017.
 */

class Emojify {

    /**
     * Method for detecting faces in a bitmap.
     *
     * @param context The application context.
     * @param picture The picture in which to detect the faces.
     */
    static void detectFaces(Context context, Bitmap picture) {

        // Create the face detector, disable tracking and enable classifications
        FaceDetector detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        // Build the frame
        Frame frame = new Frame.Builder().setBitmap(picture).build();

        // Detect the faces
        SparseArray<Face> faces = detector.detect(frame);

        // Log the number of faces
        Timber.d( "detectFaces: number of faces = " + faces.size());

        // If there are no faces detected, show a Toast message
        if(faces.size() == 0){
            Toast.makeText(context, "Face not found error", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < faces.size(); ++i) {
                Face face = faces.valueAt(i);

                // Log the classification probabilities for each face.
                getClassifications(face);
            }

        }

        // Release the detector
        detector.release();
    }

    private static void getClassifications(Face face){
        // Log all the probabilities
        Timber.d("getClassifications: smilingProb = " + face.getIsSmilingProbability());
        Timber.d( "getClassifications: leftEyeOpenProb = "
                + face.getIsLeftEyeOpenProbability());
        Timber.d( "getClassifications: rightEyeOpenProb = "
                + face.getIsRightEyeOpenProbability());
    }
}

package com.example.HRM.Helper;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;

public class ImageHelper {
    public static void ViewImageFromList(ArrayList<String> listImages, Context context){
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .setResizeAndRotateEnabledForNetwork(true)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(context, config);
        new ImageViewer.Builder(context, listImages)
                .hideStatusBar(true)
                .allowZooming(true)
                .allowSwipeToDismiss(true)
                .show();
    }
}

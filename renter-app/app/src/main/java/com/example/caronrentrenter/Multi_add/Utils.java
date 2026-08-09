package com.example.caronrentrenter.Multi_add;

import android.content.Intent;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;
public class Utils {

    public static List<Uri> getSelectedImages(Intent data) {
        List<Uri> imageUris = new ArrayList<>();
        if (data.getClipData() != null) {
            int count = data.getClipData().getItemCount();
            for (int i = 0; i < count; i++) {
                Uri imageUri = data.getClipData().getItemAt(i).getUri();
                imageUris.add(imageUri);
            }
        } else if (data.getData() != null) {
            Uri imageUri = data.getData();
            imageUris.add(imageUri);
        }
        return imageUris;
    }
}
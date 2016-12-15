package edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Got help with implementing serialization of a bitmap from another person
 */

public class SerializableBitmap implements Serializable {

    private Bitmap image;

    public SerializableBitmap(Bitmap bitmap) {
        image = bitmap;
    }

    public Bitmap getCurrentImage() {
        return image;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] array = stream.toByteArray();
        out.writeInt(array.length);
        out.write(array);
        image = BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        int length = in.readInt();
        byte[] array = new byte[length];

        int pos = 0;
        do {
            int read = in.read(array, pos, length - pos);

            if (read != -1) {
                pos += read;
            } else {
                break;
            }

        } while (pos < length);

        image = BitmapFactory.decodeByteArray(array, 0, length);

    }
}
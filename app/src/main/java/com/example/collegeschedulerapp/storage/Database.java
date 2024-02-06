package com.example.collegeschedulerapp.storage;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;

public class Database<T> {

    private File databaseFile;

    private ArrayList<T> data;

    public Database(Context context, String scope) {
        File directory = new File(context.getFilesDir(), "data");

        if (!directory.exists()) {
            directory.mkdir();
        }

        try {
            databaseFile = new File(directory, scope);
            databaseFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        deserialize();
    }

    public ArrayList<T> getData() {
        return data;
    }

    @SuppressWarnings("unchecked")
    public void deserialize() {
        try {
            InputStream fileInputStream = Files.newInputStream(databaseFile.toPath());

            data = new ArrayList<>();

            if (fileInputStream.available() > 0) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                int size = objectInputStream.readInt();
                for (int i = 0; i < size; i++) {
                    data.add((T) objectInputStream.readObject());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void serialize() {
        try {
            OutputStream fileOutputStream = Files.newOutputStream(databaseFile.toPath());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeInt(data.size());
            for (int i = 0; i < data.size(); i++) {
                objectOutputStream.writeObject(data.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package  com.hdfclimited;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;

import com.adstringo.compression.image.CompressionTechnique;
import com.adstringo.compression.image.OnImageCompressionListener;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Adstringo extends CordovaPlugin implements OnImageCompressionListener {


    CallbackContext callbackContext;
    boolean settingsFlag = false;


    JSONArray data;
    String action;

    @Override
    public boolean execute(final String action, final JSONArray args, final CallbackContext callbackContext) {

        this.action = action;
        this.data = args;
        this.callbackContext = callbackContext;
        settingsFlag = false;
        return executecode();
    }

    Boolean executecode() {

        if (action.equals("compressFile")) {

            String name = null;
            try {
                name = data.getString(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (name.startsWith("file:///storage")) {


                File file = new File(URI.create(name).getPath());
                if (file.exists()) {
                    new CompressionTechnique(this.cordova.getActivity()).setInputFile(file).setOnImageCompressionListener(this).compressImage();

                    return true;

                } else {
                    callbackContext.error("Input File does not exist");
                    return false;
                }
            } else if (name.startsWith("/storage")) {

                File file = new File(URI.create(name).getPath());
                if (file.exists()) {

                    new CompressionTechnique(this.cordova.getActivity()).setInputFile(file).setOnImageCompressionListener(this).compressImage();

                    return true;
                } else {
                    callbackContext.error("Input File does not exist");

                    return false;
                }
            } else if (name.startsWith("/data")) {

                File file = new File(URI.create(name).getPath());
                new CompressionTechnique(this.cordova.getActivity()).setInputFile(file).setOnImageCompressionListener(this).compressImage();

                return true;
            } else {
                String jarinptpath = getFilePathByUri(this.cordova.getActivity(), Uri.parse(name));


                if (jarinptpath == null) {
                    String jarinptpath1 = getpath(cordova.getActivity(), Uri.parse(name));
                    File file = new File(jarinptpath1);
                    if (file.exists()) {
                        new CompressionTechnique(this.cordova.getActivity()).setInputFile(file).setOnImageCompressionListener(this).compressImage();

                        return true;
                    } else {
                        callbackContext.error("Input File does not exist");

                        return false;
                    }
                } else {
                    File file = new File(jarinptpath);
                    if (file.exists()) {
                        new CompressionTechnique(this.cordova.getActivity()).setInputFile(file).setOnImageCompressionListener(this).compressImage();

                        return true;

                    } else {
                        callbackContext.error("Input File does not exist");

                        return false;
                    }

                }
            }

        } else {
            callbackContext.error("Action not connected");

            return false;

        }


    }


    public static String getFilePathByUri(Context context, Uri uri) {
        String path = null;
        // ? file:// ???
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            path = uri.getPath();
            return path;
        }
        // ? content:// ???,?? content://media/extenral/images/media/17766
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }
        // 4.4???? ?? content:// ???,?? content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }
                } else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);

                    if (id != null && id.startsWith("raw:")) {
                        return id.substring(4);
                    }

                    String[] contentUriPrefixesToTry = new String[]{
                            "content://downloads/public_downloads",
                            "content://downloads/my_downloads",
                            "content://downloads/all_downloads",
                            "content://com.android.providers.downloads.documents/document"
                    };

                    for (String contentUriPrefix : contentUriPrefixesToTry) {
                        Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));

                        try {
                            path = getDataColumn(context, contentUri, null, null);
                            if (path != null) {
                                return path;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    return path;
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }

            }
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    private String getTempDirectoryPath() {
        File cache = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            cache = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/Compressed_images/" + cordova.getActivity().getPackageName());
            double cac = cache.length();

            System.out.println("Intial file size......." + cac);

        }
        // Use internal storage
        else {
            cache = cordova.getActivity().getCacheDir();
        }

        // Create the cache directory if it doesn't exist
        cache.mkdirs();
        return cache.getAbsolutePath();
    }

    public static String getpath(Context context, Uri contentUri) {
        Uri queryUri = MediaStore.Files.getContentUri("external");
        String columnData = MediaStore.Files.FileColumns.DATA;
        String columnSize = MediaStore.Files.FileColumns.SIZE;

        String[] projectionData = {MediaStore.Files.FileColumns.DATA};


        String name = null;
        String size = null;

        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);

            cursor.moveToFirst();

            name = cursor.getString(nameIndex);
            size = cursor.getString(sizeIndex);

            cursor.close();
        }

        String result = null;
        if ((name != null) && (size != null)) {
            String selectionNS = columnData + " LIKE '%" + name + "' AND " + columnSize + "='" + size + "'";

            Cursor cursorLike = context.getContentResolver().query(queryUri, projectionData, selectionNS, null, null);

            if ((cursorLike != null) && (cursorLike.getCount() > 0)) {
                cursorLike.moveToFirst();
                int indexData = cursorLike.getColumnIndex(columnData);
                if (cursorLike.getString(indexData) != null) {
                    result = cursorLike.getString(indexData);
                }
                cursorLike.close();
            }
        }

        return result;

    }


    @Override
    public void onCompressionStart() {

    }

    @Override
    public void onCompressionSuccess(File file, String metaDataInfo) {
        callbackContext.success(file.getAbsolutePath());

    }

    @Override
    public void onCompressionFail(boolean isAuthError, String errorMessage) {
        callbackContext.error(errorMessage);

    }
}
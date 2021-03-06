package studio.dinhduc.doctruyen.ui.constant;

import android.os.Environment;

import java.io.File;

/**
 * Created by dinhduc on 13/11/2016.
 */

public class Const {
    public static final String APP_DIR_NAME = "DocTruyen";
    public static final String EXTERNAL_STORAGE_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String APP_DIR_PATH = EXTERNAL_STORAGE_PATH + File.separator + APP_DIR_NAME;

    public class KeyIntent {
        public static final String KEY_NOVEL_NAME = "NOVEL_NAME";
        public static final String KEY_NOVEL_PATH = "NOVEL_PATH";
        public static final String KEY_CHAPTER_NAME = "CHAPTER_NAME";
        public static final String KEY_SEARCH_QUERY = "SEARCH_QUERY";
        public static final String KEY_LIST_CHAPTER_NAME = "LIST_CHAPTER_NAME";
        public static final String KEY_CHAPTER_CHOSEN_POSITION = "CHAPTER_CHOSEN_POSITION";
        public static final String KEY_SPELL_CHECK = "SPELL_CHECK";
    }
}

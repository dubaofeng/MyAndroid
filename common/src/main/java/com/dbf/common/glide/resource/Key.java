package com.dbf.common.glide.resource;

import com.dbf.common.glide.Tool;

/**
 * Created by dbf on 2020/6/3
 * describe:
 */
public class Key {
    private final String TAG = Key.class.getCanonicalName();
    // 合格：唯一 加密的  ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a4668
    private String key;

    public Key(String path) {
        this.key = Tool.getSHA256StrJava(path);
    }

    public String getTAG() {
        return TAG;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}

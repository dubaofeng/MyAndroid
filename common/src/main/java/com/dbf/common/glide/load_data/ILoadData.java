package com.dbf.common.glide.load_data;

import android.content.Context;

import com.dbf.common.glide.resource.Value;

public
/**
 *Created by dbf on 2020/6/7 
 *describe:
 */
interface ILoadData {
    Value loadResource(String path, ResponListener responListener, Context context);
}

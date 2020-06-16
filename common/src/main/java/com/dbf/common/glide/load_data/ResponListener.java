package com.dbf.common.glide.load_data;

import com.dbf.common.glide.resource.Value;

public
/**
 *Created by dbf on 2020/6/7 
 *describe:
 */
interface ResponListener {
    public void responseSuccess(Value value);

    public void responseException(Exception e);
}

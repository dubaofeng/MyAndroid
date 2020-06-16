package com.dbf.common.glide;

import android.content.Context;

public
/**
 *Created by dbf on 2020/6/7 
 *describe:
 */
class GlideBuilder {
    public GlideBuilder(Context context) {
    }

    public Glide build() {
        RequestManagerRetriver requestManagerRetriver = new RequestManagerRetriver();
        Glide glide = new Glide(requestManagerRetriver);
        return glide;
    }
}

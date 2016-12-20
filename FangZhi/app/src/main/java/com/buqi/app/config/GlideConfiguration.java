package com.buqi.app.config;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.stream.HttpUrlGlideUrlLoader;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

/**
 * Created by smacr on 2016/9/22.
 */
public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, 1024*1024*1024));
//        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
//        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
//        builder.setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new HttpUrlGlideUrlLoader.Factory());
    }
}

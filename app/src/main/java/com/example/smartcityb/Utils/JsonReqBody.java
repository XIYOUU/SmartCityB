package com.example.smartcityb.Utils;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class JsonReqBody extends RequestBody {
    String json;
    public JsonReqBody(String str){
        json=str;
    }
    public JsonReqBody(){
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse("application/json");
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        BufferedSink bufferedSink1 = bufferedSink.writeString(json, Charset.forName("utf-8"));
    }
}

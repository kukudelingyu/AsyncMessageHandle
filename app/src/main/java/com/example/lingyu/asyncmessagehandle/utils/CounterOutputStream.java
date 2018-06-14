package com.example.lingyu.asyncmessagehandle.utils;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by duanlingyu on 2018/6/13.
 */

public class CounterOutputStream extends OutputStream {

    private AtomicLong atomicLong = new AtomicLong();

    public CounterOutputStream() {
        super();
    }

    @Override
    public void write(int b) throws IOException {
        atomicLong.addAndGet(1);
    }


    public void write(long b) throws IOException {
        atomicLong.addAndGet(b);
    }

    @Override
    public void write(@NonNull byte[] b) throws IOException {
        atomicLong.addAndGet(b.length);
    }

    @Override
    public void write(@NonNull byte[] b, int off, int len) throws IOException {
        atomicLong.addAndGet(len);
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }

    public long getLength(){
        return atomicLong.get();
    }
}

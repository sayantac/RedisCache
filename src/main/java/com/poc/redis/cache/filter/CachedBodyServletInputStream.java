package com.poc.redis.cache.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class CachedBodyServletInputStream extends ServletInputStream {
	
	private InputStream cachedBodyInputStream;

    public CachedBodyServletInputStream(byte[] cachedBody) {
        this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public boolean isFinished() {
        try {
			return cachedBodyInputStream.available() == 0;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }

    @Override
    public boolean isReady() {
        return true;
    }

	@Override
	public int read() throws IOException {
	    return cachedBodyInputStream.read();
	}

	@Override
	public void setReadListener(ReadListener listener) {
		
	}

}

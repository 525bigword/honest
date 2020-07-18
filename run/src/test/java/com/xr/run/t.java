package com.xr.run;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

public class t {
    @Test
    public void a(HttpServletRequest request) {
        String uri= request.getRealPath("web.xml");
        System.out.println(uri);
    }
}

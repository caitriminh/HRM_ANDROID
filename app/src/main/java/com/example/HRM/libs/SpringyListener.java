package com.example.HRM.libs;

public interface SpringyListener {

    /*
     * hits when Spring is Active
     * */
    void onSpringStart();

    /*
     * hits when Spring is inActive
     * */

    void onSpringStop();
}

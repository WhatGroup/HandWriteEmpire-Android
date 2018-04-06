package com.sinovoice.example.pojo;

import android.graphics.Path;
import java.io.Serializable;

/**
 * Created by lht on 17/1/5.
 */

public class SerializablePath extends Path implements Serializable {

    public SerializablePath() {
        super();
    }

    SerializablePath(SerializablePath path) {
        super(path);
    }
}

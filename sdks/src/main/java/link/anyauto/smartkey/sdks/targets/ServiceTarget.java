package link.anyauto.smartkey.sdks.targets;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

/**
 * A wrapper or just say a target of service, all generated service targets will inherit this class.
 * @param <T>
 *     the type of generated class.
 */
public abstract class ServiceTarget<T> extends Target<T> {

    /**
     * The class of the service.
     * @return
     *  the class.
     */
    protected abstract Class<? extends Service> getServiceClass();

    /**
     * Start the wrapping service.
     * @param src
     */
    public void start(Context src) {
        Intent in = prepare(src);
        in.setClass(src, getServiceClass());
        src.startService(in);
    }

    /**
     * Bind the wrapping service.
     * @param src
     *  the context
     * @param conn
     *  the service connection
     * @param flags
     *  the flags.
     */
    public void bind(Context src, ServiceConnection conn, int flags) {
        Intent in = prepare(src);
        in.setClass(src, getServiceClass());
        src.bindService(in, conn, flags);
    }
}

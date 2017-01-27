package link.anyauto.smartkey.sdks.targets;

import android.app.Service;

/**<pre>
 *  For service targets that are not determined.
 *  For example, the class of the target service is from a config file or some where else.
 * </pre>
 * Created by LYQ on 17-1-17.
 */

public class NotDeterminedServiceTarget extends ServiceTarget<NotDeterminedServiceTarget> {
    Class<? extends Service> clz;

    @Override
    protected Class<? extends Service> getServiceClass() {
        return clz;
    }

    public NotDeterminedServiceTarget serviceClass(Class<? extends Service> clz) {
        this.clz = clz;
        return this;
    }
}

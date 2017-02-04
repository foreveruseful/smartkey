package link.anyauto.smartkey.sdks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by discotek on 17-2-4.
 * Move the routine methods from every generated IntentBuilder class to a base class here to reduce methods generated.
 */
public abstract class BaseIntentKeyMapper<B, S> implements IntentKeyMapper {

    /**
     * The wrapping smart object
     */
    protected S smart;

    /**
     * Get the wrapping smart object.
     * @return
     *  the wrapping smart object
     */
    public S getSmart() {
        return smart;
    }

    /**
     * Replace the wrapping smart object with a new one.
     * @param smart
     *  the smart object, should not be null
     * @return
     *  this
     */
    public B replaceSmart(@NonNull S smart) {
        if(smart == null)
            throw new RuntimeException("Smart wrapper object should not be null.");

        this.smart = smart;
        return (B) this;
    }

    /**
     * <pre>
     * Fill the wrapping smart object with data from the given source Intent.
     * </pre>
     * @param source
     *  the intent as data source.
     * @return
     */
    public abstract B fillFromSource(Intent source);

    /**
     * Fill the wrapping smart object with data from the given source bundle.
     * @param source
     *  the bundle as data source
     * @return
     *  this
     */
    public B fillFromSource(Bundle source) {
        if (source == null) return (B) this;
        return fillFromSource(new Intent().putExtras(source));
    }

    /**
     * Write data from the wrapping smart object into a bundle.
     * @return
     *  the bundle that holds the data from the wrapping smart object
     */
    public Bundle buildBundle() {
        return buildIntent().getExtras();
    }
}

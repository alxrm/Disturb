package rm.com.disturb.storage;

import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by alex
 */

@Qualifier //
@Retention(RUNTIME) //
public @interface Password {
}

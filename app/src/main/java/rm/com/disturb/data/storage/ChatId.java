package rm.com.disturb.data.storage;

import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by alex
 */

@Qualifier //
@Retention(RUNTIME) //
public @interface ChatId {
}

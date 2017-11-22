package rm.com.disturb.inject;

import android.graphics.Typeface;
import dagger.Binds;
import dagger.Module;
import rm.com.disturb.data.resource.ContactResource;
import rm.com.disturb.data.resource.Resource;
import rm.com.disturb.data.resource.TypefaceResource;

/**
 * Created by alex
 */

@Module //
public abstract class ResourceModule {

  @Binds //
  abstract Resource<String, String> provideContactResource(ContactResource contactResource);

  @Binds
  abstract Resource<Typeface, String> provideTypefaceResource(TypefaceResource typefaceResource);
}

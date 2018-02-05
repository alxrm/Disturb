package rm.com.disturb.inject;

import dagger.Binds;
import dagger.Module;
import rm.com.disturb.data.resource.ContactResource;
import rm.com.disturb.data.resource.Resource;

/**
 * Created by alex
 */

@Module //
public abstract class ResourceModule {

  @Binds //
  abstract Resource<String, String> provideContactResource(ContactResource contactResource);
}

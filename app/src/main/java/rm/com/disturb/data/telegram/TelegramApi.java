package rm.com.disturb.data.telegram;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rm.com.disturb.data.telegram.response.MessageResponse;
import rm.com.disturb.data.telegram.response.TelegramResponse;

/**
 * Created by alex
 */

public interface TelegramApi {
  String KEY_CHAT_ID = "chat_id";
  String KEY_MESSAGE_ID = "message_id";
  String KEY_TEXT = "text";

  @GET("sendMessage?parse_mode=Markdown") //
  Call<MessageResponse> sendMessage(@QueryMap Map<String, String> params);

  @GET("deleteMessage") //
  Call<TelegramResponse> deleteMessage(@QueryMap Map<String, String> params);

  @GET("editMessageText") //
  Call<MessageResponse> editMessage(@QueryMap Map<String, String> params);
}

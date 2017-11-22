package rm.com.disturb.data.telegram;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rm.com.disturb.data.telegram.model.Chat;
import rm.com.disturb.data.telegram.model.FileData;
import rm.com.disturb.data.telegram.model.Message;
import rm.com.disturb.data.telegram.model.TelegramResponse;

/**
 * Created by alex
 */

public interface TelegramApi {
  String TELEGRAM_BASE_URL = "https://api.telegram.org/bot";
  String TELEGRAM_FILE_URL = "https://api.telegram.org/file/bot";
  String KEY_CHAT_ID = "chat_id";
  String KEY_MESSAGE_ID = "message_id";
  String KEY_TEXT = "text";

  @GET("sendMessage?parse_mode=Markdown") //
  Call<TelegramResponse<Message>> sendMessage(@QueryMap Map<String, String> params);

  @GET("deleteMessage") //
  Call<TelegramResponse> deleteMessage(@QueryMap Map<String, String> params);

  @GET("editMessageText?parse_mode=Markdown") //
  Call<TelegramResponse<Message>> editMessage(@QueryMap Map<String, String> params);

  @GET("getFile") //
  Call<TelegramResponse<FileData>> file(@Query("file_id") String fileId);

  @GET("getChat") //
  Call<TelegramResponse<Chat>> chat(@Query("chat_id") String chatId);
}

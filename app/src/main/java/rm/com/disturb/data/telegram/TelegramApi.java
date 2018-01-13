package rm.com.disturb.data.telegram;

import io.reactivex.Single;
import java.util.Map;
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

  @GET("/sendMessage?parse_mode=Markdown") //
  Single<TelegramResponse<Message>> sendMessage(@QueryMap Map<String, String> params);

  @GET("/deleteMessage") //
  Single<TelegramResponse> deleteMessage(@QueryMap Map<String, String> params);

  @GET("/editMessageText?parse_mode=Markdown") //
  Single<TelegramResponse<Message>> editMessage(@QueryMap Map<String, String> params);

  @GET("/getFile") //
  Single<TelegramResponse<FileData>> file(@Query("file_id") String fileId);

  @GET("/getChat") //
  Single<TelegramResponse<Chat>> chat(@Query("chat_id") String chatId);
}

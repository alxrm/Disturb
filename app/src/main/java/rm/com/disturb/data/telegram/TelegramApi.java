package rm.com.disturb.data.telegram;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rm.com.disturb.data.telegram.response.MessageResponse;
import rm.com.disturb.data.telegram.response.TelegramResponse;

/**
 * Created by alex
 */

public interface TelegramApi {

  @GET("sendMessage?parse_mode=Markdown") //
  Call<MessageResponse> sendMessage( //
      @Query("chat_id") String chatId, //
      @Query("text") String text //
  );

  @GET("deleteMessage") //
  Call<TelegramResponse> deleteMessage( //
      @Query("chat_id") String chatId, //
      @Query("message_id") String messageId //
  );

  @GET("editMessageText") //
  Call<MessageResponse> editMessage( //
      @Query("chat_id") String chatId, //
      @Query("message_id") String messageId, //
      @Query("text") String text //
  );
}

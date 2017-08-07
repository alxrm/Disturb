package rm.com.disturb.telegram;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
      @Query("message_id") int messageId //
  );

  @GET("editMessageText") //
  Call<MessageResponse> editMessage( //
      @Query("chat_id") String chatId, //
      @Query("message_id") int messageId, //
      @Query("text") String text //
  );
}

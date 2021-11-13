package edu.cnm.deepdive.interviewprep.service;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import edu.cnm.deepdive.interviewprep.model.Game;
import edu.cnm.deepdive.interviewprep.model.dao.GameDao;
import edu.cnm.deepdive.interviewprep.service.InterviewPrepDatabase.Converters;
import java.util.Date;

@Database(
    entities = {Game.class},
    views = {},
    version = 1,
    exportSchema = true
) //when you uninstall an app, it wipes out the database too.
@TypeConverters({Converters.class})
public abstract class InterviewPrepDatabase extends RoomDatabase {

  private static Application context;

  public static void setContext(Application context) {
    InterviewPrepDatabase.context = context;
  }

  public static InterviewPrepDatabase getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public abstract GameDao getGameDao();

  //public abstract GuessDao getGuessDao();

  private static class InstanceHolder {

    private static final InterviewPrepDatabase INSTANCE = Room.databaseBuilder(
            context, InterviewPrepDatabase.class, "interviewprep-db")
        .build();
  }

  public static class Converters {

    @TypeConverter
    public static Long dateToLong(Date value) {
      return (value != null) ? value.getTime() : null;
    }

    @TypeConverter
    public static Date longToDate(Long value) {
      return (value != null) ? new Date(value) : null;
    }
  }
}

package com.powerup.zombie.providers.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBHelper class.
 * @author Adriano Pereira Rezende
 */
public class DBHelper extends SQLiteOpenHelper {
	
	// Nome da tabela que irá conter os registros.  
    private static final String SCORE_TABLE = "score";
	
	public DBHelper(Context context, String dbName, CursorFactory factory,
			int version) {
		super(context, dbName, factory, version);
	}
	
	/* O método onCreate é chamado quando o provider é executado pela 
     * primeira vez, e usado para criar as tabelas no database 
     */ 
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + SCORE_TABLE + " (" +   
                Score.SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +   
                Score.TOTAL + " INTEGER," + 
                Score.TIME + " INTEGER," +
                Score.ZOMBIE + " INTEGER," +   
                Score.WEREWOLF + " INTEGER," + 
                Score.FRANKEINSTEIN + " INTEGER," + 
                Score.VAMPIRE + " INTEGER," +   
                Score.MUMMY + " INTEGER," + 
                Score.INNOCENT + " INTEGER" + ");");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}

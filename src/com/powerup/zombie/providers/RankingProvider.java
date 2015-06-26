package com.powerup.zombie.providers;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.powerup.zombie.providers.helpers.DBHelper;
import com.powerup.zombie.providers.helpers.Score;

/**
 * RankingProvider class.
 * @author Adriano Pereira Rezende
 */
public class RankingProvider extends ContentProvider {
	
	// Authority do nosso provider, a ser usado nas Uris.  
    public static final String AUTHORITY = "com.powerup.zombie";
    
    // Nome do arquivo que ir‡ conter o banco de dados.  
    private static final String DATABASE_NAME = "zombiesfromhell.db";
    
    // Versao do banco de dados.  
    // Este valor Ž importante pois Ž usado em futuros updates do DB.  
    private static final int  DATABASE_VERSION = 2;  
      
    // Nome da tabela que ir‡ conter os registros.  
    private static final String SCORE_TABLE = "score";
  
    // 'Id' da Uri referente ao score do jogador.  
    private  static final int SCORE = 1;
  
    // Tag usada para imprimir os logs.  
    public static final String TAG = "RankingProvider";  
      
    // Inst‰ncia da classe utilit‡ria  
    private DBHelper mHelper;  
      
    // Uri matcher - usado para extrair informa�›es das Uris  
    private static final UriMatcher mMatcher;  
  
    private static HashMap<String,String> mProjection; 
    
	
	//public static final Uri CONTENT_URI = Uri.parse("content://com.powerup.zombie");
	
	static {  
        mProjection = new HashMap<String, String>();  
        mProjection.put(Score.SCORE_ID, Score.SCORE_ID);  
        mProjection.put(Score.TOTAL, Score.TOTAL);
        mProjection.put(Score.TIME, Score.TIME);
        mProjection.put(Score.ZOMBIE, Score.ZOMBIE);  
        mProjection.put(Score.FRANKEINSTEIN, Score.FRANKEINSTEIN);
        mProjection.put(Score.WEREWOLF, Score.WEREWOLF);
        mProjection.put(Score.MUMMY, Score.MUMMY);  
        mProjection.put(Score.VAMPIRE, Score.VAMPIRE);
        mProjection.put(Score.INNOCENT, Score.INNOCENT);
    }  
      
    static {  
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);  
        mMatcher.addURI(AUTHORITY, SCORE_TABLE, SCORE);
    }
    
    /*
	 * (non-Javadoc)
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
		mHelper = new DBHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION); 
        return true;
	}
    
    /*
     * (non-Javadoc)
     * @see android.content.ContentProvider#delete(android.net.Uri, java.lang.String, java.lang.String[])
     */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		 
		SQLiteDatabase db = mHelper.getWritableDatabase();  
        int count;  
        switch (mMatcher.match(uri)) {  
            case SCORE:  
                count = db.delete(SCORE_TABLE, selection, selectionArgs);  
                break;  
            default:  
                throw new IllegalArgumentException(  
                  "URI desconhecida " + uri);  
        }  
	       
        getContext().getContentResolver().notifyChange(uri, null);  
        return count;  
	}

	/*
	 * (non-Javadoc)
	 * @see android.content.ContentProvider#getType(android.net.Uri)
	 */
	@Override
	public String getType(Uri uri) {
		
		switch (mMatcher.match(uri)) {  
        case SCORE:  
            return Score.CONTENT_TYPE;  
        default:  
            throw new IllegalArgumentException(  
                "URI desconhecida " + uri);  
		} 
	}

	/*
	 * (non-Javadoc)
	 * @see android.content.ContentProvider#insert(android.net.Uri, android.content.ContentValues)
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		switch (mMatcher.match(uri)) {  
        case SCORE:  
            SQLiteDatabase db = mHelper.getWritableDatabase();  
            long rowId = db.insert(SCORE_TABLE, Score.TOTAL, values);  
            if (rowId > 0) {  
                Uri noteUri = ContentUris.withAppendedId(  
                             Score.CONTENT_URI, rowId);  
                getContext().getContentResolver().notifyChange(  
                             noteUri, null);  
                return noteUri;  
            }  
        default:  
            throw new IllegalArgumentException(  
                    "URI desconhecida " + uri);  
		}  

	}
	
	/*
	 * (non-Javadoc)
	 * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		// Aqui usaremos o SQLiteQueryBuilder para construir  
        // a query que ser‡ feito ao DB, retornando um cursor  
        // que enviaremos ˆ aplica�‹o.  
        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();  
        SQLiteDatabase database = mHelper.getReadableDatabase();  
        Cursor cursor;  
        switch (mMatcher.match(uri)) {  
            case SCORE:  
                // O Builer receber‡ dois parametros: a tabela  
                // onde ser‡ feita a busca, e uma projection -   
                // que nada mais Ž que uma HashMap com os campos  
                // que queremos recuperar do banco de dados.  
                builder.setTables(SCORE_TABLE);  
                builder.setProjectionMap(mProjection);  
                break;  
   
            default:  
                throw new IllegalArgumentException(  
                      "URI desconhecida " + uri);  
        }  

        cursor = builder.query(database, projection, selection,   
        		selectionArgs, null, null, sortOrder);  

        cursor.setNotificationUri(getContext().getContentResolver(), uri);  
        return cursor; 
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.content.ContentProvider#update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[])
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
        SQLiteDatabase db = mHelper.getWritableDatabase();  
        int count;  
        switch (mMatcher.match(uri)) {  
            case SCORE:  
                count = db.update(SCORE_TABLE, values, selection, selectionArgs);  
                break;     
            default:  
                throw new IllegalArgumentException(  
                        "URI desconhecida " + uri);  
        }  
   
        getContext().getContentResolver().notifyChange(uri, null);  
        return count;
	}

}

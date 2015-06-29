package com.adrianoprezende.zombies.providers.helpers;

import android.net.Uri;
import android.provider.BaseColumns;

import com.adrianoprezende.zombies.providers.RankingProvider;

/**
 * Score class that represents the domain entity of the game. 
 * Each attribute represents a column of the table named Score.
 * @author Adriano Pereira Rezende
 */
public class Score implements BaseColumns {
	
	public static final Uri CONTENT_URI = Uri.parse("content://"  
            + RankingProvider.AUTHORITY + "/score");  

	public static final String CONTENT_TYPE =   
	        "vnd.android.cursor.dir/" + RankingProvider.AUTHORITY;  
	
	public static final String SCORE_ID = "_id";  
	
	public static final String TOTAL = "total";
	
	public static final String TIME = "time";
	
	public static final String ZOMBIE = "zombie";  
	
	public static final String FRANKEINSTEIN = "frankeinstein";
	
	public static final String VAMPIRE = "vampire";
	
	public static final String MUMMY = "mummy";  
	
	public static final String WEREWOLF = "werewolf";
	
	public static final String INNOCENT = "innocent";
	
}

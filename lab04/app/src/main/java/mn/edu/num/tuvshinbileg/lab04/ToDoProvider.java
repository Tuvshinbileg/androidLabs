package mn.edu.num.tuvshinbileg.lab04;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class ToDoProvider extends ContentProvider {
//package name
    public static final String AUTHORITY="mn.edu.num.tuvshinbileg.lab04";
    public static final String PATH_TODO_LIST="TODO_LIST";
    public static final String PATH_TODO_PLACE ="TODO_LIST_FROM_PLACE";
    public static final String PATH_TODO_COUNT ="TODOS_COUNT";

    //query
    public static final Uri CONTENT_URI_1= Uri.parse("content://"+AUTHORITY+"/"+PATH_TODO_LIST);
    public static final Uri CONTENT_URI_2=Uri.parse("content://"+AUTHORITY+"/"+ PATH_TODO_PLACE);
    public static final Uri CONTENT_URI_3=Uri.parse("content://"+AUTHORITY+"/"+ PATH_TODO_COUNT);
    //code
    /**
     * uri matcher doorh coder shalgana
     */
    public static final int TODOS_LIST=1;
    public static final int TODOS_FROM_SPECIFIC_PLACE=2;
    public static final int TODOS_COUNT =3;

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DatabaseHelper dbHelper;


    static {
        matcher.addURI(AUTHORITY, PATH_TODO_LIST, TODOS_LIST);
        matcher.addURI(AUTHORITY, PATH_TODO_PLACE,TODOS_FROM_SPECIFIC_PLACE);
        matcher.addURI(AUTHORITY, PATH_TODO_COUNT, TODOS_COUNT);
    }

    public static final String MIME_TYPE_1 = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+ "vnd.com.lab05.todos.";
    public static final String MIME_TYPE_2 = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+ "vnd.com.lab05.todos.place";
    public static final String MIME_TYPE_3 = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+ "vnd.com.lab05.todocount";


    @Override
    public boolean onCreate() {
        dbHelper=new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

            Cursor cursor=null;
            switch (matcher.match(uri)){
                case TODOS_LIST:cursor=dbHelper.getCursorForAll();break;
                case TODOS_FROM_SPECIFIC_PLACE:cursor=dbHelper.getCursorForSpecificPlase(strings[0]);
                case TODOS_COUNT:cursor=dbHelper.getCount();break;

                default:cursor=null;break;
            }
        return cursor;
    }

    /**
     *
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (matcher.match(uri)){
            case TODOS_LIST: return MIME_TYPE_1;
            case TODOS_FROM_SPECIFIC_PLACE: return MIME_TYPE_2;
            case TODOS_COUNT: return MIME_TYPE_3;
        }
        return null;
    }

    @Nullable
    @Override

    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues)throws UnsupportedOperationException {
    Uri returnUri=null;
        switch (matcher.match(uri))
        {
            case TODOS_LIST:returnUri=insertToDo(uri,contentValues);
            default: new UnsupportedOperationException("insert operation not supported"); break;
        }

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) throws UnsupportedOperationException{
        int deleteCount=-1;
        switch (matcher.match(uri)){
            case TODOS_LIST:deleteCount=delete(s,strings);break;

            default:new UnsupportedOperationException("delete operation not supported"); break;
        }
        return deleteCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) throws UnsupportedOperationException {
        int updateCount=-1;
        switch (matcher.match(uri)){
            case TODOS_LIST:updateCount=update(contentValues,s,strings); break;

            default:new UnsupportedOperationException("update operation not supported"); break;
        }
        return updateCount;
    }
    private Uri insertToDo(Uri uri, ContentValues contentValues){
        long id = dbHelper.insert(contentValues);
        getContext().getContentResolver().notifyChange(uri,null);
        return Uri.parse("content://"+AUTHORITY+"/"+ PATH_TODO_LIST +"/"+id);
    }

    private int update(ContentValues contentValues, String whereClause, String [] strings){
        return dbHelper.update(contentValues,whereClause,strings);
    }
    private int delete(String whereClause, String [] whereValues){
        return dbHelper.delete(whereClause,whereValues);
    }
}

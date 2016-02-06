package barqsoft.footballscores;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lucagrazioli on 06/02/16.
 */
public class WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private int mAppWidgetId;
    private Cursor data;

    private static final String[] MATCH_COLUMNS = {

            DatabaseContract.scores_table.MATCH_ID,
            DatabaseContract.scores_table.LEAGUE_COL,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.DATE_COL,
            DatabaseContract.scores_table.TIME_COL
    };
    // these indices must match the projection
    private static final int INDEX_MATCH_ID = 0;
    private static final int INDEX_LEAGUE_COL = 1;
    private static final int INDEX_HOME_COL = 2;
    private static final int INDEX_AWAY_COL = 3;
    private static final int INDEX_DATE_COL = 4;
    private static final int INDEX_TIME_COL = 5;


    public WidgetViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if(data != null){
            data.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        Uri dateuri=  DatabaseContract.scores_table.buildScoreWithDate();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date();

        data = mContext.getContentResolver().
                query(dateuri, MATCH_COLUMNS, null,
                        new String[]{df.format(dateobj )}, DatabaseContract.scores_table.DATE_COL + " ASC");
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if(data != null){
            data.close();
            data = null;
        }
    }

    @Override
    public int getCount() {
        if(data == null)
            return 0;
        else
            return data.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                data == null || !data.moveToPosition(position)) {
            return null;
        }

        RemoteViews views = new RemoteViews(mContext.getPackageName(),
                R.layout.single_score_widget);

        //int matchId = data.getInt(INDEX_MATCH_ID);
        int matchHomeIcon = (Utilies.getTeamCrestByTeamName(
                data.getString(INDEX_HOME_COL)));
        String descriptionHome = data.getString(INDEX_HOME_COL);

        int matchAwayIcon = (Utilies.getTeamCrestByTeamName(
                data.getString(INDEX_AWAY_COL)));
        String descriptionAway = data.getString(INDEX_AWAY_COL);
        String matchTime = data.getString(INDEX_TIME_COL);


        views.setImageViewResource(R.id.widget_home_crest, matchHomeIcon);
        views.setImageViewResource(R.id.widget_away_crest, matchAwayIcon);
        // Content Descriptions for RemoteViews were only added in ICS MR1

        views.setTextViewText(R.id.widget_home_name, descriptionHome);

        views.setTextViewText(R.id.widget_date, matchTime);
        views.setTextViewText(R.id.widget_away_name, descriptionAway);

        //final Intent fillInIntent = new Intent();

        //Uri dateuri=  DatabaseContract.scores_table.buildScoreWithDate();
        //fillInIntent.setData(dateuri);
        //views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(mContext.getPackageName(), R.layout.scores_list_item);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (data.moveToPosition(position))
            return data.getLong(INDEX_DATE_COL);
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}

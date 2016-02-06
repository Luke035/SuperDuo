package barqsoft.footballscores;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import barqsoft.footballscores.service.WidgetService;

/**
 * Created by lucagrazioli on 06/02/16.
 */
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for(int id: appWidgetIds){
            Intent intent = new Intent(context, WidgetService.class);
            // Add the app widget ID to the intent extras.
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.app_widget_layout);

            // This is how you populate the data.
            view.setRemoteAdapter(id, R.id.widget_scores_list, intent);
            appWidgetManager.updateAppWidget(id, view);
        }
    }
}

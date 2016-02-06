package barqsoft.footballscores.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.WidgetViewsFactory;

/**
 * Created by lucagrazioli on 06/02/16.
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetViewsFactory(this.getApplicationContext(), intent);

    }
}

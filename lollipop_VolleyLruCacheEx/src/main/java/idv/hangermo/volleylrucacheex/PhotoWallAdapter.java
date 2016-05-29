package idv.hangermo.volleylrucacheex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class PhotoWallAdapter extends BaseAdapter {
    private Context context;
    private RequestQueue mQueue;
    private ImageLoader imageLoader;

    public PhotoWallAdapter(Context context) {
        this.context = context;
        mQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(mQueue, new BitmapCache());
    }

    @Override
    public int getCount() {
        return Images.imageUrls.length;
    }

    @Override
    public Object getItem(int position) {
        return Images.imageUrls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String url = (String) getItem(position);
        final ViewHolder gridViewImageHolder;
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.gridview_photo, null);
            gridViewImageHolder = new ViewHolder();
            gridViewImageHolder.photo = (NetworkImageView) view.findViewById(R.id.nivPhoto);
            view.setTag(gridViewImageHolder);
        } else {
            gridViewImageHolder = (ViewHolder) view.getTag();
        }
        gridViewImageHolder.photo.setImageUrl(url, imageLoader);
        return view;
    }

    static class ViewHolder {
        NetworkImageView photo;
    }


}

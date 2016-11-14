package android.cs399_project3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CameraAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Camera> list = new ArrayList<Camera>();
    private Context context;
    private MainGlobal mainGlobal;

    public CameraAdapter(ArrayList<Camera> list, Context context) {
        this.list = list;
        this.context = context;
        mainGlobal = ((MainGlobal) context.getApplicationContext()); // Get global data
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.camera_list_element, parent, false);
        }

        // Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).getName());

        // Handle buttons and add onClickListeners
        RelativeLayout cameraItem = (RelativeLayout)view.findViewById(R.id.camera_item);
        ImageButton deleteBtn = (ImageButton)view.findViewById(R.id.delete_btn);
        ImageButton settingsBtn = (ImageButton)view.findViewById(R.id.settings_btn);

        // Determine camera status
        ImageView cameraStatus = (ImageView)view.findViewById(R.id.camera_status);
        int status;
        switch (list.get(position).getStatus()) {
            case 0: status = android.R.drawable.presence_online;
                    break;
            case 1: status = android.R.drawable.presence_busy;
                    break;
            case 2: status = android.R.drawable.presence_offline;
                    break;
            default: status = android.R.drawable.presence_away;
                    break;
        }
        cameraStatus.setBackgroundResource(status);

        // Add event listeners to the elements of the layout
        cameraItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mainGlobal.setCurrCamIndex(position);
                Intent intent = new Intent(context, CameraActivity.class);
                context.startActivity(intent);
                notifyDataSetChanged();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mainGlobal.removeCameraAt(position);
                notifyDataSetChanged();
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mainGlobal.setCurrCamIndex(position);
                Intent intent = new Intent(context, CameraSettingsActivity.class);
                context.startActivity(intent);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}

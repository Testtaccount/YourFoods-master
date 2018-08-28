package com.trannguyentanthuan2903.yourfoods.comment.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.comment.model.Comment;
import com.trannguyentanthuan2903.yourfoods.comment.view.ViewCommentsFragment;
import com.trannguyentanthuan2903.yourfoods.profile_user.model.User;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 11/3/2017.
 */

public class CommentListAdapter extends ArrayAdapter<Comment> {

    private static final String TAG = "CommentListAdapter";

    private LayoutInflater mInflater;
    private int layoutResource;
    private Context mContext;
    private String mIdStore;

    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private CommentListPresenter presenter;
    private ViewCommentsFragment fragment;

    public CommentListAdapter(Context context, int resource,
                              List<Comment> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        layoutResource = resource;
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        presenter = new CommentListPresenter();
        fragment = new ViewCommentsFragment();
    }

    private static class ViewHolder {
        TextView comment, username, timestamp, reply, likes;
        CircleImageView profileImage;
        ImageView delete;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder holder;
        getItent();

        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            holder.comment = (TextView) convertView.findViewById(R.id.comment);
            holder.username = (TextView) convertView.findViewById(R.id.comment_username);
            holder.timestamp = (TextView) convertView.findViewById(R.id.comment_time_posted);
            holder.reply = (TextView) convertView.findViewById(R.id.comment_reply);
            holder.delete = (ImageView) convertView.findViewById(R.id.comment_delete);
            holder.likes = (TextView) convertView.findViewById(R.id.comment_likes);
            holder.profileImage = (CircleImageView) convertView.findViewById(R.id.comment_profile_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //set the comment
        holder.comment.setText(getItem(position).getComment());

        //set the timestamp difference
        String timestampDifference = getTimestampDifference(getItem(position));
        if (!timestampDifference.equals("0")) {
            holder.timestamp.setText(timestampDifference + " days");
        } else {
            holder.timestamp.setText("Today");
        }


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(Constain.USERS)
                .orderByChild(Constain.ID_USER)
                .equalTo(getItem(position).getUser_id());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    holder.username.setText(
                            singleSnapshot.getValue(User.class).getUserName());

                    Glide.with(mContext)
                            .load(singleSnapshot.getValue(User.class).getLinkPhotoUser()).error(R.drawable.avatar).into(
                            holder.profileImage);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });

        holder.likes.setVisibility(View.GONE);
        holder.reply.setVisibility(View.GONE);
        //delete comment
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setTitle("Warning");
                builder1.setIcon(R.drawable.ic_delete);
                builder1.setMessage("Do you want to delete this Comment ?");
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.removeComment(mIdStore,getItem(position).getKey_cmt());
                        fragment.setupFirebaseAuth();
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "Deleted Comment ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder1.show();
            }
        });

        // invisible delete
        if (!getItem(position).getUser_id().equals(mAuth.getCurrentUser().getUid())){
            holder.delete.setVisibility(View.GONE);
        }else {
            holder.delete.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    /**
     * Returns a string representing the number of days ago the post was made
     *
     * @return
     */
    private String getTimestampDifference(Comment comment) {
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CANADA);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));//google 'android list of timezones'
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        final String photoTimestamp = comment.getDate_created();
        try {
            timestamp = sdf.parse(photoTimestamp);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24)));
        } catch (ParseException e) {
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage());
            difference = "0";
        }
        return difference;
    }

    private void getItent() {
        Intent intent = ((Activity) mContext).getIntent();
        mIdStore = intent.getStringExtra("id_store");
    }
}
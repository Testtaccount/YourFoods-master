package com.trannguyentanthuan2903.yourfoods.comment.model;

import com.google.firebase.database.DatabaseReference;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.HashMap;

/**
 * Created by Administrator on 11/4/2017.
 */

public class CommentListSubmitter {
    private DatabaseReference mData;

    public CommentListSubmitter(DatabaseReference mData) {
        this.mData = mData;
    }

    public void removeComment(String idStore, String idCmt) {
        mData.child(Constain.STORES).child(idStore).child(Constain.COMMENTS).child(idCmt).removeValue();
    }

    public void addComment(String idStore, String idUser, String comment, String createDate) {
        HashMap<String, Object> commentStore = new HashMap<>();
        commentStore.put(Constain.USER_ID, idUser);
        commentStore.put(Constain.COMMENT, comment);
        commentStore.put(Constain.CREATE_DATE,createDate);
        mData.child(Constain.STORES).child(idStore).child(Constain.COMMENTS).child(mData.push().getKey()).setValue(commentStore);
    }
}

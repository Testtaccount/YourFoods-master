package com.trannguyentanthuan2903.yourfoods.comment.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trannguyentanthuan2903.yourfoods.comment.model.CommentListSubmitter;

/**
 * Created by Administrator on 11/4/2017.
 */

public class CommentListPresenter {
    private DatabaseReference mData;
    private CommentListSubmitter submitter;

    public CommentListPresenter() {
        mData = FirebaseDatabase.getInstance().getReference();
        submitter = new CommentListSubmitter(mData);
    }
    public void removeComment (String idStore, String idCmt){
        submitter.removeComment(idStore, idCmt);
    }
    public void addComment (String idStore, String idUser, String comment, String createDate){
        submitter.addComment(idStore, idUser,comment,createDate);
    }

}
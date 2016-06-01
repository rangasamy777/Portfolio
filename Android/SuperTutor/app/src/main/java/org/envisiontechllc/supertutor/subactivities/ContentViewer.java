package org.envisiontechllc.supertutor.subactivities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.data.SQLManager;
import org.envisiontechllc.supertutor.internal.machine.MachineAlgorithm;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.Subject;
import org.envisiontechllc.supertutor.internal.wrappers.Topic;
import org.envisiontechllc.supertutor.settings.AppContext;

import java.io.File;
import java.util.List;

/**
 * Created by EmileBronkhorst on 21/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ContentViewer extends Activity {

    private final float BLACK_ALPHA = 0.5f, MIN_SCALE = 0.1f;
    private ScaleGestureDetector scaleDetector;
    private Matrix scaleMatrix;

    private AppContext appContext;
    private ViewFlipper flipper;
    private TextView subjectTitle;
    private LinearLayout titleBackground;
    private ImageView image;

    private Subject subject;
    private float lastTouchedX;

    /**
     * Default constructor for the super class
     * @param instance the stored instance for the intent
     */
    @Override
    public void onCreate(Bundle instance){
        super.onCreate(instance);
        setContentView(R.layout.subject_viewer);

        appContext = AppContext.getContext();
        scaleMatrix = new Matrix();
        scaleDetector = new ScaleGestureDetector(this, new ScaleListener());

        initItems();
    }

    /**
     * Initialising the components of the view
     */
    public void initItems(){
        flipper = (ViewFlipper)findViewById(R.id.subject_viewer);
        subjectTitle = (TextView)findViewById(R.id.subject_viewer_title);
        titleBackground = (LinearLayout)findViewById(R.id.subject_viewer_background);
        titleBackground.setAlpha(BLACK_ALPHA);

        String subjectName = getIntent().getExtras().getString("subjectName");
        if(subjectName != null){
            subject = appContext.getLibrary().getSubjectForName(subjectName);
            subjectTitle.setText(subject.getSubjectName());
            if(subject != null){
                List<Topic> topicsList = MachineAlgorithm.organiseTopics(appContext.getCurrentUser().getLearnerType(), subject);
                for(Topic topic: topicsList){
                    initTopicView(flipper, topic);
                }
                flipper.setDisplayedChild(subject.getBookmark());
            }
        }
    }

    /**
     * Populates a new view within the view flipper, consisting of the content for the topic and a bookmark option
     * @param flipper the ViewFlipper instance
     * @param topic the Topic for which to populate the view with
     */
    private void initTopicView(ViewFlipper flipper, final Topic topic){
        LayoutInflater inflater = LayoutInflater.from(this);
        View flipperChild = inflater.inflate(R.layout.flipper_child, null);

        if(flipperChild != null){
            image = (ImageView)flipperChild.findViewById(R.id.flipper_image);

            FloatingActionButton bookmark = (FloatingActionButton)flipperChild.findViewById(R.id.flipper_bookmark);
            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = appContext.getLibrary().getTopicIndex(topic);
                    subject.setBookmark(index);

                    new SQLManager(ContentViewer.this).updateBookmarkForSubject(subject);
                }
            });

            File imagePath = Utilities.getFileForName(subject.getSubjectName(), topic.getFileName());
            if(imagePath != null && imagePath.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath.getAbsolutePath());
                if(bitmap != null){
                    image.setImageBitmap(bitmap);
                }
            }

            flipper.addView(flipperChild);
        }
    }

    /**
     * Handles the touch events (i.e. when the user wants to turn the page)
     * @param evt The touch event which has occured
     * @return true if the flip motion was successful
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt){

        switch(evt.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastTouchedX = evt.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = evt.getX();
                boolean successPage = (lastTouchedX > currentX ? turnPage(flipper) : previousPage(flipper));
                return successPage;
        }
        scaleDetector.onTouchEvent(evt);

        return true;
    }

    /**
     * Navigates to the next page if there are any views left in the hierarchy
     * @param flipper the ViewFlipper instance
     * @return true if the flip was successful
     */
    private boolean turnPage(ViewFlipper flipper){
        if(flipper.getDisplayedChild() == subject.getTopics().size() - 1){
            return false;
        }
        flipper.setInAnimation(this, R.anim.in_from_right);
        flipper.setOutAnimation(this, R.anim.out_to_left);
        flipper.showNext();
        return true;
    }

    /**
     * Navigates to the previous page if there are any views left in the hierarchy
     * @param flipper the ViewFlipper instance
     * @return true if the flip was successful
     */
    private boolean previousPage(ViewFlipper flipper){
        if(flipper.getDisplayedChild() <= 0){
            return false;
        }
        flipper.setInAnimation(this, R.anim.in_from_left);
        flipper.setOutAnimation(this, R.anim.out_to_right);
        flipper.showPrevious();
        return true;
    }

    /**
     * Destroy the activity from the life cycle if back is pressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float tempScale = detector.getScaleFactor();

            tempScale = Math.max(MIN_SCALE, Math.min(tempScale, 5.0f));
            scaleMatrix.setScale(tempScale, tempScale);
            image.setImageMatrix(scaleMatrix);

            return true;
        }
    }
}

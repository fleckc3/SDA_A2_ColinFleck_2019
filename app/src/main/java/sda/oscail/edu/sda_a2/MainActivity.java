/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
implied.
* See the License for the specific language governing permissions
and
* limitations under the License.
*/

package sda.oscail.edu.sda_a2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


//main activity class opens the user interface of app
public class MainActivity extends AppCompatActivity {
    /**
     * code adapted from android docs described here:
     * https://developer.android.com/training/camera/photobasics
     */
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int EMAIL_ACTIVITY_RESULT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Receive intent from explicit intent activity
      /*  Intent intent = getIntent();
        String emailContent = intent.putExtra(OpenExplicitIntent. "keyName"); */
    }


    /***********
     * code adapted from android docs described here:
     * https://developer.android.com/training/camera/photobasics
     */
    //take picture intent
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    //opens the camera by calling above method
    public void openCamera(View view) {
        //do something here
        dispatchTakePictureIntent();

    }

    /****
     * code adapted for this method openGallery found here:
     * https://stackoverflow.com/questions/16928727/open-gallery-app-from-android-intent
     */
    //access gallery
    public void openGallery(View view) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /****
     * code adapted for this method openGallery found here:
     * https://developer.android.com/training/basics/firstapp/starting-activity.html
     */
    // open the email activity
    public void openExplicitIntent(View view) {
        // start the email activity
        Intent intent = new Intent(this, OpenExplicitIntent.class);
        startActivityForResult(intent, EMAIL_ACTIVITY_RESULT_CODE);
    }

    /***
     * this method onActivityResult() adapted from code found here:
     * https://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android
     */
    // Result passed back from explicit email activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that  it is OpenExplicitIntent with an ok result
        if(requestCode == EMAIL_ACTIVITY_RESULT_CODE) {
            if(resultCode == RESULT_OK) {

                // Get string data from the intent
                String returnContent = data.getStringExtra("keyName");

                //input the email content into the textview at bottom of screen
                TextView textView = (TextView) findViewById(R.id.emailContent);
                textView.setText(returnContent);
                Button sendButton = (Button) findViewById(R.id.button);
                sendButton.setEnabled(true);
            }
        }
    }

    public void openGmail (View view) {

        // finds email information from the textview
        TextView textView = (TextView) findViewById(R.id.emailContent);
        String emailContent = textView.getText().toString();

        /**
         * code was adapted from the information here:
         * https://javadevnotes.com/java-string-split-newline-examples
         */
        /*takes the above string object, splits it into parts seperated by
         * line breaks and puts it into an array
         */
        String[] content = emailContent.split("\\r?\\n");

        // created string objects out of the array for each part of the email
        String emailAddress = content[0];
        String emailSubject = content[1];
        String emailBody = content [2];

        /***
         * following code was adapted from here:
         * https://www.javatpoint.com/how-to-send-email-in-android-using-intent
         */
        //opens an email client of users choice and prepopulates with data sent back to mainactivity
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
        email.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        email.putExtra(Intent.EXTRA_TEXT, emailBody);

        //need this to prompts email client only
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));

    }
}

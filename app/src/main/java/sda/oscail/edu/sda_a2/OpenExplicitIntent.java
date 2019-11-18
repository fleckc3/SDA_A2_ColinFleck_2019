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
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;



public class OpenExplicitIntent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_explicit_intent);

        /***
         * following three chunks of code adapted from code found here:
         * https://stackoverflow.com/questions/8035107/how-to-set-cursor-position-in-edittext
         */
        // Set the cursor after the predefined text set for each edittext object
        EditText email = (EditText) findViewById(R.id.email);
        int emailCursor = email.length();
        Editable emailText = email.getText();
        Selection.setSelection(emailText, emailCursor);

        EditText subject = (EditText) findViewById(R.id.subject);
        int subjectCursor = subject.length();
        Editable subjectText = subject.getText();
        Selection.setSelection(subjectText, subjectCursor);

        EditText body = (EditText) findViewById(R.id.body);
        int bodyCursor = body.length();
        Editable bodyText = body.getText();
        Selection.setSelection(bodyText, bodyCursor);

        /***
         * code adapted from post found here:
         * https://stackoverflow.com/questions/2986387/multi-line-edittext-with-done-action-button
         */
        // Overides the multiline keyboard setting so the enter button is done typing button
        body.setImeOptions(EditorInfo.IME_ACTION_DONE);
        body.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }

    /***
     * majority of this entire method saveMessage() adapted from code found here:
     * https://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android
     */
    //saves edittext content and passes back to mainActivity
    public void saveMessage(View view) {
        // finds the edittext object by id
        EditText email = (EditText) findViewById(R.id.email);
        EditText subject = (EditText) findViewById(R.id.subject);
        EditText body = (EditText) findViewById(R.id.body);

        //Converts the edittext objects to their string values
        String emailText = email.getText().toString();
        String subjectText = subject.getText().toString();
        String bodyText = body.getText().toString();


        /***
         * code adapted from website found here:
         * https://beginnersbook.com/2013/12/java-string-replace-replacefirst-replaceall-method-examples/
         */
        //Change text from Compose: to Content:
        String updatedBodyText = bodyText.replaceFirst("Compose: ", "Content: ");

        // combines all string values into one string object
        String fullText = emailText + "\n" + subjectText + "\n" + updatedBodyText + "\n";

        // add string to intent to pass back to MainActivity
        Intent intent = new Intent();
        intent.putExtra("keyName", fullText);
        setResult(RESULT_OK, intent);
        finish();
    }
}

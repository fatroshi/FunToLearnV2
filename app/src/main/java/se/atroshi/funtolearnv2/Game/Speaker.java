package se.atroshi.funtolearnv2.Game;

import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

import se.atroshi.funtolearnv2.Controllers.Navigation;
import se.atroshi.funtolearnv2.MainActivity;

/**
 * Created by Farhad on 03/01/16.
 *
 * url: http://code.tutsplus.com/tutorials/use-text-to-speech-on-android-to-read-out-incoming-messages--cms-22524
 */
public class Speaker implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {

    private final String TAG = "Speaker";
    private TextToSpeech tts;
    private MainActivity mainActivity;
    private boolean allowedToSpeak = false;

    public Speaker(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.tts = new TextToSpeech(this.mainActivity, this);
    }

    @Override
    public void onInit(int status) {

        //TextToSpeech is successfully initialized
        if(status == TextToSpeech.SUCCESS){
            // Set speech language
            int language = tts.setLanguage(Locale.US);
            // Check if language is supported on the device
            if(language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED){
                showToast("Language is not supported");
            }else{
                // Ready to speak
                tts.setSpeechRate(0);
                allowedToSpeak = true;
                //showToast("Allowed to speak now");
            }
        }else{
            showToast("TextToSpeech Initialization Failed");
            allowedToSpeak = false;
        }
    }

    public void showToast(String msg){
        Toast.makeText(this.mainActivity,msg,Toast.LENGTH_SHORT).show();
    }

    public void speak(String speech){
        if(speech.length() != 0 && allowedToSpeak == true){
            tts.speak(speech,TextToSpeech.QUEUE_ADD,null,null);
            //showToast(speech);
        }else{
            showToast("Cant speak..." + speech);
        }
    }

    public void onDestroy(){
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
    }

    public void speakOut(){
        if(Navigation.getSelectedCategoryName() == null){
            speak("Please select a category from: \n \"Learn new words\"");
        }else{
            // Find the item
            Item item = Navigation.getGame().getItem();
            speak(", Find the " + item.getItemName());
        }
    }

    /**
     * Called when an utterance has been synthesized.
     *
     * @param utteranceId the identifier of the utterance.
     */
    @Override
    public void onUtteranceCompleted(String utteranceId) {
        allowedToSpeak = true;
    }
}

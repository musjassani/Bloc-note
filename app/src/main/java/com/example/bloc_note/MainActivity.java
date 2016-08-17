package com.example.bloc_note;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private View editionLayout;
    private View smileysLayout;
    private Button btnGras;
    private Button btnItalique;
    private Button btnSouligne;
    private Button btnCacher;
    private Button btnSourire;
    private Button btnRire;
    private Button btnClinDOeil;
    private RadioGroup colorRadioGroup;
    private RadioButton radioNoir;
    private RadioButton radioBleu;
    private RadioButton radioRouge;
    private EditText edition;
    private TextView previsualisation;
    private Html.ImageGetter imageGetter;
    private TextWatcher textWatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editionLayout = (View) findViewById(R.id.editionLayout);
        smileysLayout = (View) findViewById(R.id.smileysLayout);
        btnGras = (Button) findViewById(R.id.btnGras);
        btnItalique = (Button) findViewById(R.id.btnItalique);
        btnSouligne = (Button) findViewById(R.id.btnSouligne);
        btnCacher = (Button) findViewById(R.id.btnCacher);
        btnSourire = (Button) findViewById(R.id.smileysSourireButton);
        btnRire = (Button) findViewById(R.id.smileysRireButton);
        btnClinDOeil = (Button) findViewById(R.id.smileysClinOeilButton);
        colorRadioGroup = (RadioGroup) findViewById(R.id.couleurRadioGroup);
        radioNoir = (RadioButton) findViewById(R.id.noirRadioButton);
        radioBleu = (RadioButton) findViewById(R.id.bleuRadioButton);
        radioRouge = (RadioButton) findViewById(R.id.rougeRadioButton);
        edition = (EditText) findViewById(R.id.editionEditText);
        previsualisation = (TextView) findViewById(R.id.previsialisationTextView);
        imageGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String s) {
                int id = 0;
                if(s.equals("smiley_sourire.png")){
                    id = R.drawable.smiley_sourire;
                } else if(s.equals("smiley_rire.png")){
                    id = R.drawable.smiley_rire;
                } else if(s.equals("smiley_clin_oeil.png")){
                    id = R.drawable.smiley_clin_oeil;
                }
                LevelListDrawable d = new LevelListDrawable();
                Drawable empty = getResources().getDrawable(id);
                d.addLevel(0, 0, empty);
                // On délimite l'image (elle va de son coin en haut à gauche à son coin en bas à droite)
                d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
                return d;
            }
        };

        edition.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String text = ((SpannableStringBuilder) editable).toString();
                /*
                http://stackoverflow.com/questions/37899856/html-fromhtml-is-depricated-what-is-the-alternative
                Use Html.fromHtml(String) on all API levels, or,
                Use Html.fromHtml(String) on API Level 23 and older devices, and Html.fromHtml(String, int) on API Level 24+ devices, using Build.VERSION.SDK_INT to find out the API level of the device that you are running on
                 */
                Spanned fromHtml = Html.fromHtml(text, imageGetter, null);
                previsualisation.setText(fromHtml);
            }
        });
    }

    public void btnClicked(View view) {
        Log.i(this.getClass().getName(), "click sur " + view.getId());
        switch (view.getId()) {
            case R.id.btnCacher:
                if (btnCacher.getText().equals(getString(R.string.txtCacher))) {
                    editionLayout.setVisibility(View.GONE);
                    smileysLayout.setVisibility(View.GONE);
                    colorRadioGroup.setVisibility(View.GONE);
                    btnCacher.setText(getString(R.string.txtAfficher));
                } else {
                    editionLayout.setVisibility(View.VISIBLE);
                    smileysLayout.setVisibility(View.VISIBLE);
                    colorRadioGroup.setVisibility(View.VISIBLE);
                    btnCacher.setText(getString(R.string.txtCacher));
                }
                break;
            case R.id.btnGras:
                addTag("<b>", "</b>");
                break;
            case R.id.btnItalique:
                addTag("<i>", "</i>");
                break;
            case R.id.btnSouligne:
                addTag("<u>", "</u>");
                break;
            case R.id.smileysSourireButton:
                addImg("smiley_sourire.png");
                break;
            case R.id.smileysRireButton:
                addImg("smiley_rire.png");
                break;
            case R.id.smileysClinOeilButton:
                addImg("smiley_clin_oeil.png");
                break;
            default:
                Log.w(this.getClass().getName(), "L'id " + view.getId() + " n'est pas répertorié");
                break;
        }
    }

    private void addTag(String openTag, String closeTag) {
        StringBuilder text = new StringBuilder(edition.getText().toString().replaceAll("\\n", "<br/>"));
        int selectionStart = edition.getSelectionStart();
        int selectionEnd = edition.getSelectionEnd();
        text.insert(selectionStart, openTag);
        text.insert(selectionEnd + openTag.length(), closeTag);
        edition.setText(text);
    }

    private void addImg(String src) {
        StringBuilder text = new StringBuilder(edition.getText().toString().replaceAll("\\n", "<br/>"));
        int selectionStart = edition.getSelectionStart();
        text.insert(selectionStart, "<img src=\"" + src + "\">");
        edition.setText(text);
    }
}

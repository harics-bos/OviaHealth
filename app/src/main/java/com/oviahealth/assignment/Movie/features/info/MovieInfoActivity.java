package com.oviahealth.assignment.Movie.features.info;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.oviahealth.assignment.Movie.util.StringUtil;
import com.oviahealth.assignment.Movie.util.UIUtil;
import com.oviahealth.assignment.oviaapplication.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MovieInfoActivity extends AppCompatActivity implements IMovieInfoView {

    private IMovieUIEventListener _movieInfoPresenter = null;

    private ImageView _posterImage = null;
    private Button _searchButton = null;
    private RadioGroup _movieSearchChoiceRadio = null;
    private EditText _movieSearchEditText = null;
    private TextView _movieTitle = null;
    private RadioButton _titleSearchRadio = null;
    private RadioButton _imdbSearchRadio = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        _movieInfoPresenter = new MovieInfoPresenter(this, new MovieInfoModel());

        _posterImage = (ImageView) findViewById(R.id.movie_info_poster_image);
        _searchButton = (Button) findViewById(R.id.movie_info_search_button);
        _movieSearchChoiceRadio = (RadioGroup) findViewById(R.id.movie_info_choice_radioGroup);
        _movieSearchEditText = (EditText) findViewById(R.id.movie_info_search_input);
        _movieTitle = (TextView) findViewById(R.id.movie_info_title);
        _titleSearchRadio = (RadioButton) findViewById(R.id.movie_info_movie_radio);
        _imdbSearchRadio = (RadioButton) findViewById(R.id.movie_info_imdb_radio);

        _movieSearchChoiceRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                _movieInfoPresenter.onSearchTypeChange();
            }
        });

        _movieSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                _movieInfoPresenter.onSearchTextChange(editable.toString());
            }
        });

        _searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton selectedSerchChoicRadio = findViewById(_movieSearchChoiceRadio.getCheckedRadioButtonId());
                _movieInfoPresenter.processSearch(_movieSearchEditText.getText().toString(), selectedSerchChoicRadio.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //_movieInfoPresenter.releaseResources();
    }

    @Override
    public void setMovieSearchListener(IMovieUIEventListener movieSearchListener) {
        _movieInfoPresenter = movieSearchListener;
    }

    @Override
    public void enableSearch(boolean enable, int color) {
        _searchButton.setEnabled(enable);
        _searchButton.setBackgroundColor(color);
        _searchButton.setClickable(enable);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        _movieInfoPresenter.releaseResources();
    }

    @Override
    public void showMoviePoster(String imageUrl) {
        _posterImage.setVisibility(View.VISIBLE);
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.build().load(imageUrl).into(_posterImage, new Callback() {
            @Override
            public void onSuccess() {
                // do nothing
            }

            @Override
            public void onError() {
                //TODO: Show N/A image
                //_posterImage.setImageDrawable(null);
            }
        });
    }

    @Override
    public void showMovieTitle(String movieName) {
        _movieTitle.setAllCaps(true);
        _movieTitle.setText(movieName);
        _movieTitle.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayError(String errorTitle, String errorMessage) {
        AlertDialog errorDialog = UIUtil.createInfoDialog(errorTitle, errorMessage, this);
        errorDialog.show();

    }

    @Override
    public void clearSearchField() {
        _movieSearchEditText.setText("");
    }
}
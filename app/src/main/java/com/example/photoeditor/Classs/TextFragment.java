package com.example.photoeditor.Classs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.photoeditor.Adapter.FontAdapter;
import com.example.photoeditor.Adapter.ShadowAdapter;
import com.example.photoeditor.Adapter.TetxBackgroundAdapter;
import com.example.photoeditor.Adapter.TetxColorAdapter;
import com.example.photoeditor.R;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class TextFragment extends DialogFragment implements View.OnClickListener, FontAdapter.ItemClickListener, ShadowAdapter.ShadowItemClickListener, TetxColorAdapter.ColorListener, TetxBackgroundAdapter.BackgroundColorListener {
    public static final String EXTRA_COLOR_CODE = "extra_color_code";
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String TAG = "TextFragment";
    PolishEditText add_text_edit_text;
    CheckBox checkbox_background;
    public List<PolishCarouselPicker.PickerItem> colorItems;
    private FontAdapter fontAdapter;
    ImageView image_view_adjust;
    ImageView image_view_align_center;
    ImageView image_view_align_left;
    ImageView image_view_align_right;
    ImageView image_view_color;
    ImageView image_view_keyboard;
    ImageView image_view_save_change;
    private InputMethodManager inputMethodManager;
    ConstraintLayout linear_layout_edit_text_tools;
    LinearLayout linear_layout_preview;
    public PolishText polishText;
    public RecyclerView recycler_view_background;
    public RecyclerView recycler_view_color;
    RecyclerView recycler_view_fonts;
    RecyclerView recycler_view_shadow;
    RelativeLayout relativeLayoutBg;
    ScrollView scroll_view_change_font_adjust;
    LinearLayout scroll_view_change_font_layout;
    SeekBar seekbar_background_opacity;
    SeekBar seekbar_height;
    SeekBar seekbar_radius;
    SeekBar seekbar_text_size;
    SeekBar seekbar_width;
    private ShadowAdapter shadowAdapter;
    SeekBar textColorOpacity;
    private TextEditor textEditor;
    private List<ImageView> textFunctions;
    TextView textViewBg;
    TextView textViewColor;
    TextView textViewFont;
    TextView textViewSeekBarBackground;
    TextView textViewSeekBarColor;
    TextView textViewSeekBarHeight;
    TextView textViewSeekBarRadius;
    TextView textViewSeekBarSize;
    TextView textViewSeekBarWith;
    TextView textViewShadow;
    TextView text_view_preview_effect;

    /* loaded from: classes3.dex */
    public interface TextEditor {
        void onBackButton();

        void onDone(PolishText polishText);
    }

    @Override // com.example.photoareditor.Adapter.FontAdapter.ItemClickListener
    public void onItemClick(View view, int i) {
        FontFileAsset.setFontByName(requireContext(), this.text_view_preview_effect, FontFileAsset.getListFonts().get(i));
        this.polishText.setFontName(FontFileAsset.getListFonts().get(i));
        this.polishText.setFontIndex(i);
    }

    public static TextFragment show(AppCompatActivity appCompatActivity, String str, int i) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_INPUT_TEXT, str);
        bundle.putInt(EXTRA_COLOR_CODE, i);
        TextFragment textFragment = new TextFragment();
        textFragment.setArguments(bundle);
        textFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return textFragment;
    }

    public static TextFragment show(AppCompatActivity appCompatActivity, PolishText polishText2) {
        TextFragment textFragment = new TextFragment();
        textFragment.setPolishText(polishText2);
        textFragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return textFragment;
    }

    @Override // com.example.photoareditor.Adapter.ShadowAdapter.ShadowItemClickListener
    public void onShadowItemClick(View view, int i) {
        PolishText.TextShadow textShadow = PolishText.getLstTextShadow().get(i);
        this.text_view_preview_effect.setShadowLayer(textShadow.getRadius(), textShadow.getDx(), textShadow.getDy(), textShadow.getColorShadow());
        this.text_view_preview_effect.invalidate();
        this.polishText.setTextShadow(textShadow);
        this.polishText.setTextShadowIndex(i);
    }

    public static TextFragment show(AppCompatActivity appCompatActivity) {
        return show(appCompatActivity, "Test", ContextCompat.getColor(appCompatActivity, R.color.black));
    }

    public void setPolishText(PolishText polishText2) {
        this.polishText = polishText2;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        return layoutInflater.inflate(R.layout.fragment_add_text, viewGroup, false);
    }

    public void dismissAndShowSticker() {
        TextEditor textEditor2 = this.textEditor;
        if (textEditor2 != null) {
            textEditor2.onBackButton();
        }
        dismiss();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.polishText == null) {
            this.polishText = PolishText.getDefaultProperties();
        }
        this.add_text_edit_text = (PolishEditText) view.findViewById(R.id.add_text_edit_text);
        this.image_view_keyboard = (ImageView) view.findViewById(R.id.image_view_keyboard);
        this.image_view_color = (ImageView) view.findViewById(R.id.image_view_color);
        this.image_view_align_left = (ImageView) view.findViewById(R.id.imageViewAlignLeft);
        this.image_view_align_center = (ImageView) view.findViewById(R.id.imageViewAlignCenter);
        this.image_view_align_right = (ImageView) view.findViewById(R.id.imageViewAlignRight);
        this.textViewFont = (TextView) view.findViewById(R.id.textViewFont);
        this.textViewColor = (TextView) view.findViewById(R.id.textViewColor);
        this.textViewBg = (TextView) view.findViewById(R.id.textViewBg);
        this.textViewShadow = (TextView) view.findViewById(R.id.textViewShadow);
        this.relativeLayoutBg = (RelativeLayout) view.findViewById(R.id.relativeLayoutBg);
        this.textViewSeekBarSize = (TextView) view.findViewById(R.id.seekbarSize);
        this.textViewSeekBarColor = (TextView) view.findViewById(R.id.seekbarColor);
        this.textViewSeekBarBackground = (TextView) view.findViewById(R.id.seekbarBackground);
        this.textViewSeekBarRadius = (TextView) view.findViewById(R.id.seekbarRadius);
        this.textViewSeekBarWith = (TextView) view.findViewById(R.id.seekbarWith);
        this.textViewSeekBarHeight = (TextView) view.findViewById(R.id.seekbarHeight);
        this.image_view_adjust = (ImageView) view.findViewById(R.id.image_view_adjust);
        this.image_view_save_change = (ImageView) view.findViewById(R.id.image_view_save_change);
        this.scroll_view_change_font_layout = (LinearLayout) view.findViewById(R.id.scroll_view_change_font_layout);
        this.scroll_view_change_font_adjust = (ScrollView) view.findViewById(R.id.scroll_view_change_color_adjust);
        this.linear_layout_edit_text_tools = (ConstraintLayout) view.findViewById(R.id.linear_layout_edit_text_tools);
        this.recycler_view_fonts = (RecyclerView) view.findViewById(R.id.recycler_view_fonts);
        this.recycler_view_shadow = (RecyclerView) view.findViewById(R.id.recycler_view_shadow);
        this.textColorOpacity = (SeekBar) view.findViewById(R.id.seekbar_text_opacity);
        this.text_view_preview_effect = (TextView) view.findViewById(R.id.text_view_preview_effect);
        this.linear_layout_preview = (LinearLayout) view.findViewById(R.id.linear_layout_preview);
        this.checkbox_background = (CheckBox) view.findViewById(R.id.checkbox_background);
        this.seekbar_width = (SeekBar) view.findViewById(R.id.seekbar_width);
        this.seekbar_height = (SeekBar) view.findViewById(R.id.seekbar_height);
        this.seekbar_background_opacity = (SeekBar) view.findViewById(R.id.seekbar_background_opacity);
        this.seekbar_text_size = (SeekBar) view.findViewById(R.id.seekbar_text_size);
        this.seekbar_radius = (SeekBar) view.findViewById(R.id.seekbar_radius);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewColor);
        this.recycler_view_color = recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        this.recycler_view_color.setAdapter(new TetxColorAdapter(getContext(), this));
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerViewBg);
        this.recycler_view_background = recyclerView2;
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext(), 5));
        this.recycler_view_background.setAdapter(new TetxBackgroundAdapter(getContext(), this));
        this.add_text_edit_text.setTextFragment(this);
        initAddTextLayout();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        this.inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        setDefaultStyleForEdittext();
        this.inputMethodManager.toggleSoftInput(2, 0);
        highlightFunction(this.image_view_keyboard);
        this.recycler_view_fonts.setLayoutManager(new GridLayoutManager(getContext(), 5));
        FontAdapter fontAdapter2 = new FontAdapter(getContext(), FontFileAsset.getListFonts());
        this.fontAdapter = fontAdapter2;
        fontAdapter2.setClickListener(this);
        this.recycler_view_fonts.setAdapter(this.fontAdapter);
        this.recycler_view_shadow.setLayoutManager(new GridLayoutManager(getContext(), 5));
        ShadowAdapter shadowAdapter2 = new ShadowAdapter(getContext(), PolishText.getLstTextShadow());
        this.shadowAdapter = shadowAdapter2;
        shadowAdapter2.setClickListener(this);
        this.recycler_view_shadow.setAdapter(this.shadowAdapter);
        this.textColorOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Classs.TextFragment.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextFragment.this.textViewSeekBarColor.setText(String.valueOf(i));
                int i2 = 255 - i;
                TextFragment.this.polishText.setTextAlpha(i2);
                TextFragment.this.text_view_preview_effect.setTextColor(Color.argb(i2, Color.red(TextFragment.this.polishText.getTextColor()), Color.green(TextFragment.this.polishText.getTextColor()), Color.blue(TextFragment.this.polishText.getTextColor())));
            }
        });
        this.add_text_edit_text.addTextChangedListener(new TextWatcher() { // from class: com.example.photoareditor.Classs.TextFragment.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                TextFragment.this.text_view_preview_effect.setText(charSequence.toString());
                TextFragment.this.polishText.setText(charSequence.toString());
            }
        });
        this.checkbox_background.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.example.photoareditor.Classs.TextFragment.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (!z) {
                    TextFragment.this.polishText.setShowBackground(false);
                    TextFragment.this.text_view_preview_effect.setBackgroundResource(0);
                    TextFragment.this.text_view_preview_effect.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                } else if (!TextFragment.this.checkbox_background.isPressed() && !TextFragment.this.polishText.isShowBackground()) {
                    TextFragment.this.checkbox_background.setChecked(false);
                    TextFragment.this.polishText.setShowBackground(false);
                    TextFragment.this.initPreviewText();
                } else {
                    TextFragment.this.polishText.setShowBackground(true);
                    TextFragment.this.initPreviewText();
                }
            }
        });
        this.seekbar_width.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Classs.TextFragment.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextFragment.this.textViewSeekBarWith.setText(String.valueOf(i));
                TextFragment.this.text_view_preview_effect.setPadding(SystemUtil.dpToPx(TextFragment.this.requireContext(), i), TextFragment.this.text_view_preview_effect.getPaddingTop(), SystemUtil.dpToPx(TextFragment.this.getContext(), i), TextFragment.this.text_view_preview_effect.getPaddingBottom());
                TextFragment.this.polishText.setPaddingWidth(i);
            }
        });
        this.seekbar_height.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Classs.TextFragment.5
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextFragment.this.textViewSeekBarHeight.setText(String.valueOf(i));
                TextFragment.this.text_view_preview_effect.setPadding(TextFragment.this.text_view_preview_effect.getPaddingLeft(), SystemUtil.dpToPx(TextFragment.this.requireContext(), i), TextFragment.this.text_view_preview_effect.getPaddingRight(), SystemUtil.dpToPx(TextFragment.this.getContext(), i));
                TextFragment.this.polishText.setPaddingHeight(i);
            }
        });
        this.seekbar_background_opacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Classs.TextFragment.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextFragment.this.textViewSeekBarBackground.setText(String.valueOf(i));
                TextFragment.this.polishText.setBackgroundAlpha(255 - i);
                int red = Color.red(TextFragment.this.polishText.getBackgroundColor());
                int green = Color.green(TextFragment.this.polishText.getBackgroundColor());
                int blue = Color.blue(TextFragment.this.polishText.getBackgroundColor());
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(Color.argb(TextFragment.this.polishText.getBackgroundAlpha(), red, green, blue));
                gradientDrawable.setCornerRadius(SystemUtil.dpToPx(TextFragment.this.requireContext(), TextFragment.this.polishText.getBackgroundBorder()));
                TextFragment.this.text_view_preview_effect.setBackground(gradientDrawable);
            }
        });
        this.seekbar_text_size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Classs.TextFragment.7
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextFragment.this.textViewSeekBarSize.setText(String.valueOf(i));
                if (i < 15) {
                    i = 15;
                }
                TextFragment.this.text_view_preview_effect.setTextSize(i);
                TextFragment.this.polishText.setTextSize(i);
            }
        });
        this.seekbar_radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.example.photoareditor.Classs.TextFragment.8
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                TextFragment.this.textViewSeekBarRadius.setText(String.valueOf(i));
                TextFragment.this.polishText.setBackgroundBorder(i);
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius(SystemUtil.dpToPx(TextFragment.this.requireContext(), i));
                gradientDrawable.setColor(Color.argb(TextFragment.this.polishText.getBackgroundAlpha(), Color.red(TextFragment.this.polishText.getBackgroundColor()), Color.green(TextFragment.this.polishText.getBackgroundColor()), Color.blue(TextFragment.this.polishText.getBackgroundColor())));
                TextFragment.this.text_view_preview_effect.setBackground(gradientDrawable);
            }
        });
        if (Preference.getKeyboard(requireContext()) > 0) {
            updateAddTextBottomToolbarHeight(Preference.getKeyboard(getContext()));
        }
        initPreviewText();
    }

    @SuppressLint("WrongConstant")
    public void initPreviewText() {
        if (this.polishText.isShowBackground()) {
            if (this.polishText.getBackgroundColor() != 0) {
                this.text_view_preview_effect.setBackgroundColor(this.polishText.getBackgroundColor());
            }
            if (this.polishText.getBackgroundAlpha() < 255) {
                this.text_view_preview_effect.setBackgroundColor(Color.argb(this.polishText.getBackgroundAlpha(), Color.red(this.polishText.getBackgroundColor()), Color.green(this.polishText.getBackgroundColor()), Color.blue(this.polishText.getBackgroundColor())));
            }
            if (this.polishText.getBackgroundBorder() > 0) {
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius(SystemUtil.dpToPx(requireContext(), this.polishText.getBackgroundBorder()));
                gradientDrawable.setColor(Color.argb(this.polishText.getBackgroundAlpha(), Color.red(this.polishText.getBackgroundColor()), Color.green(this.polishText.getBackgroundColor()), Color.blue(this.polishText.getBackgroundColor())));
                this.text_view_preview_effect.setBackground(gradientDrawable);
            }
        }
        if (this.polishText.getPaddingHeight() > 0) {
            TextView textView = this.text_view_preview_effect;
            textView.setPadding(textView.getPaddingLeft(), this.polishText.getPaddingHeight(), this.text_view_preview_effect.getPaddingRight(), this.polishText.getPaddingHeight());
            this.seekbar_height.setProgress(this.polishText.getPaddingHeight());
        }
        if (this.polishText.getPaddingWidth() > 0) {
            this.text_view_preview_effect.setPadding(this.polishText.getPaddingWidth(), this.text_view_preview_effect.getPaddingTop(), this.polishText.getPaddingWidth(), this.text_view_preview_effect.getPaddingBottom());
            this.seekbar_width.setProgress(this.polishText.getPaddingWidth());
        }
        if (this.polishText.getText() != null) {
            this.text_view_preview_effect.setText(this.polishText.getText());
            this.add_text_edit_text.setText(this.polishText.getText());
        }
        if (this.polishText.getTextShader() != null) {
            this.text_view_preview_effect.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            this.text_view_preview_effect.getPaint().setShader(this.polishText.getTextShader());
        }
        if (this.polishText.getTextAlign() == 4) {
            this.image_view_align_center.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_center_select));
        } else if (this.polishText.getTextAlign() == 3) {
            this.image_view_align_right.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_right));
        } else if (this.polishText.getTextAlign() == 2) {
            this.image_view_align_left.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_left));
        }
        this.text_view_preview_effect.setPadding(SystemUtil.dpToPx(getContext(), this.polishText.getPaddingWidth()), this.text_view_preview_effect.getPaddingTop(), SystemUtil.dpToPx(getContext(), this.polishText.getPaddingWidth()), this.text_view_preview_effect.getPaddingBottom());
        this.text_view_preview_effect.setTextColor(this.polishText.getTextColor());
        this.text_view_preview_effect.setTextAlignment(this.polishText.getTextAlign());
        this.text_view_preview_effect.setTextSize(this.polishText.getTextSize());
        FontFileAsset.setFontByName(getContext(), this.text_view_preview_effect, this.polishText.getFontName());
        this.text_view_preview_effect.invalidate();
    }

    private void setDefaultStyleForEdittext() {
        this.add_text_edit_text.requestFocus();
        this.add_text_edit_text.setTextSize(20.0f);
        this.add_text_edit_text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.add_text_edit_text.setTextColor(Color.parseColor("#000000"));
    }

    private void initAddTextLayout() {
        this.textFunctions = getTextFunctions();
        this.image_view_keyboard.setOnClickListener(this);
        this.textViewFont.setOnClickListener(this);
        this.textViewColor.setOnClickListener(this);
        this.textViewBg.setOnClickListener(this);
        this.textViewShadow.setOnClickListener(this);
        this.image_view_adjust.setOnClickListener(this);
        this.image_view_color.setOnClickListener(this);
        this.image_view_save_change.setOnClickListener(this);
        this.image_view_align_left.setOnClickListener(this);
        this.image_view_align_center.setOnClickListener(this);
        this.image_view_align_right.setOnClickListener(this);
        this.scroll_view_change_font_layout.setVisibility(View.GONE);
        this.scroll_view_change_font_adjust.setVisibility(View.GONE);
        this.seekbar_width.setProgress(this.polishText.getPaddingWidth());
        this.colorItems = getColorItems();
    }

    @Override // com.example.photoareditor.Adapter.TetxColorAdapter.ColorListener
    public void onColorSelected(TetxColorAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.text_view_preview_effect.setTextColor(squareView.drawableId);
            this.polishText.setTextColor(squareView.drawableId);
            this.text_view_preview_effect.getPaint().setShader(null);
            this.polishText.setTextShader(null);
            return;
        }
        this.text_view_preview_effect.setTextColor(squareView.drawableId);
        this.polishText.setTextColor(squareView.drawableId);
        this.text_view_preview_effect.getPaint().setShader(null);
        this.polishText.setTextShader(null);
    }

    @Override // com.example.photoareditor.Adapter.TetxBackgroundAdapter.BackgroundColorListener
    public void onBackgroundColorSelected(TetxBackgroundAdapter.SquareView squareView) {
        if (squareView.isColor) {
            this.text_view_preview_effect.setBackgroundColor(squareView.drawableId);
            this.polishText.setBackgroundColor(squareView.drawableId);
            this.seekbar_radius.setEnabled(true);
            this.polishText.setShowBackground(true);
            if (!this.checkbox_background.isChecked()) {
                this.checkbox_background.setChecked(true);
            }
            int red = Color.red(this.polishText.getBackgroundColor());
            int green = Color.green(this.polishText.getBackgroundColor());
            int blue = Color.blue(this.polishText.getBackgroundColor());
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.argb(this.polishText.getBackgroundAlpha(), red, green, blue));
            gradientDrawable.setCornerRadius(SystemUtil.dpToPx(requireContext(), this.polishText.getBackgroundBorder()));
            this.text_view_preview_effect.setBackground(gradientDrawable);
            return;
        }
        this.text_view_preview_effect.setBackgroundColor(squareView.drawableId);
        this.polishText.setBackgroundColor(squareView.drawableId);
        this.seekbar_radius.setEnabled(true);
        this.polishText.setShowBackground(true);
        if (!this.checkbox_background.isChecked()) {
            this.checkbox_background.setChecked(true);
        }
        int red2 = Color.red(this.polishText.getBackgroundColor());
        int green2 = Color.green(this.polishText.getBackgroundColor());
        int blue2 = Color.blue(this.polishText.getBackgroundColor());
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(Color.argb(this.polishText.getBackgroundAlpha(), red2, green2, blue2));
        gradientDrawable2.setCornerRadius(SystemUtil.dpToPx(requireContext(), this.polishText.getBackgroundBorder()));
        this.text_view_preview_effect.setBackground(gradientDrawable2);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        ViewCompat.setOnApplyWindowInsetsListener(getDialog().getWindow().getDecorView(), new OnApplyWindowInsetsListener() { // from class: com.example.photoareditor.Classs.TextFragment.9
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return ViewCompat.onApplyWindowInsets(TextFragment.this.getDialog().getWindow().getDecorView(), windowInsetsCompat.inset(windowInsetsCompat.getSystemWindowInsetLeft(), 0, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom()));
            }
        });
    }

    public void updateAddTextBottomToolbarHeight(final int i) {
        new Handler().post(new Runnable() { // from class: com.example.photoareditor.Classs.TextFragment.10
            @Override // java.lang.Runnable
            public void run() {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) TextFragment.this.linear_layout_edit_text_tools.getLayoutParams();
                layoutParams.bottomMargin = i;
                TextFragment.this.linear_layout_edit_text_tools.setLayoutParams(layoutParams);
                TextFragment.this.linear_layout_edit_text_tools.invalidate();
                TextFragment.this.scroll_view_change_font_layout.invalidate();
                TextFragment.this.scroll_view_change_font_adjust.invalidate();
                Log.i("HIHIH", i + "");
            }
        });
    }

    public void setOnTextEditorListener(TextEditor textEditor2) {
        this.textEditor = textEditor2;
    }

    private void highlightFunction(ImageView imageView) {
        for (ImageView imageView2 : this.textFunctions) {
            if (imageView2 == imageView) {
                imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.line));
            } else {
                imageView2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.line_fake));
            }
        }
    }

    @SuppressLint("WrongConstant")
    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewAlignCenter /* 2131362224 */:
                if (this.polishText.getTextAlign() == 2 || this.polishText.getTextAlign() == 3) {
                    this.polishText.setTextAlign(4);
                    this.image_view_align_center.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_center_select));
                    this.image_view_align_left.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_left));
                    this.image_view_align_right.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_right));
                }
                this.text_view_preview_effect.setTextAlignment(this.polishText.getTextAlign());
                TextView textView = this.text_view_preview_effect;
                textView.setText(this.text_view_preview_effect.getText().toString().trim() + " ");
                TextView textView2 = this.text_view_preview_effect;
                textView2.setText(textView2.getText().toString().trim());
                return;
            case R.id.imageViewAlignLeft /* 2131362225 */:
                if (this.polishText.getTextAlign() == 3 || this.polishText.getTextAlign() == 4) {
                    this.polishText.setTextAlign(2);
                    this.image_view_align_left.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_left_select));
                    this.image_view_align_center.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_center));
                    this.image_view_align_right.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_right));
                }
                this.text_view_preview_effect.setTextAlignment(this.polishText.getTextAlign());
                TextView textView3 = this.text_view_preview_effect;
                textView3.setText(this.text_view_preview_effect.getText().toString().trim() + " ");
                TextView textView4 = this.text_view_preview_effect;
                textView4.setText(textView4.getText().toString().trim());
                return;
            case R.id.imageViewAlignRight /* 2131362226 */:
                if (this.polishText.getTextAlign() == 4 || this.polishText.getTextAlign() == 2) {
                    this.polishText.setTextAlign(3);
                    this.image_view_align_left.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_left));
                    this.image_view_align_center.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_center));
                    this.image_view_align_right.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_align_right_select));
                }
                this.text_view_preview_effect.setTextAlignment(this.polishText.getTextAlign());
                TextView textView5 = this.text_view_preview_effect;
                textView5.setText(this.text_view_preview_effect.getText().toString().trim() + " ");
                TextView textView6 = this.text_view_preview_effect;
                textView6.setText(textView6.getText().toString().trim());
                return;
            case R.id.image_view_adjust /* 2131362280 */:
                this.image_view_keyboard.setBackgroundResource(R.drawable.background_unslelected);
                this.image_view_adjust.setBackgroundResource(R.drawable.background_selected_color);
                this.image_view_color.setBackgroundResource(R.drawable.background_unslelected);
                this.image_view_keyboard.setColorFilter(getResources().getColor(R.color.black));
                this.image_view_adjust.setColorFilter(getResources().getColor(R.color.mainColor));
                this.image_view_color.setColorFilter(getResources().getColor(R.color.black));
                this.inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                this.scroll_view_change_font_adjust.setVisibility(0);
                this.scroll_view_change_font_layout.setVisibility(8);
                this.seekbar_background_opacity.setProgress(255 - this.polishText.getBackgroundAlpha());
                this.seekbar_text_size.setProgress(this.polishText.getTextSize());
                this.seekbar_radius.setProgress(this.polishText.getBackgroundBorder());
                this.seekbar_width.setProgress(this.polishText.getPaddingWidth());
                this.seekbar_height.setProgress(this.polishText.getPaddingHeight());
                this.textColorOpacity.setProgress(255 - this.polishText.getTextAlpha());
                toggleTextEditEditable(false);
                highlightFunction(this.image_view_adjust);
                return;
            case R.id.image_view_color /* 2131362289 */:
                this.image_view_keyboard.setBackgroundResource(R.drawable.background_unslelected);
                this.image_view_adjust.setBackgroundResource(R.drawable.background_unslelected);
                this.image_view_color.setBackgroundResource(R.drawable.background_selected_color);
                this.image_view_keyboard.setColorFilter(getResources().getColor(R.color.black));
                this.image_view_adjust.setColorFilter(getResources().getColor(R.color.black));
                this.image_view_color.setColorFilter(getResources().getColor(R.color.mainColor));
                this.inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                this.scroll_view_change_font_layout.setVisibility(0);
                this.scroll_view_change_font_adjust.setVisibility(8);
                toggleTextEditEditable(false);
                highlightFunction(this.image_view_color);
                this.add_text_edit_text.setVisibility(8);
                this.shadowAdapter.setSelectedItem(this.polishText.getFontIndex());
                this.fontAdapter.setSelectedItem(this.polishText.getFontIndex());
                this.checkbox_background.setChecked(this.polishText.isShowBackground());
                this.checkbox_background.setChecked(this.polishText.isShowBackground());
                return;
            case R.id.image_view_keyboard /* 2131362298 */:
                this.image_view_keyboard.setBackgroundResource(R.drawable.background_selected_color);
                this.image_view_adjust.setBackgroundResource(R.drawable.background_unslelected);
                this.image_view_color.setBackgroundResource(R.drawable.background_unslelected);
                this.image_view_keyboard.setColorFilter(getResources().getColor(R.color.mainColor));
                this.image_view_adjust.setColorFilter(getResources().getColor(R.color.black));
                this.image_view_color.setColorFilter(getResources().getColor(R.color.black));
                toggleTextEditEditable(true);
                this.add_text_edit_text.setVisibility(0);
                this.add_text_edit_text.requestFocus();
                highlightFunction(this.image_view_keyboard);
                this.scroll_view_change_font_layout.setVisibility(8);
                this.scroll_view_change_font_adjust.setVisibility(8);
                this.linear_layout_edit_text_tools.invalidate();
                this.inputMethodManager.toggleSoftInput(2, 0);
                return;
            case R.id.image_view_save_change /* 2131362305 */:
                if (this.polishText.getText() != null && this.polishText.getText().length() != 0) {
                    this.polishText.setTextWidth(this.text_view_preview_effect.getMeasuredWidth());
                    this.polishText.setTextHeight(this.text_view_preview_effect.getMeasuredHeight());
                    this.inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    this.textEditor.onDone(this.polishText);
                    dismiss();
                    return;
                }
                this.inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                this.textEditor.onBackButton();
                dismiss();
                return;
            case R.id.textViewBg /* 2131362850 */:
                this.textViewFont.setTextColor(getResources().getColor(R.color.black));
                this.textViewFont.setBackgroundResource(R.drawable.background_unslelected);
                this.textViewBg.setTextColor(getResources().getColor(R.color.white));
                this.textViewBg.setBackgroundResource(R.drawable.background_selected);
                this.textViewColor.setTextColor(getResources().getColor(R.color.black));
                this.textViewColor.setBackgroundResource(R.drawable.background_unslelected);
                this.textViewShadow.setTextColor(getResources().getColor(R.color.black));
                this.textViewShadow.setBackgroundResource(R.drawable.background_unslelected);
                this.recycler_view_fonts.setVisibility(8);
                this.recycler_view_shadow.setVisibility(8);
                this.recycler_view_color.setVisibility(8);
                this.relativeLayoutBg.setVisibility(0);
                return;
            case R.id.textViewColor /* 2131362858 */:
                this.textViewFont.setTextColor(getResources().getColor(R.color.black));
                this.textViewFont.setBackgroundResource(R.drawable.background_unslelected);
                this.textViewColor.setTextColor(getResources().getColor(R.color.white));
                this.textViewColor.setBackgroundResource(R.drawable.background_selected);
                this.textViewShadow.setTextColor(getResources().getColor(R.color.black));
                this.textViewShadow.setBackgroundResource(R.drawable.background_unslelected);
                this.textViewBg.setTextColor(getResources().getColor(R.color.black));
                this.textViewBg.setBackgroundResource(R.drawable.background_unslelected);
                this.recycler_view_fonts.setVisibility(8);
                this.recycler_view_shadow.setVisibility(8);
                this.recycler_view_color.setVisibility(0);
                this.relativeLayoutBg.setVisibility(8);
                return;
            case R.id.textViewFont /* 2131362868 */:
                this.textViewFont.setTextColor(getResources().getColor(R.color.white));
                this.textViewFont.setBackgroundResource(R.drawable.background_selected);
                this.textViewShadow.setTextColor(getResources().getColor(R.color.black));
                this.textViewShadow.setBackgroundResource(R.drawable.background_unslelected);
                this.textViewColor.setTextColor(getResources().getColor(R.color.black));
                this.textViewColor.setBackgroundResource(R.drawable.background_unslelected);
                this.textViewBg.setTextColor(getResources().getColor(R.color.black));
                this.textViewBg.setBackgroundResource(R.drawable.background_unslelected);
                this.recycler_view_fonts.setVisibility(0);
                this.recycler_view_shadow.setVisibility(8);
                this.recycler_view_color.setVisibility(8);
                this.relativeLayoutBg.setVisibility(8);
                return;
            case R.id.textViewShadow /* 2131362879 */:
                this.textViewFont.setTextColor(getResources().getColor(R.color.black));
                this.textViewFont.setBackgroundResource(R.drawable.background_unslelected);
                this.textViewShadow.setTextColor(getResources().getColor(R.color.white));
                this.textViewShadow.setBackgroundResource(R.drawable.background_selected);
                this.textViewColor.setTextColor(getResources().getColor(R.color.black));
                this.textViewColor.setBackgroundResource(R.drawable.background_unslelected);
                this.textViewBg.setTextColor(getResources().getColor(R.color.black));
                this.textViewBg.setBackgroundResource(R.drawable.background_unslelected);
                this.recycler_view_fonts.setVisibility(8);
                this.recycler_view_shadow.setVisibility(0);
                this.recycler_view_color.setVisibility(8);
                this.relativeLayoutBg.setVisibility(8);
                return;
            default:
                return;
        }
    }

    private void toggleTextEditEditable(boolean z) {
        this.add_text_edit_text.setFocusable(z);
        this.add_text_edit_text.setFocusableInTouchMode(z);
        this.add_text_edit_text.setClickable(z);
    }

    private List<ImageView> getTextFunctions() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.image_view_keyboard);
        arrayList.add(this.image_view_color);
        arrayList.add(this.image_view_adjust);
        arrayList.add(this.image_view_save_change);
        return arrayList;
    }

    public List<PolishCarouselPicker.PickerItem> getColorItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PolishCarouselPicker.ColorItem("#FFFFFF"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#BBBBBB"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#4E4E4E"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#212121"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#000000"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FFD7CD"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#F9AB9D"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#EC5C60"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#CB3243"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#CD181F"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FF0000"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FFF2CA"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FDE472"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#F3AF59"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FC7F3D"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#ED3F0F"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FFF1F1"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FFE1E4"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FFA4B9"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FF679F"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FB2C78"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#E7D5E7"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#D3A6D8"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#BA66AF"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#A53B8E"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#65218C"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#99D2F9"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#81ADEA"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#2961A9"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#0E2E89"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#171982"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#A5E7F6"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#7CE3FF"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#00B0D0"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#058BC0"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#08447E"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#DEEEE9"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#B3D0C5"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#4DAF9D"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#21887C"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#0F664E"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#D3E5A6"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#AACE87"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#A3AF38"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#6D822B"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#366131"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#E4D9C0"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#D6C392"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#A3815A"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#72462F"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#3E3129"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#A7A7A7"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#995452"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#E1D6D7"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#CCBFD4"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#A67C80"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#D4D3CF"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#7A7185"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#6E5055"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#666563"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#F0E4FB"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#EED0D5"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#E1CCD1"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#C3C9DF"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#929493"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#807D5C"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#E2E4E1"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#8596A4"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#ECECEC"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#96A48C"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#AFB0B2"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#BEC0BF"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#B7C5B3"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#A0A7BA"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FFFAF7"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#7D8970"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FF7063"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FF3274"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#3EA2D7"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FFA258"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#F22D52"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#F2A0B6"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#7285F2"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#3ADB7C"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#F2C4CD"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#129AEF"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#0278A6"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#0D8C39"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#5639A5"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#A482F4"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#4AE3D2"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#00D28E"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FA4848"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#434882"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#730068"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#729D38"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#C6E377"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#022D3C"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#900C3F"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FF5733"));
        arrayList.add(new PolishCarouselPicker.ColorItem("#FFC300"));
        return arrayList;
    }
}

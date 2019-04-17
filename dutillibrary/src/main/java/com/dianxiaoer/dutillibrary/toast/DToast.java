package com.dianxiaoer.dutillibrary.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dianxiaoer.dutillibrary.R;

/**
 * Created by dianxiaoer on 2018/11/2.
 *
 * toast 工具类
 *  https://github.com/GrenderG/Toasty
 */

public class DToast {

    @ColorInt
    private static int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
    @ColorInt
    private static int ERROR_COLOR = Color.parseColor("#D50000");
    @ColorInt
    private static int INFO_COLOR = Color.parseColor("#3F51B5");
    @ColorInt
    private static int SUCCESS_COLOR = Color.parseColor("#388E3C");
    @ColorInt
    private static int WARNING_COLOR = Color.parseColor("#FFA900");
    @ColorInt
    private static int NORMAL_COLOR = Color.parseColor("#353A3E");

    private static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
    private static Typeface currentTypeface = LOADED_TOAST_TYPEFACE;
    private static int textSize = 16; // in SP

    private static boolean tintIcon = true;
    private static Toast currentToast;
    private static View toastLayout;

    private DToast() {
        // avoiding instantiation
    }

    
    public static void normal(@NonNull Context context, @StringRes int message) {
         normal(context, context.getString(message), Toast.LENGTH_SHORT, null, false);
    }

    
    public static void normal(@NonNull Context context, @NonNull CharSequence message) {
         normal(context, message, Toast.LENGTH_SHORT, null, false);
    }

    
    public static void normal(@NonNull Context context, @StringRes int message, Drawable icon) {
         normal(context, context.getString(message), Toast.LENGTH_SHORT, icon, true);
    }

    
    public static void normal(@NonNull Context context, @NonNull CharSequence message, Drawable icon) {
         normal(context, message, Toast.LENGTH_SHORT, icon, true);
    }

    
    public static void normal(@NonNull Context context, @StringRes int message, int duration) {
         normal(context, context.getString(message), duration, null, false);
    }

    
    public static void normal(@NonNull Context context, @NonNull CharSequence message, int duration) {
         normal(context, message, duration, null, false);
    }

    
    public static void normal(@NonNull Context context, @StringRes int message, int duration,
                               Drawable icon) {
         normal(context, context.getString(message), duration, icon, true);
    }

    
    public static void normal(@NonNull Context context, @NonNull CharSequence message, int duration,
                               Drawable icon) {
         normal(context, message, duration, icon, true);
    }


    public static void normal(@NonNull Context context, @StringRes int message, int duration,
                               Drawable icon, boolean withIcon) {
         custom(context, context.getString(message), icon, NORMAL_COLOR, duration, withIcon, true);
    }


    public static void normal(@NonNull Context context, @NonNull CharSequence message, int duration,
                               Drawable icon, boolean withIcon) {
         custom(context, message, icon, NORMAL_COLOR, duration, withIcon, true);
    }


    public static void warning(@NonNull Context context, @StringRes int message) {
         warning(context, context.getString(message), Toast.LENGTH_SHORT, true);
    }


    public static void warning(@NonNull Context context, @NonNull CharSequence message) {
         warning(context, message, Toast.LENGTH_SHORT, true);
    }


    public static void warning(@NonNull Context context, @StringRes int message, int duration) {
         warning(context, context.getString(message), duration, true);
    }


    public static void warning(@NonNull Context context, @NonNull CharSequence message, int duration) {
         warning(context, message, duration, true);
    }


    public static void warning(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
         custom(context, context.getString(message), ToastU.getDrawable(context, R.drawable.ic_error_outline_white_48dp),
                WARNING_COLOR, duration, withIcon, true);
    }


    public static void warning(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
         custom(context, message, ToastU.getDrawable(context, R.drawable.ic_error_outline_white_48dp),
                WARNING_COLOR, duration, withIcon, true);
    }


    public static void info(@NonNull Context context, @StringRes int message) {
         info(context, context.getString(message), Toast.LENGTH_SHORT, true);
    }


    public static void info(@NonNull Context context, @NonNull CharSequence message) {
         info(context, message, Toast.LENGTH_SHORT, true);
    }


    public static void info(@NonNull Context context, @StringRes int message, int duration) {
         info(context, context.getString(message), duration, true);
    }


    public static void info(@NonNull Context context, @NonNull CharSequence message, int duration) {
         info(context, message, duration, true);
    }


    public static void info(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
         custom(context, context.getString(message), ToastU.getDrawable(context, R.drawable.ic_info_outline_white_48dp),
                INFO_COLOR, duration, withIcon, true);
    }


    public static void info(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
         custom(context, message, ToastU.getDrawable(context, R.drawable.ic_info_outline_white_48dp),
                INFO_COLOR, duration, withIcon, true);
    }


    public static void success(@NonNull Context context, @StringRes int message) {
         success(context, context.getString(message), Toast.LENGTH_SHORT, true);
    }


    public static void success(@NonNull Context context, @NonNull CharSequence message) {
         success(context, message, Toast.LENGTH_SHORT, true);
    }


    public static void success(@NonNull Context context, @StringRes int message, int duration) {
         success(context, context.getString(message), duration, true);
    }


    public static void success(@NonNull Context context, @NonNull CharSequence message, int duration) {
         success(context, message, duration, true);
    }


    public static void success(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
         custom(context, context.getString(message), ToastU.getDrawable(context, R.drawable.ic_check_white_48dp),
                SUCCESS_COLOR, duration, withIcon, true);
    }


    public static void success(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
         custom(context, message, ToastU.getDrawable(context, R.drawable.ic_check_white_48dp),
                SUCCESS_COLOR, duration, withIcon, true);
    }


    public static void error(@NonNull Context context, @StringRes int message) {
         error(context, context.getString(message), Toast.LENGTH_SHORT, true);
    }


    public static void error(@NonNull Context context, @NonNull CharSequence message) {
         error(context, message, Toast.LENGTH_SHORT, true);
    }


    public static void error(@NonNull Context context, @StringRes int message, int duration) {
         error(context, context.getString(message), duration, true);
    }


    public static void error(@NonNull Context context, @NonNull CharSequence message, int duration) {
         error(context, message, duration, true);
    }


    public static void error(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
         custom(context, context.getString(message), ToastU.getDrawable(context, R.drawable.ic_clear_white_48dp),
                ERROR_COLOR, duration, withIcon, true);
    }


    public static void error(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
         custom(context, message, ToastU.getDrawable(context, R.drawable.ic_clear_white_48dp),
                ERROR_COLOR, duration, withIcon, true);
    }


    public static void custom(@NonNull Context context, @StringRes int message, Drawable icon,
                               int duration, boolean withIcon) {
         custom(context, context.getString(message), icon, -1, duration, withIcon, false);
    }


    public static void custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon,
                               int duration, boolean withIcon) {
         custom(context, message, icon, -1, duration, withIcon, false);
    }


    public static void custom(@NonNull Context context, @StringRes int message, @DrawableRes int iconRes,
                               @ColorInt int tintColor, int duration,
                               boolean withIcon, boolean shouldTint) {
         custom(context, context.getString(message), ToastU.getDrawable(context, iconRes),
                tintColor, duration, withIcon, shouldTint);
    }


    public static void custom(@NonNull Context context, @NonNull CharSequence message, @DrawableRes int iconRes,
                               @ColorInt int tintColor, int duration,
                               boolean withIcon, boolean shouldTint) {
         custom(context, message, ToastU.getDrawable(context, iconRes),
                tintColor, duration, withIcon, shouldTint);
    }

    public static void custom(@NonNull Context context, @StringRes int message, Drawable icon,
                               @ColorInt int tintColor, int duration,
                               boolean withIcon, boolean shouldTint) {
         custom(context, context.getString(message), icon, tintColor, duration,
                withIcon, shouldTint);
    }

    private static CharSequence oldMsg;
    private static long tweTime;
    private static long oneTime;

    public static void custom(@NonNull Context context,
                               @NonNull CharSequence message, Drawable icon,
                               @ColorInt int tintColor, int duration,
                               boolean withIcon, boolean shouldTint) {
        if (currentToast == null) {

            currentToast = Toast.makeText(context, "", duration);
            toastLayout = BulidToast(context,message,icon,tintColor,withIcon,shouldTint,duration);
            currentToast.setView(toastLayout);
            currentToast.show();
            oldMsg = message;
            oneTime = System.currentTimeMillis();
        }else {
            tweTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (tweTime - oneTime > Toast.LENGTH_SHORT) {
                    currentToast.show();
                }
            }else {
                oldMsg = message;
                currentToast = Toast.makeText(context, "", duration);
                toastLayout = BulidToast(context,message,icon,tintColor,withIcon,shouldTint,duration);
//                TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
//                toastTextView.setText(message);

                currentToast.setView(toastLayout);
                currentToast.show();
            }
        }
        oneTime = tweTime;
    }


    private static View BulidToast(@NonNull Context context, @NonNull CharSequence message,
                                    Drawable icon, @ColorInt int tintColor,
                                    boolean withIcon, boolean shouldTint,int duration) {


         View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.view_d_toast, null);
         ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
         TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;

        if (shouldTint)
            drawableFrame = ToastU.tint9PatchDrawableFrame(context, tintColor);
        else
            drawableFrame = ToastU.getDrawable(context, R.drawable.toast_frame);
        ToastU.setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null)
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            if (tintIcon)
                icon = ToastU.tintIcon(icon, DEFAULT_TEXT_COLOR);
            ToastU.setBackground(toastIcon, icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setText(message);
        toastTextView.setTextColor(DEFAULT_TEXT_COLOR);
        toastTextView.setTypeface(currentTypeface);
        toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);


        return toastLayout;
    }

    public static class Config {
        @ColorInt
        private int DEFAULT_TEXT_COLOR = DToast.DEFAULT_TEXT_COLOR;
        @ColorInt
        private int ERROR_COLOR = DToast.ERROR_COLOR;
        @ColorInt
        private int INFO_COLOR = DToast.INFO_COLOR;
        @ColorInt
        private int SUCCESS_COLOR = DToast.SUCCESS_COLOR;
        @ColorInt
        private int WARNING_COLOR = DToast.WARNING_COLOR;

        private Typeface typeface = DToast.currentTypeface;
        private int textSize = DToast.textSize;

        private boolean tintIcon = DToast.tintIcon;

        private Config() {
            // avoiding instantiation
        }


        public static Config getInstance() {
            return new Config();
        }

        public static void reset() {
            DToast.DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
            DToast.ERROR_COLOR = Color.parseColor("#D50000");
            DToast.INFO_COLOR = Color.parseColor("#3F51B5");
            DToast.SUCCESS_COLOR = Color.parseColor("#388E3C");
            DToast.WARNING_COLOR = Color.parseColor("#FFA900");
            DToast.currentTypeface = LOADED_TOAST_TYPEFACE;
            DToast.textSize = 16;
            DToast.tintIcon = true;
        }


        public Config setTextColor(@ColorInt int textColor) {
            DEFAULT_TEXT_COLOR = textColor;
            return this;
        }


        public Config setErrorColor(@ColorInt int errorColor) {
            ERROR_COLOR = errorColor;
            return this;
        }


        public Config setInfoColor(@ColorInt int infoColor) {
            INFO_COLOR = infoColor;
            return this;
        }


        public Config setSuccessColor(@ColorInt int successColor) {
            SUCCESS_COLOR = successColor;
            return this;
        }


        public Config setWarningColor(@ColorInt int warningColor) {
            WARNING_COLOR = warningColor;
            return this;
        }


        public Config setToastTypeface(@NonNull Typeface typeface) {
            this.typeface = typeface;
            return this;
        }


        public Config setTextSize(int sizeInSp) {
            this.textSize = sizeInSp;
            return this;
        }


        public Config tintIcon(boolean tintIcon) {
            this.tintIcon = tintIcon;
            return this;
        }

        public void apply() {
            DToast.DEFAULT_TEXT_COLOR = DEFAULT_TEXT_COLOR;
            DToast.ERROR_COLOR = ERROR_COLOR;
            DToast.INFO_COLOR = INFO_COLOR;
            DToast.SUCCESS_COLOR = SUCCESS_COLOR;
            DToast.WARNING_COLOR = WARNING_COLOR;
            DToast.currentTypeface = typeface;
            DToast.textSize = textSize;
            DToast.tintIcon = tintIcon;
        }
    }
}

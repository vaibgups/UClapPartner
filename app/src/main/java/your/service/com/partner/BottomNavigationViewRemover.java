package your.service.com.partner;

import android.annotation.SuppressLint;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by Ajit Singh on 23-03-2018.
 */

public class BottomNavigationViewRemover {


    @SuppressLint("RestrictedApi")
    public static void removeShifProperty(BottomNavigationView view)
    {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);

        try
        {
            Field shiftmode=menuView.getClass().getDeclaredField("mShiftingMode");
            shiftmode.setAccessible(true);
            shiftmode.setBoolean(menuView,false);
            shiftmode.setAccessible(false);
            for (int i= 0;i<menuView.getChildCount();i++)
            {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
//                itemView.setShiftingMode(false);
                itemView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
                itemView.setChecked(itemView.getItemData().isChecked());
               // menuView.buildMenuView();
            }
        }
        catch (NoSuchFieldException e)
        {
            Log.e("Exception 1", "Unable to get shift mode field");
        } catch (IllegalAccessException e)
        {
            Log.e("Exception 2", "Unable to change value of shift mode");
        }
    }
}

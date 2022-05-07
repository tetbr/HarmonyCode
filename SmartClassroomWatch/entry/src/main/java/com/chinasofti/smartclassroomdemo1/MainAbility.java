package com.chinasofti.smartclassroomdemo1;

import com.chinasofti.smartclassroomdemo1.slice.MainAbilitySlice;
import com.ibm.mqtt.*;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.Date;

public class MainAbility extends Ability {


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());

    }


}

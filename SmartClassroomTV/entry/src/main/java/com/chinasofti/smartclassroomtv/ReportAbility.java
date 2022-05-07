package com.chinasofti.smartclassroomtv;

import com.chinasofti.smartclassroomtv.slice.ReportAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ReportAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ReportAbilitySlice.class.getName());
    }
}

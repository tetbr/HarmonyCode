package com.chinasofti.smartclassroomtv;

import com.chinasofti.smartclassroomtv.slice.ShowQuestionAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ShowQuestionAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ShowQuestionAbilitySlice.class.getName());
    }
}

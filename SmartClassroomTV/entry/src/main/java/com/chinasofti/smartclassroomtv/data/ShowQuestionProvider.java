package com.chinasofti.smartclassroomtv.data;

import com.chinasofti.smartclassroomtv.ResourceTable;

import com.chinasofti.smartclassroomtv.demain.Question;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;

import java.util.List;


public class ShowQuestionProvider extends BaseItemProvider {
    private List<Question> mListInfo;
    private AbilitySlice mSlice;
    private LayoutScatter mLayoutScatter;

    public ShowQuestionProvider(List<Question> mListInfo, AbilitySlice slice) {
        this.mListInfo = mListInfo;
        this.mSlice = slice;
        this.mLayoutScatter = LayoutScatter.getInstance(mSlice);
    }
    @Override
    public int getCount() {
        return mListInfo.size();
    }

    public Question getItem(int position) {
        return mListInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component component, ComponentContainer componentContainer) {
        Question question = (Question) getItem(position);

        if (component != null) {
            return component;
        }

        Component newComponent = mLayoutScatter.parse(ResourceTable.Layout_actionbar_item, null, false);
        Text titleText = (Text) newComponent.findComponentById(ResourceTable.Id_t_ti);

        String title = question.getqNum()+". "+question.getqStem();
        titleText.setText(title);

        return newComponent;
    }

}

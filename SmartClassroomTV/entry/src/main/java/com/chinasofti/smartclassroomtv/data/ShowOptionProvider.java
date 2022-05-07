package com.chinasofti.smartclassroomtv.data;


import com.chinasofti.smartclassroomtv.ResourceTable;
import com.chinasofti.smartclassroomtv.demain.QOption;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;

import java.util.List;

public class ShowOptionProvider extends RecycleItemProvider {
    private List<QOption> mListInfo;
    private AbilitySlice mSlice;
    private LayoutScatter mLayoutScatter;

    public ShowOptionProvider(List<QOption> mListInfo, AbilitySlice slice) {
        this.mListInfo = mListInfo;
        this.mSlice = slice;
        this.mLayoutScatter = LayoutScatter.getInstance(mSlice);
    }
    @Override
    public int getCount() {
        return mListInfo.size();
    }

    public QOption getItem(int position) {
        return mListInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component component, ComponentContainer componentContainer) {
        QOption qOption = (QOption) getItem(position);

        if (component != null) {
            return component;
        }

        Component newComponent = mLayoutScatter.parse(ResourceTable.Layout_show_question_item, null, false);
        Text titleText = (Text) newComponent.findComponentById(ResourceTable.Id_t_ti);

        String title = qOption.getContent();
        titleText.setText(title);

        return newComponent;
    }

    public void setmListInfo(List<QOption> mListInfo) {
        this.mListInfo = mListInfo;

    }
}

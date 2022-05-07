package com.chinasofti.smartclassroomdemo1.data;


import com.chinasofti.smartclassroomdemo1.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;

import java.util.List;

public class RecycleSimpleItemProvider extends RecycleItemProvider {
    private List<QOption> mListInfo;
    private AbilitySlice mSlice;
    private LayoutScatter mLayoutScatter;
    Component newComponent = null;
    public RecycleSimpleItemProvider(List<QOption> mListInfo, AbilitySlice slice) {
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
        QOption info = (QOption) getItem(position);

//        if (component != null) {
//            return component;
//        }
        if (info.isSelect()) {
            newComponent = mLayoutScatter.parse(ResourceTable.Layout_actionbar_item_selected, null, false);

        }else {
            newComponent = mLayoutScatter.parse(ResourceTable.Layout_actionbar_item, null, false);
        }
        Text titleText = (Text) newComponent.findComponentById(ResourceTable.Id_list_text_left);

        titleText.setText(info.getContent());
        return newComponent;
    }

    public void setmListInfo(List<QOption> mListInfo) {
        this.mListInfo = mListInfo;

    }

    public void clearData() {
        mListInfo.clear();

    }
}
